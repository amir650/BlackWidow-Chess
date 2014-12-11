package com.chess.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.chess.engine.classic.Alliance;
import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.board.Board.Builder;
import com.chess.engine.classic.board.Board.MoveStatus;
import com.chess.engine.classic.board.Move;
import com.chess.engine.classic.board.MoveTransition;
import com.chess.engine.classic.pieces.King;
import com.chess.engine.classic.pieces.Pawn;
import com.chess.engine.classic.pieces.Rook;

public class TestPawn {

    @Test
    public void testPawnPromotion() {

        final Builder builder = new Builder();

        // Black Layout
        builder.setPiece(3, new Rook(Alliance.BLACK, 3));
        builder.setPiece(22, new King(Alliance.BLACK, 22));
        // White Layout
        builder.setPiece(15, new Pawn(Alliance.WHITE, 15));
        builder.setPiece(52, new King(Alliance.WHITE, 52));
        // Set the current player
        builder.setMoveMaker(Alliance.WHITE);

        final Board board = builder.build();

        final Move m1 = Move.MoveFactory.createMove(board, Board.getCoordinateAtPosition("h7"), Board.getCoordinateAtPosition("h8"));
        final MoveTransition t1 = board.makeMove(m1);

        assertEquals(MoveStatus.DONE, t1.getMoveStatus());

        final Move m2 = Move.MoveFactory.createMove(t1.getTransitionBoard(), Board.getCoordinateAtPosition("d8"), Board.getCoordinateAtPosition("h8"));
        final MoveTransition t2 = t1.getTransitionBoard().makeMove(m2);

        assertEquals(MoveStatus.DONE, t2.getMoveStatus());

        final Move m3 = Move.MoveFactory.createMove(t2.getTransitionBoard(), Board.getCoordinateAtPosition("e2"), Board.getCoordinateAtPosition("d2"));
        final MoveTransition t3 = board.makeMove(m3);

        assertEquals(MoveStatus.DONE, t3.getMoveStatus());

    }

}
