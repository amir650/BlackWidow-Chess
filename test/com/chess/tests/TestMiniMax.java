package com.chess.tests;

import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.MoveTransition;
import com.chess.engine.player.ai.MiniMax;
import com.chess.engine.player.ai.MoveStrategy;
import com.chess.engine.player.ai.BlackWidowAI;
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
        assertEquals(20L, numBoardsEvaluated);
    }

    @Test
    public void testOpeningDepth2() {
        final Board board = Board.createStandardBoard();
        final MoveStrategy minMax = new MiniMax(2);
        minMax.execute(board);
        final long numBoardsEvaluated = minMax.getNumBoardsEvaluated();
        assertEquals(400L, numBoardsEvaluated);
    }

    @Test
    public void testOpeningDepth3() {
        final Board board = Board.createStandardBoard();
        final MoveStrategy minMax = new MiniMax(3);
        minMax.execute(board);
        final long numBoardsEvaluated = minMax.getNumBoardsEvaluated();
        assertEquals(8902L, numBoardsEvaluated);
    }

    @Test
    public void testOpeningDepth4() {
        final Board board = Board.createStandardBoard();
        final MoveStrategy minMax = new MiniMax(4);
        minMax.execute(board);
        final long numBoardsEvaluated = minMax.getNumBoardsEvaluated();
        assertEquals(197281L, numBoardsEvaluated);
    }

    @Test
    public void testOpeningDepth5() {
        final Board board = Board.createStandardBoard();
        final MoveStrategy minMax = new MiniMax(5);
        minMax.execute(board);
        final long numBoardsEvaluated = minMax.getNumBoardsEvaluated();
        assertEquals(4865609L, numBoardsEvaluated);
    }

    @Test
    public void testOpeningDepth6() {
        final Board board = Board.createStandardBoard();
        final MoveStrategy minMax = new MiniMax(6);
        minMax.execute(board);
        final long numBoardsEvaluated = minMax.getNumBoardsEvaluated();
        assertEquals(119060324L, numBoardsEvaluated);
    }

    @Test
    public void testKiwiPeteDepth1() {
        final Board board = FenUtilities.createGameFromFEN("r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w - - 0 1");
        System.out.println(FenUtilities.createFENFromGame(board));
        final MoveStrategy minMax = new MiniMax(1);
        minMax.execute(board);
        final long numBoardsEvaluated = minMax.getNumBoardsEvaluated();
        assertEquals(46, numBoardsEvaluated);
    }

    @Test
    public void testKiwi7PeteDepth2() {
        final Board board = FenUtilities.createGameFromFEN("r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w - - 0 1");
        System.out.println(FenUtilities.createFENFromGame(board));
        final MoveStrategy minMax = new MiniMax(2);
        minMax.execute(board);
        assertEquals(1866L, minMax.getNumBoardsEvaluated());
    }

    @Test
    public void engineIntegrity1() {
        final Board board = FenUtilities.createGameFromFEN("8/2p5/3p4/KP5r/1R3p1k/8/4P1P1/8 w - -");
        final MoveStrategy minMax = new MiniMax(6);
        minMax.execute(board);
        assertEquals(11030083, minMax.getNumBoardsEvaluated());
    }

    @Test
    public void testKiwiPeteDepth2Bug2() {
        final Board board = FenUtilities.createGameFromFEN("r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq -");
        final MoveTransition t1 = board.currentPlayer()
                .makeMove(Move.MoveFactory.createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("e5"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("d7")));
        final MoveStrategy minMax = new MiniMax(1);
        minMax.execute(t1.getToBoard());
        assertEquals(45, minMax.getNumBoardsEvaluated());
    }

    @Test
    public void testChessDotComGame() {
        final Board board = FenUtilities.createGameFromFEN("rnbk1bnr/1pN2ppp/p7/3P2q1/3Pp3/8/PPP1QPPP/RN2KB1R w KQ - 18 10");
        final MoveStrategy minMax = new MiniMax(4);
        minMax.execute(board);
    }

    @Test
    public void testPosition3Depth1() {
        final Board board = FenUtilities.createGameFromFEN("8/2p5/3p4/KP5r/1R3p1k/8/4P1P1/8 w - - 0 1");
        System.out.println(FenUtilities.createFENFromGame(board));
        final MoveStrategy minMax = new MiniMax(1);
        minMax.execute(board);
        final long numBoardsEvaluated = minMax.getNumBoardsEvaluated();
        assertEquals(14, numBoardsEvaluated);
    }

    @Test
    public void testPosition3Depth2() {
        final Board board = FenUtilities.createGameFromFEN("8/2p5/3p4/KP5r/1R3p1k/8/4P1P1/8 w - - 0 1");
        System.out.println(FenUtilities.createFENFromGame(board));
        final MoveStrategy minMax = new MiniMax(2);
        minMax.execute(board);
        final long numBoardsEvaluated = minMax.getNumBoardsEvaluated();
        assertEquals(191, numBoardsEvaluated);
    }

    @Test
    public void testMissedAttackGold() {
        //final Board board = FenUtilities.createGameFromFEN("r1bq1rk1/pppp1ppp/2nb1n2/1B2p3/3PP3/5N2/PPP2PPP/RNBQ1RK1 w - - 0 1");
        //System.out.println(FenUtilities.createFENFromGame(board));
        //final MoveStrategy minMax = new MiniMax(6);
        //minMax.execute(board);
        // final long numBoardsEvaluated = minMax.getNumBoardsEvaluated();
        //assertEquals(numBoardsEvaluated, 191);
    }

    @Test
    public void testMissedAttackGold2() {
        final Board board = FenUtilities.createGameFromFEN("r1bq1rk1/pppp1ppp/2nb1n2/1B2p3/3PP3/5N2/PPP2PPP/RNBQ1RK1 w - - 0 1");
        System.out.println(FenUtilities.createFENFromGame(board));
        final MoveStrategy minMax = new BlackWidowAI(6);
        minMax.execute(board);
        // final long numBoardsEvaluated = minMax.getNumBoardsEvaluated();
        //assertEquals(numBoardsEvaluated, 191);
    }

}
