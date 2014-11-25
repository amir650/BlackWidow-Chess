package com.chess.com.chess.pgn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.board.Move;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

public class FirstSearch implements BookSearcher {
    @Override
    public Move search(final Book book,
                       final Board board,
                       final String gameText) {
        final List<String> searchMoves = getMoves(gameText);
        final List<Game> matchGames = new ArrayList<>();
        for(final Game game : book.getGames()) {
            final List<String> bookGameMoves = game.getMoves();
            if(movesMatch(bookGameMoves, searchMoves)) {
                matchGames.add(game);
            }
        }

        final Multiset multiSet = HashMultiset.create();

        multiSet.addAll(matchGames);

        return Move.NULL_MOVE;
    }

    private static List<String> getMoves(final String gameText) {
        try {
            return PGNUtilities.processMoveText(gameText);
        }
        catch (ParsePGNException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    private static boolean movesMatch(final List<String> bookMoves,
                                      final List<String> searchMoves) {

        if(searchMoves.isEmpty()) {
            return true;
        }

        final List<String> adjusted = adjustBookMoves(bookMoves, searchMoves);
        return adjusted.equals(searchMoves);
    }

    private static List<String> adjustBookMoves(final List<String> bookMoves,
                                                final List<String> searchMoves) {
        return bookMoves.subList(0, searchMoves.size());
    }
}
