package com.chess.tests;
import com.chess.engine.classic.Alliance;
import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.board.BoardUtils;
import com.chess.engine.classic.board.Move;
import com.chess.engine.classic.pieces.King;
import com.chess.engine.classic.pieces.Pawn;
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
    public void testCheckmateFiniteState() {
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
     * Finite State Scenario 2: Testing Draw state - this case it is stalemate - when a draw occurs on white alliance player's turn
     * */
    @Test
    public void testDrawFiniteState() {
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
     * Finite State Scenario 3: Testing Check state when white alliance player attempts to check black alliance's king
     * */
    @Test
    public void testCheckFiniteState() {
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
}
