package com.chess.engine.classic.player.ai;


import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.board.Move;
import com.chess.engine.classic.board.MoveTransition;
import com.google.common.collect.ImmutableList;
import com.google.common.primitives.Ints;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public final class MoveOrdering {

    private final BoardEvaluator evaluator;

    private static final MoveOrdering INSTANCE = new MoveOrdering();
    private static final int ORDER_SEARCH_DEPTH = 2;

    private MoveOrdering() {
        this.evaluator = new StandardBoardEvaluator();
    }

    public static MoveOrdering get() {
        return INSTANCE;
    }

    public List<Move> orderMoves(final Board board) {
        return orderImpl(board, ORDER_SEARCH_DEPTH);
    }

    private static class MoveOrderEntry {
        final Move move;
        final int score;

        MoveOrderEntry(final Move move,
                       final int score) {
            this.move = move;
            this.score = score;
        }

        final Move getMove() {
            return this.move;
        }

        final int getScore() {
            return this.score;
        }

        @Override
        public String toString() {
            return "move = " +this.move+ " score = " +this.score;
        }
    }

    private List<Move> orderImpl(final Board board,
                                 final int depth) {
        final List<MoveOrderEntry> moveOrderEntries = new ArrayList<>();
        final boolean SORT_DESCENDING = board.currentPlayer().getAlliance().isWhite();
        for (final Move move : board.currentPlayer().getLegalMoves()) {
            final MoveTransition moveTransition = board.currentPlayer().makeMove(move);
            if (moveTransition.getMoveStatus().isDone()) {
                final int currentValue = board.currentPlayer().getAlliance().isWhite() ?
                        min(moveTransition.getTransitionBoard(), depth - 1) :
                        max(moveTransition.getTransitionBoard(), depth - 1);
                moveOrderEntries.add(new MoveOrderEntry(move, currentValue));
            }
        }

        if (SORT_DESCENDING) {
            Collections.sort(moveOrderEntries, new Comparator<MoveOrderEntry>() {
                @Override
                public int compare(final MoveOrderEntry o1,
                                   final MoveOrderEntry o2) {
                    return Ints.compare(o2.getScore(), o1.getScore());
                }
            });
        } else {
            Collections.sort(moveOrderEntries, new Comparator<MoveOrderEntry>() {
                @Override
                public int compare(final MoveOrderEntry o1,
                                   final MoveOrderEntry o2) {
                    return Ints.compare(o1.getScore(), o2.getScore());
                }
            });
        }

        final List<Move> orderedMoves = new ArrayList<>();
        for(final MoveOrderEntry entry : moveOrderEntries) {
            orderedMoves.add(entry.getMove());
        }

        return ImmutableList.copyOf(orderedMoves);
    }

    public int min(final Board board,
                   final int depth) {
        if(depth == 0 || isEndGameScenario(board)) {
            return this.evaluator.evaluate(board, depth);
        }
        int lowestSeenValue = Integer.MAX_VALUE;
        for (final Move move : board.currentPlayer().getLegalMoves()) {
            final MoveTransition moveTransition = board.currentPlayer().makeMove(move);
            if (moveTransition.getMoveStatus().isDone()) {
                final int currentValue = max(moveTransition.getTransitionBoard(), depth - 1);
                if (currentValue <= lowestSeenValue) {
                    lowestSeenValue = currentValue;
                }
            }
        }
        return lowestSeenValue;
    }

    public int max(final Board board,
                   final int depth) {
        if(depth == 0 || isEndGameScenario(board)) {
            return this.evaluator.evaluate(board, depth);
        }
        int highestSeenValue = Integer.MIN_VALUE;
        for (final Move move : board.currentPlayer().getLegalMoves()) {
            final MoveTransition moveTransition = board.currentPlayer().makeMove(move);
            if (moveTransition.getMoveStatus().isDone()) {
                final int currentValue = min(moveTransition.getTransitionBoard(), depth - 1);
                if (currentValue >= highestSeenValue) {
                    highestSeenValue = currentValue;
                }
            }
        }
        return highestSeenValue;
    }

    private static boolean isEndGameScenario(final Board board) {
        return  board.currentPlayer().isInCheckMate() ||
                board.currentPlayer().isInStaleMate() ||
                board.currentPlayer().getOpponent().isInCheckMate() ||
                board.currentPlayer().getOpponent().isInStaleMate();
    }

}
