package com.chess.tests;

import com.chess.engine.player.Player;
import com.chess.engine.player.ai.KingSafetyAnalyzer;
import com.chess.pgn.FenUtilities;
import org.junit.Test;

import com.chess.engine.board.Board;


import static org.junit.Assert.assertEquals;

public class TestKingSafety {

    @Test
    public void testKingUncastledInCenter() {
        final Board board = FenUtilities.createGameFromFEN("4k3/8/8/8/8/8/8/4K3 w - - 0 1");
        final Player whitePlayer = board.currentPlayer();
        System.out.println(KingSafetyAnalyzer.get().gptKingSafety(whitePlayer));
        assertEquals(-KingSafetyAnalyzer.CENTER_KING_PENALTY, KingSafetyAnalyzer.get().gptKingSafety(whitePlayer));
    }

    @Test
    public void testKingUncastledNotInCenter() {
        // White king on b1 (uncastled, not center), black king on b8
        final Board board = FenUtilities.createGameFromFEN("1k6/8/8/8/8/8/8/1K6 w - - 0 1");
        final Player whitePlayer = board.currentPlayer();
        assertEquals(-KingSafetyAnalyzer.UNCASTLED_KING_PENALTY, KingSafetyAnalyzer.get().gptKingSafety(whitePlayer));
    }

}