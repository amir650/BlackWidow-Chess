package com.chess.engine.classic;

import com.chess.engine.classic.board.BoardUtils;
import com.chess.engine.classic.player.BlackPlayer;
import com.chess.engine.classic.player.Player;
import com.chess.engine.classic.player.WhitePlayer;

public enum Alliance {

    WHITE() {

        @Override
        public boolean isWhite() {
            return true;
        }

        @Override
        public boolean isBlack() {
            return false;
        }

        @Override
        public int getDirection() {
            return UP_DIRECTION;
        }

        @Override
        public int getOppositeDirection() {
            return DOWN_DIRECTION;
        }

        @Override
        public boolean isPawnPromotionSquare(final int position) {
            return BoardUtils.INSTANCE.FIRST_ROW.get(position);
        }

        @Override
        public Player choosePlayerByAlliance(final WhitePlayer whitePlayer,
                                             final BlackPlayer blackPlayer) {
            return whitePlayer;
        }

        @Override
        public String toString() {
            return "White";
        }

        @Override
        public int pawnBonus(final int position) {
            return WHITE_PAWN_PREFERRED_COORDINATES[position];
        }

        @Override
        public int knightBonus(final int position) {
            return WHITE_KNIGHT_PREFERRED_COORDINATES[position];
        }

        @Override
        public int bishopBonus(final int position) {
            return WHITE_BISHOP_PREFERRED_COORDINATES[position];
        }

        @Override
        public int rookBonus(final int position) {
            return WHITE_ROOK_PREFERRED_COORDINATES[position];
        }

        @Override
        public int queenBonus(final int position) {
            return WHITE_QUEEN_PREFERRED_COORDINATES[position];
        }

        @Override
        public int kingBonus(final int position) {
            return WHITE_KING_PREFERRED_COORDINATES[position];
        }

    },
    BLACK() {

        @Override
        public boolean isWhite() {
            return false;
        }

        @Override
        public boolean isBlack() {
            return true;
        }

        @Override
        public int getDirection() {
            return DOWN_DIRECTION;
        }

        @Override
        public int getOppositeDirection() {
            return UP_DIRECTION;
        }

        @Override
        public boolean isPawnPromotionSquare(final int position) {
            return BoardUtils.INSTANCE.EIGHTH_ROW.get(position);
        }

        @Override
        public Player choosePlayerByAlliance(final WhitePlayer whitePlayer,
                                             final BlackPlayer blackPlayer) {
            return blackPlayer;
        }

        @Override
        public String toString() {
            return "Black";
        }

        @Override
        public int pawnBonus(final int position) {
            return BLACK_PAWN_PREFERRED_COORDINATES[position];
        }

        @Override
        public int knightBonus(final int position) {
            return BLACK_KNIGHT_PREFERRED_COORDINATES[position];
        }

        @Override
        public int bishopBonus(final int position) {
            return BLACK_BISHOP_PREFERRED_COORDINATES[position];
        }

        @Override
        public int rookBonus(final int position) {
            return BLACK_ROOK_PREFERRED_COORDINATES[position];
        }

        @Override
        public int queenBonus(final int position) {
            return BLACK_QUEEN_PREFERRED_COORDINATES[position];
        }

        @Override
        public int kingBonus(final int position) {
            return BLACK_KING_PREFERRED_COORDINATES[position];
        }
    };

    public abstract int getDirection();

    public abstract int getOppositeDirection();

    public abstract int pawnBonus(int position);

    public abstract int knightBonus(int position);

    public abstract int bishopBonus(int position);

    public abstract int rookBonus(int position);

    public abstract int queenBonus(int position);

    public abstract int kingBonus(int position);

    public abstract boolean isWhite();

    public abstract boolean isBlack();

    public abstract boolean isPawnPromotionSquare(int position);

    public abstract Player choosePlayerByAlliance(final WhitePlayer whitePlayer, final BlackPlayer blackPlayer);

    private static final int[] WHITE_PAWN_PREFERRED_COORDINATES = {
            0,  0,  0,  0,  0,  0,  0,  0,
            27, 27, 27, 27, 27, 27, 27, 27,
            9,  9, 12, 18, 18, 12,  9,  9,
            3,  3,  6, 12, 12,  6,  3,  3,
            2,  2,  3,  6,  6,  3,  2,  2,
            0,  0,  0, -3, -3,  0,  0,  0,
            2, -2, -3,  0,  0, -3, -2,  2,
            0,  0,  0,  0,  0,  0,  0,  0
    };

    private static final int[] BLACK_PAWN_PREFERRED_COORDINATES = {
            0,  0,  0,  0,  0,  0,  0,  0,
            2, -2, -3,  0,  0, -3, -2,  2,
            0,  0,  0, -3, -3,  0,  0,  0,
            2,  2,  3,  6,  6,  3,  2,  2,
            3,  3,  6, 12, 12,  6,  3,  3,
            9,  9, 12, 18, 18, 12,  9,  9,
            27, 27, 27, 27, 27, 27, 27, 27,
            0,  0,  0,  0,  0,  0,  0,  0
    };

    private static final int[] WHITE_KNIGHT_PREFERRED_COORDINATES = {
            -30,-25,-20,-20,-20,-20,-25,-30,
            -25,-12,  0,  3,  3,  0,-12,-25,
            -20,  3,  6,  9,  9,  6,  3,-20,
            -20,  3,  9, 12, 12,  9,  3,-20,
            -20,  3,  9, 12, 12,  9,  3,-20,
            -20,  3,  6,  9,  9,  6,  3,-20,
            -25,-12,  0,  0,  0,  0,-12,-25,
            -30,-25,-20,-20,-20,-20,-25,-30
    };

    private static final int[] BLACK_KNIGHT_PREFERRED_COORDINATES = {
            -30, -25, -20, -20, -20, -20, -25, -30,
            -25, -12,   0,   0,   0,   0, -12, -25,
            -20,   3,   6,   9,   9,   6,   3, -20,
            -20,   3,   9,  12,  12,   9,   3, -20,
            -20,   3,   9,  12,  12,   9,   3, -20,
            -20,   3,   6,   9,   9,   6,   3, -20,
            -25, -12,   0,   3,   3,   0, -12, -25,
            -30, -25, -20, -20, -20, -20, -25, -30
    };

    private static final int[] WHITE_BISHOP_PREFERRED_COORDINATES = {
            -12, -6, -6, -6, -6, -6, -6,-12,
            -6,  0,  0,  0,  0,  0,  0, -6,
            -6,  0,  3,  6,  6,  3,  0, -6,
            -6,  3,  3,  6,  6,  3,  3, -6,
            -6,  0,  6,  9,  9,  6,  0, -6,
            -6,  6,  6,  6,  6,  6,  6, -6,
            -6,  3,  0,  0,  0,  0,  3, -6,
            -12, -6, -6, -6, -6, -6, -6,-12
    };
    private static final int[] BLACK_BISHOP_PREFERRED_COORDINATES = {
            -12, -6, -6, -6, -6, -6, -6,-12,
            -6,  3,  0,  0,  0,  0,  3, -6,
            -6,  6,  6,  6,  6,  6,  6, -6,
            -6,  0,  6,  9,  9,  6,  0, -6,
            -6,  3,  6,  9,  9,  6,  3, -6,
            -6,  0,  6,  6,  6,  6,  0, -6,
            -6,  0,  0,  0,  0,  0,  0, -6,
            -12, -6, -6, -6, -6, -6, -6,-12
    };

    /*  ROOK  – scaled 75 %  */
    private static final int[] WHITE_ROOK_PREFERRED_COORDINATES = {
            0,  0,  0,  0,  0,  0,  0,  0,
            4, 15, 15, 15, 15, 15, 15,  4,
            -4,  0,  0,  0,  0,  0,  0, -4,
            -4,  0,  0,  0,  0,  0,  0, -4,
            -4,  0,  0,  0,  0,  0,  0, -4,
            -4,  0,  0,  0,  0,  0,  0, -4,
            -4,  0,  0,  0,  0,  0,  0, -4,
            0,  0,  0,  4,  4,  0,  0,  0
    };
    private static final int[] BLACK_ROOK_PREFERRED_COORDINATES = {
            0,  0,  0,  4,  4,  0,  0,  0,
            -4,  0,  0,  0,  0,  0,  0, -4,
            -4,  0,  0,  0,  0,  0,  0, -4,
            -4,  0,  0,  0,  0,  0,  0, -4,
            -4,  0,  0,  0,  0,  0,  0, -4,
            -4,  0,  0,  0,  0,  0,  0, -4,
            4, 15, 15, 15, 15, 15, 15,  4,
            0,  0,  0,  0,  0,  0,  0,  0
    };

    /*  QUEEN  – scaled 50 %  */
    private static final int[] WHITE_QUEEN_PREFERRED_COORDINATES = {
            -10, -5, -5, -2, -2, -5, -5,-10,
            -5,  0,  0,  0,  0,  0,  0, -5,
            -5,  0,  2,  2,  2,  2,  0, -5,
            -2,  0,  2,  5,  5,  2,  0, -2,
            -2,  0,  2,  5,  5,  2,  0, -2,
            -5,  0,  2,  2,  2,  2,  0, -5,
            -5,  0,  0,  0,  0,  0,  0, -5,
            -10, -5, -5, -2, -2, -5, -5,-10
    };
    private static final int[] BLACK_QUEEN_PREFERRED_COORDINATES = {
            -10, -5, -5, -2, -2, -5, -5,-10,
            -5,  0,  2,  0,  0,  0,  0, -5,
            -5,  2,  2,  2,  2,  2,  0, -5,
            -2,  0,  2,  5,  5,  2,  0, -2,
            -2,  0,  2,  5,  5,  2,  0, -2,
            -5,  0,  2,  2,  2,  2,  0, -5,
            -5,  0,  0,  0,  0,  0,  0, -5,
            -10, -5, -5, -2, -2, -5, -5,-10
    };

    /*  KING  – opening table scaled 80 %  */
    private static final int[] WHITE_KING_PREFERRED_COORDINATES = {
            -40,-24,-24,-24,-24,-24,-24,-40,
            -24,-24,  0,  0,  0,  0,-24,-24,
            -24, -8, 16, 24, 24, 16, -8,-24,
            -24, -8, 24, 32, 32, 24, -8,-24,
            -24, -8, 24, 32, 32, 24, -8,-24,
            -24, -8, 16, 24, 24, 16, -8,-24,
            -24,-16, -8,  0,  0, -8,-16,-24,
            -40,-32,-24,-16,-16,-24,-32,-40
    };
    private static final int[] BLACK_KING_PREFERRED_COORDINATES = {
            -40,-32,-24,-16,-16,-24,-32,-40,
            -24,-16, -8,  0,  0, -8,-16,-24,
            -24, -8, 16, 24, 24, 16, -8,-24,
            -24, -8, 24, 32, 32, 24, -8,-24,
            -24, -8, 24, 32, 32, 24, -8,-24,
            -24, -8, 16, 24, 24, 16, -8,-24,
            -24,-24,  0,  0,  0,  0,-24,-24,
            -40,-24,-24,-24,-24,-24,-24,-40
    };

    private static final int UP_DIRECTION = -1;

    private static final int DOWN_DIRECTION = 1;

}