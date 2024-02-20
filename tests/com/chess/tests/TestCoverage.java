package com.chess.tests;

import com.chess.engine.classic.Alliance;
import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.board.BoardUtils;
import com.chess.engine.classic.board.Move;
import com.chess.engine.classic.board.MoveUtils;
import com.chess.engine.classic.pieces.*;
import com.chess.engine.classic.player.ai.BoardEvaluator;
import com.chess.engine.classic.player.ai.KingSafetyAnalyzer;
import com.chess.engine.classic.player.ai.StandardBoardEvaluator;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class TestCoverage {
    @Test
    public void testEvaluationDetails() {
        final Board.Builder builder = new Board.Builder();
        // Black Layout
        builder.setPiece(new King(Alliance.BLACK, 4, false, false));
        builder.setPiece(new Pawn(Alliance.BLACK, 12));
        // White Layout
        builder.setPiece(new Pawn(Alliance.WHITE, 52));
        builder.setPiece(new King(Alliance.WHITE, 60, false, false));
        builder.setMoveMaker(Alliance.WHITE);
        // Set the current player
        final Board board = builder.build();

        BoardEvaluator evaluator = StandardBoardEvaluator.get();
        System.out.println(StandardBoardEvaluator.get().evaluationDetails(board,0));
        String result = ("White Mobility : " + 50 + "\n" +
                "White kingThreats : " + 0 + "\n" +
                "White attacks : " + 0 + "\n" +
                "White castle : " + 0 + "\n" +
                "White pieceEval : " + 100080 + "\n" +
                "White pawnStructure : " + -10 + "\n" +
                "---------------------\n" +
                "Black Mobility : " + 50 + "\n" +
                "Black kingThreats : " + 0 + "\n" +
                "Black attacks : " + 0 + "\n" +
                "Black castle : " + 0 + "\n" +
                "Black pieceEval : " + 100080 + "\n" +
                "Black pawnStructure : " + -10 + "\n\n" +
                "Final Score = " + 0);
        System.out.println(result);
        assertEquals(StandardBoardEvaluator.get().evaluationDetails(board,0), result);
    }

    // Testing that the Move hashCodes are the same if two different piece move instances
    // are created but are moving to the same position on the chess board
    @Test
    public void testMoveHashCodes () {
        final var boardUtils = BoardUtils.INSTANCE;
        var boardBuilder = new Board.Builder();

        var blackKingPiece = new King(Alliance.BLACK, 8, false, false);
        boardBuilder.setPiece(blackKingPiece);

        var whiteKingPiece = new King(Alliance.WHITE, 1, false, false);
        var pawnPiece = new Pawn(Alliance.WHITE, boardUtils.getCoordinateAtPosition("a2"));

        boardBuilder.setPiece(whiteKingPiece);
        boardBuilder.setPiece(pawnPiece);

        // Set the current player's turn
        boardBuilder.setMoveMaker(Alliance.WHITE);

        var board = boardBuilder.build();
        var movePosition = boardUtils.getCoordinateAtPosition("a3");

        // Perform the action
        var pawnMove1 = new Move.PawnMove(board, pawnPiece, movePosition);
        var pawnMove2 = new Move.PawnMove(board, pawnPiece, movePosition);

        // Assert
        assertEquals(pawnMove1.hashCode(), pawnMove2.hashCode());
    }

    // Testing the enemy King's piece safety. We will set up the board one step away from
    // a check and then assert that the distance from attack is calculated correctly
    // Expected:
    // tropism distance = 0 --> enemy king piece is in danger!
    // tropism distance = 1 --> enemy king piece is not in danger!
    @Test
    public void testEnemyKingPieceSafety () {
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

        // perform the act of running the king analyzer
        var safetyAnalyzer = KingSafetyAnalyzer.get();
        var result = safetyAnalyzer.calculateKingTropism(moveTransition.getToBoard().blackPlayer());

        // assert
        assertEquals(0, result.getDistance());
    }

    @Test
    public void testNullMovesToString() {
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
        var finalPosition = boardUtils.getCoordinateAtPosition("g3");

        var moveTransition = board.currentPlayer()
                .makeMove(Move.MoveFactory.createMove(board, initialPosition, finalPosition));

        assertEquals(moveTransition.getToBoard().getTransitionMove().toString(), "Null Move");
    }

    @Test
    public void testNullMovesGetDestination() {
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
        var finalPosition = boardUtils.getCoordinateAtPosition("g3");

        var moveTransition = board.currentPlayer()
                .makeMove(Move.MoveFactory.createMove(board, initialPosition, finalPosition));

        assertEquals(moveTransition.getToBoard().getTransitionMove().getDestinationCoordinate(), -1);
    }

    @Test
    public void testNullMovesExecute() {
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
        var finalPosition = boardUtils.getCoordinateAtPosition("g3");

        var moveTransition = board.currentPlayer()
                .makeMove(Move.MoveFactory.createMove(board, initialPosition, finalPosition));

        assertThrows(RuntimeException.class, () -> {moveTransition.getToBoard().getTransitionMove().execute();});
    }
}
