package com.chess.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.chess.engine.classic.Alliance;
import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.board.Board.Builder;
import com.chess.engine.classic.board.Move;
import com.chess.engine.classic.pieces.Bishop;
import com.chess.engine.classic.pieces.King;
import com.chess.engine.classic.pieces.Knight;
import com.chess.engine.classic.pieces.Pawn;
import com.chess.engine.classic.pieces.Queen;
import com.chess.engine.classic.pieces.Rook;
import com.chess.engine.classic.player.Player;
import com.chess.engine.classic.player.ai.AlphaBeta;
import com.chess.engine.classic.player.ai.MoveStrategy;

public class TestAlphaBeta {

    @Test
    public void testOpeningDepth1() {
        final Board board = Board.createStandardBoard();
        final Player currentPlayer = board.currentPlayer();
        final MoveStrategy alphaBeta = new AlphaBeta();
        currentPlayer.setMoveStrategy(alphaBeta);
        final Move bestMove = board.currentPlayer().getMoveStrategy().execute(board, 1);
        final long numBoardsEvaluated = alphaBeta.getNumBoardsEvaluated();
        assertEquals(numBoardsEvaluated, 20L);
        assertEquals(bestMove, Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("e2"), Board.getCoordinateAtPosition("e4")));
    }

    @Test
    public void testOpeningDepth2() {
        final Board board = Board.createStandardBoard();
        final Player currentPlayer = board.currentPlayer();
        final MoveStrategy alphaBeta = new AlphaBeta();
        currentPlayer.setMoveStrategy(alphaBeta);
        final Move bestMove = board.currentPlayer().getMoveStrategy().execute(board, 2);
        assertEquals(bestMove, Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("e2"), Board.getCoordinateAtPosition("e4")));
    }

    @Test
    public void testOpeningDepth3() {
        final Board board = Board.createStandardBoard();
        final Player currentPlayer = board.currentPlayer();
        final MoveStrategy alphaBeta = new AlphaBeta();
        currentPlayer.setMoveStrategy(alphaBeta);
        final Move bestMove = board.currentPlayer().getMoveStrategy().execute(board, 3);
        assertEquals(bestMove, Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("d2"), Board.getCoordinateAtPosition("d4")));
    }

    @Test
    public void testOpeningDepth4() {
        final Board board = Board.createStandardBoard();
        final Player currentPlayer = board.currentPlayer();
        final MoveStrategy alphaBeta = new AlphaBeta();
        currentPlayer.setMoveStrategy(alphaBeta);
        final Move bestMove = board.currentPlayer().getMoveStrategy().execute(board, 4);
        assertEquals(bestMove, Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("e2"), Board.getCoordinateAtPosition("e4")));
    }

    @Test
    public void testOpeningDepth4BlackMovesFirst() {

        final Builder builder = new Builder();

        // Black Layout
        builder.setPiece(new Rook(Alliance.BLACK, 0));
        builder.setPiece(new Knight(Alliance.BLACK, 1));
        builder.setPiece(new Bishop(Alliance.BLACK, 2));
        builder.setPiece(new Queen(Alliance.BLACK, 3));
        builder.setPiece(new King(Alliance.BLACK, 4));
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
        builder.setPiece(new King(Alliance.WHITE, 60));
        builder.setPiece(new Bishop(Alliance.WHITE, 61));
        builder.setPiece(new Knight(Alliance.WHITE, 62));
        builder.setPiece(new Rook(Alliance.WHITE, 63));
        // Set the current player
        builder.setMoveMaker(Alliance.BLACK);

        final Board board = builder.build();

        final Player currentPlayer = board.currentPlayer();
        final MoveStrategy alphaBeta = new AlphaBeta();
        currentPlayer.setMoveStrategy(alphaBeta);
        final Move bestMove = board.currentPlayer().getMoveStrategy().execute(board, 4);
        assertEquals(bestMove, Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("e7"), Board.getCoordinateAtPosition("e5")));
    }

    @Test
    public void advancedLevelProblem2NakamuraShirov() {

        final Builder builder = new Builder();

        // Black Layout
        builder.setPiece(new King(Alliance.BLACK, 5));
        builder.setPiece(new Pawn(Alliance.BLACK, 10));
        builder.setPiece(new Rook(Alliance.BLACK, 25));
        builder.setPiece(new Bishop(Alliance.BLACK, 29));
        // White Layout
        builder.setPiece(new Knight(Alliance.WHITE, 27));
        builder.setPiece(new Rook(Alliance.WHITE, 36));
        builder.setPiece(new Pawn(Alliance.WHITE, 39));
        builder.setPiece(new King(Alliance.WHITE, 42));
        builder.setPiece(new Pawn(Alliance.WHITE, 46));
        // Set the current player
        builder.setMoveMaker(Alliance.WHITE);

        final Board board = builder.build();

        final Player currentPlayer = board.currentPlayer();
        currentPlayer.setMoveStrategy(new AlphaBeta());
        final Move bestMove = board.currentPlayer().getMoveStrategy().execute(board, 6);
        assertEquals(bestMove, Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("d5"), Board.getCoordinateAtPosition("c7")));
    }

    @Test
    public void eloTest1() {
        final Builder builder = new Builder();
        // Black Layout
        builder.setPiece(new Rook(Alliance.BLACK, 0));
        builder.setPiece(new Bishop(Alliance.BLACK, 2));
        builder.setPiece(new King(Alliance.BLACK, 6));
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
        builder.setPiece(new King(Alliance.WHITE, 60));
        builder.setPiece(new Rook(Alliance.WHITE, 63));

        // Set the current player
        builder.setMoveMaker(Alliance.BLACK);
        final Board board = builder.build();
        final Player currentPlayer = board.currentPlayer();
        currentPlayer.setMoveStrategy(new AlphaBeta());
        final Move bestMove = board.currentPlayer().getMoveStrategy().execute(board, 6);
        assertEquals(bestMove, Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("c8"), Board.getCoordinateAtPosition("a6")));
    }

    @Test
    public void eloTest2() {
        final Builder builder = new Builder();
        // Black Layout
        builder.setPiece(new Knight(Alliance.BLACK, 2));
        builder.setPiece(new Queen(Alliance.BLACK, 3));
        builder.setPiece(new Knight(Alliance.BLACK, 5));
        builder.setPiece(new King(Alliance.BLACK, 6));
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
        builder.setPiece(new King(Alliance.WHITE, 62));

        // Set the current player
        builder.setMoveMaker(Alliance.WHITE);
        final Board board = builder.build();
        final Player currentPlayer = board.currentPlayer();
        currentPlayer.setMoveStrategy(new AlphaBeta());
        final Move bestMove = board.currentPlayer().getMoveStrategy().execute(board, 8);
        assertEquals(bestMove, Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("g5"), Board.getCoordinateAtPosition("h7")));
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
        builder.setPiece(new King(Alliance.BLACK, 25));
        builder.setPiece(new Knight(Alliance.BLACK, 33));
        // White Layout
        builder.setPiece(new Bishop(Alliance.WHITE, 19));
        builder.setPiece(new Pawn(Alliance.WHITE, 26));
        builder.setPiece(new King(Alliance.WHITE, 36));
        builder.setPiece(new Rook(Alliance.WHITE, 46));
        builder.setPiece(new Pawn(Alliance.WHITE, 49));
        builder.setPiece(new Pawn(Alliance.WHITE, 53));

        // Set the current player
        builder.setMoveMaker(Alliance.WHITE);
        final Board board = builder.build();
        final Player currentPlayer = board.currentPlayer();
        currentPlayer.setMoveStrategy(new AlphaBeta());
        final Move bestMove = board.currentPlayer().getMoveStrategy().execute(board, 8);
        assertEquals(bestMove, Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("g3"), Board.getCoordinateAtPosition("g6")));
    }

    @Test
    public void testCheckmateHorizon() {
        final Builder builder = new Builder();
        // Black Layout
        builder.setPiece(new Rook(Alliance.BLACK, 11));
        builder.setPiece(new Pawn(Alliance.BLACK, 16));
        builder.setPiece(new Bishop(Alliance.BLACK, 27));
        builder.setPiece(new King(Alliance.BLACK, 29));
        // White Layout
        builder.setPiece(new Rook(Alliance.WHITE, 17));
        builder.setPiece(new Rook(Alliance.WHITE, 26));
        builder.setPiece(new Pawn(Alliance.WHITE, 35));
        builder.setPiece(new Pawn(Alliance.WHITE, 45));
        builder.setPiece(new Bishop(Alliance.WHITE, 51));
        builder.setPiece(new Pawn(Alliance.WHITE, 54));
        builder.setPiece(new Pawn(Alliance.WHITE, 55));
        builder.setPiece(new King(Alliance.WHITE, 63));


        // Set the current player
        builder.setMoveMaker(Alliance.WHITE);
        final Board board = builder.build();
        final Player currentPlayer = board.currentPlayer();
        currentPlayer.setMoveStrategy(new AlphaBeta());
        final Move bestMove = board.currentPlayer().getMoveStrategy().execute(board, 8);
        assertEquals(bestMove, Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("g2"), Board.getCoordinateAtPosition("g4")));
    }

}
