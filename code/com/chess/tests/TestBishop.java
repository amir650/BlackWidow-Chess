package com.chess.tests;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.chess.engine.classic.Alliance;
import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.board.BoardConfigurator;
import com.chess.engine.classic.board.Move;
import com.chess.engine.classic.pieces.Bishop;
import com.chess.engine.classic.pieces.King;

public class TestBishop {
    @Test
    public void testMiddleBishopOnEmptyBoard() {
        final Board board = new Board(new BoardConfigurator() {
            @Override
            public void setBoardPieces(final Board board) {
                board.clearGameBoard();
                // Black Layout
                board.setPiece(4, new King(Alliance.BLACK));
                // White Layout
                board.setPiece(35, new Bishop(Alliance.WHITE));
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
                .createMove(board, Board.getCoordinateAtPosition("d4"), Board.getCoordinateAtPosition("a7"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("d4"), Board.getCoordinateAtPosition("b6"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("d4"), Board.getCoordinateAtPosition("c5"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("d4"), Board.getCoordinateAtPosition("e3"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("d4"), Board.getCoordinateAtPosition("f2"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("d4"), Board.getCoordinateAtPosition("g1"))));
    }

    @Test
    public void testTopLeftBishopOnEmptyBoard() {
        final Board board = new Board(new BoardConfigurator() {
            @Override
            public void setBoardPieces(final Board board) {
                board.clearGameBoard();
                // Black Layout
                board.setPiece(4, new King(Alliance.BLACK));
                // White Layout
                board.setPiece(0, new Bishop(Alliance.WHITE));
                board.setPiece(60, new King(Alliance.WHITE));
            }

            @Override
            public void setCurrentPlayer(final Board board) {
                board.setCurrentPlayer(board.whitePlayer());
            }
        });
        final List<Move> whiteLegals = board.whitePlayer().getLegalMoves();
        final List<Move> blackLegals = board.blackPlayer().getLegalMoves();
        assertEquals(whiteLegals.size(), 12);
        assertEquals(blackLegals.size(), 5);
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("a8"), Board.getCoordinateAtPosition("b7"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("a8"), Board.getCoordinateAtPosition("c6"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("a8"), Board.getCoordinateAtPosition("d5"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("a8"), Board.getCoordinateAtPosition("e4"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("a8"), Board.getCoordinateAtPosition("f3"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("a8"), Board.getCoordinateAtPosition("g2"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("a8"), Board.getCoordinateAtPosition("h1"))));
    }

    @Test
    public void testTopRightBishopOnEmptyBoard() {
        final Board board = new Board(new BoardConfigurator() {
            @Override
            public void setBoardPieces(final Board board) {
                board.clearGameBoard();
                // Black Layout
                board.setPiece(4, new King(Alliance.BLACK));
                // White Layout
                board.setPiece(7, new Bishop(Alliance.WHITE));
                board.setPiece(60, new King(Alliance.WHITE));
            }

            @Override
            public void setCurrentPlayer(final Board board) {
                board.setCurrentPlayer(board.whitePlayer());
            }
        });
        final List<Move> whiteLegals = board.whitePlayer().getLegalMoves();
        final List<Move> blackLegals = board.blackPlayer().getLegalMoves();
        assertEquals(whiteLegals.size(), 12);
        assertEquals(blackLegals.size(), 5);
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("h8"), Board.getCoordinateAtPosition("g7"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("h8"), Board.getCoordinateAtPosition("f6"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("h8"), Board.getCoordinateAtPosition("e5"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("h8"), Board.getCoordinateAtPosition("d4"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("h8"), Board.getCoordinateAtPosition("c3"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("h8"), Board.getCoordinateAtPosition("b2"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("h8"), Board.getCoordinateAtPosition("a1"))));
    }

    @Test
    public void testBottomLeftBishopOnEmptyBoard() {
        final Board board = new Board(new BoardConfigurator() {
            @Override
            public void setBoardPieces(final Board board) {
                board.clearGameBoard();
                // Black Layout
                board.setPiece(4, new King(Alliance.BLACK));
                // White Layout
                board.setPiece(56, new Bishop(Alliance.WHITE));
                board.setPiece(60, new King(Alliance.WHITE));
            }

            @Override
            public void setCurrentPlayer(final Board board) {
                board.setCurrentPlayer(board.whitePlayer());
            }
        });
        final List<Move> whiteLegals = board.whitePlayer().getLegalMoves();
        final List<Move> blackLegals = board.blackPlayer().getLegalMoves();
        assertEquals(whiteLegals.size(), 12);
        assertEquals(blackLegals.size(), 5);
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("a1"), Board.getCoordinateAtPosition("b2"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("a1"), Board.getCoordinateAtPosition("c3"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("a1"), Board.getCoordinateAtPosition("d4"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("a1"), Board.getCoordinateAtPosition("e5"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("a1"), Board.getCoordinateAtPosition("f6"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("a1"), Board.getCoordinateAtPosition("g7"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("a1"), Board.getCoordinateAtPosition("h8"))));
    }

    @Test
    public void testBottomRightBishopOnEmptyBoard() {
        final Board board = new Board(new BoardConfigurator() {
            @Override
            public void setBoardPieces(final Board board) {
                board.clearGameBoard();
                // Black Layout
                board.setPiece(4, new King(Alliance.BLACK));
                // White Layout
                board.setPiece(63, new Bishop(Alliance.WHITE));
                board.setPiece(60, new King(Alliance.WHITE));
            }

            @Override
            public void setCurrentPlayer(final Board board) {
                board.setCurrentPlayer(board.whitePlayer());
            }
        });
        final List<Move> whiteLegals = board.whitePlayer().getLegalMoves();
        final List<Move> blackLegals = board.blackPlayer().getLegalMoves();
        assertEquals(whiteLegals.size(), 12);
        assertEquals(blackLegals.size(), 5);
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("h1"), Board.getCoordinateAtPosition("g2"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("h1"), Board.getCoordinateAtPosition("f3"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("h1"), Board.getCoordinateAtPosition("e4"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("h1"), Board.getCoordinateAtPosition("d5"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("h1"), Board.getCoordinateAtPosition("c6"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("h1"), Board.getCoordinateAtPosition("b7"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("h1"), Board.getCoordinateAtPosition("a8"))));
    }
}
