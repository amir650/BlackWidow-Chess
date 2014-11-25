package com.chess.com.chess.pgn;

import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

public abstract class Game implements Playable {

    protected final Map<String, String> tags;
    protected final List<String> moves;
    protected final int gameId;

    Game(final int gameId,
         final Map<String, String> tags,
         final List<String> moves) {
        this.gameId = gameId;
        this.tags = ImmutableMap.copyOf(tags);
        this.moves = ImmutableList.copyOf(moves);
    }

    public List<String> getMoves() {
        return this.moves;
    }

    public enum GameStatus {
        PLAYED_SUCCESSFULLY,
        PLAYED_WITH_ERRORS
    }

}
