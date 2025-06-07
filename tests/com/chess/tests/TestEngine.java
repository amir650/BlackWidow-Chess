package com.chess.tests;

import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.board.Move;
import com.chess.engine.classic.pieces.Piece;
import com.chess.engine.classic.player.ai.MiniMax;
import com.chess.engine.classic.player.ai.MoveStrategy;
import com.chess.pgn.FenUtilities;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by amir.afghani on 6/4/17.
 * Tests from: https://chessprogramming.wikispaces.com/Perft+Results
 */
public class TestEngine {

    @Test
    public void kiwiPeteDepth1() {
        final Board board = FenUtilities.createGameFromFEN("r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq - 0 1");
        final MoveStrategy minMax = new MiniMax(1);
        minMax.execute(board);
        assertEquals(minMax.getNumBoardsEvaluated(), 48L);
    }

    @Test
    public void kiwiPeteDepth2() {
        final Board board = FenUtilities.createGameFromFEN("r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq - 0 1");
        final MoveStrategy minMax = new MiniMax(2);
        minMax.execute(board);
        assertEquals(minMax.getNumBoardsEvaluated(), 2039L);
    }

    @Test
    public void kiwiPeteDepth3() {
        final Board board = FenUtilities.createGameFromFEN("r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq - 0 1");
        final MoveStrategy minMax = new MiniMax(3);
        minMax.execute(board);
        assertEquals(minMax.getNumBoardsEvaluated(), 97862L);
    }

    @Test
    public void kiwiPeteDepth4() {
        final Board board = FenUtilities.createGameFromFEN("r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq - 0 1");
        final MoveStrategy minMax = new MiniMax(4);
        minMax.execute(board);
        assertEquals(minMax.getNumBoardsEvaluated(), 4085603L);
    }

    @Test
    public void kiwiPeteDepth5() {
        final Board board = FenUtilities.createGameFromFEN("r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq - 0 1");
        final MoveStrategy minMax = new MiniMax(5);
        minMax.execute(board);
        assertEquals(minMax.getNumBoardsEvaluated(), 193690690L);
    }


    @Test
    public void testPosition3Depth1() {
        final Board board = FenUtilities.createGameFromFEN("8/2p5/3p4/KP5r/1R3p1k/8/4P1P1/8 w - -");
        final MoveStrategy minMax = new MiniMax(1);
        minMax.execute(board);
        assertEquals(minMax.getNumBoardsEvaluated(), 14L);
    }

    @Test
    public void testPosition3Depth2() {
        final Board board = FenUtilities.createGameFromFEN("8/2p5/3p4/KP5r/1R3p1k/8/4P1P1/8 w - -");
        final MoveStrategy minMax = new MiniMax(2);
        minMax.execute(board);
        assertEquals(minMax.getNumBoardsEvaluated(), 191L);
    }

    @Test
    public void testPosition3Depth3() {
        final Board board = FenUtilities.createGameFromFEN("8/2p5/3p4/KP5r/1R3p1k/8/4P1P1/8 w - -");
        final MoveStrategy minMax = new MiniMax(3);
        minMax.execute(board);
        assertEquals(minMax.getNumBoardsEvaluated(), 2812L);
    }

    @Test
    public void testPosition3Depth4() {
        final Board board = FenUtilities.createGameFromFEN("8/2p5/3p4/KP5r/1R3p1k/8/4P1P1/8 w - -");
        final MoveStrategy minMax = new MiniMax(4);
        minMax.execute(board);
        assertEquals(minMax.getNumBoardsEvaluated(), 43238L);
    }

    @Test
    public void testPosition3Depth5() {
        final Board board = FenUtilities.createGameFromFEN("8/2p5/3p4/KP5r/1R3p1k/8/4P1P1/8 w - -");
        final MoveStrategy minMax = new MiniMax(5);
        minMax.execute(board);
        assertEquals(minMax.getNumBoardsEvaluated(), 674624L);
    }

    @Test
    public void testPosition4Depth1() {
        final Board board = FenUtilities.createGameFromFEN("r3k2r/Pppp1ppp/1b3nbN/nP6/BBP1P3/q4N2/Pp1P2PP/R2Q1RK1 w kq - 0 1");
        final MoveStrategy minMax = new MiniMax(1);
        minMax.execute(board);
        assertEquals(minMax.getNumBoardsEvaluated(), 6L);
    }

    @Test
    public void testPosition4Depth2() {
        final Board board = FenUtilities.createGameFromFEN("r3k2r/Pppp1ppp/1b3nbN/nP6/BBP1P3/q4N2/Pp1P2PP/R2Q1RK1 w kq - 0 1");
        final MoveStrategy minMax = new MiniMax(2);
        minMax.execute(board);
        assertEquals(minMax.getNumBoardsEvaluated(), 264L);
    }

    @Test
    public void testPosition4Depth3() {
        final Board board = FenUtilities.createGameFromFEN("r3k2r/Pppp1ppp/1b3nbN/nP6/BBP1P3/q4N2/Pp1P2PP/R2Q1RK1 w kq - 0 1");
        final MoveStrategy minMax = new MiniMax(3);
        minMax.execute(board);
        assertEquals(minMax.getNumBoardsEvaluated(), 9467L);
    }

    @Test
    public void testPosition4Depth4() {
        final Board board = FenUtilities.createGameFromFEN("r3k2r/Pppp1ppp/1b3nbN/nP6/BBP1P3/q4N2/Pp1P2PP/R2Q1RK1 w kq - 0 1");
        final MoveStrategy minMax = new MiniMax(4);
        minMax.execute(board);
        assertEquals(minMax.getNumBoardsEvaluated(), 422333L);
    }

    @Test
    public void testPosition4Depth5() {
        final Board board = FenUtilities.createGameFromFEN("r3k2r/Pppp1ppp/1b3nbN/nP6/BBP1P3/q4N2/Pp1P2PP/R2Q1RK1 w kq - 0 1");
        final MoveStrategy minMax = new MiniMax(5);
        minMax.execute(board);
        assertEquals(minMax.getNumBoardsEvaluated(), 15833292L);
    }

    @Test
    public void testPosition5Depth1() {
        final Board board = FenUtilities.createGameFromFEN("rnbq1k1r/pp1Pbppp/2p5/8/2B5/8/PPP1NnPP/RNBQK2R w KQ - 1 8");
        final MoveStrategy minMax = new MiniMax(1);
        minMax.execute(board);
        assertEquals(minMax.getNumBoardsEvaluated(), 44L);
    }

    @Test
    public void testPosition5Depth2() {
        final Board board = FenUtilities.createGameFromFEN("rnbq1k1r/pp1Pbppp/2p5/8/2B5/8/PPP1NnPP/RNBQK2R w KQ - 1 8");
        final MoveStrategy minMax = new MiniMax(2);
        minMax.execute(board);
        assertEquals(minMax.getNumBoardsEvaluated(), 1486L);
    }

    @Test
    public void testPosition5Depth3() {
        final Board board = FenUtilities.createGameFromFEN("rnbq1k1r/pp1Pbppp/2p5/8/2B5/8/PPP1NnPP/RNBQK2R w KQ - 1 8");
        final MoveStrategy minMax = new MiniMax(3);
        minMax.execute(board);
        assertEquals(minMax.getNumBoardsEvaluated(), 62379L);
    }

    @Test
    public void testPromotionAndCastleLegality() {
        final Board board = FenUtilities.createGameFromFEN("4k3/6P1/8/8/8/8/8/4K2R w K - 0 1");

        // Step 1: Promote pawn g7-g8=Q
        Move promotion = null;
        for (Move move : board.whitePlayer().getLegalMoves()) {
            if (move.getDestinationCoordinate() == 6 && move instanceof Move.PawnPromotion) { // g8 is square 6
                promotion = move;
                break;
            }
        }
        assertNotNull("No promotion move found", promotion);
        Board afterPromotion = promotion.execute();

        // Step 2: Confirm castling still possible
        boolean canCastle = afterPromotion.whitePlayer().getLegalMoves().stream().anyMatch(Move::isCastlingMove);
        System.out.println("Castling after promotion? " + canCastle);
        assertTrue("White should be able to castle after promotion", canCastle);

        // Step 3: Try to make the castle move
        Move castleMove = null;
        for (Move move : afterPromotion.whitePlayer().getLegalMoves()) {
            if (move.isCastlingMove()) {
                castleMove = move;
                break;
            }
        }
        assertNotNull("No castling move available after promotion!", castleMove);
        Board afterCastle = castleMove.execute();

        // Step 4: King should be on g1 (square 62), rook on f1 (square 61)
        assertNotNull(afterCastle.getPiece(62));
        assertEquals(Piece.PieceType.KING, afterCastle.getPiece(62).getPieceType());
        assertNotNull(afterCastle.getPiece(61));
        assertEquals(Piece.PieceType.ROOK, afterCastle.getPiece(61).getPieceType());
    }




    @Test
    public void testSimpleCastleLeavesKing() {
        final Board board = FenUtilities.createGameFromFEN("r3k2r/8/8/8/8/8/8/R3K2R w KQkq - 0 1");

        boolean foundKingSideCastle = false;
        boolean foundQueenSideCastle = false;

        for (Move move : board.currentPlayer().getLegalMoves()) {
            if (move.isCastlingMove()) {
                Board after = move.execute();
                boolean kingPresent = false;
                int[] active = after.whitePlayer().getAlliance().isWhite()
                        ? after.getWhitePieces()
                        : after.getBlackPieces();
                for (int i : active) {
                    Piece p = after.getPiece(i);
                    if (p.getPieceType() == Piece.PieceType.KING) {
                        kingPresent = true;
                        break;
                    }
                }
                assertTrue("White king should remain after castling", kingPresent);

                if (move instanceof Move.KingSideCastleMove) {
                    foundKingSideCastle = true;
                } else if (move instanceof Move.QueenSideCastleMove) {
                    foundQueenSideCastle = true;
                }
            }
        }

        assertTrue("Should find king-side castle move", foundKingSideCastle);
        assertTrue("Should find queen-side castle move", foundQueenSideCastle);
    }


    @Test
    public void testPosition5Depth4() {
        final Board board = FenUtilities.createGameFromFEN("rnbq1k1r/pp1Pbppp/2p5/8/2B5/8/PPP1NnPP/RNBQK2R w KQ - 1 8");
        final MoveStrategy minMax = new MiniMax(4);
        minMax.execute(board);
        assertEquals(minMax.getNumBoardsEvaluated(), 2103487L);
    }

    @Test
    public void testPosition5Depth5() {
        final Board board = FenUtilities.createGameFromFEN("rnbq1k1r/pp1Pbppp/2p5/8/2B5/8/PPP1NnPP/RNBQK2R w KQ - 1 8");
        final MoveStrategy minMax = new MiniMax(5);
        minMax.execute(board);
        assertEquals(minMax.getNumBoardsEvaluated(), 89941194L);
    }

    @Test
    public void testPosition6Depth4() {
        final Board board = FenUtilities.createGameFromFEN("r4rk1/1pp1qppp/p1np1n2/2b1p1B1/2B1P1b1/P1NP1N2/1PP1QPPP/R4RK1 w - - 0 10\n");
        final MoveStrategy minMax = new MiniMax(4);
        minMax.execute(board);
        assertEquals(minMax.getNumBoardsEvaluated(), 3894594L);
    }


}
