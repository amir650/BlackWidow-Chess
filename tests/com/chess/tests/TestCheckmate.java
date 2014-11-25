package com.chess.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.chess.engine.classic.Alliance;
import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.board.Board.Builder;
import com.chess.engine.classic.board.Board.MoveStatus;
import com.chess.engine.classic.board.Move.MoveFactory;
import com.chess.engine.classic.board.MoveTransition;
import com.chess.engine.classic.pieces.Bishop;
import com.chess.engine.classic.pieces.King;
import com.chess.engine.classic.pieces.Knight;
import com.chess.engine.classic.pieces.Pawn;
import com.chess.engine.classic.pieces.Queen;
import com.chess.engine.classic.pieces.Rook;

public class TestCheckmate {

    @Test
    public void testFoolsMate() {

        final Board board = Board.createStandardBoard();
        final MoveTransition t1 = board.makeMove(MoveFactory.createMove(board, Board.getCoordinateAtPosition("f2"),
                Board.getCoordinateAtPosition("f3")));

        assertEquals(MoveStatus.DONE, t1.getMoveStatus());

        final MoveTransition t2 = t1.getTransitionBoard().makeMove(
                MoveFactory.createMove(t1.getTransitionBoard(), Board.getCoordinateAtPosition("e7"), Board.getCoordinateAtPosition("e5")));

        assertEquals(MoveStatus.DONE, t2.getMoveStatus());

        final MoveTransition t3 = t2.getTransitionBoard().makeMove(
                MoveFactory.createMove(t2.getTransitionBoard(), Board.getCoordinateAtPosition("g2"), Board.getCoordinateAtPosition("g4")));

        assertEquals(MoveStatus.DONE, t3.getMoveStatus());

        final MoveTransition t4 = t3.getTransitionBoard().makeMove(
                MoveFactory.createMove(t3.getTransitionBoard(), Board.getCoordinateAtPosition("d8"), Board.getCoordinateAtPosition("h4")));

        assertEquals(MoveStatus.DONE, t4.getMoveStatus());

        assertTrue(t4.getTransitionBoard().currentPlayer().isInCheckMate());

    }

    @Test
    public void testScholarsMate() {

        final Board board = Board.createStandardBoard();
        final MoveTransition t1 = board.makeMove(MoveFactory.createMove(board, Board.getCoordinateAtPosition("e2"),
                Board.getCoordinateAtPosition("e4")));

        assertEquals(MoveStatus.DONE, t1.getMoveStatus());

        final MoveTransition t2 = t1.getTransitionBoard().makeMove(MoveFactory.createMove(t1.getTransitionBoard(), Board.getCoordinateAtPosition("a7"),
                Board.getCoordinateAtPosition("a6")));

        assertEquals(MoveStatus.DONE, t2.getMoveStatus());

        final MoveTransition t3 = t2.getTransitionBoard().makeMove(MoveFactory.createMove(t2.getTransitionBoard(), Board.getCoordinateAtPosition("d1"),
                Board.getCoordinateAtPosition("f3")));

        assertEquals(MoveStatus.DONE, t3.getMoveStatus());

        final MoveTransition t4 = t3.getTransitionBoard().makeMove( MoveFactory.createMove(t3.getTransitionBoard(), Board.getCoordinateAtPosition("a6"),
                Board.getCoordinateAtPosition("a5")));

        assertEquals(MoveStatus.DONE, t4.getMoveStatus());

        final MoveTransition t5 = t4.getTransitionBoard().makeMove(MoveFactory.createMove(t4.getTransitionBoard(), Board.getCoordinateAtPosition("f1"),
                Board.getCoordinateAtPosition("c4")));

        assertEquals(MoveStatus.DONE, t5.getMoveStatus());

        final MoveTransition t6 = t5.getTransitionBoard().makeMove(MoveFactory.createMove(t5.getTransitionBoard(), Board.getCoordinateAtPosition("a5"),
                        Board.getCoordinateAtPosition("a4")));

        assertEquals(MoveStatus.DONE, t6.getMoveStatus());

        final MoveTransition t7 = t6.getTransitionBoard().makeMove(MoveFactory.createMove(t6.getTransitionBoard(), Board.getCoordinateAtPosition("f3"),
                Board.getCoordinateAtPosition("f7")));

        assertEquals(MoveStatus.DONE, t7.getMoveStatus());
        assertTrue(t7.getTransitionBoard().currentPlayer().isInCheckMate());

    }

    @Test
    public void testLegalsMate() {

        final Board board = Board.createStandardBoard();
        final MoveTransition t1 = board.makeMove(MoveFactory.createMove(board, Board.getCoordinateAtPosition("e2"),
                Board.getCoordinateAtPosition("e4")));

        assertEquals(MoveStatus.DONE, t1.getMoveStatus());

        final MoveTransition t2 = t1.getTransitionBoard().makeMove(MoveFactory.createMove(t1.getTransitionBoard(), Board.getCoordinateAtPosition("e7"),
                Board.getCoordinateAtPosition("e5")));

        assertEquals(MoveStatus.DONE, t2.getMoveStatus());

        final MoveTransition t3 = t2.getTransitionBoard().makeMove(MoveFactory.createMove(t2.getTransitionBoard(), Board.getCoordinateAtPosition("f1"),
                Board.getCoordinateAtPosition("c4")));

        assertEquals(MoveStatus.DONE, t3.getMoveStatus());

        final MoveTransition t4 = t3.getTransitionBoard().makeMove(MoveFactory.createMove(t3.getTransitionBoard(), Board.getCoordinateAtPosition("d7"),
                Board.getCoordinateAtPosition("d6")));

        assertEquals(MoveStatus.DONE, t4.getMoveStatus());

        final MoveTransition t5 = t4.getTransitionBoard().makeMove(MoveFactory.createMove(t4.getTransitionBoard(), Board.getCoordinateAtPosition("g1"),
                Board.getCoordinateAtPosition("f3")));

        assertEquals(MoveStatus.DONE, t5.getMoveStatus());

        final MoveTransition t6 = t5.getTransitionBoard().makeMove(MoveFactory.createMove(t5.getTransitionBoard(), Board.getCoordinateAtPosition("c8"),
                Board.getCoordinateAtPosition("g4")));

        assertEquals(MoveStatus.DONE, t6.getMoveStatus());

        final MoveTransition t7 = t6.getTransitionBoard().makeMove(MoveFactory.createMove(t6.getTransitionBoard(), Board.getCoordinateAtPosition("b1"),
                Board.getCoordinateAtPosition("c3")));

        assertEquals(MoveStatus.DONE, t7.getMoveStatus());

        final MoveTransition t8 = t7.getTransitionBoard().makeMove(MoveFactory.createMove(t7.getTransitionBoard(), Board.getCoordinateAtPosition("g7"),
                Board.getCoordinateAtPosition("g6")));

        assertEquals(MoveStatus.DONE, t8.getMoveStatus());

        final MoveTransition t9 = t8.getTransitionBoard().makeMove(MoveFactory.createMove(t8.getTransitionBoard(), Board.getCoordinateAtPosition("f3"),
                Board.getCoordinateAtPosition("e5")));

        assertEquals(MoveStatus.DONE, t9.getMoveStatus());

        final MoveTransition t10 = t9.getTransitionBoard().makeMove(MoveFactory.createMove(t9.getTransitionBoard(), Board.getCoordinateAtPosition("g4"),
                Board.getCoordinateAtPosition("d1")));

        assertEquals(MoveStatus.DONE, t10.getMoveStatus());

        final MoveTransition t11 = t10.getTransitionBoard().makeMove(MoveFactory.createMove(t10.getTransitionBoard(), Board.getCoordinateAtPosition("c4"),
                Board.getCoordinateAtPosition("f7")));

        assertEquals(MoveStatus.DONE, t11.getMoveStatus());

        final MoveTransition t12 = t11.getTransitionBoard().makeMove(MoveFactory.createMove(t11.getTransitionBoard(), Board.getCoordinateAtPosition("e8"),
                Board.getCoordinateAtPosition("e7")));

        assertEquals(MoveStatus.DONE, t12.getMoveStatus());

        final MoveTransition t13 = t12.getTransitionBoard().makeMove(MoveFactory.createMove(t12.getTransitionBoard(), Board.getCoordinateAtPosition("c3"),
                Board.getCoordinateAtPosition("d5")));

        assertEquals(MoveStatus.DONE, t13.getMoveStatus());
        assertTrue(t13.getTransitionBoard().currentPlayer().isInCheckMate());

    }

    @Test
    public void testSevenMoveMate() {

        final Board board = Board.createStandardBoard();
        final MoveTransition t1 = board.makeMove(MoveFactory.createMove(board, Board.getCoordinateAtPosition("e2"),
                Board.getCoordinateAtPosition("e4")));

        assertEquals(MoveStatus.DONE, t1.getMoveStatus());

        final MoveTransition t2 = t1.getTransitionBoard().makeMove(MoveFactory.createMove(t1.getTransitionBoard(), Board.getCoordinateAtPosition("e7"),
                Board.getCoordinateAtPosition("e5")));

        assertEquals(MoveStatus.DONE, t2.getMoveStatus());

        final MoveTransition t3 = t2.getTransitionBoard().makeMove(MoveFactory.createMove(t2.getTransitionBoard(), Board.getCoordinateAtPosition("d2"),
                Board.getCoordinateAtPosition("d4")));

        assertEquals(MoveStatus.DONE, t3.getMoveStatus());

        final MoveTransition t4 = t3.getTransitionBoard().makeMove(MoveFactory.createMove(t3.getTransitionBoard(), Board.getCoordinateAtPosition("d7"),
                Board.getCoordinateAtPosition("d6")));

        assertEquals(MoveStatus.DONE, t4.getMoveStatus());

        final MoveTransition t5 = t4.getTransitionBoard().makeMove(MoveFactory.createMove(t4.getTransitionBoard(), Board.getCoordinateAtPosition("d4"),
                Board.getCoordinateAtPosition("e5")));

        assertEquals(MoveStatus.DONE, t5.getMoveStatus());

        final MoveTransition t6 = t5.getTransitionBoard().makeMove(MoveFactory.createMove(t5.getTransitionBoard(), Board.getCoordinateAtPosition("d8"),
                Board.getCoordinateAtPosition("e7")));

        assertEquals(MoveStatus.DONE, t6.getMoveStatus());

        final MoveTransition t7 = t6.getTransitionBoard().makeMove(MoveFactory.createMove(t6.getTransitionBoard(), Board.getCoordinateAtPosition("e5"),
                Board.getCoordinateAtPosition("d6")));

        assertEquals(MoveStatus.DONE, t7.getMoveStatus());

        final MoveTransition t8 = t7.getTransitionBoard().makeMove(MoveFactory.createMove(t7.getTransitionBoard(), Board.getCoordinateAtPosition("e7"),
                Board.getCoordinateAtPosition("e4")));

        assertEquals(MoveStatus.DONE, t8.getMoveStatus());

        final MoveTransition t9 = t8.getTransitionBoard().makeMove(MoveFactory.createMove(t8.getTransitionBoard(), Board.getCoordinateAtPosition("f1"),
                Board.getCoordinateAtPosition("e2")));

        assertEquals(MoveStatus.DONE, t9.getMoveStatus());

        final MoveTransition t10 = t9.getTransitionBoard().makeMove(MoveFactory.createMove(t9.getTransitionBoard(), Board.getCoordinateAtPosition("e4"),
                Board.getCoordinateAtPosition("g2")));

        assertEquals(MoveStatus.DONE, t10.getMoveStatus());

        final MoveTransition t11 = t10.getTransitionBoard().makeMove(MoveFactory.createMove(t10.getTransitionBoard(), Board.getCoordinateAtPosition("d6"),
                Board.getCoordinateAtPosition("c7")));

        assertEquals(MoveStatus.DONE, t11.getMoveStatus());

        final MoveTransition t12 = t11.getTransitionBoard().makeMove(MoveFactory.createMove(t11.getTransitionBoard(), Board.getCoordinateAtPosition("g2"),
                Board.getCoordinateAtPosition("h1")));

        assertEquals(MoveStatus.DONE, t12.getMoveStatus());

        final MoveTransition t13 = t12.getTransitionBoard().makeMove(MoveFactory.createMove(t12.getTransitionBoard(), Board.getCoordinateAtPosition("d1"),
                Board.getCoordinateAtPosition("d8")));

        assertEquals(MoveStatus.DONE, t13.getMoveStatus());
        assertTrue(t13.getTransitionBoard().currentPlayer().isInCheckMate());

    }

    @Test
    public void testGrecoGame() {

        final Board board = Board.createStandardBoard();
        final MoveTransition t1 = board.makeMove(MoveFactory.createMove(board, Board.getCoordinateAtPosition("d2"),
                Board.getCoordinateAtPosition("d4")));

        assertEquals(MoveStatus.DONE, t1.getMoveStatus());

        final MoveTransition t2 = t1.getTransitionBoard().makeMove(MoveFactory.createMove(t1.getTransitionBoard(), Board.getCoordinateAtPosition("g8"),
                Board.getCoordinateAtPosition("f6")));

        assertEquals(MoveStatus.DONE, t2.getMoveStatus());

        final MoveTransition t3 = t2.getTransitionBoard().makeMove(MoveFactory.createMove(t2.getTransitionBoard(), Board.getCoordinateAtPosition("b1"),
                Board.getCoordinateAtPosition("d2")));

        assertEquals(MoveStatus.DONE, t3.getMoveStatus());

        final MoveTransition t4 = t3.getTransitionBoard().makeMove(MoveFactory.createMove(t3.getTransitionBoard(), Board.getCoordinateAtPosition("e7"),
                Board.getCoordinateAtPosition("e5")));

        assertEquals(MoveStatus.DONE, t4.getMoveStatus());

        final MoveTransition t5 = t4.getTransitionBoard().makeMove(MoveFactory.createMove(t4.getTransitionBoard(), Board.getCoordinateAtPosition("d4"),
                Board.getCoordinateAtPosition("e5")));

        assertEquals(MoveStatus.DONE, t5.getMoveStatus());

        final MoveTransition t6 = t5.getTransitionBoard().makeMove(MoveFactory.createMove(t5.getTransitionBoard(), Board.getCoordinateAtPosition("f6"),
                Board.getCoordinateAtPosition("g4")));


        assertEquals(MoveStatus.DONE, t6.getMoveStatus());

        final MoveTransition t7 = t6.getTransitionBoard().makeMove(MoveFactory.createMove(t6.getTransitionBoard(), Board.getCoordinateAtPosition("h2"),
                Board.getCoordinateAtPosition("h3")));

        assertEquals(MoveStatus.DONE, t7.getMoveStatus());

        final MoveTransition t8 = t7.getTransitionBoard().makeMove(MoveFactory.createMove(t7.getTransitionBoard(), Board.getCoordinateAtPosition("g4"),
                Board.getCoordinateAtPosition("e3")));

        assertEquals(MoveStatus.DONE, t8.getMoveStatus());

        final MoveTransition t9 = t8.getTransitionBoard().makeMove(MoveFactory.createMove(t8.getTransitionBoard(), Board.getCoordinateAtPosition("f2"),
                Board.getCoordinateAtPosition("e3")));

        assertEquals(MoveStatus.DONE, t9.getMoveStatus());

        final MoveTransition t10 = t9.getTransitionBoard().makeMove(MoveFactory.createMove(t9.getTransitionBoard(), Board.getCoordinateAtPosition("d8"),
                Board.getCoordinateAtPosition("h4")));

        assertEquals(MoveStatus.DONE, t10.getMoveStatus());

        final MoveTransition t11 = t10.getTransitionBoard().makeMove(MoveFactory.createMove(t10.getTransitionBoard(), Board.getCoordinateAtPosition("g2"),
                Board.getCoordinateAtPosition("g3")));

        assertEquals(MoveStatus.DONE, t11.getMoveStatus());

        final MoveTransition t12 = t11.getTransitionBoard().makeMove(MoveFactory.createMove(t11.getTransitionBoard(), Board.getCoordinateAtPosition("h4"),
                Board.getCoordinateAtPosition("g3")));

        assertEquals(MoveStatus.DONE, t12.getMoveStatus());
        assertTrue(t12.getTransitionBoard().currentPlayer().isInCheckMate());

    }

    @Test
    public void testOlympicGame() {

        final Board board = Board.createStandardBoard();
        final MoveTransition t1 = board.makeMove(MoveFactory.createMove(board, Board.getCoordinateAtPosition("e2"),
                Board.getCoordinateAtPosition("e4")));

        assertEquals(MoveStatus.DONE, t1.getMoveStatus());

        final MoveTransition t2 = t1.getTransitionBoard().makeMove(MoveFactory.createMove(t1.getTransitionBoard(), Board.getCoordinateAtPosition("c7"),
                Board.getCoordinateAtPosition("c6")));

        assertEquals(MoveStatus.DONE, t2.getMoveStatus());

        final MoveTransition t3 = t2.getTransitionBoard().makeMove(MoveFactory.createMove(t2.getTransitionBoard(), Board.getCoordinateAtPosition("g1"),
                Board.getCoordinateAtPosition("f3")));

        assertEquals(MoveStatus.DONE, t3.getMoveStatus());

        final MoveTransition t4 = t3.getTransitionBoard().makeMove(MoveFactory.createMove(t3.getTransitionBoard(), Board.getCoordinateAtPosition("d7"),
                Board.getCoordinateAtPosition("d5")));

        assertEquals(MoveStatus.DONE, t4.getMoveStatus());

        final MoveTransition t5 = t4.getTransitionBoard().makeMove(MoveFactory.createMove(t4.getTransitionBoard(), Board.getCoordinateAtPosition("b1"),
                Board.getCoordinateAtPosition("c3")));

        assertEquals(MoveStatus.DONE, t5.getMoveStatus());

        final MoveTransition t6 = t5.getTransitionBoard().makeMove(MoveFactory.createMove(t5.getTransitionBoard(), Board.getCoordinateAtPosition("d5"),
                Board.getCoordinateAtPosition("e4")));

        assertEquals(MoveStatus.DONE, t6.getMoveStatus());

        final MoveTransition t7 = t6.getTransitionBoard().makeMove(MoveFactory.createMove(t6.getTransitionBoard(), Board.getCoordinateAtPosition("c3"),
                Board.getCoordinateAtPosition("e4")));

        assertEquals(MoveStatus.DONE, t7.getMoveStatus());

        final MoveTransition t8 = t7.getTransitionBoard().makeMove(MoveFactory.createMove(t7.getTransitionBoard(), Board.getCoordinateAtPosition("b8"),
                Board.getCoordinateAtPosition("d7")));

        assertEquals(MoveStatus.DONE, t8.getMoveStatus());

        final MoveTransition t9 = t8.getTransitionBoard().makeMove(MoveFactory.createMove(t8.getTransitionBoard(), Board.getCoordinateAtPosition("d1"),
                Board.getCoordinateAtPosition("e2")));

        assertEquals(MoveStatus.DONE, t9.getMoveStatus());

        final MoveTransition t10 = t9.getTransitionBoard().makeMove(MoveFactory.createMove(t9.getTransitionBoard(), Board.getCoordinateAtPosition("g8"),
                Board.getCoordinateAtPosition("f6")));

        assertEquals(MoveStatus.DONE, t10.getMoveStatus());

        final MoveTransition t11 = t10.getTransitionBoard().makeMove(MoveFactory.createMove(t10.getTransitionBoard(), Board.getCoordinateAtPosition("e4"),
                Board.getCoordinateAtPosition("d6")));

        assertEquals(MoveStatus.DONE, t11.getMoveStatus());
        assertTrue(t11.getTransitionBoard().currentPlayer().isInCheckMate());

    }

    @Test
    public void testAnotherGame() {

        final Board board = Board.createStandardBoard();
        final MoveTransition t1 = board.makeMove(MoveFactory.createMove(board, Board.getCoordinateAtPosition("e2"), Board.getCoordinateAtPosition("e4")));

        assertEquals(MoveStatus.DONE, t1.getMoveStatus());

        final MoveTransition t2 = t1.getTransitionBoard().makeMove(MoveFactory.createMove(t1.getTransitionBoard(), Board.getCoordinateAtPosition("e7"),
                Board.getCoordinateAtPosition("e5")));

        assertEquals(MoveStatus.DONE, t2.getMoveStatus());

        final MoveTransition t3 = t2.getTransitionBoard().makeMove(MoveFactory.createMove(t2.getTransitionBoard(), Board.getCoordinateAtPosition("g1"),
                Board.getCoordinateAtPosition("f3")));

        assertEquals(MoveStatus.DONE, t3.getMoveStatus());

        final MoveTransition t4 = t3.getTransitionBoard().makeMove(MoveFactory.createMove(t3.getTransitionBoard(), Board.getCoordinateAtPosition("b8"),
                Board.getCoordinateAtPosition("c6")));

        assertEquals(MoveStatus.DONE, t4.getMoveStatus());

        final MoveTransition t5 = t4.getTransitionBoard().makeMove(MoveFactory.createMove(t4.getTransitionBoard(), Board.getCoordinateAtPosition("f1"),
                Board.getCoordinateAtPosition("c4")));

        assertEquals(MoveStatus.DONE, t5.getMoveStatus());

        final MoveTransition t6 = t5.getTransitionBoard().makeMove(MoveFactory.createMove(t5.getTransitionBoard(), Board.getCoordinateAtPosition("c6"),
                Board.getCoordinateAtPosition("d4")));

        assertEquals(MoveStatus.DONE, t6.getMoveStatus());

        final MoveTransition t7 = t6.getTransitionBoard().makeMove(MoveFactory.createMove(t6.getTransitionBoard(), Board.getCoordinateAtPosition("f3"),
                Board.getCoordinateAtPosition("e5")));

        assertEquals(MoveStatus.DONE, t7.getMoveStatus());

        final MoveTransition t8 = t7.getTransitionBoard().makeMove(MoveFactory.createMove(t7.getTransitionBoard(), Board.getCoordinateAtPosition("d8"),
                Board.getCoordinateAtPosition("g5")));

        assertEquals(MoveStatus.DONE, t8.getMoveStatus());

        final MoveTransition t9 = t8.getTransitionBoard().makeMove(MoveFactory.createMove(t8.getTransitionBoard(), Board.getCoordinateAtPosition("e5"),
                Board.getCoordinateAtPosition("f7")));

        assertEquals(MoveStatus.DONE, t9.getMoveStatus());

        final MoveTransition t10 = t9.getTransitionBoard().makeMove(MoveFactory.createMove(t9.getTransitionBoard(), Board.getCoordinateAtPosition("g5"),
                Board.getCoordinateAtPosition("g2")));

        assertEquals(MoveStatus.DONE, t10.getMoveStatus());

        final MoveTransition t11 = t10.getTransitionBoard().makeMove(MoveFactory.createMove(t10.getTransitionBoard(), Board.getCoordinateAtPosition("h1"),
                Board.getCoordinateAtPosition("f1")));

        assertEquals(MoveStatus.DONE, t11.getMoveStatus());

        final MoveTransition t12 = t11.getTransitionBoard().makeMove(MoveFactory.createMove(t11.getTransitionBoard(), Board.getCoordinateAtPosition("g2"),
                Board.getCoordinateAtPosition("e4")));

        assertEquals(MoveStatus.DONE, t12.getMoveStatus());

        final MoveTransition t13 = t12.getTransitionBoard().makeMove(MoveFactory.createMove(t12.getTransitionBoard(), Board.getCoordinateAtPosition("c4"),
                Board.getCoordinateAtPosition("e2")));

        assertEquals(MoveStatus.DONE, t13.getMoveStatus());

        final MoveTransition t14 = t13.getTransitionBoard().makeMove(MoveFactory.createMove(t13.getTransitionBoard(), Board.getCoordinateAtPosition("d4"),
                Board.getCoordinateAtPosition("f3")));

        assertEquals(MoveStatus.DONE, t14.getMoveStatus());
        assertTrue(t14.getTransitionBoard().currentPlayer().isInCheckMate());

    }

    @Test
    public void testSmotheredMate() {

        final Board board = Board.createStandardBoard();
        final MoveTransition t1 = board.makeMove(MoveFactory.createMove(board, Board.getCoordinateAtPosition("e2"), Board.getCoordinateAtPosition("e4")));

        assertEquals(MoveStatus.DONE, t1.getMoveStatus());

        final MoveTransition t2 = t1.getTransitionBoard().makeMove(MoveFactory.createMove(t1.getTransitionBoard(), Board.getCoordinateAtPosition("e7"),
                Board.getCoordinateAtPosition("e5")));

        assertEquals(MoveStatus.DONE, t2.getMoveStatus());

        final MoveTransition t3 = t2.getTransitionBoard().makeMove(MoveFactory.createMove(t2.getTransitionBoard(), Board.getCoordinateAtPosition("g1"),
                Board.getCoordinateAtPosition("e2")));

        assertEquals(MoveStatus.DONE, t3.getMoveStatus());

        final MoveTransition t4 = t3.getTransitionBoard().makeMove(MoveFactory.createMove(t3.getTransitionBoard(), Board.getCoordinateAtPosition("b8"),
                Board.getCoordinateAtPosition("c6")));

        assertEquals(MoveStatus.DONE, t4.getMoveStatus());

        final MoveTransition t5 = t4.getTransitionBoard().makeMove(MoveFactory.createMove(t4.getTransitionBoard(), Board.getCoordinateAtPosition("b1"),
                Board.getCoordinateAtPosition("c3")));

        assertEquals(MoveStatus.DONE, t4.getMoveStatus());

        final MoveTransition t6 = t5.getTransitionBoard().makeMove(MoveFactory.createMove(t5.getTransitionBoard(), Board.getCoordinateAtPosition("c6"),
                Board.getCoordinateAtPosition("d4")));

        assertEquals(MoveStatus.DONE, t6.getMoveStatus());

        final MoveTransition t7 = t6.getTransitionBoard().makeMove(MoveFactory.createMove(t6.getTransitionBoard(), Board.getCoordinateAtPosition("g2"),
                Board.getCoordinateAtPosition("g3")));

        assertEquals(MoveStatus.DONE, t7.getMoveStatus());

        final MoveTransition t8 = t7.getTransitionBoard().makeMove(MoveFactory.createMove(t7.getTransitionBoard(), Board.getCoordinateAtPosition("d4"),
                Board.getCoordinateAtPosition("f3")));

        assertEquals(MoveStatus.DONE, t8.getMoveStatus());
        assertTrue(t8.getTransitionBoard().currentPlayer().isInCheckMate());

    }

    @Test
    public void testHippopotamusMate() {

        final Board board = Board.createStandardBoard();
        final MoveTransition t1 = board.makeMove(MoveFactory.createMove(board, Board.getCoordinateAtPosition("e2"), Board.getCoordinateAtPosition("e4")));

        assertEquals(MoveStatus.DONE, t1.getMoveStatus());

        final MoveTransition t2 = t1.getTransitionBoard().makeMove(MoveFactory.createMove(t1.getTransitionBoard(), Board.getCoordinateAtPosition("e7"),
                Board.getCoordinateAtPosition("e5")));

        assertEquals(MoveStatus.DONE, t2.getMoveStatus());

        final MoveTransition t3 = t2.getTransitionBoard().makeMove(MoveFactory.createMove(t2.getTransitionBoard(), Board.getCoordinateAtPosition("g1"),
                Board.getCoordinateAtPosition("e2")));

        assertEquals(MoveStatus.DONE, t3.getMoveStatus());

        final MoveTransition t4 = t3.getTransitionBoard().makeMove(MoveFactory.createMove(t3.getTransitionBoard(), Board.getCoordinateAtPosition("d8"),
                Board.getCoordinateAtPosition("h4")));

        assertEquals(MoveStatus.DONE, t4.getMoveStatus());

        final MoveTransition t5 = t4.getTransitionBoard().makeMove(MoveFactory.createMove(t4.getTransitionBoard(), Board.getCoordinateAtPosition("b1"),
                Board.getCoordinateAtPosition("c3")));

        assertEquals(MoveStatus.DONE, t5.getMoveStatus());

        final MoveTransition t6 = t5.getTransitionBoard().makeMove(MoveFactory.createMove(t5.getTransitionBoard(), Board.getCoordinateAtPosition("b8"),
                Board.getCoordinateAtPosition("c6")));

        assertEquals(MoveStatus.DONE, t6.getMoveStatus());

        final MoveTransition t7 = t6.getTransitionBoard().makeMove(MoveFactory.createMove(t6.getTransitionBoard(), Board.getCoordinateAtPosition("g2"),
                Board.getCoordinateAtPosition("g3")));

        assertEquals(MoveStatus.DONE, t7.getMoveStatus());

        final MoveTransition t8 = t7.getTransitionBoard().makeMove(MoveFactory.createMove(t7.getTransitionBoard(), Board.getCoordinateAtPosition("h4"),
                Board.getCoordinateAtPosition("g5")));


        assertEquals(MoveStatus.DONE, t8.getMoveStatus());

        final MoveTransition t9 = t8.getTransitionBoard().makeMove(MoveFactory.createMove(t8.getTransitionBoard(), Board.getCoordinateAtPosition("d2"),
                Board.getCoordinateAtPosition("d4")));

        assertEquals(MoveStatus.DONE, t9.getMoveStatus());

        final MoveTransition t10 = t9.getTransitionBoard().makeMove(MoveFactory.createMove(t9.getTransitionBoard(), Board.getCoordinateAtPosition("c6"),
                Board.getCoordinateAtPosition("d4")));

        assertEquals(MoveStatus.DONE, t10.getMoveStatus());

        final MoveTransition t11 = t10.getTransitionBoard().makeMove(MoveFactory.createMove(t10.getTransitionBoard(), Board.getCoordinateAtPosition("c1"),
                Board.getCoordinateAtPosition("g5")));

        assertEquals(MoveStatus.DONE, t11.getMoveStatus());

        final MoveTransition t12 = t11.getTransitionBoard().makeMove(MoveFactory.createMove(t11.getTransitionBoard(), Board.getCoordinateAtPosition("d4"),
                Board.getCoordinateAtPosition("f3")));

        assertEquals(MoveStatus.DONE, t12.getMoveStatus());
        assertTrue(t12.getTransitionBoard().currentPlayer().isInCheckMate());

    }

    @Test
    public void testBlackburneShillingMate() {

        final Board board = Board.createStandardBoard();

        final MoveTransition t1 = board.makeMove(MoveFactory.createMove(board, Board.getCoordinateAtPosition("e2"), Board.getCoordinateAtPosition("e4")));

        assertEquals(MoveStatus.DONE, t1.getMoveStatus());

        final MoveTransition t2 = t1.getTransitionBoard().makeMove(MoveFactory.createMove(t1.getTransitionBoard(), Board.getCoordinateAtPosition("e7"),
                Board.getCoordinateAtPosition("e5")));

        assertEquals(MoveStatus.DONE, t2.getMoveStatus());

        final MoveTransition t3 = t2.getTransitionBoard().makeMove(MoveFactory.createMove(t2.getTransitionBoard(), Board.getCoordinateAtPosition("g1"),
                Board.getCoordinateAtPosition("f3")));

        assertEquals(MoveStatus.DONE, t3.getMoveStatus());

        final MoveTransition t4 = t3.getTransitionBoard().makeMove(MoveFactory.createMove(t3.getTransitionBoard(), Board.getCoordinateAtPosition("b8"),
                Board.getCoordinateAtPosition("c6")));

        assertEquals(MoveStatus.DONE, t4.getMoveStatus());

        final MoveTransition t5 = t4.getTransitionBoard().makeMove(MoveFactory.createMove(t4.getTransitionBoard(), Board.getCoordinateAtPosition("f1"),
                Board.getCoordinateAtPosition("c4")));

        assertEquals(MoveStatus.DONE, t5.getMoveStatus());

        final MoveTransition t6 = t5.getTransitionBoard().makeMove(MoveFactory.createMove(t5.getTransitionBoard(), Board.getCoordinateAtPosition("c6"),
                Board.getCoordinateAtPosition("d4")));

        assertEquals(MoveStatus.DONE, t6.getMoveStatus());

        final MoveTransition t7 = t6.getTransitionBoard().makeMove(MoveFactory.createMove(t6.getTransitionBoard(), Board.getCoordinateAtPosition("f3"),
                Board.getCoordinateAtPosition("e5")));

        assertEquals(MoveStatus.DONE, t7.getMoveStatus());

        final MoveTransition t8 = t7.getTransitionBoard().makeMove(MoveFactory.createMove(t7.getTransitionBoard(), Board.getCoordinateAtPosition("d8"),
                Board.getCoordinateAtPosition("g5")));

        assertEquals(MoveStatus.DONE, t8.getMoveStatus());

        final MoveTransition t9 = t8.getTransitionBoard().makeMove(MoveFactory.createMove(t8.getTransitionBoard(), Board.getCoordinateAtPosition("e5"),
                Board.getCoordinateAtPosition("f7")));

        assertEquals(MoveStatus.DONE, t9.getMoveStatus());

        final MoveTransition t10 = t9.getTransitionBoard().makeMove(MoveFactory.createMove(t9.getTransitionBoard(), Board.getCoordinateAtPosition("g5"),
                Board.getCoordinateAtPosition("g2")));

        assertEquals(MoveStatus.DONE, t10.getMoveStatus());

        final MoveTransition t11 = t10.getTransitionBoard().makeMove(MoveFactory.createMove(t10.getTransitionBoard(), Board.getCoordinateAtPosition("h1"),
                Board.getCoordinateAtPosition("f1")));

        assertEquals(MoveStatus.DONE, t11.getMoveStatus());

        final MoveTransition t12 = t11.getTransitionBoard().makeMove(MoveFactory.createMove(t11.getTransitionBoard(), Board.getCoordinateAtPosition("g2"),
                Board.getCoordinateAtPosition("e4")));

        assertEquals(MoveStatus.DONE, t12.getMoveStatus());

        final MoveTransition t13 = t12.getTransitionBoard().makeMove(MoveFactory.createMove(t12.getTransitionBoard(), Board.getCoordinateAtPosition("c4"),
                Board.getCoordinateAtPosition("e2")));

        assertEquals(MoveStatus.DONE, t13.getMoveStatus());

        final MoveTransition t14 = t13.getTransitionBoard().makeMove(MoveFactory.createMove(t13.getTransitionBoard(), Board.getCoordinateAtPosition("d4"),
                Board.getCoordinateAtPosition("f3")));

        assertEquals(MoveStatus.DONE, t14.getMoveStatus());
        assertTrue(t14.getTransitionBoard().currentPlayer().isInCheckMate());

    }

    @Test
    public void testAnastasiaMate() {

        final Builder builder = new Builder();

        // Black Layout
        builder.setPiece(0, new Rook(Alliance.BLACK, 0));
        builder.setPiece(5, new Rook(Alliance.BLACK, 5));
        builder.setPiece(8, new Pawn(Alliance.BLACK, 8));
        builder.setPiece(9, new Pawn(Alliance.BLACK, 9));
        builder.setPiece(10, new Pawn(Alliance.BLACK, 10));
        builder.setPiece(13, new Pawn(Alliance.BLACK, 13));
        builder.setPiece(14, new Pawn(Alliance.BLACK, 14));
        builder.setPiece(15, new King(Alliance.BLACK, 15));
        // White Layout
        builder.setPiece(12, new Knight(Alliance.WHITE, 12));
        builder.setPiece(27, new Rook(Alliance.WHITE, 27));
        builder.setPiece(41, new Pawn(Alliance.WHITE, 41));
        builder.setPiece(48, new Pawn(Alliance.WHITE, 48));
        builder.setPiece(53, new Pawn(Alliance.WHITE, 53));
        builder.setPiece(54, new Pawn(Alliance.WHITE, 54));
        builder.setPiece(55, new Pawn(Alliance.WHITE, 55));
        builder.setPiece(62, new King(Alliance.WHITE, 62));

        builder.setMoveMaker(Alliance.WHITE);

        final Board board = builder.build();
        final MoveTransition t1 = board.makeMove(MoveFactory.createMove(board, Board.getCoordinateAtPosition("d5"), Board.getCoordinateAtPosition("h5")));

        assertEquals(MoveStatus.DONE, t1.getMoveStatus());
        assertTrue(t1.getTransitionBoard().currentPlayer().isInCheckMate());
    }

    @Test
    public void testTwoBishopMate() {

        final Builder builder = new Builder();

        builder.setPiece(7, new King(Alliance.BLACK, 7));
        builder.setPiece(8, new Pawn(Alliance.BLACK, 8));
        builder.setPiece(10, new Pawn(Alliance.BLACK, 10));
        builder.setPiece(15, new Pawn(Alliance.BLACK, 15));
        builder.setPiece(17, new Pawn(Alliance.BLACK, 17));
        // White Layout
        builder.setPiece(40, new Bishop(Alliance.WHITE, 40));
        builder.setPiece(48, new Bishop(Alliance.WHITE, 48));
        builder.setPiece(53, new King(Alliance.WHITE, 53));

        builder.setMoveMaker(Alliance.WHITE);

        final Board board = builder.build();
        final MoveTransition t1 = board.makeMove(MoveFactory.createMove(board, Board.getCoordinateAtPosition("a3"), Board.getCoordinateAtPosition("b2")));

        assertEquals(MoveStatus.DONE, t1.getMoveStatus());
        assertTrue(t1.getTransitionBoard().currentPlayer().isInCheckMate());
    }

    @Test
    public void testQueenRookMate() {

        final Builder builder = new Builder();

        // Black Layout
        builder.setPiece(5, new King(Alliance.BLACK, 5));
        // White Layout
        builder.setPiece(9, new Rook(Alliance.WHITE, 9));
        builder.setPiece(16, new Queen(Alliance.WHITE, 16));
        builder.setPiece(59, new King(Alliance.WHITE, 59));

        builder.setMoveMaker(Alliance.WHITE);

        final Board board = builder.build();
        final MoveTransition t1 = board.makeMove(MoveFactory.createMove(board, Board.getCoordinateAtPosition("a6"), Board.getCoordinateAtPosition("a8")));

        assertEquals(MoveStatus.DONE, t1.getMoveStatus());
        assertTrue(t1.getTransitionBoard().currentPlayer().isInCheckMate());

    }

    @Test
    public void testQueenKnightMate() {

        final Builder builder = new Builder();

        // Black Layout
        builder.setPiece(4, new King(Alliance.BLACK, 4));
        // White Layout
        builder.setPiece(15, new Queen(Alliance.WHITE, 15));
        builder.setPiece(29, new Knight(Alliance.WHITE, 29));
        builder.setPiece(55, new Pawn(Alliance.WHITE, 55));
        builder.setPiece(60, new King(Alliance.WHITE, 60));

        builder.setMoveMaker(Alliance.WHITE);

        final Board board = builder.build();
        final MoveTransition t1 = board.makeMove(MoveFactory.createMove(board, Board.getCoordinateAtPosition("h7"), Board.getCoordinateAtPosition("e7")));

        assertEquals(MoveStatus.DONE, t1.getMoveStatus());
        assertTrue(t1.getTransitionBoard().currentPlayer().isInCheckMate());

    }

    @Test
    public void testBackRankMate() {

        final Builder builder = new Builder();

        // Black Layout
        builder.setPiece(4, new King(Alliance.BLACK, 4));
        builder.setPiece(18, new Rook(Alliance.BLACK, 18));

        // White Layout
        builder.setPiece(53, new Pawn(Alliance.WHITE, 53));
        builder.setPiece(54, new Pawn(Alliance.WHITE, 54));
        builder.setPiece(55, new Pawn(Alliance.WHITE, 55));
        builder.setPiece(62, new King(Alliance.WHITE, 62));

        builder.setMoveMaker(Alliance.BLACK);

        final Board board = builder.build();

        final MoveTransition t1 = board.makeMove(MoveFactory.createMove(board, Board.getCoordinateAtPosition("c6"),
                Board.getCoordinateAtPosition("c1")));

        assertEquals(MoveStatus.DONE, t1.getMoveStatus());
        assertTrue(t1.getTransitionBoard().currentPlayer().isInCheckMate());

    }

}
