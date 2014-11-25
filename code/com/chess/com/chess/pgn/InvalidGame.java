package com.chess.com.chess.pgn;

import java.util.List;
import java.util.Map;

import com.chess.engine.classic.board.Board;

public class InvalidGame extends Game {

    public InvalidGame(final int gameId,
                       final Map<String, String> tags,
                       final List<String> moves) {
        super(gameId, tags, moves);
    }

    @Override
    public GameStatus play(final Board board) {
        throw new RuntimeException("Cannot play!");
    }

    @Override
    public String toString() {
        return "Invalid Game " +gameId + "";
    }

    @Override
    public boolean isValid() {
        return false;
    }
}
