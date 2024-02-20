package com.chess.tests;

import com.chess.engine.classic.Alliance;
import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.board.BoardUtils;
import com.chess.engine.classic.board.Move;
import com.chess.engine.classic.pieces.Bishop;
import com.chess.engine.classic.pieces.King;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertThrows;

// Partition 3 - Test for out of bounds if chess piece attempts to use a bad coordinate

// Note for Professor/TAs: This is the syntax to create parameterized tests for JUnit4. There is
// unfortunately no way to use multiple parameterized tests in one file via JUnit4, unlike JUnit5. A parameterized
// test can only be created one class at a time.
@RunWith(Parameterized.class)
public class TestBishopPieceOOBMoves {
    private final String startPosition;
    private final String endPosition;
    private Board board;

    public TestBishopPieceOOBMoves(String startPosition, String endPosition) {
        this.startPosition = startPosition;
        this.endPosition = endPosition;
    }

    @Before
    public void setupBoard() {
        var boardBuilder = new Board.Builder();
        // Set up black chess pieces. King pieces on board are mandatory or else app crashes!
        var blackKingPiece = new King(Alliance.BLACK, 8, false, false);

        // Place piece(s) on the board
        boardBuilder.setPiece(blackKingPiece);

        // Set up white chess pieces. We will set up rook piece in middle of the board. (King is needed for game to not end)
        var whiteKingPiece = new King(Alliance.WHITE, 1, false, false);

        // The piece we are testing. position 36 = e4
        var bishopPiece = new Bishop(Alliance.WHITE, 36);

        // Place piece(s) on the board
        boardBuilder.setPiece(whiteKingPiece);
        boardBuilder.setPiece(bishopPiece);

        // Set the current player's turn
        boardBuilder.setMoveMaker(Alliance.WHITE);

        // Generate the board
        this.board = boardBuilder.build();
    }

    @Parameterized.Parameters(name="Out of Bound Move: \"{0}\" -> \"{1}\"")
    public static Collection testMoveParameters() {
        return Arrays.asList(new Object[][] {
            { "e4", "aa5" },
            { "e4", "i7" },
            { "e4", "e10" },
            { "e4", "e-1" }
        });
    }

    @Test
    public void testPieceMoves() {
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
