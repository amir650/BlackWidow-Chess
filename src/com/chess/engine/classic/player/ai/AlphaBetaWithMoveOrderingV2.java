package com.chess.engine.classic.player.ai;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Observable;

import com.chess.engine.classic.Alliance;
import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.board.BoardUtils;
import com.chess.engine.classic.board.Move;
import com.chess.engine.classic.board.MoveTransition;
import com.chess.engine.classic.player.Player;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;

public class AlphaBetaWithMoveOrderingV2 extends Observable implements MoveStrategy {

    private final BoardEvaluator evaluator;
    private final MoveSorter moveSorter;
    private final int quiescenceFactor;
    private long boardsEvaluated;
    private long executionTime;
    private int quiescenceCount;
    private int cutOffsProduced;

    private enum MoveSorter {

        SORT {
            @Override
            Collection<Move> sort(final Collection<Move> moves) {
                return Ordering.from(ATTACKS_SORT).immutableSortedCopy(moves);
            }
        };

        public static Comparator<Move> ATTACKS_SORT = new Comparator<Move>() {
            @Override
            public int compare(final Move move1, final Move move2) {
                return ComparisonChain.start()
                        .compare(move2.isAttack(), move1.isAttack())
                        .compare(move1.getMovedPiece().getPieceValue(), move2.getMovedPiece().getPieceValue())
                        .compare(move2.isCastlingMove(), move1.isCastlingMove())
                        .result();
            }
        };

        abstract Collection<Move> sort(Collection<Move> moves);
    }

    public AlphaBetaWithMoveOrderingV2(final int quiescenceFactor) {
        this.evaluator = new StandardBoardEvaluator();
        this.quiescenceFactor = quiescenceFactor;
        this.moveSorter = MoveSorter.SORT;
        this.boardsEvaluated = 0;
        this.quiescenceCount = 0;
        this.cutOffsProduced = 0;
    }

    @Override
    public String toString() {
        return "AlphaBetaWithMoveOrdering";
    }

    @Override
    public long getNumBoardsEvaluated() {
        return this.boardsEvaluated;
    }

    @Override
    public Move execute(final Board board,
                        final int depth) {
        final long startTime = System.currentTimeMillis();
        final Player currentPlayer = board.currentPlayer();
        final Alliance alliance = currentPlayer.getAlliance();
        Move bestMove = Move.NULL_MOVE;
        int highestSeenValue = Integer.MIN_VALUE;
        int lowestSeenValue = Integer.MAX_VALUE;
        int currentValue;
        int moveCounter = 1;
        final List<Move> orderedMoves = MoveOrdering.get().orderMoves(board);
        int numMoves = orderedMoves.size();
        System.out.println(board.currentPlayer() + " THINKING with depth = " + depth);
        System.out.println("\tOrdered moves! : " + orderedMoves);
        for (final Move move : orderedMoves) {
            final MoveTransition moveTransition = board.currentPlayer().makeMove(move);
            this.quiescenceCount = 0;
            final String s;
            if (moveTransition.getMoveStatus().isDone()) {
                final long candidateMoveStartTime = System.nanoTime();
                currentValue = alliance.isWhite() ?
                        min(moveTransition.getTransitionBoard(), currentPlayer, Move.NULL_MOVE, highestSeenValue, lowestSeenValue, depth - 1) :
                        max(moveTransition.getTransitionBoard(), currentPlayer, Move.NULL_MOVE, highestSeenValue, lowestSeenValue, depth - 1);
                if (alliance.isWhite() && currentValue > highestSeenValue) {
                    highestSeenValue = currentValue;
                    bestMove = move;
                    setChanged();
                    notifyObservers(bestMove);
                }
                else if (alliance.isBlack() && currentValue < lowestSeenValue) {
                    lowestSeenValue = currentValue;
                    bestMove = move;
                    setChanged();
                    notifyObservers(bestMove);
                }
                final String quiescenceInfo = " [hi = " +highestSeenValue+ " low = " +lowestSeenValue+ "] quiescenceCount = " +this.quiescenceCount;
                s = "\t" + toString() + " analyzing move (" +moveCounter+ "/" +numMoves+ ") " + move + " (best move so far is:  " + bestMove
                        + quiescenceInfo + " took " +calculateTimeTaken(candidateMoveStartTime, System.nanoTime());
            } else {
                s = "\t" + toString() + " analyzing move (" +moveCounter+ "/" +numMoves+ ") " + move + " is ILLEGAL!";
            }
            System.out.println(s);
            moveCounter++;
        }
        this.executionTime = System.currentTimeMillis() - startTime;
        System.out.printf("%s SELECTS %s [#boards evaluated = %d, time taken = %d ms, eval rate = %.1f cutoffCount = %d prune percent = %.2f\n", board.currentPlayer(),
                bestMove, this.boardsEvaluated, this.executionTime, (1000 * ((double)this.boardsEvaluated/this.executionTime)), this.cutOffsProduced, 100 * ((double)this.cutOffsProduced/this.boardsEvaluated));
        return bestMove;
    }

    public int max(final Board board,
                   final Player moveMakingPlayer,
                   final Move priorMove,
                   final int highest,
                   final int lowest,
                   final int depth) {
        if (depth == 0) {
            final boolean isEndGame = BoardUtils.isEndGame(board);
            final boolean searchEndsOnEvenPlies = searchEndedOnEvenPlies(board, moveMakingPlayer);
            if ((searchEndsOnEvenPlies && !priorMove.isAttack()) || isEndGame) {
                this.boardsEvaluated++;
                return this.evaluator.evaluate(board, depth);
            }
            else {
                if(!searchEndsOnEvenPlies || this.quiescenceCount < 1000) {
                    this.quiescenceCount++;
                    return min(board, moveMakingPlayer, priorMove, highest, lowest, 1);
                }
                this.boardsEvaluated++;
                return this.evaluator.evaluate(board, depth);
            }
        }
        else {
            int currentHighest = highest;
            for (final Move move : this.moveSorter.sort((board.currentPlayer().getLegalMoves()))) {
                final MoveTransition moveTransition = board.currentPlayer().makeMove(move);
                if (moveTransition.getMoveStatus().isDone()) {
                    currentHighest = Math.max(
                            currentHighest,
                            min(moveTransition.getTransitionBoard(), moveMakingPlayer, move, currentHighest, lowest, depth - 1
                            ));
                    if (lowest <= currentHighest) {
                        this.cutOffsProduced++;
                        break;
                    }
                }
            }
            return currentHighest;
        }
    }

    public int min(final Board board,
                   final Player moveMakingPlayer,
                   final Move priorMove,
                   final int highest,
                   final int lowest,
                   final int depth) {
        if (depth == 0) {
            final boolean isEndGame = BoardUtils.isEndGame(board);
            final boolean searchEndsOnEvenPlies = searchEndedOnEvenPlies(board, moveMakingPlayer);
            if ((searchEndsOnEvenPlies && !priorMove.isAttack()) || isEndGame) {
                this.boardsEvaluated++;
                return this.evaluator.evaluate(board, depth);
            }
            else {
                if(!searchEndsOnEvenPlies || this.quiescenceCount < 1000) {
                    this.quiescenceCount++;
                    return max(board, moveMakingPlayer, priorMove, highest, lowest, 1);
                }
                this.boardsEvaluated++;
                return this.evaluator.evaluate(board, depth);
            }
        }
        else {
            int currentLowest = lowest;
            for (final Move move : this.moveSorter.sort((board.currentPlayer().getLegalMoves()))) {
                final MoveTransition moveTransition = board.currentPlayer().makeMove(move);
                if (moveTransition.getMoveStatus().isDone()) {
                    currentLowest = Math.min(
                            currentLowest,
                            max(moveTransition.getTransitionBoard(), moveMakingPlayer, priorMove, highest, currentLowest, depth - 1
                            ));
                    if (currentLowest <= highest) {
                        this.cutOffsProduced++;
                        break;
                    }
                }
            }
            return currentLowest;
        }
    }

    private static boolean searchEndedOnEvenPlies(Board board, Player moveMakingPlayer) {
        return moveMakingPlayer.getAlliance() != board.currentPlayer().getAlliance();
    }

    private static String calculateTimeTaken(final long start, final long end) {
        final long timeTaken = (end - start) / 1000000;
        return timeTaken + " ms";
    }


}