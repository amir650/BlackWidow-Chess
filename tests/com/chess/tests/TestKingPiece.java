package com.chess.tests;

import com.chess.engine.classic.Alliance;
import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.board.BoardUtils;
import com.chess.engine.classic.board.Move;
import com.chess.engine.classic.board.MoveTransition;
import com.chess.engine.classic.pieces.*;
import com.google.common.collect.Sets;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Collection;
import java.util.Set;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertNotNull;

public class TestKingPiece {
    @Test
    public void testValidMovesOnEmptyBoard() { // Partition: Valid Chess Moves
        final Board.Builder builder = new Board.Builder();
        // Black Layout
        builder.setPiece(new King(Alliance.BLACK, 4, false, false)); // e8
        // White Layout
        builder.setPiece(new Rook(Alliance.WHITE, 59)); // d1
        builder.setPiece(new King(Alliance.WHITE, 60, false, false)); // e1
        // Set the current player
        builder.setMoveMaker(Alliance.WHITE);
        final Board board = builder.build();
        final Collection<Move> whiteLegals = board.whitePlayer().getLegalMoves();
        final Collection<Move> blackLegals = board.blackPlayer().getLegalMoves();
        assertEquals(whiteLegals.size(), 4);
        assertEquals(blackLegals.size(), 5);

        // Representative Value - f1
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("e1"), BoardUtils.INSTANCE.getCoordinateAtPosition("f1"))));
        // Boundary Value - d1
        assertFalse(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("e1"), BoardUtils.INSTANCE.getCoordinateAtPosition("d1"))));
    }

    @Test
    public void testInvalidChessPieceMoves() { // Partition: Invalid Chess Moves
        final Board.Builder builder = new Board.Builder();
        // Black Layout
        builder.setPiece(new King(Alliance.BLACK, 4, false, false)); // e8
        // White Layout
        builder.setPiece(new Rook(Alliance.WHITE, 59)); // d1
        builder.setPiece(new King(Alliance.WHITE, 60, false, false)); // e1
        // Set the current player
        builder.setMoveMaker(Alliance.WHITE);
        final Board board = builder.build();
        final Collection<Move> whiteLegals = board.whitePlayer().getLegalMoves();
        final Collection<Move> blackLegals = board.blackPlayer().getLegalMoves();
        assertEquals(whiteLegals.size(), 4);
        assertEquals(blackLegals.size(), 5);

        // Representative Value - e3
        assertFalse(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("e1"), BoardUtils.INSTANCE.getCoordinateAtPosition("e3"))));
        // Boundary Value - h1
        assertFalse(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("e1"), BoardUtils.INSTANCE.getCoordinateAtPosition("h1"))));
    }

    @Test
    public void testInvalidChessPieceMovesOutOfBounds() { // Partition: Invalid Moves Out Of Bounds
        final Board.Builder builder = new Board.Builder();
        // Black Layout
        builder.setPiece(new King(Alliance.BLACK, 4, false, false)); // e8
        // White Layout
        builder.setPiece(new Rook(Alliance.WHITE, 59)); // d1
        builder.setPiece(new King(Alliance.WHITE, 60, false, false)); // e1
        // Set the current player
        builder.setMoveMaker(Alliance.WHITE);
        final Board board = builder.build();
        final Collection<Move> whiteLegals = board.whitePlayer().getLegalMoves();
        final Collection<Move> blackLegals = board.blackPlayer().getLegalMoves();
        assertEquals(whiteLegals.size(), 4);
        assertEquals(blackLegals.size(), 5);

        // Representative Value - gg6
        assertThrows(NullPointerException.class, () -> { whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("e1"), BoardUtils.INSTANCE.getCoordinateAtPosition("gg6")));});
    }
}
