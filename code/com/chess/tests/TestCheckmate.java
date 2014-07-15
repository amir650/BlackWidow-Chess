package com.chess.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.chess.engine.classic.Alliance;
import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.board.Board.MoveStatus;
import com.chess.engine.classic.board.BoardConfigurator;
import com.chess.engine.classic.board.Move.MoveFactory;
import com.chess.engine.classic.board.StandardBoardConfigurator;
import com.chess.engine.classic.pieces.Bishop;
import com.chess.engine.classic.pieces.King;
import com.chess.engine.classic.pieces.Knight;
import com.chess.engine.classic.pieces.Pawn;
import com.chess.engine.classic.pieces.Queen;
import com.chess.engine.classic.pieces.Rook;
import com.chess.engine.classic.player.Player;

public class TestCheckmate {

    @Test
    public void testFoolsMate() {
        final Board board = new Board(new StandardBoardConfigurator());
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("f2"),
                        Board.getCoordinateAtPosition("f3"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("e7"),
                        Board.getCoordinateAtPosition("e5"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("g2"),
                        Board.getCoordinateAtPosition("g4"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("d8"),
                        Board.getCoordinateAtPosition("h4"))));
        assertTrue(board.currentPlayer().isInCheckMate());
    }

    @Test
    public void testScholarsMate() {
        final Board board = new Board(new StandardBoardConfigurator());
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("e2"),
                        Board.getCoordinateAtPosition("e4"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("a7"),
                        Board.getCoordinateAtPosition("a6"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("d1"),
                        Board.getCoordinateAtPosition("f3"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("a6"),
                        Board.getCoordinateAtPosition("a5"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("f1"),
                        Board.getCoordinateAtPosition("c4"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("a5"),
                        Board.getCoordinateAtPosition("a4"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("f3"),
                        Board.getCoordinateAtPosition("f7"))));
        assertTrue(board.currentPlayer().isInCheckMate());
    }

    @Test
    public void testLegalsMate() {
        final Board board = new Board(new StandardBoardConfigurator());
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("e2"),
                        Board.getCoordinateAtPosition("e4"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("e7"),
                        Board.getCoordinateAtPosition("e5"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("f1"),
                        Board.getCoordinateAtPosition("c4"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("d7"),
                        Board.getCoordinateAtPosition("d6"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("g1"),
                        Board.getCoordinateAtPosition("f3"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("c8"),
                        Board.getCoordinateAtPosition("g4"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("b1"),
                        Board.getCoordinateAtPosition("c3"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("g7"),
                        Board.getCoordinateAtPosition("g6"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("f3"),
                        Board.getCoordinateAtPosition("e5"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("g4"),
                        Board.getCoordinateAtPosition("d1"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("c4"),
                        Board.getCoordinateAtPosition("f7"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("e8"),
                        Board.getCoordinateAtPosition("e7"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("c3"),
                        Board.getCoordinateAtPosition("d5"))));
        assertTrue(board.currentPlayer().isInCheckMate());
    }

    @Test
    public void testSevenMoveMate() {
        final Board board = new Board(new StandardBoardConfigurator());
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("e2"),
                        Board.getCoordinateAtPosition("e4"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("e7"),
                        Board.getCoordinateAtPosition("e5"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("d2"),
                        Board.getCoordinateAtPosition("d4"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("d7"),
                        Board.getCoordinateAtPosition("d6"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("d4"),
                        Board.getCoordinateAtPosition("e5"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("d8"),
                        Board.getCoordinateAtPosition("e7"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("e5"),
                        Board.getCoordinateAtPosition("d6"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("e7"),
                        Board.getCoordinateAtPosition("e4"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("f1"),
                        Board.getCoordinateAtPosition("e2"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("e4"),
                        Board.getCoordinateAtPosition("g2"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("d6"),
                        Board.getCoordinateAtPosition("c7"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("g2"),
                        Board.getCoordinateAtPosition("h1"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("d1"),
                        Board.getCoordinateAtPosition("d8"))));
        assertTrue(board.currentPlayer().isInCheckMate());
    }

    @Test
    public void testGrecoGame() {
        final Board board = new Board(new StandardBoardConfigurator());
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("d2"),
                        Board.getCoordinateAtPosition("d4"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("g8"),
                        Board.getCoordinateAtPosition("f6"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("b1"),
                        Board.getCoordinateAtPosition("d2"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("e7"),
                        Board.getCoordinateAtPosition("e5"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("d4"),
                        Board.getCoordinateAtPosition("e5"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("f6"),
                        Board.getCoordinateAtPosition("g4"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("h2"),
                        Board.getCoordinateAtPosition("h3"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("g4"),
                        Board.getCoordinateAtPosition("e3"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("f2"),
                        Board.getCoordinateAtPosition("e3"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("d8"),
                        Board.getCoordinateAtPosition("h4"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("g2"),
                        Board.getCoordinateAtPosition("g3"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("h4"),
                        Board.getCoordinateAtPosition("g3"))));
        assertTrue(board.currentPlayer().isInCheckMate());
    }

    @Test
    public void testOlympicGame() {
        final Board board = new Board(new StandardBoardConfigurator());
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("e2"),
                        Board.getCoordinateAtPosition("e4"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("c7"),
                        Board.getCoordinateAtPosition("c6"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("g1"),
                        Board.getCoordinateAtPosition("f3"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("d7"),
                        Board.getCoordinateAtPosition("d5"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("b1"),
                        Board.getCoordinateAtPosition("c3"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("d5"),
                        Board.getCoordinateAtPosition("e4"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("c3"),
                        Board.getCoordinateAtPosition("e4"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("b8"),
                        Board.getCoordinateAtPosition("d7"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("d1"),
                        Board.getCoordinateAtPosition("e2"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("g8"),
                        Board.getCoordinateAtPosition("f6"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("e4"),
                        Board.getCoordinateAtPosition("d6"))));
        assertTrue(board.currentPlayer().isInCheckMate());
    }

    @Test
    public void testAnotherGame() {
        final Board board = new Board(new StandardBoardConfigurator());
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("e2"),
                        Board.getCoordinateAtPosition("e4"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("e7"),
                        Board.getCoordinateAtPosition("e5"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("g1"),
                        Board.getCoordinateAtPosition("f3"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("b8"),
                        Board.getCoordinateAtPosition("c6"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("f1"),
                        Board.getCoordinateAtPosition("c4"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("c6"),
                        Board.getCoordinateAtPosition("d4"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("f3"),
                        Board.getCoordinateAtPosition("e5"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("d8"),
                        Board.getCoordinateAtPosition("g5"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("e5"),
                        Board.getCoordinateAtPosition("f7"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("g5"),
                        Board.getCoordinateAtPosition("g2"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("h1"),
                        Board.getCoordinateAtPosition("f1"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("g2"),
                        Board.getCoordinateAtPosition("e4"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("c4"),
                        Board.getCoordinateAtPosition("e2"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("d4"),
                        Board.getCoordinateAtPosition("f3"))));
        assertTrue(board.currentPlayer().isInCheckMate());
    }

    @Test
    public void testSmotheredMate() {
        final Board board = new Board(new StandardBoardConfigurator());
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("e2"),
                        Board.getCoordinateAtPosition("e4"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("e7"),
                        Board.getCoordinateAtPosition("e5"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("g1"),
                        Board.getCoordinateAtPosition("e2"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("b8"),
                        Board.getCoordinateAtPosition("c6"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("b1"),
                        Board.getCoordinateAtPosition("c3"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("c6"),
                        Board.getCoordinateAtPosition("d4"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("g2"),
                        Board.getCoordinateAtPosition("g3"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("d4"),
                        Board.getCoordinateAtPosition("f3"))));
        assertTrue(board.currentPlayer().isInCheckMate());
    }

    @Test
    public void testHippopotamusMate() {
        final Board board = new Board(new StandardBoardConfigurator());
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("e2"),
                        Board.getCoordinateAtPosition("e4"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("e7"),
                        Board.getCoordinateAtPosition("e5"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("g1"),
                        Board.getCoordinateAtPosition("e2"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("d8"),
                        Board.getCoordinateAtPosition("h4"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("b1"),
                        Board.getCoordinateAtPosition("c3"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("b8"),
                        Board.getCoordinateAtPosition("c6"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("g2"),
                        Board.getCoordinateAtPosition("g3"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("h4"),
                        Board.getCoordinateAtPosition("g5"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("d2"),
                        Board.getCoordinateAtPosition("d4"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("c6"),
                        Board.getCoordinateAtPosition("d4"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("c1"),
                        Board.getCoordinateAtPosition("g5"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("d4"),
                        Board.getCoordinateAtPosition("f3"))));
        assertTrue(board.currentPlayer().isInCheckMate());
    }

    @Test
    public void testBlackburneShillingMate() {
        final Board board = new Board(new StandardBoardConfigurator());
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("e2"),
                        Board.getCoordinateAtPosition("e4"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("e7"),
                        Board.getCoordinateAtPosition("e5"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("g1"),
                        Board.getCoordinateAtPosition("f3"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("b8"),
                        Board.getCoordinateAtPosition("c6"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("f1"),
                        Board.getCoordinateAtPosition("c4"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("c6"),
                        Board.getCoordinateAtPosition("d4"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("f3"),
                        Board.getCoordinateAtPosition("e5"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("d8"),
                        Board.getCoordinateAtPosition("g5"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("e5"),
                        Board.getCoordinateAtPosition("f7"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("g5"),
                        Board.getCoordinateAtPosition("g2"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("h1"),
                        Board.getCoordinateAtPosition("f1"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("g2"),
                        Board.getCoordinateAtPosition("e4"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("c4"),
                        Board.getCoordinateAtPosition("e2"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("d4"),
                        Board.getCoordinateAtPosition("f3"))));
        assertTrue(board.currentPlayer().isInCheckMate());
    }

    @Test
    public void testAnastasiaMate() {
        final Board board = new Board(new BoardConfigurator() {
            @Override
            public void setBoardPieces(final Board board) {
                board.clearGameBoard();
                // Black Layout
                board.setPiece(0, new Rook(Alliance.BLACK));
                board.setPiece(5, new Rook(Alliance.BLACK));
                board.setPiece(8, new Pawn(Alliance.BLACK));
                board.setPiece(9, new Pawn(Alliance.BLACK));
                board.setPiece(10, new Pawn(Alliance.BLACK));
                board.setPiece(13, new Pawn(Alliance.BLACK));
                board.setPiece(14, new Pawn(Alliance.BLACK));
                board.setPiece(15, new King(Alliance.BLACK));
                // White Layout
                board.setPiece(12, new Knight(Alliance.WHITE));
                board.setPiece(27, new Rook(Alliance.WHITE));
                board.setPiece(41, new Pawn(Alliance.WHITE));
                board.setPiece(48, new Pawn(Alliance.WHITE));
                board.setPiece(53, new Pawn(Alliance.WHITE));
                board.setPiece(54, new Pawn(Alliance.WHITE));
                board.setPiece(55, new Pawn(Alliance.WHITE));
                board.setPiece(62, new King(Alliance.WHITE));
            }

            @Override
            public void setCurrentPlayer(final Board board) {
                board.setCurrentPlayer(board.whitePlayer());
            }
        });
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("d5"),
                        Board.getCoordinateAtPosition("h5"))));
        assertTrue(board.currentPlayer().isInCheckMate());
    }

    @Test
    public void testTwoBishopMate() {
        final Board board = new Board(new BoardConfigurator() {
            @Override
            public void setBoardPieces(final Board board) {
                board.clearGameBoard();
                // Black Layout
                board.setPiece(7, new King(Alliance.BLACK));
                board.setPiece(8, new Pawn(Alliance.BLACK));
                board.setPiece(10, new Pawn(Alliance.BLACK));
                board.setPiece(15, new Pawn(Alliance.BLACK));
                board.setPiece(17, new Pawn(Alliance.BLACK));
                // White Layout
                board.setPiece(40, new Bishop(Alliance.WHITE));
                board.setPiece(48, new Bishop(Alliance.WHITE));
                board.setPiece(53, new King(Alliance.WHITE));
                board.setCurrentPlayer(board.whitePlayer());
            }

            @Override
            public void setCurrentPlayer(final Board board) {
                board.setCurrentPlayer(board.whitePlayer());
            }
        });
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("a3"),
                        Board.getCoordinateAtPosition("b2"))));
        assertTrue(board.currentPlayer().isInCheckMate());
    }

    @Test
    public void testQueenRookMate() {
        final Board board = new Board(new BoardConfigurator() {
            @Override
            public void setBoardPieces(final Board board) {
                board.clearGameBoard();
                // Black Layout
                board.setPiece(5, new King(Alliance.BLACK));
                // White Layout
                board.setPiece(9, new Rook(Alliance.WHITE));
                board.setPiece(16, new Queen(Alliance.WHITE));
                board.setPiece(59, new King(Alliance.WHITE));
                board.setCurrentPlayer(board.whitePlayer());
            }

            @Override
            public void setCurrentPlayer(final Board board) {
                board.setCurrentPlayer(board.whitePlayer());
            }
        });
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("a6"),
                        Board.getCoordinateAtPosition("a8"))));
        assertTrue(board.currentPlayer().isInCheckMate());
    }

    @Test
    public void testQueenKnightMate() {
        final Board board = new Board(new BoardConfigurator() {
            @Override
            public void setBoardPieces(final Board board) {
                board.clearGameBoard();
                // Black Layout
                board.setPiece(4, new King(Alliance.BLACK));
                // White Layout
                board.setPiece(15, new Queen(Alliance.WHITE));
                board.setPiece(29, new Knight(Alliance.WHITE));
                board.setPiece(55, new Pawn(Alliance.WHITE));
                board.setPiece(60, new King(Alliance.WHITE));
            }

            @Override
            public void setCurrentPlayer(final Board board) {
                board.setCurrentPlayer(board.whitePlayer());
            }
        });
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("h7"),
                        Board.getCoordinateAtPosition("e7"))));
        assertTrue(board.currentPlayer().isInCheckMate());
    }

    @Test
    public void testBackRankMate() {
        final Board board = new Board(new BoardConfigurator() {
            @Override
            public void setBoardPieces(final Board board) {
                board.clearGameBoard();
                // Black Layout
                board.setPiece(4, new King(Alliance.BLACK));
                board.setPiece(18, new Rook(Alliance.BLACK));
                // White Layout
                board.setPiece(53, new Pawn(Alliance.WHITE));
                board.setPiece(54, new Pawn(Alliance.WHITE));
                board.setPiece(55, new Pawn(Alliance.WHITE));
                board.setPiece(62, new King(Alliance.WHITE));
            }

            @Override
            public void setCurrentPlayer(final Board board) {
                board.setCurrentPlayer(board.blackPlayer());
            }
        });
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("c6"),
                        Board.getCoordinateAtPosition("c1"))));
        assertTrue(board.currentPlayer().isInCheckMate());
    }

    @Test
    public void testSeaCadetMate() {
        //        final Board board = new Board(new StandardBoardConfigurator());
        //
        //        Player.makeMove(board, MoveFactory.createMove(board, Board.getCoordinateAtPosition("e2"), Board.getCoordinateAtPosition("e4")));
        //        Player.makeMove(board, MoveFactory.createMove(board, Board.getCoordinateAtPosition("e7"), Board.getCoordinateAtPosition("e5")));
        //        Player.makeMove(board, MoveFactory.createMove(board, Board.getCoordinateAtPosition("g1"), Board.getCoordinateAtPosition("f3")));
        //        Player.makeMove(board, MoveFactory.createMove(board, Board.getCoordinateAtPosition("b8"), Board.getCoordinateAtPosition("c6")));
        //        Player.makeMove(board, MoveFactory.createMove(board, Board.getCoordinateAtPosition("d2"), Board.getCoordinateAtPosition("d4")));
        //        Player.makeMove(board, MoveFactory.createMove(board, Board.getCoordinateAtPosition("e5"), Board.getCoordinateAtPosition("d4")));
        //        Player.makeMove(board, MoveFactory.createMove(board, Board.getCoordinateAtPosition("c2"), Board.getCoordinateAtPosition("c3")));
        //        Player.makeMove(board, MoveFactory.createMove(board, Board.getCoordinateAtPosition("d4"), Board.getCoordinateAtPosition("c3")));
        //        Player.makeMove(board, MoveFactory.createMove(board, Board.getCoordinateAtPosition("b1"), Board.getCoordinateAtPosition("c3")));
        //        Player.makeMove(board, MoveFactory.createMove(board, Board.getCoordinateAtPosition("d7"), Board.getCoordinateAtPosition("d6")));
        //        Player.makeMove(board, MoveFactory.createMove(board, Board.getCoordinateAtPosition("f1"), Board.getCoordinateAtPosition("c4")));
        //        Player.makeMove(board, MoveFactory.createMove(board, Board.getCoordinateAtPosition("c8"), Board.getCoordinateAtPosition("g4")));
        //
        //
        //        Player.makeMove(board, MoveFactory.createMove(board, Board.getCoordinateAtPosition("c4"), Board.getCoordinateAtPosition("e2")));
        //
        //
        //        Player.makeMove(board, MoveFactory.createMove(board, Board.getCoordinateAtPosition("c4"), Board.getCoordinateAtPosition("e2")));
        //        Player.makeMove(board, MoveFactory.createMove(board, Board.getCoordinateAtPosition("c4"), Board.getCoordinateAtPosition("e2")));
        //        Player.makeMove(board, MoveFactory.createMove(board, Board.getCoordinateAtPosition("c4"), Board.getCoordinateAtPosition("e2")));
        //        Player.makeMove(board, MoveFactory.createMove(board, Board.getCoordinateAtPosition("d4"), Board.getCoordinateAtPosition("f3")));
        //        assertTrue(board.currentPlayer().isInCheckMate());
    }

}
