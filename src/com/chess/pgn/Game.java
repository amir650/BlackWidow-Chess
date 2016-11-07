package com.chess.pgn;

import java.util.List;

public abstract class Game implements Playable {

    protected final PGNGameTags tags;
    protected final List<String> moves;
    protected final String winner;

    Game(final PGNGameTags tags,
         final List<String> moves,
         final String outcome) {
        this.tags = tags;
        this.moves = moves;
        this.winner = calculateWinner(outcome);
    }

    @Override
    public String toString() {
        return this.tags.toString();
    }

    public List<String> getMoves() {
        return this.moves;
    }

    public String getWinner() {
        return this.winner;
    }

    private static String calculateWinner(final String gameOutcome) {
        if(gameOutcome.equals("1-0")) {
            return "White";
        }
        if(gameOutcome.equals("0-1")) {
            return "Black";
        }
        if(gameOutcome.equals("1/2-1/2")) {
            return "Tie";
        }
        return "None";
    }

}
