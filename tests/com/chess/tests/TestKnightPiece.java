package com.chess.tests;
import com.chess.engine.classic.Alliance;
import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.board.BoardUtils;
import com.chess.engine.classic.board.Move;
import com.chess.engine.classic.pieces.King;
import com.chess.engine.classic.pieces.Knight;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import java.util.Collection;

import static org.junit.Assert.*;

public class TestKnightPiece {
    private Board board;

    @BeforeEach
    public void setupBoard() {
        var boardBuilder = new Board.Builder();
        // Set up black chess pieces. King pieces on board are mandatory or else app crashes!
        var blackKingPiece = new King(Alliance.BLACK, 8, false, false);

        // Place piece(s) on the board
        boardBuilder.setPiece(blackKingPiece);

        // Set up white chess pieces. We will set up knight piece in middle of the board. (King is needed for game to not end)
        var whiteKingPiece = new King(Alliance.WHITE, 1, false, false);

        // The piece we are testing. position 36 = e4
        var knightPiece = new Knight(Alliance.WHITE, 36);

        // Place piece(s) on the board
        boardBuilder.setPiece(whiteKingPiece);
        boardBuilder.setPiece(knightPiece);

        // Set the current player's turn
        boardBuilder.setMoveMaker(Alliance.WHITE);

        // Generate the board
        this.board = boardBuilder.build();
    }

    // Partition 1 - Test the valid test cases on the board. Knights can only move in L-shapes
    @ParameterizedTest()
    @CsvSource({
            "e4,d2",
            "e4,d6",
            "e4,f6",
            "e4,f2",
            "e4,c3",
            "e4,g5",
            "e4,c5",
            "e4,g3",
    })
    public void testValidMovesOnEmptyBoard(String startPosition, String endPosition) {
        final Collection<Move> whitePlayerLegalMoves = this.board.whitePlayer().getLegalMoves();

        var boardUtils = BoardUtils.INSTANCE;
        var startCoordinate = boardUtils.getCoordinateAtPosition(startPosition);
        var endCoordinate = boardUtils.getCoordinateAtPosition(endPosition);

        assertTrue(whitePlayerLegalMoves.contains(Move.MoveFactory.createMove(board, startCoordinate, endCoordinate)));
    }

    // Partition 2 - Test invalid chess piece movements on the board
    @ParameterizedTest()
    @CsvSource({
            "e4,a5",
            "e4,f7",
            "e4,h5",
            "e4,f3",
            "e4,h1"
    })
    public void testInvalidChessPieceMoves(String startPosition, String endPosition) {
        final Collection<Move> whitePlayerLegalMoves = this.board.whitePlayer().getLegalMoves();

        var boardUtils = BoardUtils.INSTANCE;
        var startCoordinate = boardUtils.getCoordinateAtPosition(startPosition);
        var endCoordinate = boardUtils.getCoordinateAtPosition(endPosition);

        assertFalse(whitePlayerLegalMoves.contains(Move.MoveFactory.createMove(board, startCoordinate, endCoordinate)));
    }

    // Partition 3 - Test for out of bounds if chess piece attempts to use a bad coordinate
    // to move on the board
    @ParameterizedTest()
    @CsvSource({
            "e4,aa5",
            "e4,i7",
            "e4,e10",
            "e4,e-1"
    })
    public void testInvalidChessPieceMovesOutOfBounds(String startPosition, String endPosition) {
        final Collection<Move> whitePlayerLegalMoves = this.board.whitePlayer().getLegalMoves();

        assertThrows(NullPointerException.class, () -> {
            var boardUtils = BoardUtils.INSTANCE;
            var startCoordinate = boardUtils.getCoordinateAtPosition(startPosition);
            var endCoordinate = boardUtils.getCoordinateAtPosition(endPosition);
            var moveAttempt = Move.MoveFactory.createMove(board, startCoordinate, endCoordinate);
            whitePlayerLegalMoves.contains(moveAttempt);
        });
    }
}
