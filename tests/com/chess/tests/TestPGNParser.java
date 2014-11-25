package com.chess.tests;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.junit.Test;

import com.chess.com.chess.pgn.Book;
import com.chess.com.chess.pgn.Game;
import com.chess.com.chess.pgn.Game.GameStatus;
import com.chess.com.chess.pgn.PGNUtilities;
import com.chess.engine.classic.board.Board;
import com.google.common.io.Resources;

public class TestPGNParser {

    @Test
    public void test1() throws IOException {
        doTest("com/chess/tests/pgn/t1.pgn");
    }

    @Test
    public void test2() throws IOException {
        doTest("com/chess/tests/pgn/t2.pgn");
    }

    @Test
    public void test3() throws IOException {
        doTest("com/chess/tests/pgn/t3.pgn");
    }

    @Test
    public void test4() throws IOException {
        doTest("com/chess/tests/pgn/t4.pgn");
    }

    @Test
    public void test5() throws IOException {
        doTest("com/chess/tests/pgn/smallerTest.pgn");
    }

    @Test
    public void test6() throws IOException {
        doTest("com/chess/tests/pgn/t6.pgn");
    }

    @Test
    public void test8() throws IOException {
        doTest("com/chess/tests/pgn/t8.pgn");
    }

    @Test
    public void test9() throws IOException {
        doTest("com/chess/tests/pgn/t9.pgn");
    }

    @Test
    public void testPawnPromotion() throws IOException {
        doTest("com/chess/tests/pgn/queenPromotion.pgn");
    }

    @Test
    public void test10() throws IOException {
        doTest("com/chess/tests/pgn/t10.pgn");
    }

    private static void doTest(final String testFilePath) throws IOException {
        final URL url = Resources.getResource(testFilePath);
        final File testPGNFile = new File(url.getFile());
        final Book book = PGNUtilities.parsePGNFile(testPGNFile);

        int count = 0;
        for(final Game game : book.getGames()) {
            if(game.play(Board.createStandardBoard()) == GameStatus.PLAYED_SUCCESSFULLY) {
                count++;
            }
        }

        System.out.println("Played " + count + " games successfully of " + book.getGames().size());

    }
}
