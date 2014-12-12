package com.chess.tests;

import static junit.framework.Assert.assertEquals;

import org.junit.Test;

import com.chess.engine.classic.Alliance;
import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.board.Board.Builder;
import com.chess.engine.classic.pieces.King;
import com.chess.engine.classic.pieces.Pawn;
import com.chess.engine.classic.player.ai.PawnStructureAnalyzer;

public class TestPawnStructure {

    @Test
    public void test1() {
        final Builder builder = new Builder();
        // Black Layout
        builder.setPiece(4, new King(Alliance.BLACK, 4));
        builder.setPiece(12, new Pawn(Alliance.BLACK, 12));
        builder.setPiece(20, new Pawn(Alliance.BLACK, 20));
        builder.setPiece(28, new Pawn(Alliance.BLACK, 28));
        builder.setPiece(8, new Pawn(Alliance.BLACK, 8));
        builder.setPiece(16, new Pawn(Alliance.BLACK, 16));
        // White Layout
        builder.setPiece(52, new Pawn(Alliance.WHITE, 52));
        builder.setPiece(60, new King(Alliance.WHITE, 60));
        builder.setMoveMaker(Alliance.WHITE);
        // Set the current player
        final Board board = builder.build();

        assertEquals(PawnStructureAnalyzer.get().pawnStructureScore(board.getWhitePieces()), 80);
        assertEquals(PawnStructureAnalyzer.get().pawnStructureScore(board.getBlackPieces()), 30);
    }

}