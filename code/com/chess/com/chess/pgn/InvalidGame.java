package com.chess.com.chess.pgn;

import java.util.List;

public class InvalidGame extends Game {

    public InvalidGame(final PGNGameTags tags,
                       final List<String> moves,
                       final String outcome) {
        super(tags, moves, outcome);
    }

    @Override
    public String toString() {
        return "Invalid Game " + this.tags;
    }

    @Override
    public boolean isValid() {
        return false;
    }
}
