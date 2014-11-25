package com.chess.com.chess.pgn;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.common.collect.ImmutableList;

public class GameFactory {

    private static AtomicInteger GAME_COUNTER = new AtomicInteger();

    public static Game createGame(final Map<String, String> tags,
                                      final String gameText) {

        try {
            return new ValidGame(GAME_COUNTER.incrementAndGet(), tags, ImmutableList.copyOf(PGNUtilities.processMoveText(gameText)));
        } catch(final ParsePGNException e) {
            return new InvalidGame(GAME_COUNTER.incrementAndGet(), tags, Collections.<String>emptyList());
        }
    }
}