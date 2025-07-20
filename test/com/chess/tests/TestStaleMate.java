package com.chess.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.chess.pgn.FenUtilities;
import org.junit.Test;

import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move.MoveFactory;
import com.chess.engine.board.MoveTransition;


public class TestStaleMate {
    @Test
    public void testAnandKramnikStaleMate() {
        final Board board = FenUtilities.createGameFromFEN("8/6p1/5p2/5P1K/4k2P/8/8/8 b - - 0 1");
        System.out.println(FenUtilities.createFENFromGame(board));
        assertFalse(board.currentPlayer().isInStaleMate());
        final MoveTransition t1 = board.currentPlayer()
                .makeMove(MoveFactory.createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("e4"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("f5")));
        assertTrue(t1.getMoveStatus().isDone());
        assertTrue(t1.getToBoard().currentPlayer().isInStaleMate());
        assertFalse(t1.getToBoard().currentPlayer().isInCheck());
        assertFalse(t1.getToBoard().currentPlayer().isInCheckMate());
    }

    @Test
    public void testAnonymousStaleMate() {
        final Board board = FenUtilities.createGameFromFEN("2k5/2P5/8/2K5/8/8/8/8 w - - 0 1");
        System.out.println(FenUtilities.createFENFromGame(board));
        assertFalse(board.currentPlayer().isInStaleMate());
        final MoveTransition t1 = board.currentPlayer()
                .makeMove(MoveFactory.createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("c5"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("c6")));
        assertTrue(t1.getMoveStatus().isDone());
        assertTrue(t1.getToBoard().currentPlayer().isInStaleMate());
        assertFalse(t1.getToBoard().currentPlayer().isInCheck());
        assertFalse(t1.getToBoard().currentPlayer().isInCheckMate());
    }

    @Test
    public void testAnonymousStaleMate2() {
        final Board board = FenUtilities.createGameFromFEN("k7/8/PK1B4/8/8/8/8/8 w - - 0 1");
        System.out.println(FenUtilities.createFENFromGame(board));
        assertFalse(board.currentPlayer().isInStaleMate());
        final MoveTransition t1 = board.currentPlayer()
                .makeMove(MoveFactory.createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("a6"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("a7")));
        assertTrue(t1.getMoveStatus().isDone());
        assertTrue(t1.getToBoard().currentPlayer().isInStaleMate());
        assertFalse(t1.getToBoard().currentPlayer().isInCheck());
        assertFalse(t1.getToBoard().currentPlayer().isInCheckMate());
    }
}