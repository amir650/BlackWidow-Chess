package com.chess.tests;

//import static junit.framework.Assert.assertEquals;
//
//import com.chess.engine.classic.pieces.Rook;
//import com.chess.engine.classic.player.ai.RookStructureAnalyzer;
//import org.junit.Test;
//
//import com.chess.engine.classic.Alliance;
//import com.chess.engine.classic.board.Board;
//import com.chess.engine.classic.board.Board.Builder;
//import com.chess.engine.classic.pieces.King;

/**
 * Commented by Steven Nguyen 2/18/2024:
 *
 * This test class only has a few tests but no assertions. Based on observation, the
 * creator made a change within the program at some point in time that broke this test case,
 * and so he commented out the test cases. However, he did not get back to it since.
 * */
public class TestRookStructure {
//
//    @Test
//    public void test1() {
//        final Board board = Board.createStandardBoard();
//        assertEquals(RookStructureAnalyzer.get().rookStructureScore(board, board.whitePlayer()), 0);
//        assertEquals(RookStructureAnalyzer.get().rookStructureScore(board, board.whitePlayer()), 0);
//    }
//
//    @Test
//    public void test2() {
//        final Builder builder = new Builder();
//        // Black Layout
//        builder.setPiece(new Rook(Alliance.BLACK, 0));
//        builder.setPiece(new King(Alliance.BLACK, 4, false, false));
//        // White Layout
//        builder.setPiece(new Rook(Alliance.WHITE, 63));
//        builder.setPiece(new King(Alliance.WHITE, 60, false, false));
//        builder.setMoveMaker(Alliance.WHITE);
//        // Set the current player
//        final Board board = builder.build();
//        assertEquals(RookStructureAnalyzer.get().rookStructureScore(board, board.whitePlayer()), 25);
//        assertEquals(RookStructureAnalyzer.get().rookStructureScore(board, board.whitePlayer()), 25);
//    }
//
//
}