package com.chess.tests;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.board.MoveUtils;
import com.chess.engine.player.ai.MoveStrategy;
import com.chess.engine.player.ai.BlackWidowAI;
import com.chess.pgn.FenUtilities;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestAlphaBeta {

    @Test
    public void testQualityDepth6() {
        final Board board = FenUtilities.createGameFromFEN("4k2r/1R3R2/p3p1pp/4b3/1BnNr3/8/P1P5/5K2 w - - 1 0");
        final MoveStrategy alphaBeta = new BlackWidowAI(7);
        final Move expected = MoveUtils.getMove(board, "f7", "e7");
        final Move actual = alphaBeta.execute(board);
        assertEquals(expected, actual);
    }

    @Test
    public void testMateIn2_BasicRookMate1() {
        // 4r1rk/5K1b/7R/R7/8/8/8/8 w - - 0 1
        // Solution: Rxh7+ Kxh7 Rh5#
        final Board board = FenUtilities.createGameFromFEN("4r1rk/5K1b/7R/R7/8/8/8/8 w - - 0 1");
        final MoveStrategy alphaBeta = new BlackWidowAI(4);
        final Move actual = alphaBeta.execute(board);
        final Move expected = MoveUtils.getMove(board, "h6", "h7"); // Rxh7+
        assertEquals(expected, actual);
    }

    @Test
    public void testMateIn2_BasicRookMate2() {
        // 8/1r6/8/3R4/k7/p1K5/4r3/R7 w - - 0 1
        // Solution: Rxa3+ Kxa3 Ra5#
        final Board board = FenUtilities.createGameFromFEN("8/1r6/8/3R4/k7/p1K5/4r3/R7 w - - 0 1");
        final MoveStrategy alphaBeta = new BlackWidowAI(4);
        final Move actual = alphaBeta.execute(board);
        final Move expected = MoveUtils.getMove(board, "a1", "a3"); // Rxa3+
        assertEquals(expected, actual);
    }

    @Test
    public void testMateIn2_BackRankMate() {
        // 6k1/8/6K1/8/8/3r4/4r3/5R1R w - - 0 1
        // Solution: Rh8+ Kxh8 Rf8#
        final Board board = FenUtilities.createGameFromFEN("6k1/8/6K1/8/8/3r4/4r3/5R1R w - - 0 1");
        final MoveStrategy alphaBeta = new BlackWidowAI(4);
        final Move actual = alphaBeta.execute(board);
        final Move expected = MoveUtils.getMove(board, "h1", "h8"); // Rh8+
        assertEquals(expected, actual);
    }

    @Test
    public void testMateIn2_DoubleRookMate() {
        // 2rkr3/2ppp3/2n1n3/R2R4/8/8/3K4/8 w - - 0 1
        // Solution: Rxd7+ Kxd7 Rd5#
        final Board board = FenUtilities.createGameFromFEN("2rkr3/2ppp3/2n1n3/R2R4/8/8/3K4/8 w - - 0 1");
        final MoveStrategy alphaBeta = new BlackWidowAI(4);
        final Move actual = alphaBeta.execute(board);
        final Move expected = MoveUtils.getMove(board, "d5", "d7"); // Rxd7+
        assertEquals(expected, actual);
    }

    @Test
    public void testMateIn2_BishopRookMate() {
        final Board board = FenUtilities.createGameFromFEN("4rkr1/1R1R4/4bK2/8/8/8/8/8 w - - 0 1");
        final MoveStrategy alphaBeta = new BlackWidowAI(4);
        final Move actual = alphaBeta.execute(board);
        final Move expected = MoveUtils.getMove(board, "d7", "f7"); // Rf7+
        assertEquals(expected, actual);
    }

    @Test
    public void testMateIn2_KnightMate() {
        final Board board = FenUtilities.createGameFromFEN("5Knk/7b/R7/8/7B/8/8/8 w - - 0 1");
        final MoveStrategy alphaBeta = new BlackWidowAI(4);
        final Move actual = alphaBeta.execute(board);
        final Move expected = MoveUtils.getMove(board, "a6", "h6"); // Rh6
        assertEquals(expected, actual);
    }

    @Test
    public void testMateIn2_PawnPromotion() {
        final Board board = FenUtilities.createGameFromFEN("7k/6pp/5P1P/8/8/8/1p3K2/8 w - - 0 1");
        final MoveStrategy alphaBeta = new BlackWidowAI(4);
        final Move actual = alphaBeta.execute(board);
        final Move expected = MoveUtils.getMove(board, "f6", "f7"); // f7
        assertEquals(expected, actual);
    }

    @Test
    public void testMateIn2_BlackToMove() {
        final Board board = FenUtilities.createGameFromFEN("8/8/8/8/1b6/1k6/8/KBB5 b - - 0 1");
        final MoveStrategy alphaBeta = new BlackWidowAI(4);
        final Move actual = alphaBeta.execute(board);
        final Move expected = MoveUtils.getMove(board, "b4", "c3"); // Bc3+
        assertEquals(expected, actual);
    }


    @Test
    public void testMateIn3_S3MSVsHireath() {
        final Board board = FenUtilities.createGameFromFEN("6r1/ppp2krp/5q2/3N4/2pb4/8/PP2QPPP/R1B1R1K1 b - - 0 1");
        final MoveStrategy alphaBeta = new BlackWidowAI(6);
        final Move actual = alphaBeta.execute(board);
        final Move expected = MoveUtils.getMove(board, "g7", "g2"); // Rxg2+
        assertEquals(expected, actual);
    }

    @Test
    public void testMateIn3_CanIHaveYourQueen() {
        final Board board = FenUtilities.createGameFromFEN("r3b1r1/2q2pbk/3p1p1p/1pnPpP1Q/p1p1P1NN/2P3RP/2B3P1/1R4K1 w - - 1 0");
        final MoveStrategy alphaBeta = new BlackWidowAI(6);
        final Move actual = alphaBeta.execute(board);
        final Move expected = MoveUtils.getMove(board, "h5", "h6"); // Qxh6+
        assertEquals(expected, actual);
    }

    @Test
    public void testMateIn3_Berant() {
        final Board board = FenUtilities.createGameFromFEN("r4r2/pbq1npk1/1p2pNp1/4P3/3p2P1/5N2/PPPQ1PK1/R4R2 w - - 1 0");
        final MoveStrategy alphaBeta = new BlackWidowAI(6);
        final Move actual = alphaBeta.execute(board);
        final Move expected = MoveUtils.getMove(board, "d2", "h6"); // Qh6+
        assertEquals(expected, actual);
    }

    @Test
    public void testMateIn3_ChivanovValery() {
        final Board board = FenUtilities.createGameFromFEN("5R2/7p/2R5/4p1k1/8/4K1P1/1rp4P/3r4 w - - 1 0");
        final MoveStrategy alphaBeta = new BlackWidowAI(6);
        final Move actual = alphaBeta.execute(board);
        final Move expected = MoveUtils.getMove(board, "f8", "g8"); // Rg8+
        assertEquals(expected, actual);
    }

    @Test
    public void testMateIn3_Chubujenio() {
        final Board board = FenUtilities.createGameFromFEN("3q1bk1/6r1/P1Rp4/3Pp2p/1P2Pp2/3Q1P1n/7P/5R1K b - - 0 1");
        final MoveStrategy alphaBeta = new BlackWidowAI(6);
        final Move actual = alphaBeta.execute(board);
        final Move expected = MoveUtils.getMove(board, "d8", "g5"); // Qg5
        assertEquals(expected, actual);
    }

    @Test
    public void testMateIn3_ColonelVsZoran() {
        // Black mates in 3. Colonel vs zoran59, lichess, (2050)
        // 8/6rk/3pQP1p/3P4/7q/2p2B2/2r3P1/3RR1K1 b - - 0 1
        // Solution: Rgxg2+ Bxg2 Qf2+ Kh2 Qxg2#
        final Board board = FenUtilities.createGameFromFEN("8/6rk/3pQP1p/3P4/7q/2p2B2/2r3P1/3RR1K1 b - - 0 1");
        final MoveStrategy alphaBeta = new BlackWidowAI(6);
        final Move actual = alphaBeta.execute(board);
        final Move expected = MoveUtils.getMove(board, "g7", "g2"); // Rgxg2+
        assertEquals(expected, actual);
    }

    @Test
    public void testMateIn3_MuratxVsDerSaxe() {
        // White mates in 3. muratx vs DerSaxe, lichess, (2062)
        // 7k/p6p/1p4pP/2q2p2/3r1P2/PQ6/6PK/8 w - - 1 0
        // Solution: Qf7 Rd7 Qe8+ Qf8 Qxf8#
        final Board board = FenUtilities.createGameFromFEN("7k/p6p/1p4pP/2q2p2/3r1P2/PQ6/6PK/8 w - - 1 0");
        final MoveStrategy alphaBeta = new BlackWidowAI(6);
        final Move actual = alphaBeta.execute(board);
        final Move expected = MoveUtils.getMove(board, "b3", "f7"); // Qf7
        assertEquals(expected, actual);
    }

    @Test
    public void testMateIn3_BishopSacrifice() {
        // White mates in 3. khokharrahul940 vs Taco1mommy, lichess, (2063)
        // r4r1k/pp3ppp/4p2N/qb6/5Q2/3n4/PB3PPP/R3R1K1 w - - 1 0
        // Solution: Bxg7+ Kxg7 Qg5+ Kh8 Qf6#
        final Board board = FenUtilities.createGameFromFEN("r4r1k/pp3ppp/4p2N/qb6/5Q2/3n4/PB3PPP/R3R1K1 w - - 1 0");
        final MoveStrategy alphaBeta = new BlackWidowAI(6);
        final Move actual = alphaBeta.execute(board);
        final Move expected = MoveUtils.getMove(board, "b2", "g7"); // Bxg7+
        assertEquals(expected, actual);
    }

    @Test
    public void testMateIn3_RookSacrifice() {
        // White mates in 3. rodzhers vs WAYAHE, lichess, (2067)
        // rq6/4Rrkp/6N1/3P2PQ/1p3p2/7P/p7/2K5 w - - 1 0
        // Solution: Rxf7+ Kxf7 Qxh7+ Ke8 Qe7#
        final Board board = FenUtilities.createGameFromFEN("rq6/4Rrkp/6N1/3P2PQ/1p3p2/7P/p7/2K5 w - - 1 0");
        final MoveStrategy alphaBeta = new BlackWidowAI(6);
        final Move actual = alphaBeta.execute(board);
        final Move expected = MoveUtils.getMove(board, "e7", "f7"); // Rxf7+
        assertEquals(expected, actual);
    }

    // ========== MATE IN 4 PUZZLES (Depth 8) ==========

    @Test
    public void testMateIn4_PitschelVsAnderssen1851() {
        // Claus Pitschel vs Adolf Anderssen, Leipzig, 1851
        // r5rk/2p1Nppp/3p3P/pp2p1P1/4P3/2qnPQK1/8/R6R w - - 1 0
        // Solution: 1. hxg7+ Rxg7 2. Rxh7+ Rxh7 3. Qf6+ Rg7 4. Rh1#
        final Board board = FenUtilities.createGameFromFEN("r5rk/2p1Nppp/3p3P/pp2p1P1/4P3/2qnPQK1/8/R6R w - - 1 0");
        final MoveStrategy alphaBeta = new BlackWidowAI(8);
        final Move actual = alphaBeta.execute(board);
        final Move expected = MoveUtils.getMove(board, "h6", "g7"); // hxg7+
        assertEquals(expected, actual);
    }

    @Test
    public void testMateIn4_AnderssenVsDufresne1852() {
        // Adolf Anderssen vs Jean Dufresne, Berlin, 1852
        // 1r2k1r1/pbppnp1p/1b3P2/8/Q7/B1PB1q2/P4PPP/3R2K1 w - - 1 0
        // Solution: 1. Qxd7+ Kxd7 2. Bf5+ Ke8 3. Bd7+ Kf8 4. Bxe7#
        final Board board = FenUtilities.createGameFromFEN("1r2k1r1/pbppnp1p/1b3P2/8/Q7/B1PB1q2/P4PPP/3R2K1 w - - 1 0");
        final MoveStrategy alphaBeta = new BlackWidowAI(8);
        final Move actual = alphaBeta.execute(board);
        final Move expected = MoveUtils.getMove(board, "a4", "d7"); // Qxd7+
        assertEquals(expected, actual);
    }

    @Test
    public void testMateIn4_MeekVsMorphy1855() {
        // Alexander Meek vs Paul Morphy, New Orleans, 1855 (Black to move)
        // Q7/p1p1q1pk/3p2rp/4n3/3bP3/7b/PP3PPK/R1B2R2 b - - 0 1
        // Solution: 1... Bxg2 2. Qh8+ Kxh8 3. Bg5 Qxg5 4. Rfe1 Nf3#
        final Board board = FenUtilities.createGameFromFEN("Q7/p1p1q1pk/3p2rp/4n3/3bP3/7b/PP3PPK/R1B2R2 b - - 0 1");
        final MoveStrategy alphaBeta = new BlackWidowAI(8);
        final Move actual = alphaBeta.execute(board);
        final Move expected = MoveUtils.getMove(board, "h3", "g2"); // Bxg2
        assertEquals(expected, actual);
    }

    @Test
    public void testMateIn4_MorphyVsForde1858() {
        // Paul Morphy vs AP Forde, New Orleans, 1858
        // r1bqr3/ppp1B1kp/1b4p1/n2B4/3PQ1P1/2P5/P4P2/RN4K1 w - - 1 0
        // Solution: 1. Qe5+ Kh6 2. g5+ Kh5 3. Bf3+ Bg4 4. Qh2#
        final Board board = FenUtilities.createGameFromFEN("r1bqr3/ppp1B1kp/1b4p1/n2B4/3PQ1P1/2P5/P4P2/RN4K1 w - - 1 0");
        final MoveStrategy alphaBeta = new BlackWidowAI(8);
        final Move actual = alphaBeta.execute(board);
        final Move expected = MoveUtils.getMove(board, "e4", "e5"); // Qe5+
        assertEquals(expected, actual);
    }

    @Test
    public void testMateIn4_MorphyVsNn1858() {
        final Board board = FenUtilities.createGameFromFEN("r1b3kr/3pR1p1/ppq4p/5P2/4Q3/B7/P5PP/5RK1 w - - 1 0");
        final MoveStrategy alphaBeta = new BlackWidowAI(8);
        final Move actual = alphaBeta.execute(board);
        final Move expected = MoveUtils.getMove(board, "f5", "f6");
        assertEquals(expected, actual);
    }

    @Test
    public void testMateIn4_KolischVsCenturini1859() {
        final Board board = FenUtilities.createGameFromFEN("2k4r/1r1q2pp/QBp2p2/1p6/8/8/P4PPP/2R3K1 w - - 1 0");
        final MoveStrategy alphaBeta = new BlackWidowAI(8);
        final Move actual = alphaBeta.execute(board);
        final Move expected = MoveUtils.getMove(board, "a6", "a8"); // Qa8+
        assertEquals(expected, actual);
    }

    @Test
    public void testMateIn4_MorphyVsBoden1859() {
        final Board board = FenUtilities.createGameFromFEN("2r1r3/p3P1k1/1p1pR1Pp/n2q1P2/8/2p4P/P4Q2/1B3RK1 w - - 1 0");
        final MoveStrategy alphaBeta = new BlackWidowAI(8);
        final Move actual = alphaBeta.execute(board);
        final Move expected = MoveUtils.getMove(board, "f5", "f6"); // f6+
        assertEquals(expected, actual);
    }

    @Test
    public void testMateIn4_RosanesVsAnderssen1863() {
        final Board board = FenUtilities.createGameFromFEN("4r3/p4pkp/q7/3Bbb2/P2P1ppP/2N3n1/1PP2KPR/R1BQ4 b - - 0 1");
        final MoveStrategy alphaBeta = new BlackWidowAI(8);
        final Move actual = alphaBeta.execute(board);
        final Move expected = MoveUtils.getMove(board, "a6", "f1"); // Qf1+
        assertEquals(expected, actual);
    }

}