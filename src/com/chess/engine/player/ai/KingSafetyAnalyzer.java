package com.chess.engine.player.ai;

import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.pieces.Piece;
import com.chess.engine.player.Player;

import java.util.List;

public final class KingSafetyAnalyzer {

    private static final KingSafetyAnalyzer INSTANCE = new KingSafetyAnalyzer();
    private static final List<List<Boolean>> COLUMNS = initColumns();

    public static final int CENTER_KING_PENALTY = 30;
    public static final int UNCASTLED_KING_PENALTY = 20;

    private KingSafetyAnalyzer() {
    }

    public static KingSafetyAnalyzer get() {
        return INSTANCE;
    }

    private static List<List<Boolean>> initColumns() {
        return List.of(BoardUtils.FIRST_COLUMN,
                       BoardUtils.SECOND_COLUMN,
                       BoardUtils.THIRD_COLUMN,
                       BoardUtils.FOURTH_COLUMN,
                       BoardUtils.FIFTH_COLUMN,
                       BoardUtils.SIXTH_COLUMN,
                       BoardUtils.SEVENTH_COLUMN,
                       BoardUtils.EIGHTH_COLUMN);
    }

    public int gptKingSafety(final Player player) {
        int score = 0;
        final int kingSquare = player.getPlayerKing().getPiecePosition();
        if (!player.isCastled()) {
            final boolean kingInCenter = BoardUtils.FOURTH_COLUMN.get(kingSquare) ||
                                         BoardUtils.FIFTH_COLUMN.get(kingSquare);
            if (kingInCenter) {
                score -= CENTER_KING_PENALTY;
            } else {
                score -= UNCASTLED_KING_PENALTY;
            }
        }
        return score;
    }

    public int calculateKingTropism(final Player player) {
        final int playerKingSquare = player.getPlayerKing().getPiecePosition();
        Piece closestPiece = null;
        int closestDistance = Integer.MAX_VALUE;
        for(final Move move : player.getOpponent().getLegalMoves()) {
            final int currentDistance = calculateChebyshevDistance(playerKingSquare, move.getDestinationCoordinate());
            if(currentDistance < closestDistance) {
                closestDistance = currentDistance;
                closestPiece = move.getMovedPiece();
            }
        }
        final KingDistance kingDistance = new KingDistance(closestPiece, closestDistance);
        return kingDistance.tropismScore();
    }

    private static int calculateChebyshevDistance(final int kingTileId,
                                           final int enemyAttackTileId) {
        final int rankDistance = Math.abs(getRank(enemyAttackTileId) - getRank(kingTileId));
        final int fileDistance = Math.abs(getFile(enemyAttackTileId) - getFile(kingTileId));
        return Math.max(rankDistance, fileDistance);
    }

    private static int getFile(final int coordinate) {
        return coordinate % 8;
    }

    private static int getRank(final int coordinate) {
        return coordinate / 8;
    }

    private static class KingDistance {

        final Piece enemyPiece;
        final int distance;

        KingDistance(final Piece enemyDistance,
                     final int distance) {
            this.enemyPiece = enemyDistance;
            this.distance = distance;
        }

        public int tropismScore() {
            return -(this.enemyPiece.getPieceValue() / 100) * (8 - this.distance);
        }

    }

}
