package com.chess.com.chess.pgn;

import com.chess.com.chess.pgn.Game.GameStatus;
import com.chess.engine.classic.board.Board;

public interface Playable {

    public GameStatus play(Board board);

    public boolean isValid();

}
