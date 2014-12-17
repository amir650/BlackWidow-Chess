package com.chess.com.chess.pgn;

import java.util.Iterator;
import java.util.List;

import com.chess.com.chess.pgn.PGNMoves.PGNMove;
import com.google.common.collect.ImmutableList.Builder;

public class PGNMoves implements Iterable<PGNMove> {

    private final List<PGNMove> gameMoves;

    private PGNMoves(final PGNMovesBuilder builder) {
        this.gameMoves = builder.gameMoves.build();
    }

    public List<PGNMove> getGameMoves() {
        return this.gameMoves;
    }

    @Override
    public String toString() {
        return this.gameMoves.toString()
                .replaceAll("(\\[|\\])", "");
    }

    @Override
    public Iterator<PGNMove> iterator() {
        return this.gameMoves.iterator();
    }

    public static class PGNMovesBuilder {

        final Builder<PGNMove> gameMoves;

        public PGNMovesBuilder() {
            this.gameMoves = new Builder<>();
        }

        public PGNMovesBuilder addMove(final PGNMove move) {
            this.gameMoves.add(move);
            return this;
        }

        public PGNMoves build() {
            return new PGNMoves(this);
        }

    }

    public static class PGNMove {

        private final String moveText;

        PGNMove(final String moveText) {
            this.moveText = moveText;
        }

        @Override
        public String toString() {
            return this.moveText;
        }

    }

}
