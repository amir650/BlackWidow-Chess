package com.chess.com.chess.pgn;

import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.board.Move;

public interface BookSearcher {

    public Move search(final Book book, final Board board, final String gameText);

}
