package com.chess.tests;

import com.chess.engine.classic.Alliance;
import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.board.BoardUtils;
import com.chess.engine.classic.board.Move;
import com.chess.engine.classic.board.MoveTransition;
import com.chess.engine.classic.pieces.King;
import com.chess.engine.classic.pieces.Pawn;
import com.chess.engine.classic.player.ai.BoardEvaluator;
import com.chess.engine.classic.player.ai.StandardBoardEvaluator;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class TestCoverage {
    @Test
    public void testEvaluationDetails() {
        final Board.Builder builder = new Board.Builder();
        // Black Layout
        builder.setPiece(new King(Alliance.BLACK, 4, false, false));
        builder.setPiece(new Pawn(Alliance.BLACK, 12));
        // White Layout
        builder.setPiece(new Pawn(Alliance.WHITE, 52));
        builder.setPiece(new King(Alliance.WHITE, 60, false, false));
        builder.setMoveMaker(Alliance.WHITE);
        // Set the current player
        final Board board = builder.build();

        BoardEvaluator evaluator = StandardBoardEvaluator.get();
        System.out.println(StandardBoardEvaluator.get().evaluationDetails(board,0));
        String result = ("White Mobility : " + 50 + "\n" +
                "White kingThreats : " + 0 + "\n" +
                "White attacks : " + 0 + "\n" +
                "White castle : " + 0 + "\n" +
                "White pieceEval : " + 100080 + "\n" +
                "White pawnStructure : " + -10 + "\n" +
                "---------------------\n" +
                "Black Mobility : " + 50 + "\n" +
                "Black kingThreats : " + 0 + "\n" +
                "Black attacks : " + 0 + "\n" +
                "Black castle : " + 0 + "\n" +
                "Black pieceEval : " + 100080 + "\n" +
                "Black pawnStructure : " + -10 + "\n\n" +
                "Final Score = " + 0);
        System.out.println(result);
        assertEquals(StandardBoardEvaluator.get().evaluationDetails(board,0), result);
    }
}
