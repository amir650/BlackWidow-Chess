package com.chess.engine.classic.board;

public enum MoveUtils {

    INSTANCE;

    public static int exchangeScore(final Move move) {
        if(move == Move.NULL_MOVE) {
            return 1;
        }
        return move.isAttack() ?
                5 * exchangeScore(move.getBoard().getTransitionMove()) :
                exchangeScore(move.getBoard().getTransitionMove());

    }

}
