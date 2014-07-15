package com.chess.tests;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.chess.engine.classic.Alliance;
import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.board.Board.MoveStatus;
import com.chess.engine.classic.board.BoardConfigurator;
import com.chess.engine.classic.board.Move.MoveFactory;
import com.chess.engine.classic.board.StandardBoardConfigurator;
import com.chess.engine.classic.pieces.Bishop;
import com.chess.engine.classic.pieces.King;
import com.chess.engine.classic.pieces.Rook;
import com.chess.engine.classic.player.Player;
import com.chess.engine.classic.player.ai.SimpleBoardEvaluator;

public class TestPlayer {

    @Test
    public void testSimpleEvaluation() {
        final Board board = new Board(new StandardBoardConfigurator());
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("e2"),
                        Board.getCoordinateAtPosition("e4"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("e7"),
                        Board.getCoordinateAtPosition("e5"))));
        assertEquals(new SimpleBoardEvaluator().evaluate(board), 0);
    }

    @Test
    public void testBug() {
        final Board board = new Board(new StandardBoardConfigurator());
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("c2"),
                        Board.getCoordinateAtPosition("c3"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("b8"),
                        Board.getCoordinateAtPosition("a6"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("d1"),
                        Board.getCoordinateAtPosition("a4"))));
        assertEquals(Player.makeMove(board, MoveFactory.createMove(board, Board.getCoordinateAtPosition("d7"),
                Board.getCoordinateAtPosition("d6"))), MoveStatus.ILLEGAL_LEAVES_PLAYER_IN_CHECK);
    }

    @Test
    public void testDiscoveredCheck() {
        final Board board = new Board(new BoardConfigurator() {
            @Override
            public void setBoardPieces(final Board board) {
                board.clearGameBoard();
                // Black Layout
                board.setPiece(4, new King(Alliance.BLACK));
                board.setPiece(24, new Rook(Alliance.BLACK));
                // White Layout
                board.setPiece(44, new Bishop(Alliance.WHITE));
                board.setPiece(52, new Rook(Alliance.WHITE));
                board.setPiece(58, new King(Alliance.WHITE));
            }

            @Override
            public void setCurrentPlayer(final Board board) {
                board.setCurrentPlayer(board.whitePlayer());
            }
        });
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("e3"),
                        Board.getCoordinateAtPosition("b6"))));
        assertTrue(board.currentPlayer().isInCheck());
        assertEquals(MoveStatus.ILLEGAL_LEAVES_PLAYER_IN_CHECK, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("a5"),
                        Board.getCoordinateAtPosition("b5"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board,
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("a5"),
                        Board.getCoordinateAtPosition("e5"))));
    }

}
