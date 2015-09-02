package com.chess.tests;

import com.chess.com.chess.pgn.FenUtilities;
import com.chess.com.chess.pgn.MySqlGamePersistence;
import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.board.Move;
import com.chess.engine.classic.board.MoveTransition;
import org.junit.Test;

import java.io.IOException;


public class TestFENParser {

    @Test
    public void testInitialConfiguration() throws IOException {

        Board board = FenUtilities.createGameFromFEN("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");

        System.out.println(board);

    }

}
