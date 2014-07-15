package com.chess.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.chess.engine.classic.Alliance;
import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.board.BoardConfigurator;
import com.chess.engine.classic.board.Move;
import com.chess.engine.classic.board.StandardBoardConfigurator;
import com.chess.engine.classic.pieces.Bishop;
import com.chess.engine.classic.pieces.King;
import com.chess.engine.classic.pieces.Knight;
import com.chess.engine.classic.pieces.Pawn;
import com.chess.engine.classic.pieces.Rook;
import com.chess.engine.classic.player.Player;
import com.chess.engine.classic.player.ai.AlphaBeta;
import com.chess.engine.classic.player.ai.MoveStrategy;

public class TestAlphaBeta {

    @Test
    public void testOpeningDepth1() {
        final Board board = new Board(new StandardBoardConfigurator());
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
        final Board board = new Board(new StandardBoardConfigurator());
        final Player currentPlayer = board.currentPlayer();
        final MoveStrategy alphaBeta = new AlphaBeta();
        currentPlayer.setMoveStrategy(alphaBeta);
        final Move bestMove = board.currentPlayer().getMoveStrategy().execute(board, 2);
        assertEquals(bestMove, Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("d2"), Board.getCoordinateAtPosition("d4")));
    }

    @Test
    public void testOpeningDepth3() {
        final Board board = new Board(new StandardBoardConfigurator());
        final Player currentPlayer = board.currentPlayer();
        final MoveStrategy alphaBeta = new AlphaBeta();
        currentPlayer.setMoveStrategy(alphaBeta);
        final Move bestMove = board.currentPlayer().getMoveStrategy().execute(board, 3);
        assertEquals(bestMove, Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("d2"), Board.getCoordinateAtPosition("d4")));
    }

    @Test
    public void testOpeningDepth4() {
        final Board board = new Board(new StandardBoardConfigurator());
        final Player currentPlayer = board.currentPlayer();
        final MoveStrategy alphaBeta = new AlphaBeta();
        currentPlayer.setMoveStrategy(alphaBeta);
        final Move bestMove = board.currentPlayer().getMoveStrategy().execute(board, 4);
        assertEquals(bestMove, Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("e2"), Board.getCoordinateAtPosition("e4")));
    }

    @Test
    public void testOpeningDepth4BlackMovesFirst() {
        final Board board = new Board(new StandardBoardConfigurator());
        board.setCurrentPlayer(board.blackPlayer());
        final Player currentPlayer = board.currentPlayer();
        final MoveStrategy alphaBeta = new AlphaBeta();
        currentPlayer.setMoveStrategy(alphaBeta);
        final Move bestMove = board.currentPlayer().getMoveStrategy().execute(board, 4);
        assertEquals(bestMove, Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("e7"), Board.getCoordinateAtPosition("e5")));
    }

    @Test
    public void advancedLevelProblem2NakamuraShirov() {
        final Board board = new Board(new BoardConfigurator() {
            @Override
            public void setBoardPieces(final Board board) {
                board.clearGameBoard();
                // Black Layout
                board.setPiece(5, new King(Alliance.BLACK));
                board.setPiece(10, new Pawn(Alliance.BLACK));
                board.setPiece(25, new Rook(Alliance.BLACK));
                board.setPiece(29, new Bishop(Alliance.BLACK));
                // White Layout
                board.setPiece(27, new Knight(Alliance.WHITE));
                board.setPiece(36, new Rook(Alliance.WHITE));
                board.setPiece(39, new Pawn(Alliance.WHITE));
                board.setPiece(42, new King(Alliance.WHITE));
                board.setPiece(46, new Pawn(Alliance.WHITE));
                board.setCurrentPlayer(board.whitePlayer());
            }

            @Override
            public void setCurrentPlayer(final Board board) {
                board.setCurrentPlayer(board.whitePlayer());
            }
        });
        final Player currentPlayer = board.currentPlayer();
        currentPlayer.setMoveStrategy(new AlphaBeta());
        final Move bestMove = board.currentPlayer().getMoveStrategy().execute(board, 6);
        assertEquals(bestMove, Move.MoveFactory
                .createMove(board, Board.getCoordinateAtPosition("d5"), Board.getCoordinateAtPosition("c7")));
    }
}
