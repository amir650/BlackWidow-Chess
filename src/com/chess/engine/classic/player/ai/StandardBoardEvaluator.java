package com.chess.engine.classic.player.ai;

import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.pieces.Piece;
import com.chess.engine.classic.player.Player;
import com.chess.engine.classic.player.ai.KingSafetyAnalyzer.KingDistance;
import com.google.common.annotations.VisibleForTesting;

public final class StandardBoardEvaluator
        implements BoardEvaluator {

    private final static int CHECK_MATE_BONUS = 10000;
    private final static int CHECK_BONUS = 50;
    private final static int CASTLED_BONUS = 65;
    private final static int CASTLE_CAPABLE_BONUS = 25;
    private final static int MOBILITY_BONUS = 1;

    @Override
    public int evaluate(final Board board,
                        final int depth) {
        return score(board.whitePlayer(), depth) - score(board.blackPlayer(), depth);
    }

    @VisibleForTesting
    private static int score(final Player player,
                             final int depth) {
        return mobility(player) +
               checkmate(player, depth) +
               castle(player) +
               pieceValueAndLocation(player) +
               pawnStructure(player);
    }

    private static int pieceValueAndLocation(final Player player) {
        int pieceValuationScore = 0;
        for (final Piece piece : player.getActivePieces()) {
            pieceValuationScore += piece.getPieceValue() + piece.locationBonus();
        }
        return pieceValuationScore;
    }

    private static int mobility(final Player player) {
        return MOBILITY_BONUS * player.getLegalMoves().size();
    }

    private static int checkmate(final Player player,
                                 final int depth) {
        return player.getOpponent().isInCheckMate() ? CHECK_MATE_BONUS  * depthBonus(depth) : check(player, depth);
    }

    private static int check(final Player player,
                             final int depth) {
        return player.getOpponent().isInCheck() ? CHECK_BONUS : 0;
    }

    private static int depthBonus(final int depth) {
        return depth == 0 ? 1 : 100 * depth;
    }

    private static int castleCapable(final Player player) {
        return (player.isKingSideCastleCapable() || player.isQueenSideCastleCapable()) ? CASTLE_CAPABLE_BONUS : 0;
    }

    private static int castle(final Player player) {
        return player.isCastled() ? CASTLED_BONUS : castleCapable(player);
    }

    private static int pawnStructure(final Player player) {
        return PawnStructureAnalyzer.get().pawnStructureScore(player);
    }

    private static int kingSafety(final Player player) {
        final KingDistance kingDistance = KingSafetyAnalyzer.get().calculateKingTropism(player);
        return ((kingDistance.getEnemyPiece().getPieceValue() / 100) * kingDistance.getDistance());
    }

    private static int rookStructure(final Board board, final Player player) {
        return RookStructureAnalyzer.get().rookStructureScore(board, player);
    }

}
