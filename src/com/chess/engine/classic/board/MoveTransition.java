package com.chess.engine.classic.board;

import com.chess.engine.classic.board.Board.MoveStatus;

public class MoveTransition {

    private final Board transitionBoard;
    private final MoveStatus moveStatus;

    public MoveTransition(final Board transitionBoard,
                          final MoveStatus moveStatus) {
        this.transitionBoard = transitionBoard;
        this.moveStatus = moveStatus;
    }

    public Board getTransitionBoard() {
         return this.transitionBoard;
    }

    public MoveStatus getMoveStatus() {
        return this.moveStatus;
    }
}
