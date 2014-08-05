package com.chess.engine.classic.player.ai;

import com.chess.engine.classic.Alliance;
import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.board.Board.MoveStatus;
import com.chess.engine.classic.board.Move;
import com.chess.engine.classic.board.MoveTransition;
import com.chess.engine.classic.player.Player;

public class MiniMax implements MoveStrategy {

    private final BoardEvaluator evaluator;
    private long boardsEvaluated;
    private long executionTime;

    public MiniMax() {
        this.evaluator = new SimpleBoardEvaluator();
        this.boardsEvaluated = 0;
    }

    @Override
    public String getName() {
        return "MiniMax";
    }

    @Override
    public long getNumBoardsEvaluated() {
        return this.boardsEvaluated;
    }

    public Move execute(final Board board,
                        final int depth) {
        final long startTime = System.currentTimeMillis();
        final Player currentPlayer = board.currentPlayer();
        final Alliance alliance = currentPlayer.getAlliance();
        Move best_move = null;
        int highest_seen_value = Integer.MIN_VALUE;
        int lowest_seen_value = Integer.MAX_VALUE;
        int current_value;
        System.out.println(board.currentPlayer() + " THINKING with depth = " +depth);
        for (final Move move : board.currentPlayer().getLegalMoves()) {
            final MoveTransition moveTransition = board.makeMove(move);
            if (moveTransition.getMoveStatus() == MoveStatus.DONE) {
                current_value = alliance.isWhite() ? min(moveTransition.getTransitionBoard(), depth - 1) : max(moveTransition.getTransitionBoard(), depth - 1);
                System.out.println("\t" + getName() + " move " + move + " scores " + current_value);
                if (alliance.isWhite() &&
                        current_value >= highest_seen_value) {
                    highest_seen_value = current_value;
                    best_move = move;
                } else if (alliance.isBlack() &&
                        current_value <= lowest_seen_value) {
                    lowest_seen_value = current_value;
                    best_move = move;
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
            return this.evaluator.evaluate(board);
        }
        int lowest_seen_value = Integer.MAX_VALUE;
        for (final Move move : board.currentPlayer().getLegalMoves()) {
            final MoveTransition moveTransition = board.makeMove(move);
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
        if (depth == 0 ||
            board.currentPlayer().isInCheckMate() ||
            board.currentPlayer().getOpponent().isInCheckMate()) {
            this.boardsEvaluated++;
            return this.evaluator.evaluate(board);
        }
        int highest_seen_value = Integer.MIN_VALUE;
        for (final Move move : board.currentPlayer().getLegalMoves()) {
            final MoveTransition moveTransition = board.makeMove(move);
            if (moveTransition.getMoveStatus() == MoveStatus.DONE) {
                final int current_value = min(moveTransition.getTransitionBoard(), depth - 1);
                if (current_value >= highest_seen_value) {
                    highest_seen_value = current_value;
                }
            }
        }
        return highest_seen_value;
    }

}
