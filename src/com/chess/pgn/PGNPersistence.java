package com.chess.pgn;

import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.board.Move;
import com.chess.engine.classic.player.Player;

import java.io.IOException;

public interface PGNPersistence {

    void persistGame(Game game) throws IOException;

    Move getNextBestMove(Board board, Player player, String gameText);

}
