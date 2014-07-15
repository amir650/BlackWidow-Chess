package com.chess.tests;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.chess.engine.classic.Alliance;
import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.board.BoardConfigurator;
import com.chess.engine.classic.board.Move;
import com.chess.engine.classic.pieces.King;
import com.chess.engine.classic.pieces.Rook;

public class TestRook {
    @Test
    public void testMiddleRookOnEmptyBoard() {
        final Board board = new Board(new BoardConfigurator() {
            @Override
            public void setBoardPieces(final Board board) {
                board.clearGameBoard();
                // Black Layout
                board.setPiece(4, new King(Alliance.BLACK));
                // White Layout
                board.setPiece(36, new Rook(Alliance.WHITE));
                board.setPiece(60, new King(Alliance.WHITE));
            }

            @Override
            public void setCurrentPlayer(final Board board) {
                board.setCurrentPlayer(board.whitePlayer());
            }
        });

        final List<Move> whiteLegals = board.whitePlayer().getLegalMoves();
        final List<Move> blackLegals = board.blackPlayer().getLegalMoves();

        assertEquals(whiteLegals.size(), 18);
        assertEquals(blackLegals.size(), 5);
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("e4"), Board.getCoordinateAtPosition("e8"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("e4"), Board.getCoordinateAtPosition("e7"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("e4"), Board.getCoordinateAtPosition("e6"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("e4"), Board.getCoordinateAtPosition("e5"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("e4"), Board.getCoordinateAtPosition("e3"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("e4"), Board.getCoordinateAtPosition("e2"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("e4"), Board.getCoordinateAtPosition("a4"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("e4"), Board.getCoordinateAtPosition("b4"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("e4"), Board.getCoordinateAtPosition("c4"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("e4"), Board.getCoordinateAtPosition("d4"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("e4"), Board.getCoordinateAtPosition("f4"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("e4"), Board.getCoordinateAtPosition("g4"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("e4"), Board.getCoordinateAtPosition("h4"))));
    }
}
