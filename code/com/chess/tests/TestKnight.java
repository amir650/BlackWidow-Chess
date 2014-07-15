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
import com.chess.engine.classic.pieces.Knight;

public class TestKnight {
    @Test
    public void testLegalMoveAllAvailable() {
        final Board board = new Board(new BoardConfigurator() {
            @Override
            public void setBoardPieces(final Board board) {
                board.clearGameBoard();
                // Black Layout
                board.setPiece(4, new King(Alliance.BLACK));
                board.setPiece(28, new Knight(Alliance.BLACK));
                // White Layout
                board.setPiece(36, new Knight(Alliance.WHITE));
                board.setPiece(60, new King(Alliance.WHITE));
            }

            @Override
            public void setCurrentPlayer(final Board board) {
                board.setCurrentPlayer(board.whitePlayer());
            }
        });

        final List<Move> whiteLegals = board.whitePlayer().getLegalMoves();
        final List<Move> blackLegals = board.blackPlayer().getLegalMoves();

        assertEquals(whiteLegals.size(), 13);
        assertEquals(blackLegals.size(), 13);

        final Move wm1 = Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("e4"), Board.getCoordinateAtPosition("d6"));
        final Move wm2 = Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("e4"), Board.getCoordinateAtPosition("f6"));
        final Move wm3 = Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("e4"), Board.getCoordinateAtPosition("c5"));
        final Move wm4 = Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("e4"), Board.getCoordinateAtPosition("g5"));
        final Move wm5 = Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("e4"), Board.getCoordinateAtPosition("c3"));
        final Move wm6 = Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("e4"), Board.getCoordinateAtPosition("g3"));
        final Move wm7 = Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("e4"), Board.getCoordinateAtPosition("d2"));
        final Move wm8 = Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("e4"), Board.getCoordinateAtPosition("f2"));

        assertTrue(whiteLegals.contains(wm1));
        assertTrue(whiteLegals.contains(wm2));
        assertTrue(whiteLegals.contains(wm3));
        assertTrue(whiteLegals.contains(wm4));
        assertTrue(whiteLegals.contains(wm5));
        assertTrue(whiteLegals.contains(wm6));
        assertTrue(whiteLegals.contains(wm7));
        assertTrue(whiteLegals.contains(wm8));

        final Move bm1 = Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("e5"), Board.getCoordinateAtPosition("d7"));
        final Move bm2 = Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("e5"), Board.getCoordinateAtPosition("f7"));
        final Move bm3 = Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("e5"), Board.getCoordinateAtPosition("c6"));
        final Move bm4 = Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("e5"), Board.getCoordinateAtPosition("g6"));
        final Move bm5 = Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("e5"), Board.getCoordinateAtPosition("c4"));
        final Move bm6 = Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("e5"), Board.getCoordinateAtPosition("g4"));
        final Move bm7 = Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("e5"), Board.getCoordinateAtPosition("d3"));
        final Move bm8 = Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("e5"), Board.getCoordinateAtPosition("f3"));

        assertTrue(blackLegals.contains(bm1));
        assertTrue(blackLegals.contains(bm2));
        assertTrue(blackLegals.contains(bm3));
        assertTrue(blackLegals.contains(bm4));
        assertTrue(blackLegals.contains(bm5));
        assertTrue(blackLegals.contains(bm6));
        assertTrue(blackLegals.contains(bm7));
        assertTrue(blackLegals.contains(bm8));

    }

    @Test
    public void testKnightInCorners() {
        final Board board = new Board(new BoardConfigurator() {
            @Override
            public void setBoardPieces(final Board board) {
                board.clearGameBoard();
                // Black Layout
                board.setPiece(4, new King(Alliance.BLACK));
                board.setPiece(0, new Knight(Alliance.BLACK));
                // White Layout
                board.setPiece(56, new Knight(Alliance.WHITE));
                board.setPiece(60, new King(Alliance.WHITE));
            }

            @Override
            public void setCurrentPlayer(final Board board) {
                board.setCurrentPlayer(board.whitePlayer());
            }
        });

        final List<Move> whiteLegals = board.whitePlayer().getLegalMoves();
        final List<Move> blackLegals = board.blackPlayer().getLegalMoves();

        assertEquals(whiteLegals.size(), 7);
        assertEquals(blackLegals.size(), 7);

        final Move wm1 = Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("a1"), Board.getCoordinateAtPosition("b3"));
        final Move wm2 = Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("a1"), Board.getCoordinateAtPosition("c2"));

        assertTrue(whiteLegals.contains(wm1));
        assertTrue(whiteLegals.contains(wm2));

        final Move bm1 = Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("a8"), Board.getCoordinateAtPosition("b6"));
        final Move bm2 = Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("a8"), Board.getCoordinateAtPosition("c7"));

        assertTrue(blackLegals.contains(bm1));
        assertTrue(blackLegals.contains(bm2));

    }
}
