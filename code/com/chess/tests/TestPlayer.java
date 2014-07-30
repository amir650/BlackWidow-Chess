package com.chess.tests;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.chess.engine.classic.Alliance;
import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.board.Board.Builder;
import com.chess.engine.classic.board.Board.MoveStatus;
import com.chess.engine.classic.board.Move.MoveFactory;
import com.chess.engine.classic.board.MoveTransition;
import com.chess.engine.classic.pieces.Bishop;
import com.chess.engine.classic.pieces.King;
import com.chess.engine.classic.pieces.Rook;
import com.chess.engine.classic.player.ai.SimpleBoardEvaluator;

public class TestPlayer {

    @Test
    public void testSimpleEvaluation() {

        final Board board = Board.createStandardBoard();

        final MoveTransition t1 = board.makeMove(
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("e2"), Board.getCoordinateAtPosition("e4")));

        assertEquals(MoveStatus.DONE, t1.getMoveStatus());

        final MoveTransition t2 = t1.getTransitionBoard().makeMove(MoveFactory.createMove(t1.getTransitionBoard(), Board.getCoordinateAtPosition("e7"),
                Board.getCoordinateAtPosition("e5")));

        assertEquals(MoveStatus.DONE, t2.getMoveStatus());

        assertEquals(new SimpleBoardEvaluator().evaluate(t2.getTransitionBoard()), 0);
    }

    @Test
    public void testBug() {
        final Board board = Board.createStandardBoard();

        final MoveTransition t1 = board.makeMove(MoveFactory.createMove(board, Board.getCoordinateAtPosition("c2"),
                Board.getCoordinateAtPosition("c3")));

        assertEquals(MoveStatus.DONE, t1.getMoveStatus());

        final MoveTransition t2 = t1.getTransitionBoard().makeMove(MoveFactory.createMove(t1.getTransitionBoard(), Board.getCoordinateAtPosition("b8"),
                Board.getCoordinateAtPosition("a6")));

        assertEquals(MoveStatus.DONE, t2.getMoveStatus());

        final MoveTransition t3 = t2.getTransitionBoard().makeMove(MoveFactory.createMove(t2.getTransitionBoard(), Board.getCoordinateAtPosition("d1"),
                Board.getCoordinateAtPosition("a4")));

        assertEquals(MoveStatus.DONE, t3.getMoveStatus());

        final MoveTransition t4 = t3.getTransitionBoard().makeMove(MoveFactory.createMove(t3.getTransitionBoard(), Board.getCoordinateAtPosition("d7"),
                Board.getCoordinateAtPosition("d6")));

        assertEquals(MoveStatus.ILLEGAL_LEAVES_PLAYER_IN_CHECK, t4.getMoveStatus());
    }

    @Test
    public void testDiscoveredCheck() {

        Board.Builder builder = new Builder();

        // Black Layout
        builder.setPiece(4, new King(Alliance.BLACK, 4));
        builder.setPiece(24, new Rook(Alliance.BLACK, 24));
        // White Layout
        builder.setPiece(44, new Bishop(Alliance.WHITE, 44));
        builder.setPiece(52, new Rook(Alliance.WHITE, 52));
        builder.setPiece(58, new King(Alliance.WHITE, 58));

        builder.setMoveMaker(Alliance.WHITE);

        final Board board = builder.build();

        final MoveTransition t1 = board.makeMove(MoveFactory.createMove(board, Board.getCoordinateAtPosition("e3"),
                Board.getCoordinateAtPosition("b6")));

        assertEquals(MoveStatus.DONE, t1.getMoveStatus());
        assertTrue(t1.getTransitionBoard().currentPlayer().isInCheck());

        final MoveTransition t2 = t1.getTransitionBoard().makeMove(MoveFactory.createMove(t1.getTransitionBoard(), Board.getCoordinateAtPosition("a5"),
                Board.getCoordinateAtPosition("b5")));

        assertEquals(MoveStatus.ILLEGAL_LEAVES_PLAYER_IN_CHECK, t2.getMoveStatus());

        final MoveTransition t3 = t1.getTransitionBoard().makeMove(MoveFactory.createMove(t1.getTransitionBoard(), Board.getCoordinateAtPosition("a5"),
                Board.getCoordinateAtPosition("e5")));

        assertEquals(MoveStatus.DONE, t3.getMoveStatus());
    }

}
