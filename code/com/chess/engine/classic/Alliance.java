package com.chess.engine.classic;

import com.chess.engine.classic.board.Board;

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
            return DOWN_DIRECTION;
        }

        @Override
        public boolean isPawnPromotionSquare(final int position) {
            return Board.FIRST_ROW[position];
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
            return 0;
        }

        @Override
        public int rookBonus(final int position) {
            return 0;
        }

        @Override
        public int queenBonus(final int position) {
            return WHITE_QUEEN_PREFERRED_COORDINATES[position];
        }

        @Override
        public int kingBonus(final int position) {
            return 0;
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
            return UP_DIRECTION;
        }

        @Override
        public boolean isPawnPromotionSquare(final int position) {
            return Board.EIGHTH_ROW[position];
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
            return 0;
        }

        @Override
        public int rookBonus(final int position) {
            return 0;
        }

        @Override
        public int queenBonus(final int position) {
            return BLACK_QUEEN_PREFERRED_COORDINATES[position];
        }

        @Override
        public int kingBonus(final int position) {
            return 0;
        }
    };

    public abstract int getDirection();

    public abstract int pawnBonus(int position);

    public abstract int knightBonus(int position);

    public abstract int bishopBonus(int position);

    public abstract int rookBonus(int position);

    public abstract int queenBonus(int position);

    public abstract int kingBonus(int position);

    public abstract boolean isWhite();

    public abstract boolean isBlack();

    public abstract boolean isPawnPromotionSquare(int position);

    private final static int[] WHITE_PAWN_PREFERRED_COORDINATES = {
        0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 7, 7, 0, 0, 0,
        0, 0, 0, 20, 20, 0, 0, 0,
        0, 0, 5, 7, 7, 5, 0, 0,
        0, 0, -5, -10, -10, -5, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0
    };

    private final static int[] BLACK_PAWN_PREFERRED_COORDINATES = {
        0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, -5, -10, -10, -5, 0, 0,
        0, 0, 5, 7, 7, 5, 0, 0,
        0, 0, 0, 20, 20, 0, 0, 0,
        0, 0, 0, 7, 7, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0
    };

    private final static int[] WHITE_KNIGHT_PREFERRED_COORDINATES = {
       -10, -5, 0, 0, 0, 0, -5, -10,
        -5, 5, 10, 10, 10, 10, 5, -5,
        -5, 5, 15, 15, 15, 15, 5, -5,
        -5, 5, 15, 15, 15, 15, 5, -5,
        -5, 0, 10, 10, 10, 10, 5, -5,
        -5, 0, 5, 10, 10, 5, 0, -5,
        -10, 0, 0, 0, 0, 0, 0, -10,
        -20, -5, -5, -5, -5, -5, -5, -20
    };

    private final static int[] BLACK_KNIGHT_PREFERRED_COORDINATES = {
        -20, -5, -5, -5, -5, -5, -5, -20,
        -10, 0, 0, 0, 0, 0, 0, -10,
        -5, 0, 5, 10, 10, 5, 0, -5,
        -5, 0, 10, 10, 10, 10, 5, -5,
        -5, 5, 15, 15, 15, 15, 5, -5,
        -5, 5, 15, 15, 15, 15, 5, -5,
        -5, 5, 10, 10, 10, 10, 5, -5,
        -10, -5, 0, 0, 0, 0, -5, -10
    };

    private final static int[] WHITE_QUEEN_PREFERRED_COORDINATES = {
        -10, 0, 0, 0, 0, 0, 0, -10,
        -10, 0, 0, 0, 0, 0, 0, -10,
        -10, 0, 5, 5, 5, 5, 0, -10,
        -10, 0, 5, 10, 10, 5, 0, -10,
        -10, 0, 5, 10, 10, 5, 0, -10,
        -10, 0, 5, 5, 5, 5, 0, -10,
        -10, 0, 0, 0, 0, 0, 0, -10,
        -10, 0, 0, 0, 0, 0, 0, -10
    };

    private final static int[] BLACK_QUEEN_PREFERRED_COORDINATES = {
        -10, 0, 0, 0, 0, 0, 0, -10,
        -10, 0, 0, 0, 0, 0, 0, -10,
        -10, 0, 5, 5, 5, 5, 0, -10,
        -10, 0, 5, 10, 10, 5, 0, -10,
        -10, 0, 5, 10, 10, 5, 0, -10,
        -10, 0, 5, 5, 5, 5, 0, -10,
        -10, 0, 0, 0, 0, 0, 0, -10,
        -10, 0, 0, 0, 0, 0, 0, -10
    };

    private final static int[] WHITE_KING_PREFERRED_COORDINATES = {
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0
    };

    private final static int[] BLACK_KING_PREFERRED_COORDINATES = {
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0
    };

    private static final int UP_DIRECTION = -1;

    private static final int DOWN_DIRECTION = 1;

}