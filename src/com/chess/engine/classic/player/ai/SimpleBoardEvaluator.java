package com.chess.engine.classic.player.ai;

import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.pieces.Piece;
import com.chess.engine.classic.player.Player;
import com.chess.engine.classic.player.ai.KingSafetyAnalyzer.KingDistance;

public class SimpleBoardEvaluator
        implements BoardEvaluator {

    private final static int CHECK_MATE_BONUS = 10000;
    private final static int CASTLE_BONUS = 50;

    @Override
    public int evaluate(final Board board, final int depth) {
        return scorePlayer(board.whitePlayer(), depth) - scorePlayer(board.blackPlayer(), depth);
    }

    private static int scorePlayer(final Player player, final int depth) {
        int score = mobility(player) + checkmate(player, depth) + castling(player) +
                    pawnStructure(player);//+ kingSafety(player);
        for (final Piece piece : player.getActivePieces()) {
            score += piece.getPieceValue() + piece.locationBonus();
        }
        return score;
    }

    private static int mobility(final Player player) {
        return player.getLegalMoves().size();
    }

    private static int checkmate(final Player player, final int depth) {
        return player.getOpponent().isInCheckMate() ? CHECK_MATE_BONUS  * depthBonus(depth): 0;
    }

    private static int depthBonus(final int depth) {
        return depth == 0 ? 1 : 100 * depth;
    }

    private static int castling(final Player player) {
        return player.isCastled() ? CASTLE_BONUS : 0;
    }

    private static int pawnStructure(final Player player) {
        return PawnStructureAnalyzer.get().pawnStructureScore(player);
    }

    private static int kingSafety(final Player player) {
        final KingDistance kingDistance = KingSafetyAnalyzer.get().calculateKingTropism(player);
        return ((kingDistance.getEnemyPiece().getPieceValue() / 100) * kingDistance.getDistance());
    }

}
