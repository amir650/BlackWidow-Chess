package com.chess.tests;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.board.Board.MoveStatus;
import com.chess.engine.classic.board.Move;
import com.chess.engine.classic.board.StandardBoardConfigurator;
import com.chess.engine.classic.player.Player;
import com.chess.engine.classic.player.ai.AlphaBeta;

public class TestCastling {

    @Test
    public void testWhiteKingSideCastle() {

        final Board board = new Board(new StandardBoardConfigurator());

        assertEquals(MoveStatus.DONE, Player.makeMove(board, Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("e2"), Board.getCoordinateAtPosition("e4"))));

        assertEquals(MoveStatus.DONE, Player.makeMove(board, Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("e7"), Board.getCoordinateAtPosition("e5"))));

        assertEquals(MoveStatus.DONE, Player.makeMove(board, Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("g1"), Board.getCoordinateAtPosition("f3"))));

        assertEquals(MoveStatus.DONE, Player.makeMove(board, Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("d7"), Board.getCoordinateAtPosition("d6"))));

        assertEquals(MoveStatus.DONE, Player.makeMove(board, Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("f1"), Board.getCoordinateAtPosition("e2"))));

        assertEquals(MoveStatus.DONE, Player.makeMove(board, Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("d6"), Board.getCoordinateAtPosition("d5"))));

        final Move wm1 = Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("e1"), Board.getCoordinateAtPosition("g1"));
        assertTrue(board.currentPlayer().getLegalMoves().contains(wm1));
        assertEquals(MoveStatus.DONE, Player.makeMove(board, wm1));
        assertTrue(board.whitePlayer().getPlayerKing().isCastled());

    }

    @Test
    public void testWhiteQueenSideCastle() {

        final Board board = new Board(new StandardBoardConfigurator());

        assertEquals(MoveStatus.DONE, Player.makeMove(board, Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("e2"), Board.getCoordinateAtPosition("e4"))));

        assertEquals(MoveStatus.DONE, Player.makeMove(board, Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("e7"), Board.getCoordinateAtPosition("e5"))));

        assertEquals(MoveStatus.DONE, Player.makeMove(board, Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("d2"), Board.getCoordinateAtPosition("d3"))));

        assertEquals(MoveStatus.DONE, Player.makeMove(board, Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("d7"), Board.getCoordinateAtPosition("d6"))));

        assertEquals(MoveStatus.DONE, Player.makeMove(board, Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("c1"), Board.getCoordinateAtPosition("d2"))));

        assertEquals(MoveStatus.DONE, Player.makeMove(board, Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("d6"), Board.getCoordinateAtPosition("d5"))));

        assertEquals(MoveStatus.DONE, Player.makeMove(board, Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("d1"), Board.getCoordinateAtPosition("e2"))));

        assertEquals(MoveStatus.DONE, Player.makeMove(board, Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("h7"), Board.getCoordinateAtPosition("h6"))));

        assertEquals(MoveStatus.DONE, Player.makeMove(board, Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("b1"), Board.getCoordinateAtPosition("c3"))));

        assertEquals(MoveStatus.DONE, Player.makeMove(board, Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("h6"), Board.getCoordinateAtPosition("h5"))));

        final Move wm1 = Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("e1"), Board.getCoordinateAtPosition("c1"));

        assertTrue(board.currentPlayer().getLegalMoves().contains(wm1));
        assertEquals(MoveStatus.DONE, Player.makeMove(board, wm1));
        assertTrue(board.whitePlayer().getPlayerKing().isCastled());
    }

    @Test
    public void testBlackKingSideCastle() {
        final Board board = new Board(new StandardBoardConfigurator());

        assertEquals(MoveStatus.DONE, Player.makeMove(board, Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("e2"), Board.getCoordinateAtPosition("e4"))));

        assertEquals(MoveStatus.DONE, Player.makeMove(board, Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("e7"), Board.getCoordinateAtPosition("e5"))));

        assertEquals(MoveStatus.DONE, Player.makeMove(board, Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("d2"), Board.getCoordinateAtPosition("d3"))));

        assertEquals(MoveStatus.DONE, Player.makeMove(board, Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("g8"), Board.getCoordinateAtPosition("f6"))));

        assertEquals(MoveStatus.DONE, Player.makeMove(board, Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("d3"), Board.getCoordinateAtPosition("d4"))));

        assertEquals(MoveStatus.DONE, Player.makeMove(board, Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("f8"), Board.getCoordinateAtPosition("e7"))));

        assertEquals(MoveStatus.DONE, Player.makeMove(board, Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("d4"), Board.getCoordinateAtPosition("d5"))));

        final Move wm1 = Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("e8"), Board.getCoordinateAtPosition("g8"));
        assertTrue(board.currentPlayer().getLegalMoves().contains(wm1));
        assertEquals(MoveStatus.DONE, Player.makeMove(board, wm1));
        assertTrue(board.blackPlayer().getPlayerKing().isCastled());

    }

    @Test
    public void testBlackQueenSideCastle() {

        final Board board = new Board(new StandardBoardConfigurator());

        assertEquals(MoveStatus.DONE, Player.makeMove(board, Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("e2"), Board.getCoordinateAtPosition("e4"))));

        assertEquals(MoveStatus.DONE, Player.makeMove(board, Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("e7"), Board.getCoordinateAtPosition("e5"))));

        assertEquals(MoveStatus.DONE, Player.makeMove(board, Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("d2"), Board.getCoordinateAtPosition("d3"))));

        assertEquals(MoveStatus.DONE, Player.makeMove(board, Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("d8"), Board.getCoordinateAtPosition("e7"))));

        assertEquals(MoveStatus.DONE, Player.makeMove(board, Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("b1"), Board.getCoordinateAtPosition("c3"))));

        assertEquals(MoveStatus.DONE, Player.makeMove(board, Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("b8"), Board.getCoordinateAtPosition("c6"))));

        assertEquals(MoveStatus.DONE, Player.makeMove(board, Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("c1"), Board.getCoordinateAtPosition("d2"))));

        assertEquals(MoveStatus.DONE, Player.makeMove(board, Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("d7"), Board.getCoordinateAtPosition("d6"))));

        assertEquals(MoveStatus.DONE, Player.makeMove(board, Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("f1"), Board.getCoordinateAtPosition("e2"))));

        assertEquals(MoveStatus.DONE, Player.makeMove(board, Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("c8"), Board.getCoordinateAtPosition("d7"))));

        assertEquals(MoveStatus.DONE, Player.makeMove(board, Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("g1"), Board.getCoordinateAtPosition("f3"))));

        final Move wm1 = Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("e8"), Board.getCoordinateAtPosition("c8"));

        assertTrue(board.currentPlayer().getLegalMoves().contains(wm1));
        assertEquals(MoveStatus.DONE, Player.makeMove(board, wm1));
        assertTrue(board.blackPlayer().getPlayerKing().isCastled());
    }

    @Test
    public void testCastleUndoBug() {
        final Board board = new Board(new StandardBoardConfigurator());

        assertEquals(MoveStatus.DONE, Player.makeMove(board, Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("e2"), Board.getCoordinateAtPosition("e4"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board, Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("e7"), Board.getCoordinateAtPosition("e6"))));

        assertEquals(MoveStatus.DONE, Player.makeMove(board, Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("d2"), Board.getCoordinateAtPosition("d4"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board, Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("b8"), Board.getCoordinateAtPosition("c6"))));

        assertEquals(MoveStatus.DONE, Player.makeMove(board, Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("g1"), Board.getCoordinateAtPosition("f3"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board, Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("d7"), Board.getCoordinateAtPosition("d5"))));

        assertEquals(MoveStatus.DONE, Player.makeMove(board, Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("e4"), Board.getCoordinateAtPosition("e5"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board, Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("f7"), Board.getCoordinateAtPosition("f5"))));

        assertEquals(MoveStatus.DONE, Player.makeMove(board, Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("b1"), Board.getCoordinateAtPosition("c3"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board, Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("f8"), Board.getCoordinateAtPosition("b4"))));

        //
        assertEquals(MoveStatus.DONE, Player.makeMove(board, Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("c1"), Board.getCoordinateAtPosition("d2"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board, Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("b4"), Board.getCoordinateAtPosition("c3"))));

        assertEquals(MoveStatus.DONE, Player.makeMove(board, Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("d2"), Board.getCoordinateAtPosition("c3"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board, Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("g8"), Board.getCoordinateAtPosition("e7"))));

        assertEquals(MoveStatus.DONE, Player.makeMove(board, Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("f3"), Board.getCoordinateAtPosition("g5"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board, Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("e8"),
                        Board.getCoordinateAtPosition("g8"))));  //castle

        assertEquals(MoveStatus.DONE, Player.makeMove(board, Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("d1"), Board.getCoordinateAtPosition("h5"))));
        assertEquals(MoveStatus.DONE, Player.makeMove(board, Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("h7"), Board.getCoordinateAtPosition("h6"))));

        assertEquals(MoveStatus.DONE, Player.makeMove(board, Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("g5"), Board.getCoordinateAtPosition("h3"))));

        final Player currentPlayer = board.currentPlayer();
        currentPlayer.setMoveStrategy(new AlphaBeta());
        board.currentPlayer().getMoveStrategy().execute(board, 4);

    }
}
