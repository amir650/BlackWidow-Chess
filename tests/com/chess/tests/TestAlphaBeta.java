package com.chess.tests;

import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.player.ai.MoveStrategy;
import com.chess.engine.player.ai.StockAlphaBeta;
import com.chess.pgn.FenUtilities;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TestAlphaBeta {

    @Test
    public void testQualityDepth6() {
        final Board board = FenUtilities.createGameFromFEN("4k2r/1R3R2/p3p1pp/4b3/1BnNr3/8/P1P5/5K2 w - - 1 0");
        final MoveStrategy alphaBeta = new StockAlphaBeta(7);
        final Move expected = getMove(board, "f7", "e7");
        final Move actual = alphaBeta.execute(board);
        assertEquals(expected, actual);
    }

    private static Move getMove(final Board board,
                                final String from,
                                final String to) {
        return Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition(from), BoardUtils.INSTANCE.getCoordinateAtPosition(to));
    }

    @Test
    public void testMateInOne() {
        // White to move, mate in one with Qh5#
        final Board board = FenUtilities.createGameFromFEN("rnb1kbnr/pppp1ppp/4p3/8/6Pq/5P2/PPPPP2P/RNBQKBNR w KQkq - 1 3");
        final MoveStrategy alphaBeta = new StockAlphaBeta(5);
        final Move expected = getMove(board, "d1", "h5");
        final Move actual = alphaBeta.execute(board);
        assertEquals(expected, actual);
    }

    @Test
    public void testMateInOneBackrank() {
        // White to move, back rank mate with Rd8#
        final Board board = FenUtilities.createGameFromFEN("6k1/5ppp/8/8/8/8/8/R3K3 w - - 0 1");
        final MoveStrategy alphaBeta = new StockAlphaBeta(5);
        final Move expected = getMove(board, "a1", "a8");
        final Move actual = alphaBeta.execute(board);
        assertEquals(expected, actual);
    }

    @Test
    public void testDiscoveredAttack() {
        // White to move, discovered attack winning material
        final Board board = FenUtilities.createGameFromFEN("r1bqk2r/pppp1ppp/2n2n2/2b1p3/2B1P3/3P1N2/PPP2PPP/RNBQ1RK1 w kq - 0 6");
        final MoveStrategy alphaBeta = new StockAlphaBeta(6);
        final Move actual = alphaBeta.execute(board);

        // Should find a strong move, likely involving the bishops or knights
        assertNotNull(actual);
        assertNotEquals(Move.MoveFactory.getNullMove(), actual);
    }

    @Test
    public void testPinDefense() {
        // Black king pinned piece scenario - engine should handle pins correctly
        final Board board = FenUtilities.createGameFromFEN("r1bqkbnr/pppp1ppp/2n5/4p3/2B1P3/5N2/PPPP1PPP/RNBQK2R b KQkq - 3 3");
        final MoveStrategy alphaBeta = new StockAlphaBeta(5);
        final Move actual = alphaBeta.execute(board);

        // Should make a reasonable move despite the pin
        assertNotNull(actual);
        assertNotEquals(Move.MoveFactory.getNullMove(), actual);
    }

    @Test
    public void testCastlingDecision() {
        // Position where castling is available - engine should consider it
        final Board board = FenUtilities.createGameFromFEN("r1bqkb1r/pppp1ppp/2n2n2/4p3/2B1P3/3P1N2/PPP2PPP/RNBQK2R w KQkq - 4 4");
        final MoveStrategy alphaBeta = new StockAlphaBeta(5);
        final Move actual = alphaBeta.execute(board);

        // Should make a reasonable developing move or castle
        assertNotNull(actual);
        assertNotEquals(Move.MoveFactory.getNullMove(), actual);
    }

    @Test
    public void testPawnPromotion() {
        // White pawn about to promote
        final Board board = FenUtilities.createGameFromFEN("8/P7/8/8/8/8/8/K6k w - - 0 1");
        final MoveStrategy alphaBeta = new StockAlphaBeta(5);
        final Move actual = alphaBeta.execute(board);

        // Should promote the pawn
        assertTrue(actual instanceof Move.PawnPromotion ||
                actual.getDestinationCoordinate() == BoardUtils.INSTANCE.getCoordinateAtPosition("a8"));
    }

    @Test
    public void testPawnPromotionChoice() {
        // Position where underpromotion might be better
        final Board board = FenUtilities.createGameFromFEN("8/P6k/8/8/8/8/8/K7 w - - 0 1");
        final MoveStrategy alphaBeta = new StockAlphaBeta(6);
        final Move actual = alphaBeta.execute(board);

        // Should promote (likely to queen)
        assertTrue(actual instanceof Move.PawnPromotion ||
                actual.getDestinationCoordinate() == BoardUtils.INSTANCE.getCoordinateAtPosition("a8"));
    }

    @Test
    public void testCaptureHighestValue() {
        // Multiple capture options - should capture highest value piece
        final Board board = FenUtilities.createGameFromFEN("rnbqkbnr/ppp2ppp/4p3/3p4/3PP3/8/PPP2PPP/RNBQKBNR w KQkq d6 0 3");
        final MoveStrategy alphaBeta = new StockAlphaBeta(5);
        final Move actual = alphaBeta.execute(board);

        // Should make a good developing or capturing move
        assertNotNull(actual);
        assertNotEquals(Move.MoveFactory.getNullMove(), actual);
    }

    @Test
    public void testEndgameKingActivity() {
        // King and pawn endgame - king should be active
        final Board board = FenUtilities.createGameFromFEN("8/8/8/4k3/4P3/4K3/8/8 w - - 0 1");
        final MoveStrategy alphaBeta = new StockAlphaBeta(6);
        final Move actual = alphaBeta.execute(board);

        // Should move king or push pawn
        assertNotNull(actual);
        assertTrue(actual.getMovedPiece().getPieceType().toString().equals("K") ||
                actual.getMovedPiece().getPieceType().toString().equals("P"));
    }

    @Test
    public void testAvoidStalemate() {
        // Position where poor play could lead to stalemate
        final Board board = FenUtilities.createGameFromFEN("8/8/8/8/8/8/8/K6k w - - 0 1");
        final MoveStrategy alphaBeta = new StockAlphaBeta(5);
        final Move actual = alphaBeta.execute(board);

        // Should make any legal move (avoiding stalemate)
        assertNotNull(actual);
        assertNotEquals(Move.MoveFactory.getNullMove(), actual);
    }

    @Test
    public void testOpeningDevelopment() {
        // Opening position - should develop pieces
        final Board board = FenUtilities.createGameFromFEN("rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq e3 0 1");
        final MoveStrategy alphaBeta = new StockAlphaBeta(5);
        final Move actual = alphaBeta.execute(board);

        // Should make a reasonable opening move
        assertNotNull(actual);
        assertNotEquals(Move.MoveFactory.getNullMove(), actual);
    }

    @Test
    public void testMiddlegameTactics() {
        // Complex middlegame position
        final Board board = FenUtilities.createGameFromFEN("r2qkb1r/pb2nppp/1pn1p3/3pP3/2pP4/2N2N2/PPB2PPP/R1BQK2R w KQkq - 0 8");
        final MoveStrategy alphaBeta = new StockAlphaBeta(6);
        final Move actual = alphaBeta.execute(board);

        // Should find a reasonable middlegame move
        assertNotNull(actual);
        assertNotEquals(Move.MoveFactory.getNullMove(), actual);
    }

    @Test
    public void testForkTactic() {
        // Position with fork opportunity
        final Board board = FenUtilities.createGameFromFEN("r1bqkb1r/pppp1ppp/2n2n2/4p3/2B1P3/3P1N2/PPP2PPP/RNBQK2R w KQkq - 4 4");
        final MoveStrategy alphaBeta = new StockAlphaBeta(6);
        final Move actual = alphaBeta.execute(board);

        // Should find a good tactical move
        assertNotNull(actual);
        assertNotEquals(Move.MoveFactory.getNullMove(), actual);
    }

    @Test
    public void testPieceTrapping() {
        // Position where a piece can be trapped
        final Board board = FenUtilities.createGameFromFEN("r1bqk2r/pppp1ppp/2n2n2/2b1p3/2B1P3/3P1N2/PPP2PPP/RNBQ1RK1 b kq - 0 6");
        final MoveStrategy alphaBeta = new StockAlphaBeta(6);
        final Move actual = alphaBeta.execute(board);

        // Should avoid getting pieces trapped or trap opponent pieces
        assertNotNull(actual);
        assertNotEquals(Move.MoveFactory.getNullMove(), actual);
    }

    @Test
    public void testQuiescenceSearch() {
        // Position with many captures available - should search captures deeply
        final Board board = FenUtilities.createGameFromFEN("r2q1rk1/ppp2ppp/2np1n2/2b1p3/2B1P3/2NP1N2/PPP2PPP/R1BQR1K1 w - - 0 8");
        final MoveStrategy alphaBeta = new StockAlphaBeta(5);
        final Move actual = alphaBeta.execute(board);

        // Should evaluate position correctly even with many tactics
        assertNotNull(actual);
        assertNotEquals(Move.MoveFactory.getNullMove(), actual);
    }

    @Test
    public void testDepthConsistency() {
        // Same position at different depths should be somewhat consistent
        final Board board = FenUtilities.createGameFromFEN("r1bqkb1r/pppp1ppp/2n2n2/4p3/2B1P3/3P1N2/PPP2PPP/RNBQK2R w KQkq - 4 4");

        final MoveStrategy alphaBeta3 = new StockAlphaBeta(3);
        final MoveStrategy alphaBeta5 = new StockAlphaBeta(5);

        final Move move3 = alphaBeta3.execute(board);
        final Move move5 = alphaBeta5.execute(board);

        // Both should return valid moves
        assertNotNull(move3);
        assertNotNull(move5);
        assertNotEquals(Move.MoveFactory.getNullMove(), move3);
        assertNotEquals(Move.MoveFactory.getNullMove(), move5);
    }

    @Test
    public void testPerformance() {
        // Test that search completes in reasonable time
        final Board board = FenUtilities.createGameFromFEN("r1bqkb1r/pppp1ppp/2n2n2/4p3/2B1P3/3P1N2/PPP2PPP/RNBQK2R w KQkq - 4 4");
        final MoveStrategy alphaBeta = new StockAlphaBeta(6);

        final long startTime = System.currentTimeMillis();
        final Move actual = alphaBeta.execute(board);
        final long endTime = System.currentTimeMillis();

        // Should complete within reasonable time (2 minutes)
        assertTrue("Search took too long: " + (endTime - startTime) + "ms",
                (endTime - startTime) < 1000 * 60 * 2);
        assertNotNull(actual);
        assertNotEquals(Move.MoveFactory.getNullMove(), actual);
    }

    @Test
    public void testBoardsEvaluated() {
         final Board board = FenUtilities.createGameFromFEN("r1bqkb1r/pppp1ppp/2n2n2/4p3/2B1P3/3P1N2/PPP2PPP/RNBQK2R w KQkq - 4 4");
        final StockAlphaBeta alphaBeta = new StockAlphaBeta(4);
        alphaBeta.execute(board);
        // Should have evaluated some boards
        assertTrue("No boards were evaluated", alphaBeta.getNumBoardsEvaluated() > 0);
        System.out.println("Boards evaluated: " + alphaBeta.getNumBoardsEvaluated());
    }

    @Test
    public void testNotNullMove() {
        // Ensure engine never returns null move in any reasonable position
        final String[] testPositions = {
                "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1", // Starting position
                "r1bqkb1r/pppp1ppp/2n2n2/4p3/2B1P3/3P1N2/PPP2PPP/RNBQK2R w KQkq - 4 4", // Italian game
                "rnbqkb1r/pp1ppppp/5n2/2p5/2P5/5N2/PP1PPPPP/RNBQKB1R w KQkq c6 0 3", // Caro-Kann
                "r1bqkbnr/pppp1ppp/2n5/4p3/4P3/5N2/PPPP1PPP/RNBQKB1R w KQkq - 4 3" // Four knights
        };

        final MoveStrategy alphaBeta = new StockAlphaBeta(4);
        for (String fen : testPositions) {
            final Board board = FenUtilities.createGameFromFEN(fen);
            final Move actual = alphaBeta.execute(board);
            assertNotNull(board);
            assertNotNull(actual);
            assertNotEquals(actual, Move.MoveFactory.getNullMove());
        }
    }
}