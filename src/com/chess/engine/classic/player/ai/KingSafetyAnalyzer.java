package com.chess.engine.classic.player.ai;

import com.chess.engine.classic.board.BoardUtils;
import com.chess.engine.classic.board.Move;
import com.chess.engine.classic.pieces.Piece;
import com.chess.engine.classic.player.Player;

import java.util.ArrayList;
import java.util.Collections;
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
        final List<List<Boolean>> columns = new ArrayList<>();
        columns.add(BoardUtils.INSTANCE.FIRST_COLUMN);
        columns.add(BoardUtils.INSTANCE.SECOND_COLUMN);
        columns.add(BoardUtils.INSTANCE.THIRD_COLUMN);
        columns.add(BoardUtils.INSTANCE.FOURTH_COLUMN);
        columns.add(BoardUtils.INSTANCE.FIFTH_COLUMN);
        columns.add(BoardUtils.INSTANCE.SIXTH_COLUMN);
        columns.add(BoardUtils.INSTANCE.SEVENTH_COLUMN);
        columns.add(BoardUtils.INSTANCE.EIGHTH_COLUMN);
        return Collections.unmodifiableList(columns);
    }

    public int gptKingSafety(final Player player) {
        int score = 0;
        final int kingSquare = player.getPlayerKing().getPiecePosition();
        if (!player.isCastled()) {
            final boolean kingInCenter = BoardUtils.INSTANCE.FOURTH_COLUMN.get(kingSquare) ||
                                         BoardUtils.INSTANCE.FIFTH_COLUMN.get(kingSquare);
            if (kingInCenter) {
                score -= CENTER_KING_PENALTY;
            } else {
                score -= UNCASTLED_KING_PENALTY;
            }
        }
        return score;
    }

    private static final int PAWN_SHIELD_PENALTY = 12; // You can tune this value

//    public static int pawnShieldPenalty(final Player player) {
//        int penalty = 0;
//        final int kingSquare = player.getPlayerKing().getPiecePosition();
//        final boolean isWhite = player.isWhite();
//        final int kingFile = kingSquare % 8;
//
//        boolean ksc = player.getPlayerKing().isCastled()
//
//        // Castling detection (assumes king on g1/c1/g8/c8)
//        boolean kingsideCastled = (isWhite && kingSquare == 6) || (!isWhite && kingSquare == 62);
//        boolean queensideCastled = (isWhite && kingSquare == 2) || (!isWhite && kingSquare == 58);
//
//        int shieldRank = isWhite ? 1 : 6; // 2nd rank for white, 7th for black
//        int[] filesToCheck;
//
//        if (kingsideCastled) {
//            filesToCheck = new int[]{5, 6, 7}; // f, g, h files
//        } else if (queensideCastled) {
//            filesToCheck = new int[]{0, 1, 2}; // a, b, c files
//        } else {
//            // King not castled: check files around king
//            filesToCheck = new int[] {
//                    Math.max(kingFile - 1, 0),
//                    kingFile,
//                    Math.min(kingFile + 1, 7)
//            };
//        }
//
//        for (int file : filesToCheck) {
//            int shieldSquare = shieldRank * 8 + file;
//            Piece p = player.getBoard().getPiece(shieldSquare);
//            if (p == null || p.getPieceType() != Piece.PieceType.PAWN || p.getPieceAlliance() != player.getAlliance()) {
//                penalty -= PAWN_SHIELD_PENALTY;
//            }
//        }
//        return penalty;
//    }


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
        if(BoardUtils.INSTANCE.FIRST_COLUMN.get(coordinate)) {
            return 1;
        } else if(BoardUtils.INSTANCE.SECOND_COLUMN.get(coordinate)) {
            return 2;
        } else if(BoardUtils.INSTANCE.THIRD_COLUMN.get(coordinate)) {
            return 3;
        } else if(BoardUtils.INSTANCE.FOURTH_COLUMN.get(coordinate)) {
            return 4;
        } else if(BoardUtils.INSTANCE.FIFTH_COLUMN.get(coordinate)) {
            return 5;
        } else if(BoardUtils.INSTANCE.SIXTH_COLUMN.get(coordinate)) {
            return 6;
        } else if(BoardUtils.INSTANCE.SEVENTH_COLUMN.get(coordinate)) {
            return 7;
        } else if(BoardUtils.INSTANCE.EIGHTH_COLUMN.get(coordinate)) {
            return 8;
        }
        throw new RuntimeException("should not reach here!");
    }

    private static int getRank(final int coordinate) {
        if(BoardUtils.INSTANCE.FIRST_ROW.get(coordinate)) {
            return 1;
        } else if(BoardUtils.INSTANCE.SECOND_ROW.get(coordinate)) {
            return 2;
        } else if(BoardUtils.INSTANCE.THIRD_ROW.get(coordinate)) {
            return 3;
        } else if(BoardUtils.INSTANCE.FOURTH_ROW.get(coordinate)) {
            return 4;
        } else if(BoardUtils.INSTANCE.FIFTH_ROW.get(coordinate)) {
            return 5;
        } else if(BoardUtils.INSTANCE.SIXTH_ROW.get(coordinate)) {
            return 6;
        } else if(BoardUtils.INSTANCE.SEVENTH_ROW.get(coordinate)) {
            return 7;
        } else if(BoardUtils.INSTANCE.EIGHTH_ROW.get(coordinate)) {
            return 8;
        }
        throw new RuntimeException("should not reach here!");
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
            return (this.enemyPiece.getPieceValue() / 100) * this.distance;
        }

    }

}
