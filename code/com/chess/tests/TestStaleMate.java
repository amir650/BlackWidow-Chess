package com.chess.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.chess.engine.classic.Alliance;
import com.chess.engine.classic.board.Board.MoveStatus;
import com.chess.engine.classic.pieces.Bishop;
import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.board.BoardConfigurator;
import com.chess.engine.classic.pieces.King;
import com.chess.engine.classic.board.Move.MoveFactory;
import com.chess.engine.classic.pieces.Pawn;
import com.chess.engine.classic.player.Player;

public class TestStaleMate {
    @Test
    public void testAnandKramnikStaleMate() {
        final Board board = new Board(new BoardConfigurator() {
            @Override
            public void setBoardPieces(final Board board) {
                //Black Layout
                board.setPiece(14, new Pawn(Alliance.BLACK));
                board.setPiece(21, new Pawn(Alliance.BLACK));
                board.setPiece(36, new King(Alliance.BLACK));
                // White Layout
                board.setPiece(29, new Pawn(Alliance.WHITE));
                board.setPiece(31, new King(Alliance.WHITE));
                board.setPiece(39, new Pawn(Alliance.WHITE));
            }

            @Override
            public void setCurrentPlayer(final Board board) {
                board.setCurrentPlayer(board.whitePlayer());
            }
        });
        board.setCurrentPlayer(board.blackPlayer());
        assertFalse(board.currentPlayer().isInStaleMate());
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("e4"),
                        Board.getCoordinateAtPosition("f5"))));
        assertTrue(board.currentPlayer().isInStaleMate());
        assertFalse(board.currentPlayer().isInCheck());
        assertFalse(board.currentPlayer().isInCheckMate());
    }

    @Test
    public void testAnonymousStaleMate() {
        final Board board = new Board(new BoardConfigurator() {
            @Override
            public void setBoardPieces(final Board board) {
                //Black Layout
                board.setPiece(2, new King(Alliance.BLACK));
                // White Layout
                board.setPiece(10, new Pawn(Alliance.WHITE));
                board.setPiece(26, new King(Alliance.WHITE));
            }

            @Override
            public void setCurrentPlayer(final Board board) {
                board.setCurrentPlayer(board.whitePlayer());
            }
        });
        assertFalse(board.currentPlayer().isInStaleMate());
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("c5"),
                        Board.getCoordinateAtPosition("c6"))));
        assertTrue(board.currentPlayer().isInStaleMate());
        assertFalse(board.currentPlayer().isInCheck());
        assertFalse(board.currentPlayer().isInCheckMate());
    }

    @Test
    public void testAnonymousStaleMate2() {
        final Board board = new Board(new BoardConfigurator() {
            @Override
            public void setBoardPieces(final Board board) {
                //Black Layout
                board.setPiece(0, new King(Alliance.BLACK));
                // White Layout
                board.setPiece(16, new Pawn(Alliance.WHITE));
                board.setPiece(17, new King(Alliance.WHITE));
                board.setPiece(19, new Bishop(Alliance.WHITE));
            }

            @Override
            public void setCurrentPlayer(final Board board) {
                board.setCurrentPlayer(board.whitePlayer());
            }
        });
        assertFalse(board.currentPlayer().isInStaleMate());
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("a6"),
                        Board.getCoordinateAtPosition("a7"))));
        assertTrue(board.currentPlayer().isInStaleMate());
        assertFalse(board.currentPlayer().isInCheck());
        assertFalse(board.currentPlayer().isInCheckMate());
    }
}