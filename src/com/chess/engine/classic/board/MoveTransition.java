package com.chess.engine.classic.board;

import com.chess.engine.classic.board.Move.MoveStatus;

public final class MoveTransition {

    private final Board fromBoard;
    private final Board toBoard;
    private final Move move;
    private final MoveStatus moveStatus;

    public MoveTransition(final Board fromBoard,
                          final Board toBoard,
                          final Move move,
                          final MoveStatus moveStatus) {
        this.fromBoard = fromBoard;
        this.toBoard = toBoard;
        this.move = move;
        this.moveStatus = moveStatus;
    }

    public Board getToBoard() {
         return this.toBoard;
    }

    public Board getFromBoard() {
        return this.fromBoard;
    }

    public MoveStatus getMoveStatus() {
        return this.moveStatus;
    }
}
