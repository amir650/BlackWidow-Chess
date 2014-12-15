package com.chess.tests;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import com.chess.engine.classic.Alliance;
import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.board.Board.Builder;
import com.chess.engine.classic.board.Board.MoveStatus;
import com.chess.engine.classic.board.Move.MoveFactory;
import com.chess.engine.classic.board.MoveTransition;
import com.chess.engine.classic.board.Tile;
import com.chess.engine.classic.pieces.King;
import com.chess.engine.classic.pieces.Pawn;
import com.chess.engine.classic.player.ai.SimpleBoardEvaluator;

public class TestBoard {

    @Test
    public void initialBoard() {
        final Board board = Board.createStandardBoard();
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
    public void testPlainKingMove() {

        final Builder builder = new Builder();
        // Black Layout
        builder.setPiece(new King(Alliance.BLACK, 4));
        builder.setPiece(new Pawn(Alliance.BLACK, 12));
        // White Layout
        builder.setPiece(new Pawn(Alliance.WHITE, 52));
        builder.setPiece(new King(Alliance.WHITE, 60));
        builder.setMoveMaker(Alliance.WHITE);
        // Set the current player
        final Board board = builder.build();

        assertEquals(board.whitePlayer().getLegalMoves().size(), 6);
        assertEquals(board.blackPlayer().getLegalMoves().size(), 6);
        assertFalse(board.currentPlayer().isInCheck());
        assertFalse(board.currentPlayer().isInCheckMate());
        assertFalse(board.currentPlayer().getOpponent().isInCheck());
        assertFalse(board.currentPlayer().getOpponent().isInCheckMate());
        assertEquals(board.currentPlayer(), board.whitePlayer());
        assertEquals(board.currentPlayer().getOpponent(), board.blackPlayer());
        assertEquals(new SimpleBoardEvaluator().evaluate(board), 0);

        final MoveTransition moveTransition = board.makeMove(
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("e1"), Board.getCoordinateAtPosition("f1")));

        assertEquals(MoveStatus.DONE, moveTransition.getMoveStatus());
        assertEquals(moveTransition.getTransitionBoard().whitePlayer().getPlayerKing().getPiecePosition(), 61);
    }

    @Test
    public void testBoardConsistency() {
        final Board board = Board.createStandardBoard();
        assertEquals(board.currentPlayer(), board.whitePlayer());

        final MoveTransition t1 = board.makeMove(
                MoveFactory.createMove(board, Board.getCoordinateAtPosition("e2"), Board.getCoordinateAtPosition("e4")));
        final MoveTransition t2 = t1.getTransitionBoard().makeMove(MoveFactory.createMove(t1.getTransitionBoard(), Board.getCoordinateAtPosition("e7"),
                Board.getCoordinateAtPosition("e5")));

        final MoveTransition t3 = t2.getTransitionBoard().makeMove(MoveFactory.createMove(t2.getTransitionBoard(), Board.getCoordinateAtPosition("g1"),
                Board.getCoordinateAtPosition("f3")));
        final MoveTransition t4 = t3.getTransitionBoard().makeMove(MoveFactory.createMove(t3.getTransitionBoard(), Board.getCoordinateAtPosition("d7"),
                Board.getCoordinateAtPosition("d5")));

        final MoveTransition t5 = t4.getTransitionBoard().makeMove(MoveFactory.createMove(t4.getTransitionBoard(), Board.getCoordinateAtPosition("e4"),
                Board.getCoordinateAtPosition("d5")));
        final MoveTransition t6 = t5.getTransitionBoard().makeMove(MoveFactory.createMove(t5.getTransitionBoard(), Board.getCoordinateAtPosition("d8"),
                Board.getCoordinateAtPosition("d5")));

        final MoveTransition t7 = t6.getTransitionBoard().makeMove(MoveFactory.createMove(t6.getTransitionBoard(), Board.getCoordinateAtPosition("f3"),
                Board.getCoordinateAtPosition("g5")));
        final MoveTransition t8 = t7.getTransitionBoard().makeMove(MoveFactory.createMove(t7.getTransitionBoard(), Board.getCoordinateAtPosition("f7"),
                Board.getCoordinateAtPosition("f6")));

        final MoveTransition t9 = t8.getTransitionBoard().makeMove(MoveFactory.createMove(t8.getTransitionBoard(), Board.getCoordinateAtPosition("d1"),
                Board.getCoordinateAtPosition("h5")));
        final MoveTransition t10 = t9.getTransitionBoard().makeMove(MoveFactory.createMove(t9.getTransitionBoard(), Board.getCoordinateAtPosition("g7"),
                Board.getCoordinateAtPosition("g6")));

        final MoveTransition t11 = t10.getTransitionBoard().makeMove(MoveFactory.createMove(t10.getTransitionBoard(), Board.getCoordinateAtPosition("h5"),
                Board.getCoordinateAtPosition("h4")));
        final MoveTransition t12 = t11.getTransitionBoard().makeMove(MoveFactory.createMove(t11.getTransitionBoard(), Board.getCoordinateAtPosition("f6"),
                Board.getCoordinateAtPosition("g5")));

        final MoveTransition t13 = t12.getTransitionBoard().makeMove(MoveFactory.createMove(t12.getTransitionBoard(), Board.getCoordinateAtPosition("h4"),
                Board.getCoordinateAtPosition("g5")));
        final MoveTransition t14 = t13.getTransitionBoard().makeMove(MoveFactory.createMove(t13.getTransitionBoard(), Board.getCoordinateAtPosition("d5"),
                Board.getCoordinateAtPosition("e4")));

        assertTrue(t14.getTransitionBoard().whitePlayer().getActivePieces().size() == calculatedActivesFor(t14.getTransitionBoard(), Alliance.WHITE));
        assertTrue(t14.getTransitionBoard().blackPlayer().getActivePieces().size() == calculatedActivesFor(t14.getTransitionBoard(), Alliance.BLACK));

    }

    private static int calculatedActivesFor(final Board board,
                                            final Alliance alliance) {
        int count = 0;
        for (final Tile t : board.getGameBoard()) {
            if (t.isTileOccupied() && t.getPiece().getPieceAllegiance() == alliance) {
                count++;
            }
        }
        return count;
    }

}

