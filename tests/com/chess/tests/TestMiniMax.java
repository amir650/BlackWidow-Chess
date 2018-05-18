package com.chess.tests;

import com.chess.engine.classic.Alliance;
import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.board.Board.Builder;
import com.chess.engine.classic.board.BoardUtils;
import com.chess.engine.classic.board.Move;
import com.chess.engine.classic.board.MoveTransition;
import com.chess.engine.classic.pieces.*;
import com.chess.engine.classic.player.ai.MiniMax;
import com.chess.engine.classic.player.ai.MoveStrategy;
import com.chess.pgn.FenUtilities;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestMiniMax {

    @Test
    public void testOpeningDepth1() {
        final Board board = Board.createStandardBoard();
        final MoveStrategy minMax = new MiniMax(1);
        minMax.execute(board);
        final long numBoardsEvaluated = minMax.getNumBoardsEvaluated();
        assertEquals(numBoardsEvaluated, 20L);
    }

    @Test
    public void testOpeningDepth2() {
        final Board board = Board.createStandardBoard();
        final MoveStrategy minMax = new MiniMax(2);
        minMax.execute(board);
        final long numBoardsEvaluated = minMax.getNumBoardsEvaluated();
        assertEquals(numBoardsEvaluated, 400L);
    }

    @Test
    public void testOpeningDepth3() {
        final Board board = Board.createStandardBoard();
        final MoveStrategy minMax = new MiniMax(3);
        minMax.execute(board);
        final long numBoardsEvaluated = minMax.getNumBoardsEvaluated();
        assertEquals(numBoardsEvaluated, 8902L);
    }

    @Test
    public void testOpeningDepth4() {
        final Board board = Board.createStandardBoard();
        final MoveStrategy minMax = new MiniMax(4);
        minMax.execute(board);
        final long numBoardsEvaluated = minMax.getNumBoardsEvaluated();
        assertEquals(numBoardsEvaluated, 197281L);
    }

    @Test
    public void testOpeningDepth5() {
        final Board board = Board.createStandardBoard();
        final MoveStrategy minMax = new MiniMax(5);
        minMax.execute(board);
        final long numBoardsEvaluated = minMax.getNumBoardsEvaluated();
        assertEquals(numBoardsEvaluated, 4865609L);
    }

    @Test
    public void testOpeningDepth6() {
        final Board board = Board.createStandardBoard();
        final MoveStrategy minMax = new MiniMax(6);
        minMax.execute(board);
        final long numBoardsEvaluated = minMax.getNumBoardsEvaluated();
        assertEquals(numBoardsEvaluated, 119060324L);
    }

    @Test
    public void testKiwiPeteDepth1() {
        final Builder builder = new Builder();
        // Black Layout
        builder.setPiece(new Rook(Alliance.BLACK, 0));
        builder.setPiece(new King(Alliance.BLACK, 4, false, false));
        builder.setPiece(new Rook(Alliance.BLACK, 7));
        builder.setPiece(new Pawn(Alliance.BLACK, 8));
        builder.setPiece(new Pawn(Alliance.BLACK, 10));
        builder.setPiece(new Pawn(Alliance.BLACK, 11));
        builder.setPiece(new Queen(Alliance.BLACK, 12));
        builder.setPiece(new Pawn(Alliance.BLACK, 13));
        builder.setPiece(new Bishop(Alliance.BLACK, 14));
        builder.setPiece(new Bishop(Alliance.BLACK, 16));
        builder.setPiece(new Knight(Alliance.BLACK, 17));
        builder.setPiece(new Pawn(Alliance.BLACK, 20));
        builder.setPiece(new Knight(Alliance.BLACK, 21));
        builder.setPiece(new Pawn(Alliance.BLACK, 22));
        builder.setPiece(new Pawn(Alliance.BLACK, 33));
        builder.setPiece(new Pawn(Alliance.BLACK, 47));
        // White Layout
        builder.setPiece(new Pawn(Alliance.WHITE, 27));
        builder.setPiece(new Knight(Alliance.WHITE, 28));
        builder.setPiece(new Pawn(Alliance.WHITE, 36));
        builder.setPiece(new Knight(Alliance.WHITE, 42));
        builder.setPiece(new Queen(Alliance.WHITE, 45));
        builder.setPiece(new Pawn(Alliance.WHITE, 48));
        builder.setPiece(new Pawn(Alliance.WHITE, 49));
        builder.setPiece(new Pawn(Alliance.WHITE, 50));
        builder.setPiece(new Bishop(Alliance.WHITE, 51));
        builder.setPiece(new Bishop(Alliance.WHITE, 52));
        builder.setPiece(new Pawn(Alliance.WHITE, 53));
        builder.setPiece(new Pawn(Alliance.WHITE, 54));
        builder.setPiece(new Pawn(Alliance.WHITE, 55));
        builder.setPiece(new Rook(Alliance.WHITE, 56));
        builder.setPiece(new King(Alliance.WHITE, 60, false, false));
        builder.setPiece(new Rook(Alliance.WHITE, 63));
        // Set the current player
        builder.setMoveMaker(Alliance.WHITE);
        final Board board = builder.build();
        final MoveStrategy minMax = new MiniMax(1);
        minMax.execute(board);
        final long numBoardsEvaluated = minMax.getNumBoardsEvaluated();
        assertEquals(numBoardsEvaluated, 46);
    }

    @Test
    public void testKiwiPeteDepth2() {
        final Builder builder = new Builder();
        // Black Layout
        builder.setPiece(new Rook(Alliance.BLACK, 0));
        builder.setPiece(new King(Alliance.BLACK, 4, false, false));
        builder.setPiece(new Rook(Alliance.BLACK, 7));
        builder.setPiece(new Pawn(Alliance.BLACK, 8));
        builder.setPiece(new Pawn(Alliance.BLACK, 10));
        builder.setPiece(new Pawn(Alliance.BLACK, 11));
        builder.setPiece(new Queen(Alliance.BLACK, 12));
        builder.setPiece(new Pawn(Alliance.BLACK, 13));
        builder.setPiece(new Bishop(Alliance.BLACK, 14));
        builder.setPiece(new Bishop(Alliance.BLACK, 16));
        builder.setPiece(new Knight(Alliance.BLACK, 17));
        builder.setPiece(new Pawn(Alliance.BLACK, 20));
        builder.setPiece(new Knight(Alliance.BLACK, 21));
        builder.setPiece(new Pawn(Alliance.BLACK, 22));
        builder.setPiece(new Pawn(Alliance.BLACK, 33));
        builder.setPiece(new Pawn(Alliance.BLACK, 47));
        // White Layout
        builder.setPiece(new Pawn(Alliance.WHITE, 27));
        builder.setPiece(new Knight(Alliance.WHITE, 28));
        builder.setPiece(new Pawn(Alliance.WHITE, 36));
        builder.setPiece(new Knight(Alliance.WHITE, 42));
        builder.setPiece(new Queen(Alliance.WHITE, 45));
        builder.setPiece(new Pawn(Alliance.WHITE, 48));
        builder.setPiece(new Pawn(Alliance.WHITE, 49));
        builder.setPiece(new Pawn(Alliance.WHITE, 50));
        builder.setPiece(new Bishop(Alliance.WHITE, 51));
        builder.setPiece(new Bishop(Alliance.WHITE, 52));
        builder.setPiece(new Pawn(Alliance.WHITE, 53));
        builder.setPiece(new Pawn(Alliance.WHITE, 54));
        builder.setPiece(new Pawn(Alliance.WHITE, 55));
        builder.setPiece(new Rook(Alliance.WHITE, 56));
        builder.setPiece(new King(Alliance.WHITE, 60, false, false));
        builder.setPiece(new Rook(Alliance.WHITE, 63));
        // Set the current player
        builder.setMoveMaker(Alliance.WHITE);
        final Board board = builder.build();
        System.out.println(FenUtilities.createFENFromGame(board));
        final MoveStrategy minMax = new MiniMax(2);
        minMax.execute(board);
        assertEquals(minMax.getNumBoardsEvaluated(), 1866L);
    }

    @Test
    public void engineIntegrity1() {
        final Board board = FenUtilities.createGameFromFEN("8/2p5/3p4/KP5r/1R3p1k/8/4P1P1/8 w - -\n");
        final MoveStrategy minMax = new MiniMax(6);
        minMax.execute(board);
        assertEquals(minMax.getNumBoardsEvaluated(), 11030083);
    }

    @Test
    public void testKiwiPeteDepth2Bug2() {
        final Board board = FenUtilities.createGameFromFEN("r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq -");
        final MoveTransition t1 = board.currentPlayer()
                .makeMove(Move.MoveFactory.createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("e5"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("d7")));
        final MoveStrategy minMax = new MiniMax(1);
        minMax.execute(t1.getToBoard());
        assertEquals(minMax.getNumBoardsEvaluated(), 45);
    }

    @Test
    public void testChessDotComGame() {
        final Board board = FenUtilities.createGameFromFEN("rnbk1bnr/1pN2ppp/p7/3P2q1/3Pp3/8/PPP1QPPP/RN2KB1R w KQ - 18 10");
        final MoveStrategy minMax = new MiniMax(4);
        minMax.execute(board);
    }

    @Test
    public void testPosition3Depth1() {
        final Builder builder = new Builder();
        // Black Layout
        builder.setPiece(new Pawn(Alliance.BLACK, 10));
        builder.setPiece(new Pawn(Alliance.BLACK, 19));
        builder.setPiece(new Rook(Alliance.BLACK, 31));
        builder.setPiece(new Pawn(Alliance.BLACK, 37));
        builder.setPiece(new King(Alliance.BLACK, 39, false, false));
        // White Layout
        builder.setPiece(new King(Alliance.WHITE, 24, false, false));
        builder.setPiece(new Pawn(Alliance.WHITE, 25));
        builder.setPiece(new Rook(Alliance.WHITE, 33));
        builder.setPiece(new Pawn(Alliance.WHITE, 52));
        builder.setPiece(new Pawn(Alliance.WHITE, 54));
        // Set the current player
        builder.setMoveMaker(Alliance.WHITE);
        final Board board = builder.build();
        final MoveStrategy minMax = new MiniMax(1);
        minMax.execute(board);
        final long numBoardsEvaluated = minMax.getNumBoardsEvaluated();
        assertEquals(numBoardsEvaluated, 14);
    }

    @Test
    public void testPosition3Depth2() {
        final Builder builder = new Builder();
        // Black Layout
        builder.setPiece(new Pawn(Alliance.BLACK, 10));
        builder.setPiece(new Pawn(Alliance.BLACK, 19));
        builder.setPiece(new Rook(Alliance.BLACK, 31));
        builder.setPiece(new Pawn(Alliance.BLACK, 37));
        builder.setPiece(new King(Alliance.BLACK, 39, false, false));
        // White Layout
        builder.setPiece(new King(Alliance.WHITE, 24, false, false));
        builder.setPiece(new Pawn(Alliance.WHITE, 25));
        builder.setPiece(new Rook(Alliance.WHITE, 33));
        builder.setPiece(new Pawn(Alliance.WHITE, 52));
        builder.setPiece(new Pawn(Alliance.WHITE, 54));
        // Set the current player
        builder.setMoveMaker(Alliance.WHITE);
        final Board board = builder.build();
        final MoveStrategy minMax = new MiniMax(2);
        minMax.execute(board);
        final long numBoardsEvaluated = minMax.getNumBoardsEvaluated();
        assertEquals(numBoardsEvaluated, 191);
    }

}
