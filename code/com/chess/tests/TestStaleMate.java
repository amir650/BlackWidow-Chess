package com.chess.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.chess.engine.classic.Alliance;
import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.board.Board.Builder;
import com.chess.engine.classic.board.Board.MoveStatus;
import com.chess.engine.classic.board.Move.MoveFactory;
import com.chess.engine.classic.board.MoveTransition;
import com.chess.engine.classic.pieces.Bishop;
import com.chess.engine.classic.pieces.King;
import com.chess.engine.classic.pieces.Pawn;

public class TestStaleMate {
    @Test
    public void testAnandKramnikStaleMate() {

        Board.Builder builder = new Builder();

        // Black Layout
        builder.setPiece(14, new Pawn(Alliance.BLACK, 14));
        builder.setPiece(21, new Pawn(Alliance.BLACK, 21));
        builder.setPiece(36, new King(Alliance.BLACK, 36));
        // White Layout
        builder.setPiece(29, new Pawn(Alliance.WHITE, 29));
        builder.setPiece(31, new King(Alliance.WHITE, 31));
        builder.setPiece(39, new Pawn(Alliance.WHITE, 39));

        builder.setMoveMaker(Alliance.BLACK);

        final Board board = builder.build();

        assertFalse(board.currentPlayer().isInStaleMate());

        MoveTransition t1 = board.makeMove(MoveFactory.createMove(board, Board.getCoordinateAtPosition("e4"),
                Board.getCoordinateAtPosition("f5")));

        assertEquals(MoveStatus.DONE, t1.getMoveStatus());
        assertTrue(t1.getTransitionBoard().currentPlayer().isInStaleMate());
        assertFalse(t1.getTransitionBoard().currentPlayer().isInCheck());
        assertFalse(t1.getTransitionBoard().currentPlayer().isInCheckMate());
    }

    @Test
    public void testAnonymousStaleMate() {

        Board.Builder builder = new Builder();

        // Black Layout
        builder.setPiece(2, new King(Alliance.BLACK, 2));
        // White Layout
        builder.setPiece(10, new Pawn(Alliance.WHITE, 10));
        builder.setPiece(26, new King(Alliance.WHITE, 26));

        builder.setMoveMaker(Alliance.WHITE);

        final Board board = builder.build();

        assertFalse(board.currentPlayer().isInStaleMate());

        MoveTransition t1 = board.makeMove(MoveFactory.createMove(board, Board.getCoordinateAtPosition("c5"),
                Board.getCoordinateAtPosition("c6")));

        assertEquals(MoveStatus.DONE, t1.getMoveStatus());
        assertTrue(t1.getTransitionBoard().currentPlayer().isInStaleMate());
        assertFalse(t1.getTransitionBoard().currentPlayer().isInCheck());
        assertFalse(t1.getTransitionBoard().currentPlayer().isInCheckMate());
    }

    @Test
    public void testAnonymousStaleMate2() {

        Board.Builder builder = new Builder();

        // Black Layout
        builder.setPiece(0, new King(Alliance.BLACK, 0));
        // White Layout
        builder.setPiece(16, new Pawn(Alliance.WHITE, 16));
        builder.setPiece(17, new King(Alliance.WHITE, 17));
        builder.setPiece(19, new Bishop(Alliance.WHITE, 19));

        builder.setMoveMaker(Alliance.WHITE);

        final Board board = builder.build();

        assertFalse(board.currentPlayer().isInStaleMate());

        MoveTransition t1 = board.makeMove(MoveFactory.createMove(board, Board.getCoordinateAtPosition("a6"),
                Board.getCoordinateAtPosition("a7")));

        assertEquals(MoveStatus.DONE, t1.getMoveStatus());
        assertTrue(t1.getTransitionBoard().currentPlayer().isInStaleMate());
        assertFalse(t1.getTransitionBoard().currentPlayer().isInCheck());
        assertFalse(t1.getTransitionBoard().currentPlayer().isInCheckMate());
    }
}