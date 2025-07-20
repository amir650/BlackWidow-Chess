package com.chess.engine.player.ai;

import com.chess.engine.board.*;
import com.chess.engine.board.MoveUtils.MoveSorter;

import java.util.*;


public class BlackWidowAI implements MoveStrategy {

    private final BoardEvaluator evaluator;
    private final int searchDepth;
    private final MoveSorter moveSorter;
    private long boardsEvaluated;

    private static final int TT_SIZE = 800_000;
    private final Map<Board, TTEntry> transpositionTable;
    private final boolean isTTEnabled;

    private final int quiescenceExtension;

    private final List<AIProgressListener> progressListeners = new ArrayList<>();

    public BlackWidowAI(final int searchDepth) {
        this.evaluator = StandardBoardEvaluator.get();
        this.searchDepth = searchDepth;
        this.boardsEvaluated = 0;
        this.moveSorter =  MoveSorter.STANDARD;
        this.quiescenceExtension = 0;
        this.transpositionTable = createTranspositionTable();
        this.isTTEnabled = false;
    }

    public BlackWidowAI(final int searchDepth,
                        final boolean sortMoves,
                        final int quiescenceExtension,
                        final boolean enableTransposeTable) {
        this.evaluator = StandardBoardEvaluator.get();
        this.searchDepth = searchDepth;
        this.boardsEvaluated = 0;
        this.moveSorter = sortMoves ? MoveSorter.STANDARD : MoveSorter.NONE;
        this.quiescenceExtension = quiescenceExtension;
        this.transpositionTable = createTranspositionTable();
        this.isTTEnabled = enableTransposeTable;
    }

    @Override
    public long getNumBoardsEvaluated() {
        return this.boardsEvaluated;
    }

    public void addAIProgressListener(AIProgressListener listener) {
        progressListeners.add(listener);
    }

    private void notifyProgressListeners(String progress) {
        progressListeners.forEach(listener -> listener.onAIProgress(progress));
    }

    public String toString() {
        return "BlackWidow(" +this.searchDepth+ ")";
    }

    @Override
    public Move execute(final Board board) {
        final long startTime = System.currentTimeMillis();
        final String initialMessage = board.currentPlayer() + " THINKING at depth " + searchDepth;
        System.out.println(initialMessage);
        notifyProgressListeners(initialMessage);
        Move bestMove = Move.MoveFactory.getNullMove();
        int bestValue = board.currentPlayer().getAlliance().isWhite() ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        final Collection<Move> moves = this.moveSorter.sort(board.currentPlayer().getLegalMoves());
        int moveCounter = 1;
        int movesSearched = 0;
        for (final Move move : moves) {
            final long moveStartTime = System.currentTimeMillis();
            final MoveTransition moveTransition = board.currentPlayer().makeMove(move);
            if (!moveTransition.getMoveStatus().isDone()) {
                final String illegalMoveMessage = "\t" +this+ "("+moveCounter+") " + move + " is illegal!";
                System.out.println(illegalMoveMessage);
                notifyProgressListeners(illegalMoveMessage);
                moveCounter++;
                continue;
            }
            final Board nextBoard = moveTransition.getToBoard();
            final int value = board.currentPlayer().getAlliance().isWhite()
                    ? min(nextBoard, searchDepth - 1, Integer.MIN_VALUE, Integer.MAX_VALUE)
                    : max(nextBoard, searchDepth - 1, Integer.MIN_VALUE, Integer.MAX_VALUE);
            final long moveEndTime = System.currentTimeMillis();
            final String progressMessage = String.format(
                    "%s │ %2d/%-2d │ ● %4s %5s │ ★ %4s %5s | %6d │ %3d ms",
                    this, moveCounter, moves.size(), move, value,
                    bestMove, bestValue, this.transpositionTable.size(), (moveEndTime - moveStartTime)
            );
            System.out.println(progressMessage);
            notifyProgressListeners(progressMessage);
            // Update best move and alpha-beta window
            if (board.currentPlayer().getAlliance().isWhite()) {
                if (value > bestValue) {
                    bestValue = value;
                    bestMove = move;
                }
            } else {
                if (value < bestValue) {
                    bestValue = value;
                    bestMove = move;
                }
            }
            moveCounter++;
            movesSearched++;
        }
        final long executionTime = System.currentTimeMillis() - startTime;
        final String finalMessage = String.format("%s SELECTS %s [#boards evaluated = %d, moves searched = %d/%d, time taken = %s, rate = %.1f]\n",
                board.currentPlayer(), bestMove, this.boardsEvaluated, movesSearched, moves.size(),
                BoardUtils.humanReadableElapsedTime(executionTime), (1000 * ((double) this.boardsEvaluated / executionTime)));
        System.out.println(finalMessage);
        notifyProgressListeners(finalMessage);
        return bestMove;
    }

    private int max(final Board board,
                    final int depth,
                    int alpha,
                    int beta) {
        final TTEntry ttEntry = this.transpositionTable.get(board);
        if (this.isTTEnabled && ttEntry != null && ttEntry.depth >= depth) {
            return ttEntry.score;
        }
        if (depth == 0 || BoardUtils.isEndGame(board)) {
            return quiescenceMax(board, depth, this.quiescenceExtension, alpha, beta);
        }
        int value = Integer.MIN_VALUE;
        final Collection<Move> moves = this.moveSorter.sort(board.currentPlayer().getLegalMoves());
        for (final Move move : moves) {
            final MoveTransition moveTransition = board.currentPlayer().makeMove(move);
            if (!moveTransition.getMoveStatus().isDone()) {
                continue;
            }
            final Board nextBoard = moveTransition.getToBoard();
            value = Math.max(value, min(nextBoard, depth - 1, alpha, beta));
            if (value >= beta) {
                return value;
            }
            alpha = Math.max(alpha, value);
        }
        if(this.isTTEnabled) {
            this.transpositionTable.put(board, new TTEntry(depth, value));
        }
        return value;
    }

    private int min(final Board board,
                    final int depth,
                    int alpha,
                    int beta) {
        final TTEntry ttEntry = this.transpositionTable.get(board);
        if (this.isTTEnabled && ttEntry != null && ttEntry.depth >= depth) {
            return ttEntry.score;
        }
        if (depth == 0 || BoardUtils.isEndGame(board)) {
            return quiescenceMin(board, depth, this.quiescenceExtension, alpha, beta);
        }
        int value = Integer.MAX_VALUE;
        final Collection<Move> moves = this.moveSorter.sort(board.currentPlayer().getLegalMoves());
        for (final Move move : moves) {
            final MoveTransition moveTransition = board.currentPlayer().makeMove(move);
            if (!moveTransition.getMoveStatus().isDone()) {
                continue;
            }
            final Board nextBoard = moveTransition.getToBoard();
            value = Math.min(value, max(nextBoard, depth - 1, alpha, beta));
            if (value <= alpha) {
                return value;
            }
            beta = Math.min(beta, value);
        }
        if(this.isTTEnabled) {
            this.transpositionTable.put(board, new TTEntry(depth, value));
        }
        return value;
    }

    private int quiescenceMax(final Board board,
                              final int depth,
                              final int q_depth,
                              int alpha,
                              int beta) {
        this.boardsEvaluated++;
        final int standPat = this.evaluator.evaluate(board, depth);
        if (standPat >= beta) {
            return beta;
        }
        if (standPat > alpha) {
            alpha = standPat;
        }
        if (q_depth == 0 || BoardUtils.isEndGame(board)) {
            return standPat;
        }
        final List<Move> captures = MoveUtils.getQuiescenceMoves(board.currentPlayer());
        if (captures.isEmpty()) {
            return standPat;
        }
        for (final Move move : captures) {
            final MoveTransition moveTransition = board.currentPlayer().makeMove(move);
            if (moveTransition.getMoveStatus().isDone()) {
                final int eval = quiescenceMin(moveTransition.getToBoard(), depth - 1, q_depth - 1, alpha, beta);
                if (eval >= beta) {
                    return beta;
                }
                if (eval > alpha) {
                    alpha = eval;
                }
            }
        }
        return alpha;
    }

    private int quiescenceMin(final Board board,
                              final int depth,
                              final int q_depth,
                              int alpha,
                              int beta) {
        this.boardsEvaluated++;
        final int standPat = this.evaluator.evaluate(board, depth);
        if (standPat <= alpha) {
            return alpha;
        }
        if (standPat < beta) {
            beta = standPat;
        }
        if (q_depth == 0 || BoardUtils.isEndGame(board)) {
            return standPat;
        }
        final List<Move> captures = MoveUtils.getQuiescenceMoves(board.currentPlayer());
        if (captures.isEmpty()) {
            return standPat;
        }
        for (final Move move : captures) {
            final MoveTransition moveTransition = board.currentPlayer().makeMove(move);
            if (moveTransition.getMoveStatus().isDone()) {
                final int eval = quiescenceMax(moveTransition.getToBoard(), depth - 1, q_depth, alpha, beta);
                if (eval <= alpha) {
                    return alpha;
                }
                if (eval < beta) {
                    beta = eval;
                }
            }
        }
        return beta;
    }

    private static class TTEntry {

        final int depth;
        final int score;

        TTEntry(final int depth,
                final int score) {
            this.depth = depth;
            this.score = score;
        }

    }

    private static Map<Board, TTEntry> createTranspositionTable() {
        return new LinkedHashMap<>(TT_SIZE, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(final Map.Entry<Board, TTEntry> eldest) {
                return size() > TT_SIZE;
            }
        };
    }

}