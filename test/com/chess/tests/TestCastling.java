package com.chess.tests;

import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.MoveTransition;
import com.chess.engine.player.ai.MoveStrategy;
import com.chess.engine.player.ai.BlackWidowAI;
import com.chess.pgn.FenUtilities;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestCastling {

    @Test
    public void testWhiteKingSideCastle() {
        final Board board = Board.createStandardBoard();
        final MoveTransition t1 = board.currentPlayer()
                .makeMove(Move.MoveFactory.createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("e2"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("e4")));
        assertTrue(t1.getMoveStatus().isDone());
        final MoveTransition t2 = t1.getToBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t1.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("e7"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("e5")));
        assertTrue(t2.getMoveStatus().isDone());
        final MoveTransition t3 = t2.getToBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t2.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("g1"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("f3")));
        assertTrue(t3.getMoveStatus().isDone());
        final MoveTransition t4 = t3.getToBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t3.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("d7"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("d6")));
        assertTrue(t4.getMoveStatus().isDone());
        final MoveTransition t5 = t4.getToBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t4.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("f1"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("e2")));
        assertTrue(t5.getMoveStatus().isDone());
        final MoveTransition t6 = t5.getToBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t5.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("d6"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("d5")));
        assertTrue(t6.getMoveStatus().isDone());
        final Move wm1 = Move.MoveFactory
                .createMove(t6.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition(
                        "e1"), BoardUtils.INSTANCE.getCoordinateAtPosition("g1"));
        assertTrue(t6.getToBoard().currentPlayer().getLegalMoves().contains(wm1));
        final MoveTransition t7 = t6.getToBoard().currentPlayer().makeMove(wm1);
        assertTrue(t7.getMoveStatus().isDone());
        assertTrue(t7.getToBoard().whitePlayer().isCastled());
        assertFalse(t7.getToBoard().whitePlayer().isKingSideCastleCapable());
        assertFalse(t7.getToBoard().whitePlayer().isQueenSideCastleCapable());
    }

    @Test
    public void testWhiteQueenSideCastle() {
        final Board board = Board.createStandardBoard();
        final MoveTransition t1 = board.currentPlayer()
                .makeMove(Move.MoveFactory.createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("e2"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("e4")));
        assertTrue(t1.getMoveStatus().isDone());
        final MoveTransition t2 = t1.getToBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t1.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("e7"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("e5")));
        assertTrue(t2.getMoveStatus().isDone());
        final MoveTransition t3 = t2.getToBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t2.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("d2"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("d3")));
        assertTrue(t3.getMoveStatus().isDone());
        final MoveTransition t4 = t3.getToBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t3.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("d7"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("d6")));
        assertTrue(t4.getMoveStatus().isDone());
        final MoveTransition t5 = t4.getToBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t4.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("c1"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("d2")));
        assertTrue(t5.getMoveStatus().isDone());
        final MoveTransition t6 = t5.getToBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t5.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("d6"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("d5")));
        assertTrue(t6.getMoveStatus().isDone());
        final MoveTransition t7 = t6.getToBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t6.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("d1"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("e2")));
        assertTrue(t7.getMoveStatus().isDone());
        final MoveTransition t8 = t7.getToBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t7.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("h7"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("h6")));
        assertTrue(t8.getMoveStatus().isDone());
        final MoveTransition t9 = t8.getToBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t8.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("b1"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("c3")));
        assertTrue(t9.getMoveStatus().isDone());
        final MoveTransition t10 = t9.getToBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t9.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("h6"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("h5")));
        assertTrue(t10.getMoveStatus().isDone());
        final Move wm1 = Move.MoveFactory
                .createMove(t10.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition(
                        "e1"), BoardUtils.INSTANCE.getCoordinateAtPosition("c1"));
        assertTrue(t10.getToBoard().currentPlayer().getLegalMoves().contains(wm1));
        final MoveTransition t11 = t10.getToBoard().currentPlayer().makeMove(wm1);
        assertTrue(t11.getMoveStatus().isDone());
        assertTrue(t11.getToBoard().whitePlayer().isCastled());
        assertFalse(t11.getToBoard().whitePlayer().isKingSideCastleCapable());
        assertFalse(t11.getToBoard().whitePlayer().isQueenSideCastleCapable());
    }

    @Test
    public void testBlackKingSideCastle() {
        final Board board = Board.createStandardBoard();
        final MoveTransition t1 = board.currentPlayer()
                .makeMove(Move.MoveFactory.createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("e2"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("e4")));
        assertTrue(t1.getMoveStatus().isDone());
        final MoveTransition t2 = t1.getToBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t1.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("e7"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("e5")));
        assertTrue(t2.getMoveStatus().isDone());
        final MoveTransition t3 = t2.getToBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t2.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("d2"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("d3")));
        assertTrue(t3.getMoveStatus().isDone());
        final MoveTransition t4 = t3.getToBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t3.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("g8"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("f6")));
        assertTrue(t4.getMoveStatus().isDone());
        final MoveTransition t5 = t4.getToBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t4.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("d3"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("d4")));
        assertTrue(t5.getMoveStatus().isDone());
        final MoveTransition t6 = t5.getToBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t5.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("f8"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("e7")));
        assertTrue(t6.getMoveStatus().isDone());
        final MoveTransition t7 = t6.getToBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t6.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("d4"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("d5")));
        assertTrue(t7.getMoveStatus().isDone());
        final Move wm1 = Move.MoveFactory
                .createMove(t7.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition(
                        "e8"), BoardUtils.INSTANCE.getCoordinateAtPosition("g8"));
        assertTrue(t7.getToBoard().currentPlayer().getLegalMoves().contains(wm1));
        final MoveTransition t8 = t7.getToBoard().currentPlayer().makeMove(wm1);
        assertTrue(t8.getMoveStatus().isDone());
        assertTrue(t8.getToBoard().blackPlayer().isCastled());
        assertFalse(t8.getToBoard().blackPlayer().isKingSideCastleCapable());
        assertFalse(t8.getToBoard().blackPlayer().isQueenSideCastleCapable());
    }

    @Test
    public void testBlackQueenSideCastle() {
        final Board board = Board.createStandardBoard();
        final MoveTransition t1 = board.currentPlayer()
                .makeMove(Move.MoveFactory.createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("e2"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("e4")));
        assertTrue(t1.getMoveStatus().isDone());
        final MoveTransition t2 = t1.getToBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t1.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("e7"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("e5")));
        assertTrue(t2.getMoveStatus().isDone());
        final MoveTransition t3 = t2.getToBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t2.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("d2"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("d3")));
        assertTrue(t3.getMoveStatus().isDone());
        final MoveTransition t4 = t3.getToBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t3.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("d8"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("e7")));
        assertTrue(t4.getMoveStatus().isDone());
        final MoveTransition t5 = t4.getToBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t4.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("b1"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("c3")));
        assertTrue(t5.getMoveStatus().isDone());
        final MoveTransition t6 = t5.getToBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t5.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("b8"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("c6")));
        assertTrue(t6.getMoveStatus().isDone());
        final MoveTransition t7 = t6.getToBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t6.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("c1"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("d2")));
        assertTrue(t7.getMoveStatus().isDone());
        final MoveTransition t8 = t7.getToBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t7.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("d7"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("d6")));
        assertTrue(t8.getMoveStatus().isDone());
        final MoveTransition t9 = t8.getToBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t8.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("f1"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("e2")));
        assertTrue(t9.getMoveStatus().isDone());
        final MoveTransition t10 = t9.getToBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t9.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("c8"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("d7")));
        assertTrue(t10.getMoveStatus().isDone());
        final MoveTransition t11 = t10.getToBoard()
                .currentPlayer()
                .makeMove(
                        Move.MoveFactory.createMove(t10.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("g1"),
                                BoardUtils.INSTANCE.getCoordinateAtPosition("f3")));
        assertTrue(t11.getMoveStatus().isDone());
        final Move wm1 = Move.MoveFactory
                .createMove(t11.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition(
                        "e8"), BoardUtils.INSTANCE.getCoordinateAtPosition("c8"));
        assertTrue(t11.getToBoard().currentPlayer().getLegalMoves().contains(wm1));
        final MoveTransition t12 = t11.getToBoard().currentPlayer().makeMove(wm1);
        assertTrue(t12.getMoveStatus().isDone());
        assertTrue(t12.getToBoard().blackPlayer().isCastled());
        assertFalse(t12.getToBoard().blackPlayer().isKingSideCastleCapable());
        assertFalse(t12.getToBoard().blackPlayer().isQueenSideCastleCapable());
    }

    @Test
    public void testCastleBugOne() {
        final Board board = Board.createStandardBoard();
        final MoveTransition t1 = board.currentPlayer()
                .makeMove(Move.MoveFactory.createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("e2"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("e4")));
        assertTrue(t1.getMoveStatus().isDone());
        final MoveTransition t2 = t1.getToBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t1.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("d7"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("d5")));
        assertTrue(t2.getMoveStatus().isDone());
        final MoveTransition t3 = t2.getToBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t2.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("e4"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("e5")));
        assertTrue(t3.getMoveStatus().isDone());
        final MoveTransition t4 = t3.getToBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t3.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("c8"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("f5")));
        assertTrue(t4.getMoveStatus().isDone());
        final MoveTransition t5 = t4.getToBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t4.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("f1"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("d3")));
        assertTrue(t5.getMoveStatus().isDone());
        final MoveTransition t6 = t5.getToBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t5.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("f5"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("d3")));
        assertTrue(t6.getMoveStatus().isDone());
        final MoveTransition t7 = t6.getToBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t6.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("c2"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("d3")));
        assertTrue(t7.getMoveStatus().isDone());
        final MoveTransition t8 = t7.getToBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t7.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("e7"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("e6")));
        assertTrue(t8.getMoveStatus().isDone());
        final MoveTransition t9 = t8.getToBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t8.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("d1"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("a4")));
        assertTrue(t9.getMoveStatus().isDone());
        final MoveTransition t10 = t9.getToBoard()
                .currentPlayer()
                .makeMove(Move.MoveFactory.createMove(t9.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("d8"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("d7")));
        assertTrue(t10.getMoveStatus().isDone());
        final MoveTransition t11 = t10.getToBoard()
                .currentPlayer()
                .makeMove(
                        Move.MoveFactory.createMove(t10.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("b1"),
                                BoardUtils.INSTANCE.getCoordinateAtPosition("c3")));
        assertTrue(t11.getMoveStatus().isDone());

        final MoveStrategy moveStrategy = new BlackWidowAI(6);

        moveStrategy.execute(t11.getToBoard());
    }

    @Test
    public void testCastleBugOneVerbose() {
        final Board board = Board.createStandardBoard();
        System.out.println("Initial Board:");
        System.out.println(board);

        final MoveTransition t1 = board.currentPlayer().makeMove(
                Move.MoveFactory.createMove(board,
                        BoardUtils.INSTANCE.getCoordinateAtPosition("e2"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("e4")));
        System.out.println("After t1 (e2-e4): " + t1.getTransitionMove());
        System.out.println(t1.getToBoard());
        assertTrue(t1.getMoveStatus().isDone());

        final MoveTransition t2 = t1.getToBoard().currentPlayer().makeMove(
                Move.MoveFactory.createMove(t1.getToBoard(),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("d7"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("d5")));
        System.out.println("After t2 (d7-d5): " + t2.getTransitionMove());
        System.out.println(t2.getToBoard());
        assertTrue(t2.getMoveStatus().isDone());

        final MoveTransition t3 = t2.getToBoard().currentPlayer().makeMove(
                Move.MoveFactory.createMove(t2.getToBoard(),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("e4"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("e5")));
        System.out.println("After t3 (e4-e5): " + t3.getTransitionMove());
        System.out.println(t3.getToBoard());
        assertTrue(t3.getMoveStatus().isDone());

        final MoveTransition t4 = t3.getToBoard().currentPlayer().makeMove(
                Move.MoveFactory.createMove(t3.getToBoard(),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("c8"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("f5")));
        System.out.println("After t4 (c8-f5): " + t4.getTransitionMove());
        System.out.println(t4.getToBoard());
        assertTrue(t4.getMoveStatus().isDone());

        final MoveTransition t5 = t4.getToBoard().currentPlayer().makeMove(
                Move.MoveFactory.createMove(t4.getToBoard(),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("f1"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("d3")));
        System.out.println("After t5 (f1-d3): " + t5.getTransitionMove());
        System.out.println(t5.getToBoard());
        assertTrue(t5.getMoveStatus().isDone());

        final MoveTransition t6 = t5.getToBoard().currentPlayer().makeMove(
                Move.MoveFactory.createMove(t5.getToBoard(),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("f5"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("d3")));
        System.out.println("After t6 (f5-d3): " + t6.getTransitionMove());
        System.out.println(t6.getToBoard());
        assertTrue(t6.getMoveStatus().isDone());

        final MoveTransition t7 = t6.getToBoard().currentPlayer().makeMove(
                Move.MoveFactory.createMove(t6.getToBoard(),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("c2"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("d3")));
        System.out.println("After t7 (c2-d3): " + t7.getTransitionMove());
        System.out.println(t7.getToBoard());
        assertTrue(t7.getMoveStatus().isDone());

        final MoveTransition t8 = t7.getToBoard().currentPlayer().makeMove(
                Move.MoveFactory.createMove(t7.getToBoard(),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("e7"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("e6")));
        System.out.println("After t8 (e7-e6): " + t8.getTransitionMove());
        System.out.println(t8.getToBoard());
        assertTrue(t8.getMoveStatus().isDone());

        final MoveTransition t9 = t8.getToBoard().currentPlayer().makeMove(
                Move.MoveFactory.createMove(t8.getToBoard(),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("d1"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("a4")));
        System.out.println("After t9 (d1-a4): " + t9.getTransitionMove());
        System.out.println(t9.getToBoard());
        assertTrue(t9.getMoveStatus().isDone());

        final MoveTransition t10 = t9.getToBoard().currentPlayer().makeMove(
                Move.MoveFactory.createMove(t9.getToBoard(),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("d8"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("d7")));
        System.out.println("After t10 (d8-d7): " + t10.getTransitionMove());
        System.out.println(t10.getToBoard());
        assertTrue(t10.getMoveStatus().isDone());

        final MoveTransition t11 = t10.getToBoard().currentPlayer().makeMove(
                Move.MoveFactory.createMove(t10.getToBoard(),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("b1"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("c3")));
        System.out.println("After t11 (b1-c3): " + t11.getTransitionMove());
        System.out.println(t11.getToBoard());
        assertTrue(t11.getMoveStatus().isDone());

        // Print the final board before the search
        System.out.println("Final board before search:");
        System.out.println(t11.getToBoard());
        System.out.println(FenUtilities.createFENFromGame(t11.getToBoard()));
        final MoveStrategy moveStrategy = new BlackWidowAI(6);

        moveStrategy.execute(t11.getToBoard());
    }

    @Test
    public void testIslateBug() {
        final Board board = FenUtilities.createGameFromFEN("rn2kbnr/pppq1ppp/4p3/3pP3/Q7/2NP4/PP1P1PPP/R1B1K1NR b KQkq - 0 1");
        final MoveStrategy moveStrategy = new BlackWidowAI(6);
        moveStrategy.execute(board);
    }

    @Test
    public void testNoCastlingOutOfCheck() {
        final Board board = FenUtilities.createGameFromFEN("r3k2r/1pN1nppp/p3p3/3p4/8/8/PPPK1PPP/R6R b kq - 1 18");
        final Move illegalCastleMove = Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("e8"), BoardUtils.INSTANCE.getCoordinateAtPosition("c8"));
        final MoveTransition t1 = board.currentPlayer()
                .makeMove(illegalCastleMove);
        assertFalse(t1.getMoveStatus().isDone());
    }

}
