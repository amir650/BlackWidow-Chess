package com.chess.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.chess.engine.classic.Alliance;
import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.board.Board.MoveStatus;
import com.chess.engine.classic.board.BoardConfigurator;
import com.chess.engine.classic.board.Move;
import com.chess.engine.classic.board.Move.MoveFactory;
import com.chess.engine.classic.board.StandardBoardConfigurator;
import com.chess.engine.classic.board.Tile;
import com.chess.engine.classic.pieces.King;
import com.chess.engine.classic.pieces.Pawn;
import com.chess.engine.classic.player.Player;
import com.chess.engine.classic.player.ai.SimpleBoardEvaluator;

public class TestBoard {

    @Test
    public void initialBoard() {
        final Board board = new Board(new StandardBoardConfigurator());
        assertEquals(board.whitePlayer().getLegalMoves().size(), 20);
        assertEquals(board.blackPlayer().getLegalMoves().size(), 20);
        assertFalse(board.currentPlayer().isInCheck());
        assertFalse(board.currentPlayer().isInCheckMate());
        assertFalse(board.currentPlayer().getOpponent().isInCheck());
        assertFalse(board.currentPlayer().getOpponent().isInCheckMate());
        assertEquals(board.currentPlayer(), board.whitePlayer());
        assertEquals(board.currentPlayer().getOpponent(), board.blackPlayer());
        assertEquals(new SimpleBoardEvaluator().evaluate(board), 0);
    }

    @Test
    public void testKing() {
        final Board board = new Board(new BoardConfigurator() {
            @Override
            public void setBoardPieces(final Board board) {
                board.clearGameBoard();
                // Black Layout
                board.setPiece(4, new King(Alliance.BLACK));
                board.setPiece(12, new Pawn(Alliance.BLACK));
                // White Layout
                board.setPiece(52, new Pawn(Alliance.WHITE));
                board.setPiece(60, new King(Alliance.WHITE));
                board.setCurrentPlayer(board.whitePlayer());
            }

            @Override
            public void setCurrentPlayer(final Board board) {
                board.setCurrentPlayer(board.whitePlayer());
            }
        });
        assertEquals(board.whitePlayer().getLegalMoves().size(), 6);
        assertEquals(board.blackPlayer().getLegalMoves().size(), 6);
        assertFalse(board.currentPlayer().isInCheck());
        assertFalse(board.currentPlayer().isInCheckMate());
        assertFalse(board.currentPlayer().getOpponent().isInCheck());
        assertFalse(board.currentPlayer().getOpponent().isInCheckMate());
        assertEquals(board.currentPlayer(), board.whitePlayer());
        assertEquals(board.currentPlayer().getOpponent(), board.blackPlayer());
        assertEquals(new SimpleBoardEvaluator().evaluate(board), 0);
        final Board copy = board.createCopy();
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("e1"),
                        Board.getCoordinateAtPosition("f1"))));
        assertEquals(board.whitePlayer().getPlayerKing().getPiecePosition(), 61);
        assertEquals(copy.whitePlayer().getPlayerKing().getPiecePosition(), 60);
    }

    @Test
    public void testCopy() {
        final Board board = new Board(new StandardBoardConfigurator());
        assertEquals(board.whitePlayer().getLegalMoves().size(), 20);
        assertEquals(board.blackPlayer().getLegalMoves().size(), 20);
        assertFalse(board.currentPlayer().isInCheck());
        assertFalse(board.currentPlayer().isInCheckMate());
        assertFalse(board.currentPlayer().getOpponent().isInCheck());
        assertFalse(board.currentPlayer().getOpponent().isInCheckMate());
        assertEquals(board.currentPlayer(), board.whitePlayer());
        assertEquals(board.currentPlayer().getOpponent(), board.blackPlayer());
        assertEquals(new SimpleBoardEvaluator().evaluate(board), 0);
        final Board copy = board.createCopy();
        assertEquals(MoveStatus.DONE, Player.makeMove(copy,
                MoveFactory.createMove(copy, Board.getCoordinateAtPosition("e2"),
                        Board.getCoordinateAtPosition("e4"))));
        assertEquals(board.whitePlayer().getLegalMoves().size(), 20);
        assertEquals(board.blackPlayer().getLegalMoves().size(), 20);
        assertFalse(board.currentPlayer().isInCheck());
        assertFalse(board.currentPlayer().isInCheckMate());
        assertFalse(board.currentPlayer().getOpponent().isInCheck());
        assertFalse(board.currentPlayer().getOpponent().isInCheckMate());
        assertEquals(board.currentPlayer(), board.whitePlayer());
        assertEquals(board.currentPlayer().getOpponent(), board.blackPlayer());
        assertEquals(new SimpleBoardEvaluator().evaluate(board), 0);
    }

    @Test
    public void testMoveAndUnMoveSingle() {
        final Board board = new Board(new StandardBoardConfigurator());
        final Move m =
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("e2"), Board.getCoordinateAtPosition("e4"));
        assertEquals(board.currentPlayer(), board.whitePlayer());
        assertTrue(board.whitePlayer().getLegalMoves().contains(m));
        assertEquals(board.whitePlayer().getLegalMoves().size(), 20);
        assertEquals(MoveStatus.DONE, Player.makeMove(board, m));
        assertEquals(MoveStatus.UNDONE, Player.unMakeMove(board, m));
        assertEquals(board.currentPlayer(), board.whitePlayer());
        assertTrue(board.whitePlayer().getLegalMoves().contains(m));
        assertEquals(board.whitePlayer().getLegalMoves().size(), 20);
    }

    @Test
    public void testMoveAndUnMoveMulti() {
        final Board board = new Board(new StandardBoardConfigurator());
        assertEquals(board.currentPlayer(), board.whitePlayer());
        assertFalse(board.currentPlayer().isInCheck());
        assertEquals(board.whitePlayer().getLegalMoves().size(), 20);
        for (int i = 0; i < board.currentPlayer().getLegalMoves().size(); i++) {
            final Move m = board.currentPlayer().getLegalMoves().get(i);
            assertEquals(MoveStatus.DONE, Player.makeMove(board, m));
            assertEquals(MoveStatus.UNDONE, Player.unMakeMove(board, m));
        }
        assertEquals(board.currentPlayer(), board.whitePlayer());
        assertFalse(board.currentPlayer().isInCheck());
        assertEquals(board.whitePlayer().getLegalMoves().size(), 20);
    }

    @Test
    public void testBoardConsistency() {
        final Board board = new Board(new StandardBoardConfigurator());
        assertEquals(board.currentPlayer(), board.whitePlayer());

        Player.makeMove(board, MoveFactory.createMove(board, Board.getCoordinateAtPosition("e2"),
                Board.getCoordinateAtPosition("e4")));
        Player.makeMove(board, MoveFactory.createMove(board, Board.getCoordinateAtPosition("e7"),
                Board.getCoordinateAtPosition("e5")));

        Player.makeMove(board, MoveFactory.createMove(board, Board.getCoordinateAtPosition("g1"),
                Board.getCoordinateAtPosition("f3")));
        Player.makeMove(board, MoveFactory.createMove(board, Board.getCoordinateAtPosition("d7"),
                Board.getCoordinateAtPosition("d5")));

        Player.makeMove(board, MoveFactory.createMove(board, Board.getCoordinateAtPosition("e4"),
                Board.getCoordinateAtPosition("d5")));
        Player.makeMove(board, MoveFactory.createMove(board, Board.getCoordinateAtPosition("d8"),
                Board.getCoordinateAtPosition("d5")));

        Player.makeMove(board, MoveFactory.createMove(board, Board.getCoordinateAtPosition("f3"),
                Board.getCoordinateAtPosition("g5")));
        Player.makeMove(board, MoveFactory.createMove(board, Board.getCoordinateAtPosition("f7"),
                Board.getCoordinateAtPosition("f6")));

        Player.makeMove(board, MoveFactory.createMove(board, Board.getCoordinateAtPosition("d1"),
                Board.getCoordinateAtPosition("h5")));
        Player.makeMove(board, MoveFactory.createMove(board, Board.getCoordinateAtPosition("g7"),
                Board.getCoordinateAtPosition("g6")));

        Player.makeMove(board, MoveFactory.createMove(board, Board.getCoordinateAtPosition("h5"),
                Board.getCoordinateAtPosition("h4")));
        Player.makeMove(board, MoveFactory.createMove(board, Board.getCoordinateAtPosition("f6"),
                Board.getCoordinateAtPosition("g5")));

        Player.makeMove(board, MoveFactory.createMove(board, Board.getCoordinateAtPosition("h4"),
                Board.getCoordinateAtPosition("g5")));
        Player.makeMove(board, MoveFactory.createMove(board, Board.getCoordinateAtPosition("d5"),
                Board.getCoordinateAtPosition("e4")));

        assertTrue(board.whitePlayer().getActivePieces().size() == calculatedActivesFor(board, Alliance.WHITE));
        assertTrue(board.blackPlayer().getActivePieces().size() == calculatedActivesFor(board, Alliance.BLACK));

    }

    private static int calculatedActivesFor(final Board board, final Alliance desired) {
        int count = 0;
        for (final Tile t : board.getGameBoard()) {
            if (t.isTileOccupied() && t.getPiece().getPieceAllegiance() == desired) {
                count++;
            }
        }
        return count;
    }

}

