package com.chess.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
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

    @Ignore
    @Test
    public void testOpeningDepth2() {
        final Board board = Board.createStandardBoard();
        final Player currentPlayer = board.currentPlayer();
        final MoveStrategy alphaBeta = new AlphaBeta();
        currentPlayer.setMoveStrategy(alphaBeta);
        final Move bestMove = board.currentPlayer().getMoveStrategy().execute(board, 2);
        assertEquals(bestMove, Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("d2"), Board.getCoordinateAtPosition("d4")));
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
        builder.setPiece(0, new Rook(Alliance.BLACK, 0));
        builder.setPiece(1, new Knight(Alliance.BLACK, 1));
        builder.setPiece(2, new Bishop(Alliance.BLACK, 2));
        builder.setPiece(3, new Queen(Alliance.BLACK, 3));
        builder.setPiece(4, new King(Alliance.BLACK, 4));
        builder.setPiece(5, new Bishop(Alliance.BLACK, 5));
        builder.setPiece(6, new Knight(Alliance.BLACK, 6));
        builder.setPiece(7, new Rook(Alliance.BLACK, 7));
        builder.setPiece(8, new Pawn(Alliance.BLACK, 8));
        builder.setPiece(9, new Pawn(Alliance.BLACK, 9));
        builder.setPiece(10, new Pawn(Alliance.BLACK, 10));
        builder.setPiece(11, new Pawn(Alliance.BLACK, 11));
        builder.setPiece(12, new Pawn(Alliance.BLACK, 12));
        builder.setPiece(13, new Pawn(Alliance.BLACK, 13));
        builder.setPiece(14, new Pawn(Alliance.BLACK, 14));
        builder.setPiece(15, new Pawn(Alliance.BLACK, 15));
        // White Layout
        builder.setPiece(48, new Pawn(Alliance.WHITE, 48));
        builder.setPiece(49, new Pawn(Alliance.WHITE, 49));
        builder.setPiece(50, new Pawn(Alliance.WHITE, 50));
        builder.setPiece(51, new Pawn(Alliance.WHITE, 51));
        builder.setPiece(52, new Pawn(Alliance.WHITE, 52));
        builder.setPiece(53, new Pawn(Alliance.WHITE, 53));
        builder.setPiece(54, new Pawn(Alliance.WHITE, 54));
        builder.setPiece(55, new Pawn(Alliance.WHITE, 55));
        builder.setPiece(56, new Rook(Alliance.WHITE, 56));
        builder.setPiece(57, new Knight(Alliance.WHITE, 57));
        builder.setPiece(58, new Bishop(Alliance.WHITE, 58));
        builder.setPiece(59, new Queen(Alliance.WHITE, 59));
        builder.setPiece(60, new King(Alliance.WHITE, 60));
        builder.setPiece(61, new Bishop(Alliance.WHITE, 61));
        builder.setPiece(62, new Knight(Alliance.WHITE, 62));
        builder.setPiece(63, new Rook(Alliance.WHITE, 63));

        //black to move
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
        builder.setPiece(5, new King(Alliance.BLACK, 5));
        builder.setPiece(10, new Pawn(Alliance.BLACK, 10));
        builder.setPiece(25, new Rook(Alliance.BLACK, 25));
        builder.setPiece(29, new Bishop(Alliance.BLACK, 29));
        // White Layout
        builder.setPiece(27, new Knight(Alliance.WHITE, 27));
        builder.setPiece(36, new Rook(Alliance.WHITE, 36));
        builder.setPiece(39, new Pawn(Alliance.WHITE, 39));
        builder.setPiece(42, new King(Alliance.WHITE, 42));
        builder.setPiece(46, new Pawn(Alliance.WHITE, 46));

        //white to move
        builder.setMoveMaker(Alliance.WHITE);

        final Board board = builder.build();

        final Player currentPlayer = board.currentPlayer();
        currentPlayer.setMoveStrategy(new AlphaBeta());
        final Move bestMove = board.currentPlayer().getMoveStrategy().execute(board, 6);
        assertEquals(bestMove, Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("d5"), Board.getCoordinateAtPosition("c7")));
    }
}
