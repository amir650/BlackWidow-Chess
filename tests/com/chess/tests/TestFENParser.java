package com.chess.tests;

import com.chess.com.chess.pgn.FenUtilities;
import com.chess.com.chess.pgn.MySqlGamePersistence;
import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.board.Move;
import com.chess.engine.classic.board.MoveTransition;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;


public class TestFENParser {



    @Test
    public void testInitialConfiguration() throws IOException {

        Board board = FenUtilities.createGameFromFEN("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
        //"r1b321k/1------p/-P-n-pr-/pq-p----/--b-P---/---N--N-/-PP-QBPP/PR---K--/R  - - 0 1"

        System.out.println(board);

    }

    @Test
    public void testWriteFEN1() throws IOException {

        Board board = Board.createStandardBoard();

        final String fenString = FenUtilities.createFENFromGame(board);

        System.out.println(fenString);
        assertEquals(fenString, "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");

    }

}
