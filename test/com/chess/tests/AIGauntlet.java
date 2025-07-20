package com.chess.tests;

import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.pieces.Piece;
import com.chess.engine.player.ai.MoveStrategy;
import com.chess.engine.player.ai.BlackWidowAI;
import com.chess.pgn.FenUtilities;
import org.junit.Test;

import java.io.*;
import java.util.*;

import static org.junit.Assert.assertEquals;


public class AIGauntlet {

    private static class TestCase {
        final String id;
        final String fen;
        final String expectedMove;

        TestCase(String id, String fen, String expectedMove) {
            this.id = id;
            this.fen = fen;
            this.expectedMove = expectedMove;
        }
    }

    private static Move parseUciMove(Board board, String uci) {
        // UCI like "f7g7" or "e7e8q"
        int from = BoardUtils.INSTANCE.getCoordinateAtPosition(uci.substring(0, 2));
        int to = BoardUtils.INSTANCE.getCoordinateAtPosition(uci.substring(2, 4));
        if(uci.length() > 4) {
            Piece.PieceType promotion = null;
            final String pt = uci.substring(4);
            switch (pt) {
                case "n":
                    promotion = Piece.PieceType.KNIGHT;
                    break;
                case "b":
                    promotion = Piece.PieceType.BISHOP;
                    break;
                case "r":
                    promotion = Piece.PieceType.ROOK;
                    break;
                case "q":
                    promotion = Piece.PieceType.QUEEN;
                    break;
                default:
                    break;
            }
            return Move.MoveFactory.createPromotionMove(board, from, to, promotion);
        }
        return Move.MoveFactory.createMove(board, from, to);
    }

    private static List<TestCase> readTestCases(String filename) throws IOException {
        final List<TestCase> cases = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            int testNumber = 1;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty() || line.startsWith("#")) {
                    continue;
                }
                final String[] parts = line.split(",");
                final String fen = parts[0].trim();
                final String move = parts[1].trim().replaceAll("\\s+", "");
                cases.add(new TestCase(Integer.toString(testNumber), fen, move));
                testNumber++;
            }
        }
        return cases;
    }

    @Test
    public void testAIGauntletFromFile() throws IOException {
        final String filename = "test_data.csv";
        final List<TestCase> cases = readTestCases(filename);
        for (final TestCase tc : cases) {
            final Board board = FenUtilities.createGameFromFEN(tc.fen);
            final MoveStrategy ai = new BlackWidowAI(1, false, 0, false); // You can tune depth
            final Move expectedMove = parseUciMove(board, tc.expectedMove);
            final Move aiMove = ai.execute(board);
            assertEquals(expectedMove, aiMove);
            if (!Objects.equals(aiMove, expectedMove)) {
                System.out.println("FAILED TEST: " + tc.id);
                System.out.println("FEN: " + tc.fen);
                System.out.println("Expected: " + expectedMove);
                System.out.println("AI chose: " + aiMove);
                System.out.println("Legal moves: " + board.currentPlayer().getLegalMoves());
            }
        }
    }

}
