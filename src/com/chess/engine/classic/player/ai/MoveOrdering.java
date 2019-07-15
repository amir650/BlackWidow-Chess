package com.chess.engine.classic.player.ai;


import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.board.Move;
import com.chess.engine.classic.board.MoveTransition;
import com.chess.engine.classic.player.Player;
import com.google.common.collect.ImmutableList;
import com.google.common.primitives.Booleans;
import com.google.common.primitives.Ints;

import java.util.*;

public final class MoveOrdering {

    private final BoardEvaluator evaluator;

    private static final MoveOrdering INSTANCE = new MoveOrdering();
    private static final int ORDER_SEARCH_DEPTH = 2;

    private MoveOrdering() {
        this.evaluator = StandardBoardEvaluator.get();
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
        for (final Move move : board.currentPlayer().getLegalMoves()) {
            final MoveTransition moveTransition = board.currentPlayer().makeMove(move);
            if (moveTransition.getMoveStatus().isDone()) {
                final int attackBonus = calculateAttackBonus(board.currentPlayer(), move);
                final int currentValue = attackBonus + (board.currentPlayer().getAlliance().isWhite() ?
                        min(moveTransition.getToBoard(), depth - 1) :
                        max(moveTransition.getToBoard(), depth - 1));
                moveOrderEntries.add(new MoveOrderEntry(move, currentValue));
            }
        }
        Collections.sort(moveOrderEntries, new Comparator<MoveOrderEntry>() {
            @Override
            public int compare(final MoveOrderEntry o1, final MoveOrderEntry o2) {
                return Ints.compare(o2.getScore(), o1.getScore());
            }
        });
        final List<Move> orderedMoves = new ArrayList<>();
        for(final MoveOrderEntry entry : moveOrderEntries) {
            orderedMoves.add(entry.getMove());
        }

        return ImmutableList.copyOf(orderedMoves);
    }

    private int calculateAttackBonus(final Player player,
                                     final Move move) {
        final int attackBonus = move.isAttack() ? 1000 : 0;
        return attackBonus * (player.getAlliance().isWhite() ? 1 : -1);
    }

    private static Collection<Move> calculateSimpleMoveOrder(final Collection<Move> moves) {
        final List<Move> sortedMoves = new ArrayList<>();
        sortedMoves.addAll(moves);
        Collections.sort(sortedMoves, (m1, m2) -> Booleans.compare(m2.isAttack(), m1.isAttack()));
        return sortedMoves;
    }

    public int min(final Board board,
                   final int depth) {
        if(depth == 0 || isEndGameScenario(board)) {
            return this.evaluator.evaluate(board, depth);
        }
        int lowestSeenValue = Integer.MAX_VALUE;
        for (final Move move : calculateSimpleMoveOrder(board.currentPlayer().getLegalMoves())) {
            final MoveTransition moveTransition = board.currentPlayer().makeMove(move);
            if (moveTransition.getMoveStatus().isDone()) {
                final int currentValue = max(moveTransition.getToBoard(), depth - 1);
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
        for (final Move move : calculateSimpleMoveOrder(board.currentPlayer().getLegalMoves())) {
            final MoveTransition moveTransition = board.currentPlayer().makeMove(move);
            if (moveTransition.getMoveStatus().isDone()) {
                final int currentValue = min(moveTransition.getToBoard(), depth - 1);
                if (currentValue >= highestSeenValue) {
                    highestSeenValue = currentValue;
                }
            }
        }
        return highestSeenValue;
    }

    private static boolean isEndGameScenario(final Board board) {
        return  board.currentPlayer().isInCheckMate() ||
                board.currentPlayer().isInStaleMate();
    }

}
