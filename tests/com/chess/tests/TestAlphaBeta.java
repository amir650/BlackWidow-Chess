package com.chess.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.chess.com.chess.pgn.FenUtilities;
import com.chess.engine.classic.board.MoveTransition;
import com.chess.engine.classic.player.ai.AlphaBetaWithMoveOrdering;
import com.chess.engine.classic.player.ai.StockAlphaBeta;
import org.junit.Ignore;
import org.junit.Test;

import com.chess.engine.classic.Alliance;
import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.board.Board.Builder;
import com.chess.engine.classic.board.BoardUtils;
import com.chess.engine.classic.board.Move;
import com.chess.engine.classic.pieces.Bishop;
import com.chess.engine.classic.pieces.King;
import com.chess.engine.classic.pieces.Knight;
import com.chess.engine.classic.pieces.Pawn;
import com.chess.engine.classic.pieces.Queen;
import com.chess.engine.classic.pieces.Rook;
import com.chess.engine.classic.player.Player;
import com.chess.engine.classic.player.ai.MoveStrategy;

public class TestAlphaBeta {

    @Test
    public void testOpeningDepth4BlackMovesFirst() {
        final Builder builder = new Builder();
        // Black Layout
        builder.setPiece(new Rook(Alliance.BLACK, 0));
        builder.setPiece(new Knight(Alliance.BLACK, 1));
        builder.setPiece(new Bishop(Alliance.BLACK, 2));
        builder.setPiece(new Queen(Alliance.BLACK, 3));
        builder.setPiece(new King(Alliance.BLACK, 4, false, false));
        builder.setPiece(new Bishop(Alliance.BLACK, 5));
        builder.setPiece(new Knight(Alliance.BLACK, 6));
        builder.setPiece(new Rook(Alliance.BLACK, 7));
        builder.setPiece(new Pawn(Alliance.BLACK, 8));
        builder.setPiece(new Pawn(Alliance.BLACK, 9));
        builder.setPiece(new Pawn(Alliance.BLACK, 10));
        builder.setPiece(new Pawn(Alliance.BLACK, 11));
        builder.setPiece(new Pawn(Alliance.BLACK, 12));
        builder.setPiece(new Pawn(Alliance.BLACK, 13));
        builder.setPiece(new Pawn(Alliance.BLACK, 14));
        builder.setPiece(new Pawn(Alliance.BLACK, 15));
        // White Layout
        builder.setPiece(new Pawn(Alliance.WHITE, 48));
        builder.setPiece(new Pawn(Alliance.WHITE, 49));
        builder.setPiece(new Pawn(Alliance.WHITE, 50));
        builder.setPiece(new Pawn(Alliance.WHITE, 51));
        builder.setPiece(new Pawn(Alliance.WHITE, 52));
        builder.setPiece(new Pawn(Alliance.WHITE, 53));
        builder.setPiece(new Pawn(Alliance.WHITE, 54));
        builder.setPiece(new Pawn(Alliance.WHITE, 55));
        builder.setPiece(new Rook(Alliance.WHITE, 56));
        builder.setPiece(new Knight(Alliance.WHITE, 57));
        builder.setPiece(new Bishop(Alliance.WHITE, 58));
        builder.setPiece(new Queen(Alliance.WHITE, 59));
        builder.setPiece(new King(Alliance.WHITE, 60, false, false));
        builder.setPiece(new Bishop(Alliance.WHITE, 61));
        builder.setPiece(new Knight(Alliance.WHITE, 62));
        builder.setPiece(new Rook(Alliance.WHITE, 63));
        // Set the current player
        builder.setMoveMaker(Alliance.BLACK);

        final Board board = builder.build();

        final Player currentPlayer = board.currentPlayer();
        final MoveStrategy alphaBeta = new StockAlphaBeta();
        currentPlayer.setMoveStrategy(alphaBeta);
        final Move bestMove = board.currentPlayer().getMoveStrategy().execute(board, 4);
        assertEquals(bestMove, Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("b8"), BoardUtils.getCoordinateAtPosition("c6")));
    }

    @Test
    public void advancedLevelProblem2NakamuraShirov() {
        final Builder builder = new Builder();
        // Black Layout
        builder.setPiece(new King(Alliance.BLACK, 5, false, false));
        builder.setPiece(new Pawn(Alliance.BLACK, 10));
        builder.setPiece(new Rook(Alliance.BLACK, 25));
        builder.setPiece(new Bishop(Alliance.BLACK, 29));
        // White Layout
        builder.setPiece(new Knight(Alliance.WHITE, 27));
        builder.setPiece(new Rook(Alliance.WHITE, 36));
        builder.setPiece(new Pawn(Alliance.WHITE, 39));
        builder.setPiece(new King(Alliance.WHITE, 42, false, false));
        builder.setPiece(new Pawn(Alliance.WHITE, 46));
        // Set the current player
        builder.setMoveMaker(Alliance.WHITE);

        final Board board = builder.build();

        final Player currentPlayer = board.currentPlayer();
        currentPlayer.setMoveStrategy(new StockAlphaBeta());
        final Move bestMove = board.currentPlayer().getMoveStrategy().execute(board, 6);
        assertEquals(bestMove, Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("d5"), BoardUtils.getCoordinateAtPosition("c7")));
    }

    @Ignore
    @Test
    public void eloTest1() {
        final Builder builder = new Builder();
        // Black Layout
        builder.setPiece(new Rook(Alliance.BLACK, 0));
        builder.setPiece(new Bishop(Alliance.BLACK, 2));
        builder.setPiece(new King(Alliance.BLACK, 6, false, false));
        builder.setPiece(new Pawn(Alliance.BLACK, 14));
        builder.setPiece(new Knight(Alliance.BLACK, 18));
        builder.setPiece(new Pawn(Alliance.BLACK, 20));
        builder.setPiece(new Rook(Alliance.BLACK, 21));
        builder.setPiece(new Pawn(Alliance.BLACK, 23));
        builder.setPiece(new Queen(Alliance.BLACK, 24));
        builder.setPiece(new Pawn(Alliance.BLACK, 26));
        builder.setPiece(new Bishop(Alliance.BLACK, 33));
        // White Layout
        builder.setPiece(new Pawn(Alliance.WHITE, 16));
        builder.setPiece(new Pawn(Alliance.WHITE, 35));
        builder.setPiece(new Knight(Alliance.WHITE, 42));
        builder.setPiece(new Knight(Alliance.WHITE, 45));
        builder.setPiece(new Pawn(Alliance.WHITE, 48));
        builder.setPiece(new Pawn(Alliance.WHITE, 49));
        builder.setPiece(new Queen(Alliance.WHITE, 51));
        builder.setPiece(new Bishop(Alliance.WHITE, 52));
        builder.setPiece(new Pawn(Alliance.WHITE, 53));
        builder.setPiece(new Pawn(Alliance.WHITE, 54));
        builder.setPiece(new Pawn(Alliance.WHITE, 55));
        builder.setPiece(new Rook(Alliance.WHITE, 56));
        builder.setPiece(new King(Alliance.WHITE, 60, false, false));
        builder.setPiece(new Rook(Alliance.WHITE, 63));
        // Set the current player
        builder.setMoveMaker(Alliance.BLACK);
        final Board board = builder.build();
        final String fen = FenUtilities.createFENFromGame(board);
        System.out.println(fen);
        final Player currentPlayer = board.currentPlayer();
        currentPlayer.setMoveStrategy(new StockAlphaBeta());
        final Move bestMove = board.currentPlayer().getMoveStrategy().execute(board, 6);
        assertEquals(bestMove, Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("c5"), BoardUtils.getCoordinateAtPosition("d4")));
    }

    @Ignore
    @Test
    public void eloTest2() {
        final Builder builder = new Builder();
        // Black Layout
        builder.setPiece(new Knight(Alliance.BLACK, 2));
        builder.setPiece(new Queen(Alliance.BLACK, 3));
        builder.setPiece(new Knight(Alliance.BLACK, 5));
        builder.setPiece(new King(Alliance.BLACK, 6, false, false));
        builder.setPiece(new Pawn(Alliance.BLACK, 13));
        builder.setPiece(new Pawn(Alliance.BLACK, 15));
        builder.setPiece(new Pawn(Alliance.BLACK, 20));
        builder.setPiece(new Pawn(Alliance.BLACK, 22));
        builder.setPiece(new Pawn(Alliance.BLACK, 24));
        builder.setPiece(new Bishop(Alliance.BLACK, 25));
        builder.setPiece(new Pawn(Alliance.BLACK, 27));
        builder.setPiece(new Pawn(Alliance.BLACK, 33));
        // White Layout
        builder.setPiece(new Queen(Alliance.WHITE, 23));
        builder.setPiece(new Pawn(Alliance.WHITE, 28));
        builder.setPiece(new Knight(Alliance.WHITE, 30));
        builder.setPiece(new Pawn(Alliance.WHITE, 31));
        builder.setPiece(new Pawn(Alliance.WHITE, 35));
        builder.setPiece(new Pawn(Alliance.WHITE, 38));
        builder.setPiece(new Pawn(Alliance.WHITE, 41));
        builder.setPiece(new Knight(Alliance.WHITE, 46));
        builder.setPiece(new Pawn(Alliance.WHITE, 48));
        builder.setPiece(new Pawn(Alliance.WHITE, 53));
        builder.setPiece(new Bishop(Alliance.WHITE, 54));
        builder.setPiece(new King(Alliance.WHITE, 62, false, false));
        // Set the current player
        builder.setMoveMaker(Alliance.WHITE);
        final Board board = builder.build();
        System.out.println(FenUtilities.createFENFromGame(board));
        final Player currentPlayer = board.currentPlayer();
        currentPlayer.setMoveStrategy(new AlphaBetaWithMoveOrdering(1000));
        final Move bestMove = board.currentPlayer().getMoveStrategy().execute(board, 8);
        assertEquals(bestMove, Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("g5"), BoardUtils.getCoordinateAtPosition("h7")));
    }

    @Test
    public void eloTest3() {
        final Builder builder = new Builder();
        // Black Layout
        builder.setPiece(new Rook(Alliance.BLACK, 11));
        builder.setPiece(new Pawn(Alliance.BLACK, 14));
        builder.setPiece(new Pawn(Alliance.BLACK, 16));
        builder.setPiece(new Pawn(Alliance.BLACK, 17));
        builder.setPiece(new Pawn(Alliance.BLACK, 20));
        builder.setPiece(new Pawn(Alliance.BLACK, 22));
        builder.setPiece(new King(Alliance.BLACK, 25, false, false));
        builder.setPiece(new Knight(Alliance.BLACK, 33));
        // White Layout
        builder.setPiece(new Bishop(Alliance.WHITE, 19));
        builder.setPiece(new Pawn(Alliance.WHITE, 26));
        builder.setPiece(new King(Alliance.WHITE, 36, false, false));
        builder.setPiece(new Rook(Alliance.WHITE, 46));
        builder.setPiece(new Pawn(Alliance.WHITE, 49));
        builder.setPiece(new Pawn(Alliance.WHITE, 53));
        // Set the current player
        builder.setMoveMaker(Alliance.WHITE);
        final Board board = builder.build();
        final Player currentPlayer = board.currentPlayer();
        currentPlayer.setMoveStrategy(new StockAlphaBeta());
        final Move bestMove = board.currentPlayer().getMoveStrategy().execute(board, 6);
        assertEquals(bestMove, Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("g3"), BoardUtils.getCoordinateAtPosition("g6")));
    }

    @Test
    public void blackWidowLoss1() {
        final Board board = FenUtilities.createGameFromFEN("r2qkb1r/3p1pp1/p1n1p2p/1p1bP3/P2p4/1PP5/5PPP/RNBQNRK1 w kq - 0 13");
        final Player currentPlayer = board.currentPlayer();
        currentPlayer.setMoveStrategy(new StockAlphaBeta());
        final Move bestMove = board.currentPlayer().getMoveStrategy().execute(board, 6);
        assertEquals(bestMove, Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("a4"), BoardUtils.getCoordinateAtPosition("b5")));
    }

    @Test
    public void testCheckmateHorizon() {
        final Builder builder = new Builder();
        // Black Layout
        builder.setPiece(new Rook(Alliance.BLACK, 11));
        builder.setPiece(new Pawn(Alliance.BLACK, 16));
        builder.setPiece(new Bishop(Alliance.BLACK, 27));
        builder.setPiece(new King(Alliance.BLACK, 29, false, false));
        // White Layout
        builder.setPiece(new Rook(Alliance.WHITE, 17));
        builder.setPiece(new Rook(Alliance.WHITE, 26));
        builder.setPiece(new Pawn(Alliance.WHITE, 35));
        builder.setPiece(new Pawn(Alliance.WHITE, 45));
        builder.setPiece(new Bishop(Alliance.WHITE, 51));
        builder.setPiece(new Pawn(Alliance.WHITE, 54));
        builder.setPiece(new Pawn(Alliance.WHITE, 55));
        builder.setPiece(new King(Alliance.WHITE, 63, false, false));
        // Set the current player
        builder.setMoveMaker(Alliance.WHITE);
        final Board board = builder.build();
        final Player currentPlayer = board.currentPlayer();
        currentPlayer.setMoveStrategy(new StockAlphaBeta());
        final Move bestMove = board.currentPlayer().getMoveStrategy().execute(board, 4);
        assertEquals(bestMove, Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("g2"), BoardUtils.getCoordinateAtPosition("g4")));
    }

    @Test
    public void testBlackInTrouble() {
        final Builder builder = new Builder();
        // Black Layout
        builder.setPiece(new King(Alliance.BLACK, 7, false, false));
        builder.setPiece(new Pawn(Alliance.BLACK, 8));
        builder.setPiece(new Pawn(Alliance.BLACK, 9));
        builder.setPiece(new Pawn(Alliance.BLACK, 10));
        builder.setPiece(new Queen(Alliance.BLACK, 11));
        builder.setPiece(new Rook(Alliance.BLACK, 14));
        builder.setPiece(new Pawn(Alliance.BLACK, 15));
        builder.setPiece(new Bishop(Alliance.BLACK, 17));
        builder.setPiece(new Knight(Alliance.BLACK, 18));
        builder.setPiece(new Pawn(Alliance.BLACK, 19));
        builder.setPiece(new Pawn(Alliance.BLACK, 21));
        // White Layout
        builder.setPiece(new Knight(Alliance.WHITE, 31));
        builder.setPiece(new Pawn(Alliance.WHITE, 35));
        builder.setPiece(new Rook(Alliance.WHITE, 36));
        builder.setPiece(new Queen(Alliance.WHITE, 46));
        builder.setPiece(new Pawn(Alliance.WHITE, 48));
        builder.setPiece(new Pawn(Alliance.WHITE, 53));
        builder.setPiece(new Pawn(Alliance.WHITE, 54));
        builder.setPiece(new Pawn(Alliance.WHITE, 55));
        builder.setPiece(new King(Alliance.WHITE, 62, false, false));
        // Set the current player
        builder.setMoveMaker(Alliance.WHITE);
        final Board board = builder.build();
        final Player currentPlayer = board.currentPlayer();
        currentPlayer.setMoveStrategy(new StockAlphaBeta());
        final Move bestMove = board.currentPlayer().getMoveStrategy().execute(board, 4);
        assertEquals(bestMove, Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e4"), BoardUtils.getCoordinateAtPosition("e8")));
    }

    @Test
    public void findMate1() {
        final Board board = FenUtilities.createGameFromFEN("r2r1n2/pp2bk2/2p1p2p/3q4/3PN1QP/2P3R1/P4PP1/5RK1 w - - 0 1");
        final Player currentPlayer = board.currentPlayer();
        currentPlayer.setMoveStrategy(new StockAlphaBeta());
        final Move bestMove = board.currentPlayer().getMoveStrategy().execute(board, 7);
        assertEquals(bestMove, Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("g4"), BoardUtils.getCoordinateAtPosition("g7")));
    }

    @Test
    public void findMate2() {
        final Board board = FenUtilities.createGameFromFEN("1k2B3/1N6/1K6/8/8/8/8/8 w - -");
        final Player currentPlayer = board.currentPlayer();
        currentPlayer.setMoveStrategy(new StockAlphaBeta());
        final Move bestMove = board.currentPlayer().getMoveStrategy().execute(board, 7);
        assertEquals(bestMove, Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("e8"), BoardUtils.getCoordinateAtPosition("d7")));

        final MoveTransition t1 = board.currentPlayer()
                .makeMove(bestMove);

        assertTrue(t1.getMoveStatus().isDone());
    }

    @Test
    public void findMate3() {
        final Board board = FenUtilities.createGameFromFEN("5rk1/5Npp/8/3Q4/8/8/8/7K w - - 0");
        final Player currentPlayer = board.currentPlayer();
        currentPlayer.setMoveStrategy(new StockAlphaBeta());
        final Move bestMove = board.currentPlayer().getMoveStrategy().execute(board, 5);
        assertEquals(bestMove, Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("f7"), BoardUtils.getCoordinateAtPosition("h6")));
        final MoveTransition t1 = board.currentPlayer()
                .makeMove(bestMove);
        assertTrue(t1.getMoveStatus().isDone());
    }

    @Test
         public void runawayPawn() {
        final Board board = FenUtilities.createGameFromFEN("2k5/8/8/8/p7/8/8/4K3 b - - 0 1");
        final Player currentPlayer = board.currentPlayer();
        currentPlayer.setMoveStrategy(new StockAlphaBeta());
        final Move bestMove = board.currentPlayer().getMoveStrategy().execute(board, 5);
        assertEquals(bestMove, Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("a4"), BoardUtils.getCoordinateAtPosition("a3")));
        final MoveTransition t1 = board.currentPlayer()
                .makeMove(bestMove);
        assertTrue(t1.getMoveStatus().isDone());
    }

    @Test
    public void testMackHackScenario() {
        final Board board = FenUtilities.createGameFromFEN("1r1k1r2/p5Q1/2p3p1/8/1q1p2n1/3P2P1/P3RPP1/4RK2 b - - 0 1");
        final Player currentPlayer = board.currentPlayer();
        currentPlayer.setMoveStrategy(new StockAlphaBeta());
        final Move bestMove = board.currentPlayer().getMoveStrategy().execute(board, 8);
        assertEquals(bestMove, Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("f8"), BoardUtils.getCoordinateAtPosition("f2")));
        final MoveTransition t1 = board.currentPlayer()
                .makeMove(bestMove);
        assertTrue(t1.getMoveStatus().isDone());
    }

    @Test
    public void testAutoResponseVsPrinChess() {
        final Board board = FenUtilities.createGameFromFEN("r2q1rk1/p1p2pp1/3p1b2/2p2QNb/4PB1P/6R1/PPPR4/2K5 b - - 0 1");
        final Player currentPlayer = board.currentPlayer();
        currentPlayer.setMoveStrategy(new StockAlphaBeta());
        final Move bestMove = board.currentPlayer().getMoveStrategy().execute(board, 6);
        assertEquals(bestMove, Move.MoveFactory
                .createMove(board, BoardUtils.getCoordinateAtPosition("h5"), BoardUtils.getCoordinateAtPosition("g6")));
        final MoveTransition t1 = board.currentPlayer()
                .makeMove(bestMove);
        assertTrue(t1.getMoveStatus().isDone());
    }
}
