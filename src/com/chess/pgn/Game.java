package com.chess.pgn;

import java.util.List;

public interface Game {
    boolean isValid();
    PGNGameTags getTags();
    List<MoveRecord> getMoves();
    void saveGame(MySqlGamePersistence persistence);
}
