package com.chess.tests;

import com.chess.pgn.FenUtilities;
import com.chess.pgn.PGNUtilities;
import org.junit.Test;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

import static org.junit.Assert.*;


public class TestPGNParser {

    @Test
    public void test1() {
        final Board board =  FenUtilities.createGameFromFEN("6k1/3r3p/pR4p1/P3p3/4P3/1p3P2/2r3PP/1R4K1 w - - 0 1");
        final String san = "R6xb3";
        final Move move = PGNUtilities.createMove(board, san);
        assertNotNull(move);
    }

    @Test
    public void test2() {
        final Board board = FenUtilities.createGameFromFEN("r2qkb1r/1b3ppp/p3pn2/1p1P4/1n6/1BN2N2/PP2QPPP/R1BR2K1 b kq - 0 1");
        final Move bad_move = board.currentPlayer().getLegalMoves().toArray(new Move[0])[35];
        System.out.println(bad_move.toString());
        final String san = "Nbxd5";
        final Move move = PGNUtilities.createMove(board, san);
        assertNotSame(move, Move.MoveFactory.getNullMove());
    }

    @Test
    public void test3() {
        final Board board = FenUtilities.createGameFromFEN("r1bq1Bk1/pp2pp1p/2n3p1/2pn4/5P2/NP2PN2/P1PPp1PP/3RQRK1 b - - 0 1");
        final Move bad_move = board.currentPlayer().getLegalMoves().toArray(new Move[0])[40];
        System.out.println(bad_move.toString());
        final String san = "exd1=Q";
        final Move move = PGNUtilities.createMove(board, san);
        assertNotSame(move, Move.MoveFactory.getNullMove());
    }

}
