package com.chess.com.chess.pgn;

import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.board.Move;
import com.chess.engine.classic.player.Player;

public interface PGNPersistence {

    public void persistGame(Game game);

    public Move getNextBestMove(Board board, Player player, String gameText);

}
