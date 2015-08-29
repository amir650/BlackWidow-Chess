package com.chess.tests;

import static org.junit.Assert.assertEquals;

import com.chess.engine.classic.pieces.Piece;
import org.junit.Ignore;
import org.junit.Test;

import com.chess.engine.classic.Alliance;
import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.board.Board.Builder;
import com.chess.engine.classic.board.Board.MoveStatus;
import com.chess.engine.classic.board.BoardUtils;
import com.chess.engine.classic.board.Move;
import com.chess.engine.classic.board.MoveTransition;
import com.chess.engine.classic.pieces.King;
import com.chess.engine.classic.pieces.Pawn;
import com.chess.engine.classic.pieces.Rook;

import java.util.Iterator;

public class TestPawn {

    @Test
    public void testPawnPromotion() {

        final Builder builder = new Builder();

        // Black Layout
        builder.setPiece(new Rook(Alliance.BLACK, 3));
        builder.setPiece(new King(Alliance.BLACK, 22));
        // White Layout
        builder.setPiece(new Pawn(Alliance.WHITE, 15));
        builder.setPiece(new King(Alliance.WHITE, 52));
        // Set the current player
        builder.setMoveMaker(Alliance.WHITE);

        final Board board = builder.build();

        final Move m1 = Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition(
                "h7"), BoardUtils.getCoordinateAtPosition("h8"));
        final MoveTransition t1 = board.currentPlayer().makeMove(m1);

        assertEquals(MoveStatus.DONE, t1.getMoveStatus());

        final Move m2 = Move.MoveFactory.createMove(t1.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("d8"), BoardUtils
                .getCoordinateAtPosition("h8"));
        final MoveTransition t2 = t1.getTransitionBoard().currentPlayer().makeMove(m2);

        assertEquals(MoveStatus.DONE, t2.getMoveStatus());

        final Move m3 = Move.MoveFactory.createMove(t2.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("e2"), BoardUtils
                .getCoordinateAtPosition("d2"));
        final MoveTransition t3 = board.currentPlayer().makeMove(m3);

        assertEquals(MoveStatus.DONE, t3.getMoveStatus());

    }

    @Test
    public void testSimpleWhiteEnPassant() {
        final Builder builder = new Builder();

        // Black Layout
        builder.setPiece(new King(Alliance.BLACK, 4));
        builder.setPiece(new Pawn(Alliance.BLACK, 11));
        // White Layout
        builder.setPiece(new Pawn(Alliance.WHITE, 52));
        builder.setPiece(new King(Alliance.WHITE, 60));
        // Set the current player
        builder.setMoveMaker(Alliance.WHITE);

        final Board board = builder.build();

        final Move m1 = Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition(
                "e2"), BoardUtils.getCoordinateAtPosition("e4"));
        final MoveTransition t1 = board.currentPlayer().makeMove(m1);

        assertEquals(MoveStatus.DONE, t1.getMoveStatus());

        final Move m2 = Move.MoveFactory.createMove(t1.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("e8"), BoardUtils
                .getCoordinateAtPosition("d8"));
        final MoveTransition t2 = t1.getTransitionBoard().currentPlayer().makeMove(m2);

        assertEquals(MoveStatus.DONE, t2.getMoveStatus());

        final Move m3 = Move.MoveFactory.createMove(t2.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("e4"), BoardUtils
                .getCoordinateAtPosition("e5"));
        final MoveTransition t3 = t2.getTransitionBoard().currentPlayer().makeMove(m3);

        assertEquals(MoveStatus.DONE, t3.getMoveStatus());

        final Move m4 = Move.MoveFactory.createMove(t3.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("d7"), BoardUtils
                .getCoordinateAtPosition("d5"));
        final MoveTransition t4 = t3.getTransitionBoard().currentPlayer().makeMove(m4);

        assertEquals(MoveStatus.DONE, t4.getMoveStatus());

        final Move m5 = Move.MoveFactory.createMove(t4.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("e5"), BoardUtils
                .getCoordinateAtPosition("d6"));
        final MoveTransition t5 = t4.getTransitionBoard().currentPlayer().makeMove(m5);

        assertEquals(MoveStatus.DONE, t5.getMoveStatus());

        System.out.println(t5.getTransitionBoard());
    }

    @Test
    public void testSimpleBlackEnPassant() {
        final Builder builder = new Builder();

        // Black Layout
        builder.setPiece(new King(Alliance.BLACK, 4));
        builder.setPiece(new Pawn(Alliance.BLACK, 11));
        // White Layout
        builder.setPiece(new Pawn(Alliance.WHITE, 52));
        builder.setPiece(new King(Alliance.WHITE, 60));
        // Set the current player
        builder.setMoveMaker(Alliance.WHITE);

        final Board board = builder.build();

        final Move m1 = Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition(
                "e1"), BoardUtils.getCoordinateAtPosition("d1"));
        final MoveTransition t1 = board.currentPlayer().makeMove(m1);

        assertEquals(MoveStatus.DONE, t1.getMoveStatus());

        final Move m2 = Move.MoveFactory.createMove(t1.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("d7"), BoardUtils
                .getCoordinateAtPosition("d5"));
        final MoveTransition t2 = t1.getTransitionBoard().currentPlayer().makeMove(m2);

        assertEquals(MoveStatus.DONE, t2.getMoveStatus());

        final Move m3 = Move.MoveFactory.createMove(t2.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("d1"), BoardUtils
                .getCoordinateAtPosition("c1"));
        final MoveTransition t3 = t2.getTransitionBoard().currentPlayer().makeMove(m3);

        assertEquals(MoveStatus.DONE, t3.getMoveStatus());

        final Move m4 = Move.MoveFactory.createMove(t3.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("d5"), BoardUtils
                .getCoordinateAtPosition("d4"));
        final MoveTransition t4 = t3.getTransitionBoard().currentPlayer().makeMove(m4);

        assertEquals(MoveStatus.DONE, t4.getMoveStatus());

        final Move m5 = Move.MoveFactory.createMove(t4.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("e2"), BoardUtils
                .getCoordinateAtPosition("e4"));
        final MoveTransition t5 = t4.getTransitionBoard().currentPlayer().makeMove(m5);

        assertEquals(MoveStatus.DONE, t5.getMoveStatus());

        final Move m6 = Move.MoveFactory.createMove(t5.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("d4"), BoardUtils
                .getCoordinateAtPosition("e3"));
        final MoveTransition t6 = t5.getTransitionBoard().currentPlayer().makeMove(m6);

        assertEquals(MoveStatus.DONE, t6.getMoveStatus());

        System.out.println(t6.getTransitionBoard());
    }

    @Test
    public void testEnPassant2() {

        final Board board = Board.createStandardBoard();

        final Move m1 = Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition(
                "e2"), BoardUtils.getCoordinateAtPosition("e3"));
        final MoveTransition t1 = board.currentPlayer().makeMove(m1);

        assertEquals(MoveStatus.DONE, t1.getMoveStatus());

        final Move m2 = Move.MoveFactory.createMove(t1.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("h7"), BoardUtils
                .getCoordinateAtPosition("h5"));
        final MoveTransition t2 = t1.getTransitionBoard().currentPlayer().makeMove(m2);

        assertEquals(MoveStatus.DONE, t2.getMoveStatus());

        final Move m3 = Move.MoveFactory.createMove(t2.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("e3"), BoardUtils
                .getCoordinateAtPosition("e4"));
        final MoveTransition t3 = t2.getTransitionBoard().currentPlayer().makeMove(m3);

        assertEquals(MoveStatus.DONE, t3.getMoveStatus());

        final Move m4 = Move.MoveFactory.createMove(t3.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("h5"), BoardUtils
                .getCoordinateAtPosition("h4"));
        final MoveTransition t4 = t3.getTransitionBoard().currentPlayer().makeMove(m4);

        assertEquals(MoveStatus.DONE, t4.getMoveStatus());

        final Move m5 = Move.MoveFactory.createMove(t4.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("g2"), BoardUtils
                .getCoordinateAtPosition("g4"));
        final MoveTransition t5 = t4.getTransitionBoard().currentPlayer().makeMove(m5);

        assertEquals(MoveStatus.DONE, t5.getMoveStatus());

        System.out.println(t5.getTransitionBoard());

        System.out.println(t5.getTransitionBoard().getBlackPieces().iterator().next().calculateLegalMoves(t5.getTransitionBoard()));


    }

}
