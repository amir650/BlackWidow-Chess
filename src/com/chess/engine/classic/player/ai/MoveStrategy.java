package com.chess.engine.classic.player.ai;

import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.board.Move;

public interface MoveStrategy {

    long getNumBoardsEvaluated();

    long getNumAttacks();

    long getNumCastles();

    Move execute(Board board, int depth);

}
