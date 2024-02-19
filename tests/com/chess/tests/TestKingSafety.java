package com.chess.tests;

import org.junit.Test;

import com.chess.engine.classic.Alliance;
import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.board.Board.Builder;
import com.chess.engine.classic.pieces.King;
import com.chess.engine.classic.pieces.Pawn;

/**
 * Commented by Steven Nguyen 2/18/2024:
 *
 * This test class only has one test but no assertion. Based on observation, the
 * creator made a change within the program at some point in time that broke this test case,
 * and so he commented out the assertion line. However, he did not get back to it since.
 *
 * */
public class TestKingSafety {
//    @Test
//    public void test1() {
//        final Builder builder = new Builder();
//        // Black Layout
//        builder.setPiece(new King(Alliance.BLACK, 4, false, false));
//        builder.setPiece(new Pawn(Alliance.BLACK, 12));
//        // White Layout
//        builder.setPiece(new Pawn(Alliance.WHITE, 52));
//        builder.setPiece(new King(Alliance.WHITE, 60, false, false));
//        builder.setMoveMaker(Alliance.WHITE);
//        // Set the current player
//        final Board board = builder.build();
//
//        assertEquals(KingSafetyAnalyzer.get().calculateKingTropism(board.whitePlayer()).tropismScore(), 40);
//    }

}