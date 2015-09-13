package com.chess.tests;

import static junit.framework.Assert.assertEquals;

import com.chess.com.chess.pgn.FenUtilities;
import com.chess.engine.classic.player.ai.StandardBoardEvaluator;
import org.junit.Ignore;
import org.junit.Test;

import com.chess.engine.classic.Alliance;
import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.board.Board.Builder;
import com.chess.engine.classic.pieces.King;
import com.chess.engine.classic.pieces.Pawn;
import com.chess.engine.classic.player.ai.PawnStructureAnalyzer;

public class TestPawnStructure {

    @Ignore
    @Test
    public void test1() {
        final Builder builder = new Builder();
        // Black Layout
        builder.setPiece(new King(Alliance.BLACK, 4));
        builder.setPiece(new Pawn(Alliance.BLACK, 12));
        builder.setPiece(new Pawn(Alliance.BLACK, 20));
        builder.setPiece(new Pawn(Alliance.BLACK, 28));
        builder.setPiece(new Pawn(Alliance.BLACK, 8));
        builder.setPiece(new Pawn(Alliance.BLACK, 16));
        // White Layout
        builder.setPiece(new Pawn(Alliance.WHITE, 52));
        builder.setPiece(new King(Alliance.WHITE, 60));
        builder.setMoveMaker(Alliance.WHITE);
        // Set the current player
        final Board board = builder.build();

        assertEquals(PawnStructureAnalyzer.get().pawnStructureScore(board.whitePlayer()), -25);
        assertEquals(PawnStructureAnalyzer.get().pawnStructureScore(board.blackPlayer()), -100);
    }

    @Test
    public void test2() {

        final Board board = FenUtilities.createGameFromFEN("4k3/2p1p1p1/8/8/8/8/2P1P1P1/4K3 w KQkq -");

        System.out.println(PawnStructureAnalyzer.get().pawnStructureScore(board.whitePlayer()));
        System.out.println(PawnStructureAnalyzer.get().pawnStructureScore(board.blackPlayer()));

        StandardBoardEvaluator boardEvaluator = new StandardBoardEvaluator();

        System.out.println(boardEvaluator.evaluate(board, 0));

    }

}