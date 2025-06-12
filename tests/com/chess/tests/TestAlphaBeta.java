package com.chess.tests;

import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.board.BoardUtils;
import com.chess.engine.classic.board.Move;
import com.chess.engine.classic.board.MoveTransition;
import com.chess.engine.classic.pieces.*;
import com.chess.engine.classic.player.ai.MoveStrategy;
import com.chess.engine.classic.player.ai.StockAlphaBeta;
import com.chess.pgn.FenUtilities;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestAlphaBeta {

    @Test
    public void testOpeningDepth4BlackMovesFirst() {
        final Board board = FenUtilities.createGameFromFEN("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR b - - 0 1");
        System.out.println(FenUtilities.createFENFromGame(board));
        final MoveStrategy alphaBeta = new StockAlphaBeta(4);
        final Move bestMove = alphaBeta.execute(board);
        assertEquals(bestMove, Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("e7"), BoardUtils.INSTANCE.getCoordinateAtPosition("e5")));
    }

    @Test
    public void advancedLevelProblem2NakamuraShirov() {
        final Board board = FenUtilities.createGameFromFEN("5k2/2p5/8/1r1N1b2/4R2P/2K3P1/8/8 w - - 0 1");
        System.out.println(FenUtilities.createFENFromGame(board));
        final MoveStrategy alphaBeta = new StockAlphaBeta(6);
        final Move bestMove = alphaBeta.execute(board);
        assertEquals(bestMove, Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("d5"), BoardUtils.INSTANCE.getCoordinateAtPosition("c7")));
    }

    @Test
    public void eloTest1() {
        final Board board = FenUtilities.createGameFromFEN("r1b3k1/6p1/P1n1pr1p/q1p5/1b1P4/2N2N2/PP1QBPPP/R3K2R b - - 0 1");
        final String fen = FenUtilities.createFENFromGame(board);
        System.out.println(fen);
        final MoveStrategy alphaBeta = new StockAlphaBeta(8);
        final Move bestMove = alphaBeta.execute(board);
        assertEquals(bestMove, Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("c8"), BoardUtils.INSTANCE.getCoordinateAtPosition("a6")));
    }

    @Test
    public void testQualityDepth6() {
        final Board board = FenUtilities.createGameFromFEN("4k2r/1R3R2/p3p1pp/4b3/1BnNr3/8/P1P5/5K2 w - - 1 0");
        final MoveStrategy alphaBeta = new StockAlphaBeta(7);
        final Move bestMove = alphaBeta.execute(board);
        assertEquals(bestMove, Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("f7"), BoardUtils.INSTANCE.getCoordinateAtPosition("e7")));
    }

    @Test
    public void testQualityTwoDepth6() {
        final Board board = FenUtilities.createGameFromFEN("6k1/3b3r/1p1p4/p1n2p2/1PPNpP1q/P3Q1p1/1R1RB1P1/5K2 b - - 0-1");
        final MoveStrategy alphaBeta = new StockAlphaBeta(6);
        final Move bestMove = alphaBeta.execute(board);
        assertEquals(bestMove, Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("h4"), BoardUtils.INSTANCE.getCoordinateAtPosition("f4")));
    }

    @Test
    public void testQualityThreeDepth6() {
        final Board board = FenUtilities.createGameFromFEN("r2r1n2/pp2bk2/2p1p2p/3q4/3PN1QP/2P3R1/P4PP1/5RK1 w - - 0 1");
        final MoveStrategy alphaBeta = new StockAlphaBeta(7);
        final Move bestMove = alphaBeta.execute(board);
        assertEquals(bestMove, Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("g4"), BoardUtils.INSTANCE.getCoordinateAtPosition("g7")));
    }

    @Test
    public void testQualityFourDepth6() {
        final Board board = FenUtilities.createGameFromFEN("r1b1k2r/pp3pbp/1qn1p1p1/2pnP3/3p1PP1/1P1P1NBP/P1P5/RN1QKB1R b KQkq - 2 11");
        final MoveStrategy alphaBeta = new StockAlphaBeta(7);
        final Move bestMove = alphaBeta.execute(board);
        assertEquals(bestMove, Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("d5"), BoardUtils.INSTANCE.getCoordinateAtPosition("e3")));
    }

    @Test
    public void eloTest2() {
        final Board board = FenUtilities.createGameFromFEN("2nq1nk1/5p1p/4p1pQ/pb1pP1NP/1p1P2P1/1P4N1/P4PB1/6K1 w - - 0 1");
        System.out.println(FenUtilities.createFENFromGame(board));
        final MoveStrategy alphaBeta = new StockAlphaBeta(8);
        final Move bestMove = alphaBeta.execute(board);
        assertEquals(bestMove, Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("h5"), BoardUtils.INSTANCE.getCoordinateAtPosition("g6")));
    }

    @Test
    public void eloTest3() {
        final Board board = FenUtilities.createGameFromFEN("8/3r2p1/pp1Bp1p1/1kP5/1n2K3/6R1/1P3P2/8 w - - 0 1");
        System.out.println(FenUtilities.createFENFromGame(board));
        final MoveStrategy alphaBeta = new StockAlphaBeta(8);
        final Move bestMove = alphaBeta.execute(board);
        assertEquals(bestMove, Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("g3"), BoardUtils.INSTANCE.getCoordinateAtPosition("g6")));
    }

    @Test
    public void blackWidowLoss1() {
        final Board board = FenUtilities.createGameFromFEN("r2qkb1r/3p1pp1/p1n1p2p/1p1bP3/P2p4/1PP5/5PPP/RNBQNRK1 w kq - 0 13");
        final MoveStrategy alphaBeta = new StockAlphaBeta(6);
        final Move bestMove = alphaBeta.execute(board);
        assertEquals(bestMove, Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("c3"), BoardUtils.INSTANCE.getCoordinateAtPosition("d4")));
    }

    @Test
    public void testCheckmateHorizon() {
        final Board board = FenUtilities.createGameFromFEN("8/3r4/pR6/2Rb1k2/3P4/5P2/3B2PP/7K w - - 0 1");
        System.out.println(FenUtilities.createFENFromGame(board));
        final MoveStrategy alphaBeta = new StockAlphaBeta(4);
        final Move bestMove = alphaBeta.execute(board);
        assertEquals(bestMove, Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("g2"), BoardUtils.INSTANCE.getCoordinateAtPosition("g4")));
    }

    @Test
    public void testBlackInTrouble() {
        final Board board = FenUtilities.createGameFromFEN("7k/pppq2rp/1bnp1p2/7N/3PR3/6Q1/P4PPP/6K1 w - - 0 1");
        System.out.println(FenUtilities.createFENFromGame(board));
        final MoveStrategy alphaBeta = new StockAlphaBeta(4);
        final Move bestMove = alphaBeta.execute(board);
        assertEquals(bestMove, Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("e4"), BoardUtils.INSTANCE.getCoordinateAtPosition("e8")));
    }

    @Test
    public void findMate3() {
        final Board board = FenUtilities.createGameFromFEN("5rk1/5Npp/8/3Q4/8/8/8/7K w - - 0");
        System.out.println(FenUtilities.createFENFromGame(board));
        final MoveStrategy alphaBeta = new StockAlphaBeta(6);
        final Move bestMove = alphaBeta.execute(board);
        assertEquals(bestMove, Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("f7"), BoardUtils.INSTANCE.getCoordinateAtPosition("h6")));
        final MoveTransition t1 = board.currentPlayer()
                .makeMove(bestMove);
        assertTrue(t1.getMoveStatus().isDone());
    }

    @Test
    public void runawayPawnMakesIt() {
        final Board board = FenUtilities.createGameFromFEN("2k5/8/8/8/p7/8/8/4K3 b - - 0 1");
        final MoveStrategy alphaBeta = new StockAlphaBeta(5);
        final Move bestMove = alphaBeta.execute(board);
        assertEquals(bestMove, Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("a4"), BoardUtils.INSTANCE.getCoordinateAtPosition("a3")));
        final MoveTransition t1 = board.currentPlayer()
                .makeMove(bestMove);
        assertTrue(t1.getMoveStatus().isDone());
    }

    @Test
    public void testMajorAttackMoveArrayPatching() {
        // Board: White knight on c3, Black queen on d5, white to move
        final Board board = FenUtilities.createGameFromFEN("rnb1kbnr/ppp1pppp/8/3q4/8/2N5/PPPP1PPP/R1BQKBNR w KQkq - 0 3");

        // The move: Nc3xd5
        final int knightPos = BoardUtils.INSTANCE.getCoordinateAtPosition("c3");
        final int queenPos = BoardUtils.INSTANCE.getCoordinateAtPosition("d5");
        final Move move = Move.MoveFactory.createMove(board, knightPos, queenPos);

        // Should be a MajorAttackMove
        assertTrue(move instanceof Move.MajorAttackMove);

        // Execute the move
        final MoveTransition t1 = board.currentPlayer().makeMove(move);

        // The move should be done, and the new board should have a knight on d5 and no queen
        assertTrue(t1.getMoveStatus().isDone());
        final Board nextBoard = t1.getToBoard();

        // The knight is now on d5
        final Piece pieceOnD5 = nextBoard.getPiece(queenPos);
        assertNotNull(pieceOnD5);
        assertEquals(Piece.PieceType.KNIGHT, pieceOnD5.getPieceType());
        assertEquals(board.currentPlayer().getAlliance(), pieceOnD5.getPieceAllegiance());

        // There is no piece left on c3
        assertNull(nextBoard.getPiece(knightPos));

        // The black queen is gone from the board
        boolean blackQueenExists = nextBoard.getAllPieces().stream()
                .anyMatch(p -> p.getPieceType() == Piece.PieceType.QUEEN && p.getPieceAllegiance().isBlack());
        assertFalse(blackQueenExists);
    }

    @Test
    public void testMackHackScenario() {
        final Board board = FenUtilities.createGameFromFEN("1r1k1r2/p5Q1/2p3p1/8/1q1p2n1/3P2P1/P3RPP1/4RK2 b - - 0 1");
        final MoveStrategy alphaBeta = new StockAlphaBeta(8);
        final Move bestMove = alphaBeta.execute(board);
        assertEquals(bestMove, Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("f8"), BoardUtils.INSTANCE.getCoordinateAtPosition("f2")));
        final MoveTransition t1 = board.currentPlayer()
                .makeMove(bestMove);
        assertTrue(t1.getMoveStatus().isDone());
    }

    @Test
    public void testAutoResponseVsPrinChess() {
        final Board board = FenUtilities.createGameFromFEN("r2q1rk1/p1p2pp1/3p1b2/2p2QNb/4PB1P/6R1/PPPR4/2K5 b - - 0 1");
        final MoveStrategy alphaBeta = new StockAlphaBeta(6);
        final Move bestMove = alphaBeta.execute(board);
        assertEquals(bestMove, Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("h5"), BoardUtils.INSTANCE.getCoordinateAtPosition("g6")));
        final MoveTransition t1 = board.currentPlayer()
                .makeMove(bestMove);
        assertTrue(t1.getMoveStatus().isDone());
    }

    @Test
    public void testBratcoKopec1() {
        final Board board = FenUtilities.createGameFromFEN("1k1r4/pp1b1R2/3q2pp/4p3/2B5/4Q3/PPP2B2/2K5 b - - 0 1");
        final MoveStrategy alphaBeta = new StockAlphaBeta(6);
        final Move bestMove = alphaBeta.execute(board);
        assertEquals(bestMove, Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("d6"), BoardUtils.INSTANCE.getCoordinateAtPosition("d1")));
        final MoveTransition t1 = board.currentPlayer()
                .makeMove(bestMove);
        assertTrue(t1.getMoveStatus().isDone());
    }

    @Test
    public void testBratcoKopec2() {
        final Board board = FenUtilities.createGameFromFEN("3r1k2/4npp1/1ppr3p/p6P/P2PPPP1/1NR5/5K2/2R5 w - - 0 1");
        final MoveStrategy alphaBeta = new StockAlphaBeta(8);
        final Move bestMove = alphaBeta.execute(board);
        assertEquals(bestMove, Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("e4"), BoardUtils.INSTANCE.getCoordinateAtPosition("e5")));
        final MoveTransition t1 = board.currentPlayer()
                .makeMove(bestMove);
        assertTrue(t1.getMoveStatus().isDone());
    }

    @Test
    public void testGPT1() {
        final Board board = FenUtilities.createGameFromFEN("r1b1k2r/pp2bppp/2n5/2pqN3/3p1B2/2PP1N2/P1P2PPP/R2QKB1R b KQkq - 0 9");
        final MoveStrategy alphaBeta = new StockAlphaBeta(6);
        final Move bestMove = alphaBeta.execute(board);
        assertEquals(bestMove, Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("d5"), BoardUtils.INSTANCE.getCoordinateAtPosition("e6")));
        final MoveTransition t1 = board.currentPlayer()
                .makeMove(bestMove);
        assertTrue(t1.getMoveStatus().isDone());
    }

    @Test
    public void testBratcoKopec19() {
        final Board board = FenUtilities.createGameFromFEN("3rr3/2pq2pk/p2p1pnp/8/2QBPP2/1P6/P5PP/4RRK1 b - -");
        final MoveStrategy alphaBeta = new StockAlphaBeta(6);
        final Move bestMove = alphaBeta.execute(board);
        assertEquals(bestMove, Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("d6"), BoardUtils.INSTANCE.getCoordinateAtPosition("d5")));
        final MoveTransition t1 = board.currentPlayer()
                .makeMove(bestMove);
        assertTrue(t1.getMoveStatus().isDone());
    }

}
