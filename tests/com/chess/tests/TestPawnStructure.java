package com.chess.tests;

import com.chess.engine.board.Board;
import com.chess.engine.player.ai.PawnStructureAnalyzer;
import com.chess.engine.player.ai.StandardBoardEvaluator;
import com.chess.pgn.FenUtilities;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class TestPawnStructure {

    @Test
    public void testIsolatedPawnsOnStandardBoard() {
        final Board board = Board.createStandardBoard();
        assertEquals(0, PawnStructureAnalyzer.get().isolatedPawnPenalty(board.whitePlayer()));
        assertEquals(0, PawnStructureAnalyzer.get().isolatedPawnPenalty(board.blackPlayer()));
    }

    @Test
    public void testIsolatedPawnByExample1() {
        final Board board = FenUtilities.createGameFromFEN("r1bq1rk1/pp2bppp/1np2n2/6B1/3P4/1BNQ4/PP2NPPP/R3R1K1 b - - 0 13");
        assertEquals(PawnStructureAnalyzer.ISOLATED_PAWN_PENALTY, PawnStructureAnalyzer.get().isolatedPawnPenalty(board.whitePlayer()));
        assertEquals(0, PawnStructureAnalyzer.get().isolatedPawnPenalty(board.blackPlayer()));
    }

    @Test
    public void testIsolatedPawnByExample2() {
        final Board board = FenUtilities.createGameFromFEN("r1bq1rk1/p3bppp/1np2n2/6B1/3P4/1BNQ4/PP2NPPP/R3R1K1 b - - 0 1");
        assertEquals(PawnStructureAnalyzer.ISOLATED_PAWN_PENALTY, PawnStructureAnalyzer.get().isolatedPawnPenalty(board.whitePlayer()));
        assertEquals(PawnStructureAnalyzer.ISOLATED_PAWN_PENALTY * 2, PawnStructureAnalyzer.get().isolatedPawnPenalty(board.blackPlayer()));
    }

    @Test
    public void testIsolatedPawnByExample3() {
        final Board board = FenUtilities.createGameFromFEN("4k3/p3p3/p3p3/4p3/8/8/4P3/4K3 w - - 0 1");
        System.out.println(FenUtilities.createFENFromGame(board));
        assertEquals(PawnStructureAnalyzer.ISOLATED_PAWN_PENALTY, PawnStructureAnalyzer.get().isolatedPawnPenalty(board.whitePlayer()));
        assertEquals(PawnStructureAnalyzer.ISOLATED_PAWN_PENALTY * 5, PawnStructureAnalyzer.get().isolatedPawnPenalty(board.blackPlayer()));
    }

    @Test
    public void testIsolatedPawnByExample4() {
        final Board board = FenUtilities.createGameFromFEN("4k3/2p1p1p1/8/8/8/8/2P1P1P1/4K3 w KQkq -");
        assertEquals(PawnStructureAnalyzer.ISOLATED_PAWN_PENALTY * 3, PawnStructureAnalyzer.get().isolatedPawnPenalty(board.whitePlayer()));
        assertEquals(PawnStructureAnalyzer.ISOLATED_PAWN_PENALTY * 3, PawnStructureAnalyzer.get().isolatedPawnPenalty(board.blackPlayer()));
        final StandardBoardEvaluator boardEvaluator = StandardBoardEvaluator.get();
        assertEquals(0, boardEvaluator.evaluate(board, 1));
    }

    @Test
    public void testIsolatedPawnByExample5() {
        final Board board = FenUtilities.createGameFromFEN("6k1/p6p/8/8/8/8/P6P/6K1 b - - 0 1");
        assertEquals(PawnStructureAnalyzer.ISOLATED_PAWN_PENALTY * 2, PawnStructureAnalyzer.get().isolatedPawnPenalty(board.whitePlayer()));
        assertEquals(PawnStructureAnalyzer.ISOLATED_PAWN_PENALTY * 2, PawnStructureAnalyzer.get().isolatedPawnPenalty(board.blackPlayer()));
        final StandardBoardEvaluator boardEvaluator = StandardBoardEvaluator.get();
        assertEquals(0, boardEvaluator.evaluate(board, 1));
    }

    @Test
    public void testIsolatedPawnByExample6() {
        final Board board = FenUtilities.createGameFromFEN("6k1/4p3/4p3/8/8/4P3/4P3/6K1 b - - 0 1");
        assertEquals(PawnStructureAnalyzer.ISOLATED_PAWN_PENALTY * 2, PawnStructureAnalyzer.get().isolatedPawnPenalty(board.whitePlayer()));
        assertEquals(PawnStructureAnalyzer.ISOLATED_PAWN_PENALTY * 2, PawnStructureAnalyzer.get().isolatedPawnPenalty(board.blackPlayer()));
        final StandardBoardEvaluator boardEvaluator = StandardBoardEvaluator.get();
        assertEquals(0, boardEvaluator.evaluate(board, 1));
    }

    @Test
    public void testDoubledPawnByExample1() {
        final Board board = Board.createStandardBoard();
        assertEquals(0, PawnStructureAnalyzer.get().doubledPawnPenalty(board.whitePlayer()));
        assertEquals(0, PawnStructureAnalyzer.get().doubledPawnPenalty(board.blackPlayer()));
        final StandardBoardEvaluator boardEvaluator = StandardBoardEvaluator.get();
        assertEquals(0, boardEvaluator.evaluate(board, 1));
    }

    @Test
    public void testDoubledPawnByExample2() {
        final Board board = FenUtilities.createGameFromFEN("6k1/4p3/4p3/8/8/4P3/4P3/6K1 b - - 0 1");
        assertEquals(PawnStructureAnalyzer.DOUBLED_PAWN_PENALTY * 2, PawnStructureAnalyzer.get().doubledPawnPenalty(board.whitePlayer()));
        assertEquals(PawnStructureAnalyzer.DOUBLED_PAWN_PENALTY * 2, PawnStructureAnalyzer.get().doubledPawnPenalty(board.blackPlayer()));
        final StandardBoardEvaluator boardEvaluator = StandardBoardEvaluator.get();
        assertEquals(0, boardEvaluator.evaluate(board, 1));
    }

    @Test
    public void testDoubledPawnByExample3() {
        final Board board = FenUtilities.createGameFromFEN("6k1/8/8/P7/P7/P7/8/6K1 b - - 0 1");
        assertEquals(PawnStructureAnalyzer.DOUBLED_PAWN_PENALTY * 3, PawnStructureAnalyzer.get().doubledPawnPenalty(board.whitePlayer()));
        assertEquals(0, PawnStructureAnalyzer.get().doubledPawnPenalty(board.blackPlayer()));
    }

    @Test
    public void testDoubledPawnByExample4() {
        final Board board = FenUtilities.createGameFromFEN("6k1/8/8/P6p/P6p/P6p/8/6K1 b - - 0 1");
        assertEquals(PawnStructureAnalyzer.DOUBLED_PAWN_PENALTY * 3, PawnStructureAnalyzer.get().doubledPawnPenalty(board.whitePlayer()));
        assertEquals(PawnStructureAnalyzer.DOUBLED_PAWN_PENALTY * 3, PawnStructureAnalyzer.get().doubledPawnPenalty(board.blackPlayer()));
        assertEquals(PawnStructureAnalyzer.ISOLATED_PAWN_PENALTY * 3, PawnStructureAnalyzer.get().isolatedPawnPenalty(board.whitePlayer()));
        assertEquals(PawnStructureAnalyzer.ISOLATED_PAWN_PENALTY * 3, PawnStructureAnalyzer.get().isolatedPawnPenalty(board.blackPlayer()));
    }

}