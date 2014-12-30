package com.chess.engine.classic.player.ai;

import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.pieces.Piece;
import com.chess.engine.classic.player.Player;

public class SimpleBoardEvaluator
        implements BoardEvaluator {

    private final static int CHECK_MATE_BONUS = 10000;
    private final static int CASTLE_BONUS = 50;

    @Override
    public int evaluate(final Board board) {
        return scorePlayer(board.whitePlayer()) - scorePlayer(board.blackPlayer());
    }

    private static int scorePlayer(final Player player) {
        int score = mobilityScore(player) + checkMateScore(player) + castleScore(player) + pawnStructureScore(player);
        for (final Piece piece : player.getActivePieces()) {
            score += piece.getPieceValue() + piece.locationBonus();
        }
        return score;
    }

    private static int mobilityScore(final Player player) {
        return player.getLegalMoves().size();
    }

    private static int checkMateScore(final Player player) {
        return player.getOpponent().isInCheckMate() ? CHECK_MATE_BONUS : 0;
    }

    private static int castleScore(final Player player) {
        return player.isCastled() ? CASTLE_BONUS : 0;
    }

    private static int pawnStructureScore(final Player player) {
        return PawnStructureAnalyzer.get().pawnStructureScore(player);
    }

}
