package com.chess.tests;
import com.chess.engine.classic.Alliance;
import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.board.BoardUtils;
import com.chess.engine.classic.board.Move;
import com.chess.engine.classic.pieces.King;
import com.chess.engine.classic.pieces.Pawn;
import com.chess.engine.classic.pieces.Queen;
import com.chess.engine.classic.pieces.Rook;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestGameFiniteStates {
    /**
     * Finite State Scenario 1: Testing Checkmate state when White Alliance player checkmates Black Alliance player
     *
     * Using Anderssen’s Mate Checkmate style: https://chessfox.com/checkmate-patterns/
     * */
    @Test
    public void testWhiteCheckmateFiniteState() {
        // Setup board such that there is just one move away from a Checkmate state
        var boardBuilder = new Board.Builder();
        var boardUtils = BoardUtils.INSTANCE;

        // Set up Black Alliance pieces
        var blackKingPosition = boardUtils.getCoordinateAtPosition("g8");
        var blackKingPiece = new King(Alliance.BLACK, blackKingPosition, false, false);
        boardBuilder.setPiece(blackKingPiece);

        // Set up White Alliance pieces
        var whiteKingPosition = boardUtils.getCoordinateAtPosition("f6");
        var whiteKingPiece = new King(Alliance.WHITE, whiteKingPosition, false, false);
        boardBuilder.setPiece(whiteKingPiece);

        var whitePawnPosition = boardUtils.getCoordinateAtPosition("g7");
        var whitePawnPiece = new Pawn(Alliance.WHITE, whitePawnPosition);
        boardBuilder.setPiece(whitePawnPiece);

        var whiteRookPosition = boardUtils.getCoordinateAtPosition("h2");
        var whiteRookPiece = new Rook(Alliance.WHITE, whiteRookPosition);
        boardBuilder.setPiece(whiteRookPiece);

        // Set the current player's turn
        boardBuilder.setMoveMaker(Alliance.WHITE);

        final Board board = boardBuilder.build();

        var initialPosition = boardUtils.getCoordinateAtPosition("h2");
        var finalPosition = boardUtils.getCoordinateAtPosition("h8");

        var moveTransition = board.currentPlayer()
                .makeMove(Move.MoveFactory.createMove(board, initialPosition, finalPosition));

        assertTrue(moveTransition.getToBoard().currentPlayer().isInCheckMate());
    }
    /**
     * Finite State Scenario 1-2: Testing Checkmate state when Black Alliance player checkmates White Alliance player
     * Back Rank Mate
     * */
    @Test
    public void testBlackCheckmateFiniteState() {
        // Setup board such that there is just one move away from a Checkmate state
        var boardBuilder = new Board.Builder();
        var boardUtils = BoardUtils.INSTANCE;

        // Set up Black Alliance pieces
        var blackKingPosition = boardUtils.getCoordinateAtPosition("g8");
        var blackKingPiece = new King(Alliance.BLACK, blackKingPosition, false, false);
        boardBuilder.setPiece(blackKingPiece);

        var blackPawnPiece1 = new Pawn(Alliance.BLACK, boardUtils.getCoordinateAtPosition("g7"));
        boardBuilder.setPiece(blackPawnPiece1);

        var blackPawnPiece2 = new Pawn(Alliance.BLACK, boardUtils.getCoordinateAtPosition("h7"));
        boardBuilder.setPiece(blackPawnPiece2);

        var blackPawnPiece3= new Pawn(Alliance.BLACK, boardUtils.getCoordinateAtPosition("h7"));
        boardBuilder.setPiece(blackPawnPiece3);

        var blackRookPiece = new Rook(Alliance.BLACK, boardUtils.getCoordinateAtPosition("d8"));
        boardBuilder.setPiece(blackRookPiece);
        // Set up White Alliance pieces
        var whiteKingPosition = boardUtils.getCoordinateAtPosition("g1");
        var whiteKingPiece = new King(Alliance.WHITE, whiteKingPosition, false, false);
        boardBuilder.setPiece(whiteKingPiece);

        var whitePawnPiece1 = new Pawn(Alliance.WHITE, boardUtils.getCoordinateAtPosition("f2"));
        boardBuilder.setPiece(whitePawnPiece1);

        var whitePawnPiece2 = new Pawn(Alliance.WHITE, boardUtils.getCoordinateAtPosition("g2"));
        boardBuilder.setPiece(whitePawnPiece2);

        var whitePawnPiece3 = new Pawn(Alliance.WHITE, boardUtils.getCoordinateAtPosition("h2"));
        boardBuilder.setPiece(whitePawnPiece3);

        // Set the current player's turn
        boardBuilder.setMoveMaker(Alliance.BLACK);

        final Board board = boardBuilder.build();

        var initialPosition = boardUtils.getCoordinateAtPosition("d8");
        var finalPosition = boardUtils.getCoordinateAtPosition("d1");

        var moveTransition = board.currentPlayer()
                .makeMove(Move.MoveFactory.createMove(board, initialPosition, finalPosition));

        assertTrue(moveTransition.getToBoard().currentPlayer().isInCheckMate());
    }

    /**
     * Finite State Scenario 2: Testing Draw state - this case it is stalemate - when a draw occurs on white alliance player's turn
     * */
    @Test
    public void testWhiteDrawFiniteState() {
        // Setup board such that there is just one move away from a Checkmate state
        var boardBuilder = new Board.Builder();
        var boardUtils = BoardUtils.INSTANCE;

        // Set up Black Alliance pieces
        var blackKingPosition = boardUtils.getCoordinateAtPosition("c8");
        var blackKingPiece = new King(Alliance.BLACK, blackKingPosition, false, false);
        boardBuilder.setPiece(blackKingPiece);

        // Set up White Alliance pieces
        var whiteKingPosition = boardUtils.getCoordinateAtPosition("c5");
        var whiteKingPiece = new King(Alliance.WHITE, whiteKingPosition, false, false);
        boardBuilder.setPiece(whiteKingPiece);

        var whitePawnPosition = boardUtils.getCoordinateAtPosition("c7");
        var whitePawnPiece = new Pawn(Alliance.WHITE, whitePawnPosition);
        boardBuilder.setPiece(whitePawnPiece);

        // Set the current player's turn
        boardBuilder.setMoveMaker(Alliance.WHITE);

        final Board board = boardBuilder.build();

        var initialPosition = boardUtils.getCoordinateAtPosition("c5");
        var finalPosition = boardUtils.getCoordinateAtPosition("c6");

        var moveTransition = board.currentPlayer()
            .makeMove(Move.MoveFactory.createMove(board, initialPosition, finalPosition));

        assertTrue(moveTransition.getToBoard().currentPlayer().isInStaleMate());
    }
    /**
     * Finite State Scenario 2-2: Testing Draw state - this case it is stalemate - when a draw occurs on Black alliance player's turn
     * */
    @Test
    public void testBlackDrawFiniteState() {
        // Setup board such that there is just one move away from a Checkmate state
        var boardBuilder = new Board.Builder();
        var boardUtils = BoardUtils.INSTANCE;

        // Set up Black Alliance pieces
        var blackKingPosition = boardUtils.getCoordinateAtPosition("a8");
        var blackKingPiece = new King(Alliance.BLACK, blackKingPosition, false, false);
        boardBuilder.setPiece(blackKingPiece);

        var blackQueenPiece = new Queen(Alliance.BLACK, boardUtils.getCoordinateAtPosition("e2"));
        boardBuilder.setPiece(blackQueenPiece);

        // Set up White Alliance pieces
        var whiteKingPosition = boardUtils.getCoordinateAtPosition("h1");
        var whiteKingPiece = new King(Alliance.WHITE, whiteKingPosition, false, false);
        boardBuilder.setPiece(whiteKingPiece);

        // Set the current player's turn
        boardBuilder.setMoveMaker(Alliance.BLACK);

        final Board board = boardBuilder.build();

        var initialPosition = boardUtils.getCoordinateAtPosition("e2");
        var finalPosition = boardUtils.getCoordinateAtPosition("f2");

        var moveTransition = board.currentPlayer()
                .makeMove(Move.MoveFactory.createMove(board, initialPosition, finalPosition));

        assertTrue(moveTransition.getToBoard().currentPlayer().isInStaleMate());
    }

    /**
     * Finite State Scenario 3: Testing Check state when white alliance player attempts to check black alliance's king
     * */
    @Test
    public void testWhiteCheckFiniteState() {
        // Setup board such that there is just one move away from a Checkmate state
        var boardBuilder = new Board.Builder();
        var boardUtils = BoardUtils.INSTANCE;

        // Set up Black Alliance pieces
        var blackKingPosition = boardUtils.getCoordinateAtPosition("g8");
        var blackKingPiece = new King(Alliance.BLACK, blackKingPosition, false, false);
        boardBuilder.setPiece(blackKingPiece);

        // Set up White Alliance pieces
        var whiteKingPosition = boardUtils.getCoordinateAtPosition("f6");
        var whiteKingPiece = new King(Alliance.WHITE, whiteKingPosition, false, false);
        boardBuilder.setPiece(whiteKingPiece);

        var whiteRookPosition = boardUtils.getCoordinateAtPosition("h2");
        var whiteRookPiece = new Rook(Alliance.WHITE, whiteRookPosition);
        boardBuilder.setPiece(whiteRookPiece);

        // Set the current player's turn
        boardBuilder.setMoveMaker(Alliance.WHITE);

        final Board board = boardBuilder.build();

        var initialPosition = boardUtils.getCoordinateAtPosition("h2");
        var finalPosition = boardUtils.getCoordinateAtPosition("h8");

        var moveTransition = board.currentPlayer()
                .makeMove(Move.MoveFactory.createMove(board, initialPosition, finalPosition));

        assertTrue(moveTransition.getToBoard().currentPlayer().isInCheck());
    }
    /**
     * Finite State Scenario 3-2: Testing Check state when black alliance player attempts to check white alliance's king
     * */
    @Test
    public void testBlackCheckFiniteState() {
        // Setup board such that there is just one move away from a Checkmate state
        var boardBuilder = new Board.Builder();
        var boardUtils = BoardUtils.INSTANCE;

        // Set up Black Alliance pieces
        var blackKingPosition = boardUtils.getCoordinateAtPosition("a8");
        var blackKingPiece = new King(Alliance.BLACK, blackKingPosition, false, false);
        boardBuilder.setPiece(blackKingPiece);

        var blackQueenPiece = new Queen(Alliance.BLACK, boardUtils.getCoordinateAtPosition("e2"));
        boardBuilder.setPiece(blackQueenPiece);

        // Set up White Alliance pieces
        var whiteKingPosition = boardUtils.getCoordinateAtPosition("d7");
        var whiteKingPiece = new King(Alliance.WHITE, whiteKingPosition, false, false);
        boardBuilder.setPiece(whiteKingPiece);

        // Set the current player's turn
        boardBuilder.setMoveMaker(Alliance.BLACK);

        final Board board = boardBuilder.build();

        var initialPosition = boardUtils.getCoordinateAtPosition("e2");
        var finalPosition = boardUtils.getCoordinateAtPosition("d2");

        var moveTransition = board.currentPlayer()
                .makeMove(Move.MoveFactory.createMove(board, initialPosition, finalPosition));

        assertTrue(moveTransition.getToBoard().currentPlayer().isInCheck());
    }
}
