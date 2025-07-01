package com.chess.pgn;

import java.util.List;
import java.util.Objects;

public class InvalidGame implements Game {
    private final String reason;           // Why this game is invalid (e.g., parse error)
    private final PGNGameTags tags;        // Optionally, whatever tags could be parsed

    public InvalidGame(final String reason,
                       final PGNGameTags tags) {
        this.reason = reason;
        this.tags = tags;
    }

    @Override
    public boolean isValid() {
        return false;
    }

    @Override
    public PGNGameTags getTags() {
        return tags;
    }

    public List<MoveRecord> getMoves() {
        return null;
    }

    @Override
    public void saveGame(MySqlGamePersistence persistence) {
    }

    public String getReason() {
        return reason;
    }

    @Override
    public String toString() {
        return "InvalidGame{" +
                "reason='" + reason + '\'' +
                ", tags=" + tags +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InvalidGame)) return false;
        InvalidGame that = (InvalidGame) o;
        return Objects.equals(reason, that.reason) &&
               Objects.equals(tags, that.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reason, tags);
    }
}
