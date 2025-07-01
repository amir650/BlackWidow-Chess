package com.chess.engine.player.ai;

import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.MoveTransition;
import com.chess.engine.player.Player;

import java.util.*;

import static com.chess.engine.board.BoardUtils.mvvlva;
import static com.chess.engine.board.Move.MoveFactory;

public class StockAlphaBeta extends Observable implements MoveStrategy {

    private final BoardEvaluator evaluator;
    private final int searchDepth;
    private long boardsEvaluated;

    // Add max quiescence depth constant
    private static final int MAX_QUIESCENCE_DEPTH = 2;

    private static final Comparator<Move> SIMPLE_MOVE_COMPARATOR = (m1, m2) -> {
        if (m1.isCastlingMove() != m2.isCastlingMove()) {
            return m1.isCastlingMove() ? -1 : 1;
        }
        return Integer.compare(mvvlva(m2), mvvlva(m1));
    };

    private enum MoveSorter {
        STANDARD {
            @Override
            Collection<Move> sort(Collection<Move> moves) {
                final List<Move> sorted = new ArrayList<>(moves);
                sorted.sort(SIMPLE_MOVE_COMPARATOR);
                return sorted;
            }
        };
        abstract Collection<Move> sort(Collection<Move> moves);
    }

    public StockAlphaBeta(final int searchDepth) {
        this.evaluator = StandardBoardEvaluator.get();
        this.searchDepth = searchDepth;
        this.boardsEvaluated = 0;
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
                s.append("\t")
                        .append(this)
                        .append("(")
                        .append(this.searchDepth)
                        .append(")|(")
                        .append(moveCounter)
                        .append("/")
                        .append(numMoves)
                        .append(") ")
                        .append("[best: ")
                        .append(bestValue)
                        .append("|score: ")
                        .append(bestValue)
                        .append("] [current: ")
                        .append(bestMove)
                        .append("|score")
                        .append(currentValue)
                        .append("]")
                        .append(", t: ")
                        .append(calculateTimeTaken(candidateMoveStartTime, System.nanoTime()));
            } else {
                s.append("\t")
                        .append(this).append("(")
                        .append(this.searchDepth)
                        .append(")|(")
                        .append(moveCounter)
                        .append("/")
                        .append(numMoves)
                        .append(") ")
                        .append(move)
                        .append(" is illegal! best: ")
                        .append(bestMove);
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
        // Only examine captures (removed expensive check detection)
        final List<Move> captures = getCaptureOnlyMoves(board.currentPlayer());
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
        // Only examine captures (removed expensive check detection)
        final List<Move> captures = getCaptureOnlyMoves(board.currentPlayer());
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

    // Simplified helper method - only captures, no expensive check detection
    private List<Move> getCaptureOnlyMoves(final Player player) {
        final List<Move> captures = new ArrayList<>();
        for (final Move move : player.getLegalMoves()) {
            if (move.isAttack()) {
                captures.add(move);
            }
        }
        // Sort captures by MVV-LVA for better ordering
        captures.sort(SIMPLE_MOVE_COMPARATOR);
        return captures;
    }

    private static String calculateTimeTaken(final long start,
                                             final long end) {
        final long timeTaken = (end - start) / 1_000_000;
        return timeTaken + " ms";
    }
}