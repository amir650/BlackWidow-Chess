package com.chess.tests;

import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.MoveFactory;
import com.chess.engine.board.MoveTransition;
import com.chess.engine.player.ai.MoveStrategy;
import com.chess.engine.player.ai.BlackWidowAI;
import com.chess.pgn.FenUtilities;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestCheckmate {

    @Test
    public void testFoolsMate() {

        final Board board = Board.createStandardBoard();
        final MoveTransition t1 = board.currentPlayer()
                .makeMove(MoveFactory.createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("f2"),
                                BoardUtils.INSTANCE.getCoordinateAtPosition("f3")));

        assertTrue(t1.getMoveStatus().isDone());

        final MoveTransition t2 = t1.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t1.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("e7"),
                                BoardUtils.INSTANCE.getCoordinateAtPosition("e5")));

        assertTrue(t2.getMoveStatus().isDone());

        final MoveTransition t3 = t2.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t2.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("g2"),
                                BoardUtils.INSTANCE.getCoordinateAtPosition("g4")));

        assertTrue(t3.getMoveStatus().isDone());

        final MoveTransition t4 = t3.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t3.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("d8"),
                                BoardUtils.INSTANCE.getCoordinateAtPosition("h4")));

        assertTrue(t4.getMoveStatus().isDone());

        assertTrue(t4.getToBoard().currentPlayer().isInCheckMate());

    }

    @Test
    public void testScholarsMate() {

        final Board board = Board.createStandardBoard();
        final MoveTransition t1 = board.currentPlayer()
                .makeMove(MoveFactory.createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("e2"),
                                BoardUtils.INSTANCE.getCoordinateAtPosition("e4")));

        assertTrue(t1.getMoveStatus().isDone());

        final MoveTransition t2 = t1.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t1.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("a7"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("a6")));

        assertTrue(t2.getMoveStatus().isDone());

        final MoveTransition t3 = t2.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t2.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("d1"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("f3")));

        assertTrue(t3.getMoveStatus().isDone());

        final MoveTransition t4 = t3.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t3.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("a6"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("a5")));

        assertTrue(t4.getMoveStatus().isDone());

        final MoveTransition t5 = t4.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t4.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("f1"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("c4")));

        assertTrue(t5.getMoveStatus().isDone());

        final MoveTransition t6 = t5.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t5.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("a5"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("a4")));

        assertTrue(t6.getMoveStatus().isDone());

        final MoveTransition t7 = t6.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t6.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("f3"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("f7")));

        assertTrue(t7.getMoveStatus().isDone());
        assertTrue(t7.getToBoard().currentPlayer().isInCheckMate());

    }

    @Test
    public void testLegalsMate() {

        final Board board = Board.createStandardBoard();
        final MoveTransition t1 = board.currentPlayer()
                .makeMove(MoveFactory.createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("e2"),
                                BoardUtils.INSTANCE.getCoordinateAtPosition("e4")));

        assertTrue(t1.getMoveStatus().isDone());

        final MoveTransition t2 = t1.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t1.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("e7"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("e5")));

        assertTrue(t2.getMoveStatus().isDone());

        final MoveTransition t3 = t2.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t2.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("f1"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("c4")));

        assertTrue(t3.getMoveStatus().isDone());

        final MoveTransition t4 = t3.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t3.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("d7"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("d6")));

        assertTrue(t4.getMoveStatus().isDone());

        final MoveTransition t5 = t4.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t4.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("g1"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("f3")));

        assertTrue(t5.getMoveStatus().isDone());

        final MoveTransition t6 = t5.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t5.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("c8"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("g4")));

        assertTrue(t6.getMoveStatus().isDone());

        final MoveTransition t7 = t6.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t6.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("b1"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("c3")));

        assertTrue(t7.getMoveStatus().isDone());

        final MoveTransition t8 = t7.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t7.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("g7"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("g6")));

        assertTrue(t8.getMoveStatus().isDone());

        final MoveTransition t9 = t8.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t8.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("f3"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("e5")));

        assertTrue(t9.getMoveStatus().isDone());

        final MoveTransition t10 = t9.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t9.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("g4"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("d1")));

        assertTrue(t10.getMoveStatus().isDone());

        final MoveTransition t11 = t10.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t10.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("c4"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("f7")));

        assertTrue(t11.getMoveStatus().isDone());

        final MoveTransition t12 = t11.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t11.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("e8"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("e7")));

        assertTrue(t12.getMoveStatus().isDone());

        final MoveTransition t13 = t12.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t12.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("c3"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("d5")));

        assertTrue(t13.getMoveStatus().isDone());
        assertTrue(t13.getToBoard().currentPlayer().isInCheckMate());

    }

    @Test
    public void testSevenMoveMate() {

        final Board board = Board.createStandardBoard();
        final MoveTransition t1 = board.currentPlayer()
                .makeMove(MoveFactory.createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("e2"),
                                BoardUtils.INSTANCE.getCoordinateAtPosition("e4")));

        assertTrue(t1.getMoveStatus().isDone());

        final MoveTransition t2 = t1.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t1.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("e7"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("e5")));

        assertTrue(t2.getMoveStatus().isDone());

        final MoveTransition t3 = t2.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t2.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("d2"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("d4")));

        assertTrue(t3.getMoveStatus().isDone());

        final MoveTransition t4 = t3.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t3.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("d7"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("d6")));

        assertTrue(t4.getMoveStatus().isDone());

        final MoveTransition t5 = t4.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t4.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("d4"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("e5")));

        assertTrue(t5.getMoveStatus().isDone());

        final MoveTransition t6 = t5.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t5.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("d8"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("e7")));

        assertTrue(t6.getMoveStatus().isDone());

        final MoveTransition t7 = t6.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t6.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("e5"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("d6")));

        assertTrue(t7.getMoveStatus().isDone());

        final MoveTransition t8 = t7.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t7.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("e7"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("e4")));

        assertTrue(t8.getMoveStatus().isDone());

        final MoveTransition t9 = t8.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t8.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("f1"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("e2")));

        assertTrue(t9.getMoveStatus().isDone());

        final MoveTransition t10 = t9.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t9.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("e4"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("g2")));

        assertTrue(t10.getMoveStatus().isDone());

        final MoveTransition t11 = t10.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t10.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("d6"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("c7")));

        assertTrue(t11.getMoveStatus().isDone());

        final MoveTransition t12 = t11.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t11.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("g2"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("h1")));

        assertTrue(t12.getMoveStatus().isDone());

        final MoveTransition t13 = t12.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t12.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("d1"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("d8")));

        assertTrue(t13.getMoveStatus().isDone());
        assertTrue(t13.getToBoard().currentPlayer().isInCheckMate());

    }

    @Test
    public void testGrecoGame() {

        final Board board = Board.createStandardBoard();
        final MoveTransition t1 = board.currentPlayer()
                .makeMove(MoveFactory.createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("d2"),
                                BoardUtils.INSTANCE.getCoordinateAtPosition("d4")));

        assertTrue(t1.getMoveStatus().isDone());

        final MoveTransition t2 = t1.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t1.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("g8"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("f6")));

        assertTrue(t2.getMoveStatus().isDone());

        final MoveTransition t3 = t2.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t2.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("b1"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("d2")));

        assertTrue(t3.getMoveStatus().isDone());

        final MoveTransition t4 = t3.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t3.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("e7"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("e5")));

        assertTrue(t4.getMoveStatus().isDone());

        final MoveTransition t5 = t4.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t4.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("d4"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("e5")));

        assertTrue(t5.getMoveStatus().isDone());

        final MoveTransition t6 = t5.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t5.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("f6"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("g4")));


        assertTrue(t6.getMoveStatus().isDone());

        final MoveTransition t7 = t6.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t6.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("h2"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("h3")));

        assertTrue(t7.getMoveStatus().isDone());

        final MoveTransition t8 = t7.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t7.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("g4"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("e3")));

        assertTrue(t8.getMoveStatus().isDone());

        final MoveTransition t9 = t8.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t8.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("f2"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("e3")));

        assertTrue(t9.getMoveStatus().isDone());

        final MoveTransition t10 = t9.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t9.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("d8"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("h4")));

        assertTrue(t10.getMoveStatus().isDone());

        final MoveTransition t11 = t10.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t10.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("g2"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("g3")));

        assertTrue(t11.getMoveStatus().isDone());

        final MoveTransition t12 = t11.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t11.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("h4"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("g3")));

        assertTrue(t12.getMoveStatus().isDone());
        assertTrue(t12.getToBoard().currentPlayer().isInCheckMate());

    }

    @Test
    public void testOlympicGame() {

        final Board board = Board.createStandardBoard();
        final MoveTransition t1 = board.currentPlayer()
                .makeMove(MoveFactory.createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("e2"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("e4")));

        assertTrue(t1.getMoveStatus().isDone());

        final MoveTransition t2 = t1.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t1.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("c7"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("c6")));

        assertTrue(t2.getMoveStatus().isDone());

        final MoveTransition t3 = t2.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t2.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("g1"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("f3")));

        assertTrue(t3.getMoveStatus().isDone());

        final MoveTransition t4 = t3.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t3.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("d7"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("d5")));

        assertTrue(t4.getMoveStatus().isDone());

        final MoveTransition t5 = t4.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t4.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("b1"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("c3")));

        assertTrue(t5.getMoveStatus().isDone());

        final MoveTransition t6 = t5.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t5.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("d5"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("e4")));

        assertTrue(t6.getMoveStatus().isDone());

        final MoveTransition t7 = t6.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t6.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("c3"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("e4")));

        assertTrue(t7.getMoveStatus().isDone());

        final MoveTransition t8 = t7.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t7.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("b8"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("d7")));

        assertTrue(t8.getMoveStatus().isDone());

        final MoveTransition t9 = t8.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t8.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("d1"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("e2")));

        assertTrue(t9.getMoveStatus().isDone());

        final MoveTransition t10 = t9.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t9.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("g8"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("f6")));

        assertTrue(t10.getMoveStatus().isDone());

        final MoveTransition t11 = t10.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t10.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("e4"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("d6")));

        assertTrue(t11.getMoveStatus().isDone());
        assertTrue(t11.getToBoard().currentPlayer().isInCheckMate());

    }

    @Test
    public void testAnotherGame() {

        final Board board = Board.createStandardBoard();
        final MoveTransition t1 = board.currentPlayer()
                .makeMove(MoveFactory.createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("e2"),
                                BoardUtils.INSTANCE.getCoordinateAtPosition("e4")));

        assertTrue(t1.getMoveStatus().isDone());

        final MoveTransition t2 = t1.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t1.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("e7"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("e5")));

        assertTrue(t2.getMoveStatus().isDone());

        final MoveTransition t3 = t2.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t2.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("g1"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("f3")));

        assertTrue(t3.getMoveStatus().isDone());

        final MoveTransition t4 = t3.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t3.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("b8"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("c6")));

        assertTrue(t4.getMoveStatus().isDone());

        final MoveTransition t5 = t4.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t4.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("f1"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("c4")));

        assertTrue(t5.getMoveStatus().isDone());

        final MoveTransition t6 = t5.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t5.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("c6"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("d4")));

        assertTrue(t6.getMoveStatus().isDone());

        final MoveTransition t7 = t6.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t6.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("f3"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("e5")));

        assertTrue(t7.getMoveStatus().isDone());

        final MoveTransition t8 = t7.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t7.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("d8"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("g5")));

        assertTrue(t8.getMoveStatus().isDone());

        final MoveTransition t9 = t8.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t8.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("e5"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("f7")));

        assertTrue(t9.getMoveStatus().isDone());

        final MoveTransition t10 = t9.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t9.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("g5"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("g2")));

        assertTrue(t10.getMoveStatus().isDone());

        final MoveTransition t11 = t10.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t10.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("h1"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("f1")));

        assertTrue(t11.getMoveStatus().isDone());

        final MoveTransition t12 = t11.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t11.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("g2"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("e4")));

        assertTrue(t12.getMoveStatus().isDone());

        final MoveTransition t13 = t12.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t12.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("c4"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("e2")));

        assertTrue(t13.getMoveStatus().isDone());

        final MoveTransition t14 = t13.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t13.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("d4"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("f3")));

        assertTrue(t14.getMoveStatus().isDone());
        assertTrue(t14.getToBoard().currentPlayer().isInCheckMate());

    }

    @Test
    public void testSmotheredMate() {

        final Board board = Board.createStandardBoard();
        final MoveTransition t1 = board.currentPlayer()
                .makeMove(MoveFactory.createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("e2"),
                                BoardUtils.INSTANCE.getCoordinateAtPosition("e4")));

        assertTrue(t1.getMoveStatus().isDone());

        final MoveTransition t2 = t1.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t1.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("e7"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("e5")));

        assertTrue(t2.getMoveStatus().isDone());

        final MoveTransition t3 = t2.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t2.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("g1"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("e2")));

        assertTrue(t3.getMoveStatus().isDone());

        final MoveTransition t4 = t3.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t3.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("b8"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("c6")));

        assertTrue(t4.getMoveStatus().isDone());

        final MoveTransition t5 = t4.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t4.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("b1"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("c3")));

        assertTrue(t4.getMoveStatus().isDone());

        final MoveTransition t6 = t5.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t5.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("c6"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("d4")));

        assertTrue(t6.getMoveStatus().isDone());

        final MoveTransition t7 = t6.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t6.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("g2"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("g3")));

        assertTrue(t7.getMoveStatus().isDone());

        final MoveTransition t8 = t7.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t7.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("d4"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("f3")));

        assertTrue(t8.getMoveStatus().isDone());
        assertTrue(t8.getToBoard().currentPlayer().isInCheckMate());

    }

    @Test
    public void testHippopotamusMate() {

        final Board board = Board.createStandardBoard();
        final MoveTransition t1 = board.currentPlayer()
                .makeMove(MoveFactory.createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("e2"),
                                BoardUtils.INSTANCE.getCoordinateAtPosition("e4")));

        assertTrue(t1.getMoveStatus().isDone());

        final MoveTransition t2 = t1.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t1.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("e7"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("e5")));

        assertTrue(t2.getMoveStatus().isDone());

        final MoveTransition t3 = t2.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t2.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("g1"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("e2")));

        assertTrue(t3.getMoveStatus().isDone());

        final MoveTransition t4 = t3.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t3.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("d8"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("h4")));

        assertTrue(t4.getMoveStatus().isDone());

        final MoveTransition t5 = t4.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t4.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("b1"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("c3")));

        assertTrue(t5.getMoveStatus().isDone());

        final MoveTransition t6 = t5.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t5.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("b8"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("c6")));

        assertTrue(t6.getMoveStatus().isDone());

        final MoveTransition t7 = t6.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t6.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("g2"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("g3")));

        assertTrue(t7.getMoveStatus().isDone());

        final MoveTransition t8 = t7.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t7.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("h4"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("g5")));


        assertTrue(t8.getMoveStatus().isDone());

        final MoveTransition t9 = t8.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t8.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("d2"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("d4")));

        assertTrue(t9.getMoveStatus().isDone());

        final MoveTransition t10 = t9.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t9.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("c6"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("d4")));

        assertTrue(t10.getMoveStatus().isDone());

        final MoveTransition t11 = t10.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t10.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("c1"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("g5")));

        assertTrue(t11.getMoveStatus().isDone());

        final MoveTransition t12 = t11.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t11.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("d4"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("f3")));

        assertTrue(t12.getMoveStatus().isDone());
        assertTrue(t12.getToBoard().currentPlayer().isInCheckMate());

    }

    @Test
    public void testBlackburneShillingMate() {

        final Board board = Board.createStandardBoard();

        final MoveTransition t1 = board.currentPlayer()
                .makeMove(MoveFactory.createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("e2"),
                                BoardUtils.INSTANCE.getCoordinateAtPosition("e4")));

        assertTrue(t1.getMoveStatus().isDone());

        final MoveTransition t2 = t1.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t1.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("e7"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("e5")));

        assertTrue(t2.getMoveStatus().isDone());

        final MoveTransition t3 = t2.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t2.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("g1"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("f3")));

        assertTrue(t3.getMoveStatus().isDone());

        final MoveTransition t4 = t3.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t3.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("b8"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("c6")));

        assertTrue(t4.getMoveStatus().isDone());

        final MoveTransition t5 = t4.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t4.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("f1"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("c4")));

        assertTrue(t5.getMoveStatus().isDone());

        final MoveTransition t6 = t5.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t5.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("c6"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("d4")));

        assertTrue(t6.getMoveStatus().isDone());

        final MoveTransition t7 = t6.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t6.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("f3"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("e5")));

        assertTrue(t7.getMoveStatus().isDone());

        final MoveTransition t8 = t7.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t7.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("d8"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("g5")));

        assertTrue(t8.getMoveStatus().isDone());

        final MoveTransition t9 = t8.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t8.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("e5"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("f7")));

        assertTrue(t9.getMoveStatus().isDone());

        final MoveTransition t10 = t9.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t9.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("g5"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("g2")));

        assertTrue(t10.getMoveStatus().isDone());

        final MoveTransition t11 = t10.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t10.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("h1"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("f1")));

        assertTrue(t11.getMoveStatus().isDone());

        final MoveTransition t12 = t11.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t11.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("g2"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("e4")));

        assertTrue(t12.getMoveStatus().isDone());

        final MoveTransition t13 = t12.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t12.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("c4"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("e2")));

        assertTrue(t13.getMoveStatus().isDone());

        final MoveTransition t14 = t13.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t13.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("d4"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("f3")));

        assertTrue(t14.getMoveStatus().isDone());
        assertTrue(t14.getToBoard().currentPlayer().isInCheckMate());

    }

    @Test
    public void testAnastasiaMate() {
        final Board board = FenUtilities.createGameFromFEN("r4r2/ppp1Nppk/8/3R4/8/1P6/P4PPP/6K1 w - - 0 1");
        System.out.println(FenUtilities.createFENFromGame(board));
        final MoveTransition t1 = board.currentPlayer()
                .makeMove(MoveFactory.createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("d5"),
                                BoardUtils.INSTANCE.getCoordinateAtPosition("h5")));
        assertTrue(t1.getMoveStatus().isDone());
        assertTrue(t1.getToBoard().currentPlayer().isInCheckMate());
    }

    @Test
    public void testTwoBishopMate() {
        final Board board = FenUtilities.createGameFromFEN("7k/p1p4p/1p6/8/8/B7/B4K2/8 w - - 0 1");
        System.out.println(FenUtilities.createFENFromGame(board));
        final MoveTransition t1 = board.currentPlayer()
                .makeMove(MoveFactory.createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("a3"),
                                BoardUtils.INSTANCE.getCoordinateAtPosition("b2")));
        assertTrue(t1.getMoveStatus().isDone());
        assertTrue(t1.getToBoard().currentPlayer().isInCheckMate());
    }

    @Test
    public void testQueenRookMate() {
        final Board board = FenUtilities.createGameFromFEN("5k2/1R6/Q7/8/8/8/8/3K4 w - - 0 1");
        System.out.println(FenUtilities.createFENFromGame(board));
        final MoveTransition t1 = board.currentPlayer()
                .makeMove(MoveFactory.createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("a6"),
                                BoardUtils.INSTANCE.getCoordinateAtPosition("a8")));

        assertTrue(t1.getMoveStatus().isDone());
        assertTrue(t1.getToBoard().currentPlayer().isInCheckMate());

    }

    @Test
    public void testQueenKnightMate() {
        final Board board = FenUtilities.createGameFromFEN("4k3/7Q/8/5N2/8/8/7P/4K3 w - - 0 1");
        System.out.println(FenUtilities.createFENFromGame(board));
        final MoveTransition t1 = board.currentPlayer()
                .makeMove(MoveFactory.createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("h7"),
                                BoardUtils.INSTANCE.getCoordinateAtPosition("e7")));

        assertTrue(t1.getMoveStatus().isDone());
        assertTrue(t1.getToBoard().currentPlayer().isInCheckMate());
    }

    @Test
    public void testBackRankMate() {
        final Board board = FenUtilities.createGameFromFEN("4k3/8/2r5/8/8/8/5PPP/6K1 b - - 0 1");
        System.out.println(FenUtilities.createFENFromGame(board));
        final MoveTransition t1 = board.currentPlayer()
                .makeMove(MoveFactory.createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("c6"),
                                BoardUtils.INSTANCE.getCoordinateAtPosition("c1")));
        assertTrue(t1.getMoveStatus().isDone());
        assertTrue(t1.getToBoard().currentPlayer().isInCheckMate());
    }

    @Test
    public void testMateInTwoTest1() {
        final Board board = FenUtilities.createGameFromFEN("6k1/1b4pp/1B1Q4/4p1P1/p3q3/2P3r1/P1P2PP1/R5K1 w - - 1 0");
        final MoveStrategy alphaBeta = new BlackWidowAI(6);
        final Move bestMove = alphaBeta.execute(board);
        assertEquals(bestMove, Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("d6"), BoardUtils.INSTANCE.getCoordinateAtPosition("e6")));
    }

    @Test
    public void testMateInTwoTest2() {
        final Board board = FenUtilities.createGameFromFEN("3r3r/1Q5p/p3q2k/3NBp1B/3p3n/5P2/PP4PP/4R2K w - - 1 0");
        final MoveStrategy alphaBeta = new BlackWidowAI(6);
        final Move actual = alphaBeta.execute(board);
        final Move expected = Move.MoveFactory.createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("b7"),
                BoardUtils.INSTANCE.getCoordinateAtPosition("g7"));
        assertEquals(expected, actual);
    }


    @Test
    public void testMateInFourTest1() {
        final Board board = FenUtilities.createGameFromFEN("7k/4r2B/1pb5/2P5/4p2Q/2q5/2P2R2/1K6 w - - 1 0");
        final MoveStrategy alphaBeta = new BlackWidowAI(7);
        final Move bestMove = alphaBeta.execute(board);
        assertEquals(bestMove, Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("f2"), BoardUtils.INSTANCE.getCoordinateAtPosition("f8")));
    }

    @Test
    public void testMagnusBlackToMoveAndWinTest1() {
        final Board board = FenUtilities.createGameFromFEN("2rr2k1/pb3pp1/4q2p/2pn4/2Q1P3/P4P2/1P3BPP/2KR2NR b - - 0 1");
        final MoveStrategy alphaBeta = new BlackWidowAI(6);
        final Move bestMove = alphaBeta.execute(board);
        assertEquals(bestMove, Move.MoveFactory
                .createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("d5"), BoardUtils.INSTANCE.getCoordinateAtPosition("e3")));
    }

}
