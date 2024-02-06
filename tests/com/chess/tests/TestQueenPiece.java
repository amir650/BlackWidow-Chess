package com.chess.tests;
import com.chess.engine.classic.Alliance;
import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.board.BoardUtils;
import com.chess.engine.classic.board.Move;
import com.chess.engine.classic.pieces.King;
import com.chess.engine.classic.pieces.Queen;
import org.junit.Test;

import java.util.Collection;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertThrows;

public class TestQueenPiece {
    @Test
    public void testValidMoveOnEmptyBoard() { // Partition: Valid Chess Moves
        final Board.Builder builder = new Board.Builder();
        // Black Layout
        builder.setPiece(new King(Alliance.BLACK, 4, false, false)); // e8
        // White Layout
        builder.setPiece(new Queen(Alliance.WHITE, 36)); // e4
        builder.setPiece(new King(Alliance.WHITE, 60, false, false)); // e1
        // Set the current player
        builder.setMoveMaker(Alliance.WHITE);
        final Board board = builder.build();
        final Collection<Move> whiteLegals = board.whitePlayer().getLegalMoves();

        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("e4"), BoardUtils.INSTANCE.getCoordinateAtPosition("h4"))));
    }

    @Test
    public void testInvalidChessPieceMove() { // Partition: Invalid Chess Moves
        final Board.Builder builder = new Board.Builder();
        // Black Layout
        builder.setPiece(new King(Alliance.BLACK, 4, false, false)); // e8
        // White Layout
        builder.setPiece(new Queen(Alliance.WHITE, 36)); // e4
        builder.setPiece(new King(Alliance.WHITE, 60, false, false)); // e1
        // Set the current player
        builder.setMoveMaker(Alliance.WHITE);
        final Board board = builder.build();
        final Collection<Move> whiteLegals = board.whitePlayer().getLegalMoves();

        // Representative Value - g6
        assertFalse(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("e4"), BoardUtils.INSTANCE.getCoordinateAtPosition("h5"))));
    }

    @Test
    public void testInvalidChessPieceMoveOutOfBounds() { // Partition: Invalid Moves Out Of Bounds
        final Board.Builder builder = new Board.Builder();
        // Black Layout
        builder.setPiece(new King(Alliance.BLACK, 4, false, false)); // e8
        // White Layout
        builder.setPiece(new Queen(Alliance.WHITE, 36)); // e4
        builder.setPiece(new King(Alliance.WHITE, 60, false, false)); // e1
        // Set the current player
        builder.setMoveMaker(Alliance.WHITE);
        final Board board = builder.build();
        final Collection<Move> whiteLegals = board.whitePlayer().getLegalMoves();

        // Representative Value - gg6
        assertThrows(NullPointerException.class, () -> { whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("e4"), BoardUtils.INSTANCE.getCoordinateAtPosition("gg6")));});
    }
}