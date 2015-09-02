package com.chess.engine.classic.player.ai;

import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.board.Board.MoveStatus;
import com.chess.engine.classic.board.BoardUtils;
import com.chess.engine.classic.board.Move;
import com.chess.engine.classic.board.MoveTransition;

import java.util.concurrent.atomic.AtomicInteger;

public class MiniMax implements MoveStrategy {

    private final BoardEvaluator evaluator;
    private long boardsEvaluated;
    private long executionTime;
    private FreqTableRow[] freqTable;
    private int freqTableIndex;

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

    public Move execute(final Board board,
                        final int depth) {
        final long startTime = System.currentTimeMillis();
        Move best_move = null;
        int highest_seen_value = Integer.MIN_VALUE;
        int lowest_seen_value = Integer.MAX_VALUE;
        int current_value;
        System.out.println(board.currentPlayer() + " THINKING with depth = " +depth);
        freqTable = new FreqTableRow[board.currentPlayer().getLegalMoves().size()];
        freqTableIndex = 0;
        for (final Move move : board.currentPlayer().getLegalMoves()) {
            final MoveTransition moveTransition = board.currentPlayer().makeMove(move);
            if (moveTransition.getMoveStatus() == MoveStatus.DONE) {
                FreqTableRow row = new FreqTableRow(move);
                freqTable[freqTableIndex] = row;
                current_value = board.currentPlayer().getAlliance().isWhite() ?
                                min(moveTransition.getTransitionBoard(), depth - 1) :
                                max(moveTransition.getTransitionBoard(), depth - 1);
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
            }
        }
        this.executionTime = System.currentTimeMillis() - startTime;
        System.out.printf("%s SELECTS %s [#boards = %d time taken = %d ms, rate = %.1f\n", board.currentPlayer(),
                best_move, this.boardsEvaluated, this.executionTime, (1000 * ((double)this.boardsEvaluated/this.executionTime)));
        int total = 0;
        for (FreqTableRow aFreqTable : freqTable) {
            if(aFreqTable != null) {
                total += aFreqTable.getCount();
            }
        }
        System.out.println("minMou totaww = " +total);
        return best_move;
    }

    public int min(final Board board,
                   final int depth) {
        if(depth == 0) {
            this.boardsEvaluated++;
            freqTable[freqTableIndex].increment();
            return this.evaluator.evaluate(board, depth);
        }
        if(isEndGameScenario(board)) {
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
            }
        }
        return lowest_seen_value;
    }

    public int max(final Board board,
                   final int depth) {
        if(depth == 0) {
            this.boardsEvaluated++;
            freqTable[freqTableIndex].increment();
            return this.evaluator.evaluate(board, depth);
        }
        if(isEndGameScenario(board)) {
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
        }
        return highest_seen_value;
    }

    private static boolean isEndGameScenario(final Board board) {
        return  board.currentPlayer().isInCheckMate() ||
                board.currentPlayer().isInStaleMate() ||
                board.currentPlayer().getOpponent().isInCheckMate() ||
                board.currentPlayer().getOpponent().isInStaleMate();
    }

    private static class FreqTableRow {

        private final Move move;
        private final AtomicInteger count;

        FreqTableRow(final Move move) {
            this.count = new AtomicInteger();
            this.move = move;
        }

        public int getCount() {
            return this.count.get();
        }

        public void increment() {
            this.count.incrementAndGet();
        }

        @Override
        public String toString() {
            return BoardUtils.getPositionAtCoordinate(move.getCurrentCoordinate()) + BoardUtils.getPositionAtCoordinate(move.getDestinationCoordinate()) + " : " +count;
        }
    }

}
