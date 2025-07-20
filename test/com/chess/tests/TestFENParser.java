package com.chess.tests;

import com.chess.pgn.FenUtilities;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.MoveTransition;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class TestFENParser {

    @Test
    public void testWriteFEN1() throws IOException {
        final Board board = Board.createStandardBoard();
        final String fenString = FenUtilities.createFENFromGame(board);
        assertEquals("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1", fenString);
    }

    @Test
    public void testWriteFEN2() throws IOException {
        final Board board = Board.createStandardBoard();
        final MoveTransition t1 = board.currentPlayer()
                .makeMove(Move.MoveFactory.createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("e2"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("e4")));
        assertTrue(t1.getMoveStatus().isDone());
        final String fenString = FenUtilities.createFENFromGame(t1.getToBoard());
        assertEquals("rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq e3 0 1", fenString);
        final MoveTransition t2 = t1.getToBoard().currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t1.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("c7"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("c5")));
        assertTrue(t2.getMoveStatus().isDone());
        final String fenString2 = FenUtilities.createFENFromGame(t2.getToBoard());
        assertEquals("rnbqkbnr/pp1ppppp/8/2p5/4P3/8/PPPP1PPP/RNBQKBNR w KQkq c6 0 1", fenString2);

    }

}
