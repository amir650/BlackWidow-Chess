package com.chess.tests;

import com.chess.engine.classic.Alliance;
import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.board.BoardUtils;
import com.chess.engine.classic.board.Move;
import com.chess.engine.classic.board.MoveTransition;
import com.chess.engine.classic.pieces.*;
import com.google.common.collect.Sets;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;
import java.util.Set;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertNotNull;

public class TestPieces {

    @Test
    public void testMiddleQueenOnEmptyBoard() {
        final Board.Builder builder = new Board.Builder();
        // Black Layout
        builder.setPiece(new King(Alliance.BLACK, 4, false, false));
        // White Layout
        builder.setPiece(new Queen(Alliance.WHITE, 36));
        builder.setPiece(new King(Alliance.WHITE, 60, false, false));
        // Set the current player
        builder.setMoveMaker(Alliance.WHITE);
        final Board board = builder.build();
        final Collection<Move> whiteLegals = board.whitePlayer().getLegalMoves();
        final Collection<Move> blackLegals = board.blackPlayer().getLegalMoves();
        assertEquals(whiteLegals.size(), 31);
        assertEquals(blackLegals.size(), 5);
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("e4"), BoardUtils.INSTANCE.getCoordinateAtPosition("e8"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("e4"), BoardUtils.INSTANCE.getCoordinateAtPosition("e7"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("e4"), BoardUtils.INSTANCE.getCoordinateAtPosition("e6"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("e4"), BoardUtils.INSTANCE.getCoordinateAtPosition("e5"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("e4"), BoardUtils.INSTANCE.getCoordinateAtPosition("e3"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("e4"), BoardUtils.INSTANCE.getCoordinateAtPosition("e2"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("e4"), BoardUtils.INSTANCE.getCoordinateAtPosition("a4"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("e4"), BoardUtils.INSTANCE.getCoordinateAtPosition("b4"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("e4"), BoardUtils.INSTANCE.getCoordinateAtPosition("c4"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("e4"), BoardUtils.INSTANCE.getCoordinateAtPosition("d4"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("e4"), BoardUtils.INSTANCE.getCoordinateAtPosition("f4"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("e4"), BoardUtils.INSTANCE.getCoordinateAtPosition("g4"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("e4"), BoardUtils.INSTANCE.getCoordinateAtPosition("h4"))));
    }

    @Test
    public void testLegalMoveAllAvailable() {

        final Board.Builder boardBuilder = new Board.Builder();
        // Black Layout
        boardBuilder.setPiece(new King(Alliance.BLACK, 4, false, false));
        boardBuilder.setPiece(new Knight(Alliance.BLACK, 28));
        // White Layout
        boardBuilder.setPiece(new Knight(Alliance.WHITE, 36));
        boardBuilder.setPiece(new King(Alliance.WHITE, 60, false, false));
        // Set the current player
        boardBuilder.setMoveMaker(Alliance.WHITE);
        final Board board = boardBuilder.build();
        final Collection<Move> whiteLegals = board.whitePlayer().getLegalMoves();
        assertEquals(whiteLegals.size(), 13);
        final Move wm1 = Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("e4"), BoardUtils.INSTANCE.getCoordinateAtPosition("d6"));
        final Move wm2 = Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("e4"), BoardUtils.INSTANCE.getCoordinateAtPosition("f6"));
        final Move wm3 = Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("e4"), BoardUtils.INSTANCE.getCoordinateAtPosition("c5"));
        final Move wm4 = Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("e4"), BoardUtils.INSTANCE.getCoordinateAtPosition("g5"));
        final Move wm5 = Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("e4"), BoardUtils.INSTANCE.getCoordinateAtPosition("c3"));
        final Move wm6 = Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("e4"), BoardUtils.INSTANCE.getCoordinateAtPosition("g3"));
        final Move wm7 = Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("e4"), BoardUtils.INSTANCE.getCoordinateAtPosition("d2"));
        final Move wm8 = Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("e4"), BoardUtils.INSTANCE.getCoordinateAtPosition("f2"));

        assertTrue(whiteLegals.contains(wm1));
        assertTrue(whiteLegals.contains(wm2));
        assertTrue(whiteLegals.contains(wm3));
        assertTrue(whiteLegals.contains(wm4));
        assertTrue(whiteLegals.contains(wm5));
        assertTrue(whiteLegals.contains(wm6));
        assertTrue(whiteLegals.contains(wm7));
        assertTrue(whiteLegals.contains(wm8));

        final Board.Builder boardBuilder2 = new Board.Builder();
        // Black Layout
        boardBuilder2.setPiece(new King(Alliance.BLACK, 4, false, false));
        boardBuilder2.setPiece(new Knight(Alliance.BLACK, 28));
        // White Layout
        boardBuilder2.setPiece(new Knight(Alliance.WHITE, 36));
        boardBuilder2.setPiece(new King(Alliance.WHITE, 60, false, false));
        // Set the current player
        boardBuilder2.setMoveMaker(Alliance.BLACK);
        final Board board2 = boardBuilder2.build();
        final Collection<Move> blackLegals = board.blackPlayer().getLegalMoves();

        final Move bm1 = Move.MoveFactory
                .createMove(board2, BoardUtils.INSTANCE.getCoordinateAtPosition("e5"), BoardUtils.INSTANCE.getCoordinateAtPosition("d7"));
        final Move bm2 = Move.MoveFactory
                .createMove(board2, BoardUtils.INSTANCE.getCoordinateAtPosition("e5"), BoardUtils.INSTANCE.getCoordinateAtPosition("f7"));
        final Move bm3 = Move.MoveFactory
                .createMove(board2, BoardUtils.INSTANCE.getCoordinateAtPosition("e5"), BoardUtils.INSTANCE.getCoordinateAtPosition("c6"));
        final Move bm4 = Move.MoveFactory
                .createMove(board2, BoardUtils.INSTANCE.getCoordinateAtPosition("e5"), BoardUtils.INSTANCE.getCoordinateAtPosition("g6"));
        final Move bm5 = Move.MoveFactory
                .createMove(board2, BoardUtils.INSTANCE.getCoordinateAtPosition("e5"), BoardUtils.INSTANCE.getCoordinateAtPosition("c4"));
        final Move bm6 = Move.MoveFactory
                .createMove(board2, BoardUtils.INSTANCE.getCoordinateAtPosition("e5"), BoardUtils.INSTANCE.getCoordinateAtPosition("g4"));
        final Move bm7 = Move.MoveFactory
                .createMove(board2, BoardUtils.INSTANCE.getCoordinateAtPosition("e5"), BoardUtils.INSTANCE.getCoordinateAtPosition("d3"));
        final Move bm8 = Move.MoveFactory
                .createMove(board2, BoardUtils.INSTANCE.getCoordinateAtPosition("e5"), BoardUtils.INSTANCE.getCoordinateAtPosition("f3"));

        assertEquals(blackLegals.size(), 13);

        assertTrue(blackLegals.contains(bm1));
        assertTrue(blackLegals.contains(bm2));
        assertTrue(blackLegals.contains(bm3));
        assertTrue(blackLegals.contains(bm4));
        assertTrue(blackLegals.contains(bm5));
        assertTrue(blackLegals.contains(bm6));
        assertTrue(blackLegals.contains(bm7));
        assertTrue(blackLegals.contains(bm8));
    }

    @Test
    public void testKnightInCorners() {
        final Board.Builder boardBuilder = new Board.Builder();
        boardBuilder.setPiece(new King(Alliance.BLACK, 4, false, false));
        boardBuilder.setPiece(new Knight(Alliance.BLACK, 0));
        boardBuilder.setPiece(new Knight(Alliance.WHITE, 56));
        boardBuilder.setPiece(new King(Alliance.WHITE, 60, false, false));
        boardBuilder.setMoveMaker(Alliance.WHITE);
        final Board board = boardBuilder.build();
        final Collection<Move> whiteLegals = board.whitePlayer().getLegalMoves();
        final Collection<Move> blackLegals = board.blackPlayer().getLegalMoves();
        assertEquals(whiteLegals.size(), 7);
        assertEquals(blackLegals.size(), 7);
        final Move wm1 = Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("a1"), BoardUtils.INSTANCE.getCoordinateAtPosition("b3"));
        final Move wm2 = Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("a1"), BoardUtils.INSTANCE.getCoordinateAtPosition("c2"));
        assertTrue(whiteLegals.contains(wm1));
        assertTrue(whiteLegals.contains(wm2));
        final Move bm1 = Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("a8"), BoardUtils.INSTANCE.getCoordinateAtPosition("b6"));
        final Move bm2 = Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("a8"), BoardUtils.INSTANCE.getCoordinateAtPosition("c7"));
        assertTrue(blackLegals.contains(bm1));
        assertTrue(blackLegals.contains(bm2));

    }

    @Test
    public void testMiddleBishopOnEmptyBoard() {
        final Board.Builder builder = new Board.Builder();
        // Black Layout
        builder.setPiece(new King(Alliance.BLACK, 4, false, false));
        // White Layout
        builder.setPiece(new Bishop(Alliance.WHITE, 35));
        builder.setPiece(new King(Alliance.WHITE, 60, false, false));
        // Set the current player
        builder.setMoveMaker(Alliance.WHITE);
        //build the board
        final Board board = builder.build();
        final Collection<Move> whiteLegals = board.whitePlayer().getLegalMoves();
        final Collection<Move> blackLegals = board.blackPlayer().getLegalMoves();
        assertEquals(whiteLegals.size(), 18);
        assertEquals(blackLegals.size(), 5);
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("d4"), BoardUtils.INSTANCE.getCoordinateAtPosition("a7"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("d4"), BoardUtils.INSTANCE.getCoordinateAtPosition("b6"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("d4"), BoardUtils.INSTANCE.getCoordinateAtPosition("c5"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("d4"), BoardUtils.INSTANCE.getCoordinateAtPosition("e3"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("d4"), BoardUtils.INSTANCE.getCoordinateAtPosition("f2"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("d4"), BoardUtils.INSTANCE.getCoordinateAtPosition("g1"))));
    }

    @Test
    public void testTopLeftBishopOnEmptyBoard() {
        Board.Builder builder = new Board.Builder();
        // Black Layout
        builder.setPiece(new King(Alliance.BLACK, 4, false, false));
        // White Layout
        builder.setPiece(new Bishop(Alliance.WHITE, 0));
        builder.setPiece(new King(Alliance.WHITE, 60, false, false));
        // Set the current player
        builder.setMoveMaker(Alliance.WHITE);
        //build the board
        final Board board = builder.build();
        final Collection<Move> whiteLegals = board.whitePlayer().getLegalMoves();
        final Collection<Move> blackLegals = board.blackPlayer().getLegalMoves();
        assertEquals(board.getPiece(0), board.getPiece(0));
        assertNotNull(board.getPiece(0));
        assertEquals(whiteLegals.size(), 12);
        assertEquals(blackLegals.size(), 5);
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("a8"), BoardUtils.INSTANCE.getCoordinateAtPosition("b7"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("a8"), BoardUtils.INSTANCE.getCoordinateAtPosition("c6"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("a8"), BoardUtils.INSTANCE.getCoordinateAtPosition("d5"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("a8"), BoardUtils.INSTANCE.getCoordinateAtPosition("e4"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("a8"), BoardUtils.INSTANCE.getCoordinateAtPosition("f3"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("a8"), BoardUtils.INSTANCE.getCoordinateAtPosition("g2"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("a8"), BoardUtils.INSTANCE.getCoordinateAtPosition("h1"))));
    }

    @Test
    public void testTopRightBishopOnEmptyBoard() {
        Board.Builder builder = new Board.Builder();
        // Black Layout
        builder.setPiece(new King(Alliance.BLACK, 4, false, false));
        // White Layout
        builder.setPiece(new Bishop(Alliance.WHITE, 7));
        builder.setPiece(new King(Alliance.WHITE, 60, false, false));
        // Set the current player
        builder.setMoveMaker(Alliance.WHITE);
        //build the board
        final Board board = builder.build();
        final Collection<Move> whiteLegals = board.whitePlayer().getLegalMoves();
        final Collection<Move> blackLegals = board.blackPlayer().getLegalMoves();
        assertEquals(whiteLegals.size(), 12);
        assertEquals(blackLegals.size(), 5);
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("h8"), BoardUtils.INSTANCE.getCoordinateAtPosition("g7"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("h8"), BoardUtils.INSTANCE.getCoordinateAtPosition("f6"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("h8"), BoardUtils.INSTANCE.getCoordinateAtPosition("e5"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("h8"), BoardUtils.INSTANCE.getCoordinateAtPosition("d4"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("h8"), BoardUtils.INSTANCE.getCoordinateAtPosition("c3"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("h8"), BoardUtils.INSTANCE.getCoordinateAtPosition("b2"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("h8"), BoardUtils.INSTANCE.getCoordinateAtPosition("a1"))));
    }

    @Test
    public void testBottomLeftBishopOnEmptyBoard() {
        Board.Builder builder = new Board.Builder();
        // Black Layout
        builder.setPiece(new King(Alliance.BLACK, 4, false, false));
        // White Layout
        builder.setPiece(new Bishop(Alliance.WHITE, 56));
        builder.setPiece(new King(Alliance.WHITE, 60, false, false));
        // Set the current player
        builder.setMoveMaker(Alliance.WHITE);
        //build the board
        final Board board = builder.build();
        final Collection<Move> whiteLegals = board.whitePlayer().getLegalMoves();
        final Collection<Move> blackLegals = board.blackPlayer().getLegalMoves();
        assertEquals(whiteLegals.size(), 12);
        assertEquals(blackLegals.size(), 5);
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("a1"), BoardUtils.INSTANCE.getCoordinateAtPosition("b2"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("a1"), BoardUtils.INSTANCE.getCoordinateAtPosition("c3"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("a1"), BoardUtils.INSTANCE.getCoordinateAtPosition("d4"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("a1"), BoardUtils.INSTANCE.getCoordinateAtPosition("e5"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("a1"), BoardUtils.INSTANCE.getCoordinateAtPosition("f6"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("a1"), BoardUtils.INSTANCE.getCoordinateAtPosition("g7"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("a1"), BoardUtils.INSTANCE.getCoordinateAtPosition("h8"))));
    }

    @Test
    public void testBottomRightBishopOnEmptyBoard() {
        Board.Builder builder = new Board.Builder();
        // Black Layout
        builder.setPiece(new King(Alliance.BLACK, 4, false, false));
        // White Layout
        builder.setPiece(new Bishop(Alliance.WHITE, 63));
        builder.setPiece(new King(Alliance.WHITE, 60, false, false));
        // Set the current player
        builder.setMoveMaker(Alliance.WHITE);
        //build the board
        final Board board = builder.build();
        final Collection<Move> whiteLegals = board.whitePlayer().getLegalMoves();
        final Collection<Move> blackLegals = board.blackPlayer().getLegalMoves();
        assertEquals(whiteLegals.size(), 12);
        assertEquals(blackLegals.size(), 5);
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("h1"), BoardUtils.INSTANCE.getCoordinateAtPosition("g2"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("h1"), BoardUtils.INSTANCE.getCoordinateAtPosition("f3"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("h1"), BoardUtils.INSTANCE.getCoordinateAtPosition("e4"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("h1"), BoardUtils.INSTANCE.getCoordinateAtPosition("d5"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("h1"), BoardUtils.INSTANCE.getCoordinateAtPosition("c6"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("h1"), BoardUtils.INSTANCE.getCoordinateAtPosition("b7"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("h1"), BoardUtils.INSTANCE.getCoordinateAtPosition("a8"))));
    }

    @Test
    public void testMiddleRookOnEmptyBoard() {
        final Board.Builder builder = new Board.Builder();
        // Black Layout
        builder.setPiece(new King(Alliance.BLACK, 4, false, false));
        // White Layout
        builder.setPiece(new Rook(Alliance.WHITE, 36));
        builder.setPiece(new King(Alliance.WHITE, 60, false, false));
        // Set the current player
        builder.setMoveMaker(Alliance.WHITE);
        final Board board = builder.build();
        final Collection<Move> whiteLegals = board.whitePlayer().getLegalMoves();
        final Collection<Move> blackLegals = board.blackPlayer().getLegalMoves();
        assertEquals(whiteLegals.size(), 18);
        assertEquals(blackLegals.size(), 5);
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("e4"), BoardUtils.INSTANCE.getCoordinateAtPosition("e8"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("e4"), BoardUtils.INSTANCE.getCoordinateAtPosition("e7"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("e4"), BoardUtils.INSTANCE.getCoordinateAtPosition("e6"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("e4"), BoardUtils.INSTANCE.getCoordinateAtPosition("e5"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("e4"), BoardUtils.INSTANCE.getCoordinateAtPosition("e3"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("e4"), BoardUtils.INSTANCE.getCoordinateAtPosition("e2"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("e4"), BoardUtils.INSTANCE.getCoordinateAtPosition("a4"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("e4"), BoardUtils.INSTANCE.getCoordinateAtPosition("b4"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("e4"), BoardUtils.INSTANCE.getCoordinateAtPosition("c4"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("e4"), BoardUtils.INSTANCE.getCoordinateAtPosition("d4"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("e4"), BoardUtils.INSTANCE.getCoordinateAtPosition("f4"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("e4"), BoardUtils.INSTANCE.getCoordinateAtPosition("g4"))));
        assertTrue(whiteLegals.contains(Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("e4"), BoardUtils.INSTANCE.getCoordinateAtPosition("h4"))));
    }

    @Test
    public void testPawnPromotion() {
        final Board.Builder builder = new Board.Builder();
        // Black Layout
        builder.setPiece(new Rook(Alliance.BLACK, 3));
        builder.setPiece(new King(Alliance.BLACK, 22, false, false));
        // White Layout
        builder.setPiece(new Pawn(Alliance.WHITE, 15));
        builder.setPiece(new King(Alliance.WHITE, 52, false, false));
        // Set the current player
        builder.setMoveMaker(Alliance.WHITE);
        final Board board = builder.build();
        final Move m1 = Move.MoveFactory.createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition(
                "h7"), BoardUtils.INSTANCE.getCoordinateAtPosition("h8"));
        final MoveTransition t1 = board.currentPlayer().makeMove(m1);
        Assert.assertTrue(t1.getMoveStatus().isDone());
        final Move m2 = Move.MoveFactory.createMove(t1.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("d8"), BoardUtils.INSTANCE.getCoordinateAtPosition("h8"));
        final MoveTransition t2 = t1.getToBoard().currentPlayer().makeMove(m2);
        Assert.assertTrue(t2.getMoveStatus().isDone());
        final Move m3 = Move.MoveFactory.createMove(t2.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("e2"), BoardUtils.INSTANCE.getCoordinateAtPosition("d2"));
        final MoveTransition t3 = board.currentPlayer().makeMove(m3);
        Assert.assertTrue(t3.getMoveStatus().isDone());
    }

    @Test
    public void testSimpleWhiteEnPassant() {
        final Board.Builder builder = new Board.Builder();
        // Black Layout
        builder.setPiece(new King(Alliance.BLACK, 4, false, false));
        builder.setPiece(new Pawn(Alliance.BLACK, 11));
        // White Layout
        builder.setPiece(new Pawn(Alliance.WHITE, 52));
        builder.setPiece(new King(Alliance.WHITE, 60, false, false));
        // Set the current player
        builder.setMoveMaker(Alliance.WHITE);
        final Board board = builder.build();
        final Move m1 = Move.MoveFactory.createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition(
                "e2"), BoardUtils.INSTANCE.getCoordinateAtPosition("e4"));
        final MoveTransition t1 = board.currentPlayer().makeMove(m1);
        Assert.assertTrue(t1.getMoveStatus().isDone());
        final Move m2 = Move.MoveFactory.createMove(t1.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("e8"), BoardUtils.INSTANCE.getCoordinateAtPosition("d8"));
        final MoveTransition t2 = t1.getToBoard().currentPlayer().makeMove(m2);
        Assert.assertTrue(t2.getMoveStatus().isDone());
        final Move m3 = Move.MoveFactory.createMove(t2.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("e4"), BoardUtils.INSTANCE.getCoordinateAtPosition("e5"));
        final MoveTransition t3 = t2.getToBoard().currentPlayer().makeMove(m3);
        Assert.assertTrue(t3.getMoveStatus().isDone());
        final Move m4 = Move.MoveFactory.createMove(t3.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("d7"), BoardUtils.INSTANCE.getCoordinateAtPosition("d5"));
        final MoveTransition t4 = t3.getToBoard().currentPlayer().makeMove(m4);
        Assert.assertTrue(t4.getMoveStatus().isDone());
        final Move m5 = Move.MoveFactory.createMove(t4.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("e5"), BoardUtils.INSTANCE.getCoordinateAtPosition("d6"));
        final MoveTransition t5 = t4.getToBoard().currentPlayer().makeMove(m5);
        Assert.assertTrue(t5.getMoveStatus().isDone());
    }

    @Test
    public void testSimpleBlackEnPassant() {
        final Board.Builder builder = new Board.Builder();
        // Black Layout
        builder.setPiece(new King(Alliance.BLACK, 4, false, false));
        builder.setPiece(new Pawn(Alliance.BLACK, 11));
        // White Layout
        builder.setPiece(new Pawn(Alliance.WHITE, 52));
        builder.setPiece(new King(Alliance.WHITE, 60, false, false));
        // Set the current player
        builder.setMoveMaker(Alliance.WHITE);
        final Board board = builder.build();
        final Move m1 = Move.MoveFactory.createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition(
                "e1"), BoardUtils.INSTANCE.getCoordinateAtPosition("d1"));
        final MoveTransition t1 = board.currentPlayer().makeMove(m1);
        assertTrue(t1.getMoveStatus().isDone());
        final Move m2 = Move.MoveFactory.createMove(t1.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("d7"), BoardUtils.INSTANCE.getCoordinateAtPosition("d5"));
        final MoveTransition t2 = t1.getToBoard().currentPlayer().makeMove(m2);
        assertTrue(t2.getMoveStatus().isDone());
        final Move m3 = Move.MoveFactory.createMove(t2.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("d1"), BoardUtils.INSTANCE.getCoordinateAtPosition("c1"));
        final MoveTransition t3 = t2.getToBoard().currentPlayer().makeMove(m3);
        assertTrue(t3.getMoveStatus().isDone());
        final Move m4 = Move.MoveFactory.createMove(t3.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("d5"), BoardUtils.INSTANCE.getCoordinateAtPosition("d4"));
        final MoveTransition t4 = t3.getToBoard().currentPlayer().makeMove(m4);
        assertTrue(t4.getMoveStatus().isDone());
        final Move m5 = Move.MoveFactory.createMove(t4.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("e2"), BoardUtils.INSTANCE.getCoordinateAtPosition("e4"));
        final MoveTransition t5 = t4.getToBoard().currentPlayer().makeMove(m5);
        assertTrue(t5.getMoveStatus().isDone());
        final Move m6 = Move.MoveFactory.createMove(t5.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("d4"), BoardUtils.INSTANCE.getCoordinateAtPosition("e3"));
        final MoveTransition t6 = t5.getToBoard().currentPlayer().makeMove(m6);
        Assert.assertTrue(t6.getMoveStatus().isDone());
    }

    @Test
    public void testEnPassant2() {
        final Board board = Board.createStandardBoard();
        final Move m1 = Move.MoveFactory.createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition(
                "e2"), BoardUtils.INSTANCE.getCoordinateAtPosition("e3"));
        final MoveTransition t1 = board.currentPlayer().makeMove(m1);
        assertTrue(t1.getMoveStatus().isDone());
        final Move m2 = Move.MoveFactory.createMove(t1.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("h7"), BoardUtils.INSTANCE.getCoordinateAtPosition("h5"));
        final MoveTransition t2 = t1.getToBoard().currentPlayer().makeMove(m2);
        assertTrue(t2.getMoveStatus().isDone());
        final Move m3 = Move.MoveFactory.createMove(t2.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("e3"), BoardUtils.INSTANCE.getCoordinateAtPosition("e4"));
        final MoveTransition t3 = t2.getToBoard().currentPlayer().makeMove(m3);
        assertTrue(t3.getMoveStatus().isDone());
        final Move m4 = Move.MoveFactory.createMove(t3.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("h5"), BoardUtils.INSTANCE.getCoordinateAtPosition("h4"));
        final MoveTransition t4 = t3.getToBoard().currentPlayer().makeMove(m4);
        assertTrue(t4.getMoveStatus().isDone());
        final Move m5 = Move.MoveFactory.createMove(t4.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("g2"), BoardUtils.INSTANCE.getCoordinateAtPosition("g4"));
        final MoveTransition t5 = t4.getToBoard().currentPlayer().makeMove(m5);
        assertTrue(t5.getMoveStatus().isDone());
    }

    @Test
    public void testKingEquality() {
        final Board board = Board.createStandardBoard();
        final Board board2 = Board.createStandardBoard();
        assertEquals(board.getPiece(60), board2.getPiece(60));
        assertFalse(board.getPiece(60).equals(null));
    }

    @Test
    public void testHashCode() {
        final Board board = Board.createStandardBoard();
        final Set<Piece> pieceSet = Sets.newHashSet(board.getAllPieces());
        final Set<Piece> whitePieceSet = Sets.newHashSet(board.getWhitePieces());
        final Set<Piece> blackPieceSet = Sets.newHashSet(board.getBlackPieces());
        assertTrue(pieceSet.size() == 32);
        assertTrue(whitePieceSet.size() == 16);
        assertTrue(blackPieceSet.size() == 16);
    }

}
