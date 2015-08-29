package com.chess.engine.classic.player.ai;

import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.board.Board.MoveStatus;
import com.chess.engine.classic.board.Move;
import com.chess.engine.classic.board.MoveTransition;

public class MiniMax implements MoveStrategy {

    private final BoardEvaluator evaluator;
    private long boardsEvaluated;
    private long numCaptures;
    private long numCastles;
    private long numEnPassants;
    private long executionTime;

    static int[] freqTable;
    static int freqTableIndex;

    public MiniMax() {
        this.evaluator = new SimpleBoardEvaluator();
        this.boardsEvaluated = 0;
    }

    @Override
    public String toString() {
        return "MiniMax";
    }

    @Override
    public long getNumBoardsEvaluated() {
        return this.boardsEvaluated;
    }

    @Override
    public long getNumAttacks() {
        return this.numCaptures;
    }

    @Override
    public long getNumCastles() {
        return this.numCastles;
    }

    public Move execute(final Board board,
                        final int depth) {
        final long startTime = System.currentTimeMillis();
        Move best_move = null;
        int highest_seen_value = Integer.MIN_VALUE;
        int lowest_seen_value = Integer.MAX_VALUE;
        int current_value;
        System.out.println(board.currentPlayer() + " THINKING with depth = " +depth);
        freqTable = new int[board.currentPlayer().getLegalMoves().size()];
        freqTableIndex = 0;
        for (final Move move : board.currentPlayer().getLegalMoves()) {
            final MoveTransition moveTransition = board.currentPlayer().makeMove(move);
            if (moveTransition.getMoveStatus() == MoveStatus.DONE) {
                current_value = board.currentPlayer().getAlliance().isWhite() ?
                                min(moveTransition.getTransitionBoard(), depth - 1) :
                                max(moveTransition.getTransitionBoard(), depth - 1);
                freqTable[freqTableIndex] += 1;
                System.out.println("\t" + toString() + " analyzing move " + move + " scores " + current_value + " " +freqTable[freqTableIndex]);
                freqTableIndex++;
                if (board.currentPlayer().getAlliance().isWhite() &&
                        current_value >= highest_seen_value) {
                    highest_seen_value = current_value;
                    best_move = move;
                } else if (board.currentPlayer().getAlliance().isBlack() &&
                        current_value <= lowest_seen_value) {
                    lowest_seen_value = current_value;
                    best_move = move;
                }
                if(move.isAttack()) {
                    this.numCaptures++;
                }
                if(move.isCastle()) {
                    this.numCastles++;
                }
            }
        }
        this.executionTime = System.currentTimeMillis() - startTime;
        System.out.printf("%s SELECTS %s [#boards = %d time taken = %d ms, rate = %.1f\n", board.currentPlayer(),
                best_move, this.boardsEvaluated, this.executionTime, (1000 * ((double)this.boardsEvaluated/this.executionTime)));
        return best_move;
    }

    public int min(final Board board,
                   final int depth) {
        if (depth == 0 ||
            board.currentPlayer().isInCheckMate() ||
            board.currentPlayer().getOpponent().isInCheckMate()) {
            this.boardsEvaluated++;
            return this.evaluator.evaluate(board, depth);
        }
        int lowest_seen_value = Integer.MAX_VALUE;
        for (final Move move : board.currentPlayer().getLegalMoves()) {
            final MoveTransition moveTransition = board.currentPlayer().makeMove(move);
            if (moveTransition.getMoveStatus() == MoveStatus.DONE) {
                final int current_value = max(moveTransition.getTransitionBoard(), depth - 1);
                if (current_value <= lowest_seen_value) {
                    lowest_seen_value = current_value;
                }
                if(move.isAttack()) {
                    this.numCaptures++;
                }
                if(move.isCastle()) {
                    this.numCastles++;
                }
                freqTable[freqTableIndex] += 1;
            }
        }
        return lowest_seen_value;
    }

    public int max(final Board board,
                   final int depth) {
        if (depth == 0 ||
            board.currentPlayer().isInCheckMate() ||
            board.currentPlayer().getOpponent().isInCheckMate()) {
            this.boardsEvaluated++;
            return this.evaluator.evaluate(board, depth);
        }
        int highest_seen_value = Integer.MIN_VALUE;
        for (final Move move : board.currentPlayer().getLegalMoves()) {
            final MoveTransition moveTransition = board.currentPlayer().makeMove(move);
            if (moveTransition.getMoveStatus() == MoveStatus.DONE) {
                final int current_value = min(moveTransition.getTransitionBoard(), depth - 1);
                if (current_value >= highest_seen_value) {
                    highest_seen_value = current_value;
                }
            }
            if(move.isAttack()) {
                this.numCaptures++;
            }
            if(move.isCastle()) {
                this.numCastles++;
            }
            freqTable[freqTableIndex] += 1;
        }
        return highest_seen_value;
    }

}
