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

    // Add max quiescence depth constant
    private static final int MAX_QUIESCENCE_DEPTH = 2;

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
        int bestValue = currentPlayer.getAlliance().isWhite() ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        System.out.println(board.currentPlayer() + " THINKING with depth = " + this.searchDepth);
        int moveCounter = 1;
        int numMoves = board.currentPlayer().getLegalMoves().size();
        for (final Move move : MoveSorter.STANDARD.sort(board.currentPlayer().getLegalMoves())) {
            final MoveTransition moveTransition = board.currentPlayer().makeMove(move);
            //this.quiescenceCount = 0;
            final StringBuilder s = new StringBuilder();
            if (moveTransition.getMoveStatus().isDone()) {
                final long candidateMoveStartTime = System.nanoTime();
                final int currentValue = currentPlayer.getAlliance().isWhite() ?
                        min(moveTransition.getToBoard(), this.searchDepth - 1,
                                Integer.MIN_VALUE,
                                Integer.MAX_VALUE) :
                        max(moveTransition.getToBoard(), this.searchDepth - 1,
                                Integer.MIN_VALUE,
                                Integer.MAX_VALUE);
                if (currentPlayer.getAlliance().isWhite() && currentValue > bestValue) {
                    bestValue = currentValue;
                    bestMove = move;
                    if (moveTransition.getToBoard().blackPlayer().isInCheckMate()) {
                        break;
                    }
                } else if (currentPlayer.getAlliance().isBlack() && currentValue < bestValue) {
                    bestValue = currentValue;
                    bestMove = move;
                    if (moveTransition.getToBoard().whitePlayer().isInCheckMate()) {
                        break;
                    }
                }

                final String scoreInfo = " [score: " + currentValue + "] q: " + this.quiescenceCount;

                s.append("\t" + this + "(" + this.searchDepth + ")|(" + moveCounter + "/" + numMoves + ") " + move + ", best:  " + bestMove +
                        scoreInfo + ", t: " + calculateTimeTaken(candidateMoveStartTime, System.nanoTime()));
            } else {
                s.append("\t" + this + "(" + this.searchDepth + ")|(" + moveCounter + "/" + numMoves + ") " + move + " is illegal! best: " + bestMove);
            }
            System.out.println(s);
            setChanged();
            notifyObservers(s.toString());
            moveCounter++;
        }
        final long executionTime = System.currentTimeMillis() - startTime;
        final String result = board.currentPlayer() + " SELECTS " + bestMove + " [#boards evaluated = " + this.boardsEvaluated +
                " time taken = " + executionTime / 1000 + " rate = " + (1000 * ((double) this.boardsEvaluated / executionTime));
        System.out.printf("%s SELECTS %s [#boards evaluated = %d, time taken = %s, rate = %.1f]\n", board.currentPlayer(),
                bestMove, this.boardsEvaluated, BoardUtils.humanReadableElapsedTime(executionTime), (1000 * ((double) this.boardsEvaluated / executionTime)));
        setChanged();
        notifyObservers(result);
        return bestMove;
    }

    private int max(final Board board,
                    final int depth,
                    int alpha,
                    int beta) {
        if (depth == 0 || BoardUtils.isEndGame(board)) {
            return quiescenceMax(board, MAX_QUIESCENCE_DEPTH, alpha, beta);
        }
        int maxEval = Integer.MIN_VALUE;
        for (final Move move : MoveSorter.STANDARD.sort(board.currentPlayer().getLegalMoves())) {
            final MoveTransition moveTransition = board.currentPlayer().makeMove(move);
            if (moveTransition.getMoveStatus().isDone()) {
                final int eval = min(moveTransition.getToBoard(), depth - 1, alpha, beta);
                maxEval = Math.max(maxEval, eval);
                alpha = Math.max(alpha, eval);
                if (alpha >= beta) {
                    break;
                }
            }
        }
        return maxEval;
    }

    private int min(final Board board,
                    int depth,
                    int alpha,
                    int beta) {
        if (depth == 0 || BoardUtils.isEndGame(board)) {
            return quiescenceMin(board, MAX_QUIESCENCE_DEPTH, alpha, beta);
        }
        int minEval = Integer.MAX_VALUE;
        for (final Move move : MoveSorter.STANDARD.sort(board.currentPlayer().getLegalMoves())) {
            final MoveTransition moveTransition = board.currentPlayer().makeMove(move);
            if (moveTransition.getMoveStatus().isDone()) {
                final int eval = max(moveTransition.getToBoard(), depth - 1, alpha, beta);
                minEval = Math.min(minEval, eval);
                beta = Math.min(beta, eval);
                if (beta <= alpha) {
                    break;
                }
            }
        }
        return minEval;
    }

    private int quiescenceMax(final Board board,
                              final int depth,
                              int alpha,
                              int beta) {
        this.boardsEvaluated++;
        this.quiescenceCount++;
        // Stand pat score - evaluation of current position
        final int standPat = this.evaluator.evaluate(board, 0);
        // If we're already winning by a lot, no need to search captures
        if (standPat >= beta) {
            return beta;
        }
        // Update alpha if stand pat is better
        if (standPat > alpha) {
            alpha = standPat;
        }
        // Stop if we've searched too deep in quiescence or game is over
        if (depth == 0 || BoardUtils.isEndGame(board)) {
            return standPat;
        }
        // Only examine captures
        final List<Move> captures = getCaptureAndCheckMoves(board.currentPlayer());
        // If no captures available, return stand pat score
        if (captures.isEmpty()) {
            return standPat;
        }
        // Search capture moves
        for (final Move move : captures) {
            final MoveTransition moveTransition = board.currentPlayer().makeMove(move);
            if (moveTransition.getMoveStatus().isDone()) {
                final int eval = quiescenceMin(moveTransition.getToBoard(), depth - 1, alpha, beta);
                if (eval >= beta) {
                    return beta;  // Beta cutoff
                }
                if (eval > alpha) {
                    alpha = eval;
                }
            }
        }
        return alpha;
    }

    // Quiescence search for minimizing player
    private int quiescenceMin(final Board board,
                              final int depth,
                              int alpha,
                              int beta) {
        this.boardsEvaluated++;
        this.quiescenceCount++;
        // Stand pat score
        final int standPat = this.evaluator.evaluate(board, 0);
        // If we're already losing by a lot, no need to search captures
        if (standPat <= alpha) {
            return alpha;
        }
        // Update beta if stand pat is better
        if (standPat < beta) {
            beta = standPat;
        }
        // Stop if we've searched too deep or game is over
        if (depth == 0 || BoardUtils.isEndGame(board)) {
            return standPat;
        }
        // Only examine captures
        final List<Move> captures = getCaptureAndCheckMoves(board.currentPlayer());
        // If no captures available, return stand pat score
        if (captures.isEmpty()) {
            return standPat;
        }
        // Search capture moves
        for (final Move move : captures) {
            final MoveTransition moveTransition = board.currentPlayer().makeMove(move);
            if (moveTransition.getMoveStatus().isDone()) {
                final int eval = quiescenceMax(moveTransition.getToBoard(), depth - 1, alpha, beta);
                if (eval <= alpha) {
                    return alpha;  // Alpha cutoff
                }
                if (eval < beta) {
                    beta = eval;
                }
            }
        }

        return beta;
    }

    // Helper method to get only capture moves and checks
    private List<Move> getCaptureAndCheckMoves(final Player player) {
        final List<Move> capturesAndCheck = new ArrayList<>();
        for (final Move move : player.getLegalMoves()) {
            if (move.isAttack()) {
                capturesAndCheck.add(move);
            } else {
                // Check if this move gives check
                final MoveTransition transition = player.makeMove(move);
                if (transition.getMoveStatus().isDone() &&
                    transition.getToBoard().currentPlayer().isInCheck()) {
                    capturesAndCheck.add(move);
                }
            }
        }
        // Sort captures by MVV-LVA for better ordering
        capturesAndCheck.sort(SIMPLE_MOVE_COMPARATOR);
        return capturesAndCheck;
    }

    private static String calculateTimeTaken(final long start,
                                             final long end) {
        final long timeTaken = (end - start) / 1_000_000;
        return timeTaken + " ms";
    }
}