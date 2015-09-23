package com.chess.tests;

import static junit.framework.Assert.assertEquals;

import com.chess.engine.classic.pieces.Rook;
import com.chess.engine.classic.player.ai.RookStructureAnalyzer;
import org.junit.Ignore;
import org.junit.Test;

import com.chess.com.chess.pgn.FenUtilities;
import com.chess.engine.classic.Alliance;
import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.board.Board.Builder;
import com.chess.engine.classic.pieces.King;
import com.chess.engine.classic.pieces.Pawn;
import com.chess.engine.classic.player.ai.PawnStructureAnalyzer;
import com.chess.engine.classic.player.ai.StandardBoardEvaluator;

public class TestRookStructure {

    @Test
    public void test1() {

        final Board board = Board.createStandardBoard();

        assertEquals(RookStructureAnalyzer.get().rookStructureScore(board, board.whitePlayer()), 0);
        assertEquals(RookStructureAnalyzer.get().rookStructureScore(board, board.whitePlayer()), 0);
    }

    @Test
    public void test2() {
        final Builder builder = new Builder();
        // Black Layout
        builder.setPiece(new Rook(Alliance.BLACK, 0));
        builder.setPiece(new King(Alliance.BLACK, 4, false, false));
        // White Layout
        builder.setPiece(new Rook(Alliance.WHITE, 63));
        builder.setPiece(new King(Alliance.WHITE, 60, false, false));
        builder.setMoveMaker(Alliance.WHITE);
        // Set the current player
        final Board board = builder.build();

        assertEquals(RookStructureAnalyzer.get().rookStructureScore(board, board.whitePlayer()), 25);
        assertEquals(RookStructureAnalyzer.get().rookStructureScore(board, board.whitePlayer()), 25);

    }


}