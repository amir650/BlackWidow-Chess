package com.chess.com.chess.pgn;

import java.util.List;
import java.util.Map;

import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.board.Board.MoveStatus;
import com.chess.engine.classic.board.Move;
import com.chess.engine.classic.board.MoveTransition;

public class ValidGame
        extends Game {

    public ValidGame(final int gameId,
                     final Map<String, String> tags,
                     final List<String> moves) {
        super(gameId, tags, moves);
    }

    @Override
    public GameStatus play(final Board board) {
        Board nextBoard = board;
        for(final String moveText : this.moves) {
            final Move move = PGNUtilities.createMove(nextBoard, moveText);
            final MoveTransition transition = nextBoard.makeMove(move);
            if(transition.getMoveStatus() != MoveStatus.DONE) {
                return GameStatus.PLAYED_WITH_ERRORS;
            }
            nextBoard = transition.getTransitionBoard();
        }
        return GameStatus.PLAYED_SUCCESSFULLY;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public String toString() {
        return "Valid Game " +gameId + "";
    }

    public List<String> getMoves() {
        return this.moves;
    }

}
