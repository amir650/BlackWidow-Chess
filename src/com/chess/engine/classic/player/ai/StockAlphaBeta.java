package com.chess.engine.classic.player.ai;

import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.board.BoardUtils;
import com.chess.engine.classic.board.Move;
import com.chess.engine.classic.board.MoveTransition;
import com.chess.engine.classic.player.Player;

import java.util.*;

import static com.chess.engine.classic.board.BoardUtils.mvvlva;
import static com.chess.engine.classic.board.BoardUtils.scoreMove;
import static com.chess.engine.classic.board.Move.MoveFactory;

public class StockAlphaBeta extends Observable implements MoveStrategy {

    private final BoardEvaluator evaluator;
    private final int searchDepth;
    private long boardsEvaluated;
    private int quiescenceCount;
    private static final int MAX_QUIESCENCE = 5000 * 0;

    private static final Comparator<Move> SIMPLE_MOVE_COMPARATOR = (m1, m2) -> {
        if (m1.isCastlingMove() != m2.isCastlingMove()) {
            return m1.isCastlingMove() ? -1 : 1;
        }
        return Integer.compare(mvvlva(m2), mvvlva(m1));
    };

    private static final Comparator<Move> EXPENSIVE_MOVE_COMPARATOR = (m1, m2) -> {
        final int score1 = scoreMove(m1);
        final int score2 = scoreMove(m2);
        return Integer.compare(score2, score1);
    };

    private enum MoveSorter {
        STANDARD {
            @Override
            Collection<Move> sort(Collection<Move> moves) {
                final List<Move> sorted = new ArrayList<>(moves);
                sorted.sort(SIMPLE_MOVE_COMPARATOR);
                return sorted;
            }
        },
        EXPENSIVE {
            @Override
            Collection<Move> sort(Collection<Move> moves) {
                final List<Move> sorted = new ArrayList<>(moves);
                sorted.sort(EXPENSIVE_MOVE_COMPARATOR);
                return sorted;
            }
        };

        abstract Collection<Move> sort(Collection<Move> moves);
    }

    public StockAlphaBeta(final int searchDepth) {
        this.evaluator = StandardBoardEvaluator.get();
        this.searchDepth = searchDepth;
        this.boardsEvaluated = 0;
        this.quiescenceCount = 0;
    }

    @Override
    public String toString() {
        return "StockAB";
    }

    @Override
    public long getNumBoardsEvaluated() {
        return this.boardsEvaluated;
    }

    @Override
    public Move execute(final Board board) {
        final long startTime = System.currentTimeMillis();
        final Player currentPlayer = board.currentPlayer();
        Move bestMove = MoveFactory.getNullMove();
        int highestSeenValue = Integer.MIN_VALUE;
        int lowestSeenValue = Integer.MAX_VALUE;
        int currentValue;
        System.out.println(board.currentPlayer() + " THINKING with depth = " + this.searchDepth);
        int moveCounter = 1;
        int numMoves = board.currentPlayer().getLegalMoves().size();
        for (final Move move : MoveSorter.EXPENSIVE.sort(board.currentPlayer().getLegalMoves())) {
            final MoveTransition moveTransition = board.currentPlayer().makeMove(move);
            this.quiescenceCount = 0;
            final String s;
            if (moveTransition.getMoveStatus().isDone()) {
                final long candidateMoveStartTime = System.nanoTime();
                currentValue = currentPlayer.getAlliance().isWhite() ?
                        min(moveTransition.getToBoard(), this.searchDepth - 1, highestSeenValue, lowestSeenValue) :
                        max(moveTransition.getToBoard(), this.searchDepth - 1, highestSeenValue, lowestSeenValue);
                if (currentPlayer.getAlliance().isWhite() && currentValue > highestSeenValue) {
                    highestSeenValue = currentValue;
                    bestMove = move;
                    if (moveTransition.getToBoard().blackPlayer().isInCheckMate()) {
                        break;
                    }
                } else if (currentPlayer.getAlliance().isBlack() && currentValue < lowestSeenValue) {
                    lowestSeenValue = currentValue;
                    bestMove = move;
                    if (moveTransition.getToBoard().whitePlayer().isInCheckMate()) {
                        break;
                    }
                }
                final String quiescenceInfo = " " + score(currentPlayer, highestSeenValue, lowestSeenValue) + " q: " + this.quiescenceCount;
                s = "\t" + this + "(" + this.searchDepth + "), m: (" + moveCounter + "/" + numMoves + ") " + move + ", best:  " + bestMove +
                        quiescenceInfo + ", t: " + calculateTimeTaken(candidateMoveStartTime, System.nanoTime());
            } else {
                s = "\t" + this + "(" + this.searchDepth + ")" + ", m: (" + moveCounter + "/" + numMoves + ") " + move + " is illegal! best: " + bestMove;
            }
            System.out.println(s);
            setChanged();
            notifyObservers(s);
            moveCounter++;
        }

        final long executionTime = System.currentTimeMillis() - startTime;
        final String result = board.currentPlayer() + " SELECTS " + bestMove + " [#boards evaluated = " + this.boardsEvaluated +
                " time taken = " + executionTime / 1000 + " rate = " + (1000 * ((double) this.boardsEvaluated / executionTime));
        System.out.printf("%s SELECTS %s [#boards evaluated = %d, time taken = %s, rate = %.1f\n", board.currentPlayer(),
                bestMove, this.boardsEvaluated, BoardUtils.humanReadableElapsedTime(executionTime), (1000 * ((double) this.boardsEvaluated / executionTime)));
        setChanged();
        notifyObservers(result);
        return bestMove;
    }

    private static String score(final Player currentPlayer, final int highestSeenValue, final int lowestSeenValue) {
        return currentPlayer.getAlliance().isWhite() ? "[score: " + highestSeenValue + "]" : "[score: " + lowestSeenValue + "]";
    }

    private int max(final Board board, final int depth, final int highest, final int lowest) {
        if (depth == 0 || BoardUtils.isEndGame(board)) {
            this.boardsEvaluated++;
            return this.evaluator.evaluate(board, depth);
        }
        int currentHighest = highest;
        for (final Move move : MoveSorter.STANDARD.sort(board.currentPlayer().getLegalMoves())) {
            final MoveTransition moveTransition = board.currentPlayer().makeMove(move);
            if (moveTransition.getMoveStatus().isDone()) {
                final Board toBoard = moveTransition.getToBoard();
                currentHighest = Math.max(currentHighest, min(toBoard, depth - 1, currentHighest, lowest));
                if (currentHighest >= lowest) {
                    return lowest;
                }
            }
        }
        return currentHighest;
    }

    private int min(final Board board, final int depth, final int highest, final int lowest) {
        if (depth == 0 || BoardUtils.isEndGame(board)) {
            this.boardsEvaluated++;
            return this.evaluator.evaluate(board, depth);
        }
        int currentLowest = lowest;
        for (final Move move : MoveSorter.STANDARD.sort(board.currentPlayer().getLegalMoves())) {
            final MoveTransition moveTransition = board.currentPlayer().makeMove(move);
            if (moveTransition.getMoveStatus().isDone()) {
                final Board toBoard = moveTransition.getToBoard();
                currentLowest = Math.min(currentLowest, max(toBoard, depth - 1, highest, currentLowest));
                if (currentLowest <= highest) {
                    return highest;
                }
            }
        }
        return currentLowest;
    }

    private int calculateQuiescenceDepth(final Board toBoard, final int depth) {
        if (depth == 1 && this.quiescenceCount < MAX_QUIESCENCE) {
            int activityMeasure = 0;
            if (toBoard.currentPlayer().isInCheck()) {
                activityMeasure++;
            }
            for (final Move move : BoardUtils.lastNMoves(toBoard, 2)) {
                if (move.isAttack()) {
                    activityMeasure++;
                }
            }
            if (activityMeasure >= 2) {
                this.quiescenceCount++;
                return 2;
            }
        }
        return depth - 1;
    }

    private static String calculateTimeTaken(final long start, final long end) {
        final long timeTaken = (end - start) / 1_000_000;
        return timeTaken + " ms";
    }
}
