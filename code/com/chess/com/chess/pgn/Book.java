package com.chess.com.chess.pgn;

import java.util.ArrayList;
import java.util.List;

import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.board.Move;
import com.google.common.collect.ImmutableList;

public class Book {

    final List<Game> bookGames;
    final BookSearcher bookSearcher;

    private Book(final Builder builder) {
        this.bookGames = ImmutableList.copyOf(builder.bookGames);
        this.bookSearcher = new FirstSearch();
    }

    public List<Game> getGames() {
        return bookGames;
    }

    public Move findBookMove(final Board board,
                             final String gameText) {
        return this.bookSearcher.search(this, board, gameText);
    }

    public static class Builder {

        List<Game> bookGames;

        public Builder() {
            this.bookGames = new ArrayList<>();
        }

        public Builder addGame(final Game game) {
            this.bookGames.add(game);
            return this;
        }

        public Book build() {
            return new Book(this);
        }

    }


}
