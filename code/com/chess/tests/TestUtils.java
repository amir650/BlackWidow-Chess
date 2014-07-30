package com.chess.tests;

import static org.junit.Assert.assertEquals;

import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.board.Board.MoveStatus;
import com.chess.engine.classic.board.Move.MoveFactory;
import com.chess.engine.classic.board.MoveTransition;

public class TestUtils {


    public static Board makeMoves(final Board board,
                                  final String... moves) {
        Board currentBoard = board;
        for(final String move : moves) {
            final String[] moveParts = move.split("-");
            final MoveTransition moveTransition = currentBoard.makeMove(
                    MoveFactory.createMove(board, Board.getCoordinateAtPosition(moveParts[0]),
                            Board.getCoordinateAtPosition(moveParts[1])));
            assertEquals(MoveStatus.DONE, moveTransition.getMoveStatus());
            currentBoard = moveTransition.getTransitionBoard();
        }

        return currentBoard;
    }

}
