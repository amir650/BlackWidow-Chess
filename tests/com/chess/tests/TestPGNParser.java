package com.chess.tests;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import com.chess.pgn.*;
import org.junit.Test;

import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.board.Move;
import com.chess.engine.classic.board.MoveTransition;
import com.google.common.io.Resources;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class TestPGNParser {

    @Test
    public void test1() {
        final Board board = FenUtilities.createGameFromFEN("6k1/3r3p/pR4p1/P3p3/4P3/1p3P2/2r3PP/1R4K1 w - - 0 1");
        final String san = "R6xb3";
        final Move move = PGNUtilities.createMove(board, san);
        assertTrue(move != null);
    }

}
