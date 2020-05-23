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

    private final static int[] WHITE_PAWN_PREFERRED_COORDINATES = {
            0,  0,  0,  0,  0,  0,  0,  0,
            75, 75, 75, 75, 75, 75, 75, 75,
            25, 25, 29, 29, 29, 29, 25, 25,
            5,  5, 10, 55, 55, 10,  5,  5,
            0,  0,  0, 20, 20,  0,  0,  0,
            5, -5,-10,  0,  0,-10, -5,  5,
            5, 10, 10,-20,-20, 10, 10,  5,
            0,  0,  0,  0,  0,  0,  0,  0
    };

    private final static int[] BLACK_PAWN_PREFERRED_COORDINATES = {
            0,  0,  0,  0,  0,  0,  0,  0,
            5, 10, 10,-20,-20, 10, 10,  5,
            5, -5,-10,  0,  0,-10, -5,  5,
            0,  0,  0, 20, 20,  0,  0,  0,
            5,  5, 10, 55, 55, 10,  5,  5,
            25, 25, 29, 29, 29, 29, 25, 25,
            75, 75, 75, 75, 75, 75, 75, 75,
            0,  0,  0,  0,  0,  0,  0,  0
    };

    private final static int[] WHITE_KNIGHT_PREFERRED_COORDINATES = {
            -50,-40,-30,-30,-30,-30,-40,-50,
            -40,-20,  0,  0,  0,  0,-20,-40,
            -30,  0, 10, 15, 15, 10,  0,-30,
            -30,  5, 15, 20, 20, 15,  5,-30,
            -30,  0, 15, 20, 20, 15,  0,-30,
            -30,  5, 10, 15, 15, 10,  5,-30,
            -40,-20,  0,  5,  5,  0,-20,-40,
            -50,-40,-30,-30,-30,-30,-40,-50
    };

    private final static int[] BLACK_KNIGHT_PREFERRED_COORDINATES = {
            -50,-40,-30,-30,-30,-30,-40,-50,
            -40,-20,  0,  5,  5,  0,-20,-40,
            -30,  5, 10, 15, 15, 10,  5,-30,
            -30,  0, 15, 20, 20, 15,  0,-30,
            -30,  5, 15, 20, 20, 15,  5,-30,
            -30,  0, 10, 15, 15, 10,  0,-30,
            -40,-20,  0,  0,  0,  0,-20,-40,
            -50,-40,-30,-30,-30,-30,-40,-50,
    };

    private final static int[] WHITE_BISHOP_PREFERRED_COORDINATES = {
            -20,-10,-10,-10,-10,-10,-10,-20,
            -10,  0,  0,  0,  0,  0,  0,-10,
            -10,  0,  5, 10, 10,  5,  0,-10,
            -10,  5,  5, 10, 10,  5,  5,-10,
            -10,  0, 10, 10, 10, 10,  0,-10,
            -10, 10, 10, 10, 10, 10, 10,-10,
            -10,  5,  0,  0,  0,  0,  5,-10,
            -20,-10,-10,-10,-10,-10,-10,-20
    };

    private final static int[] BLACK_BISHOP_PREFERRED_COORDINATES = {
            -20,-10,-10,-10,-10,-10,-10,-20,
            -10,  5,  0,  0,  0,  0,  5,-10,
            -10, 10, 10, 10, 10, 10, 10,-10,
            -10,  0, 10, 10, 10, 10,  0,-10,
            -10,  5,  5, 10, 10,  5,  5,-10,
            -10,  0,  5, 10, 10,  5,  0,-10,
            -10,  0,  0,  0,  0,  0,  0,-10,
            -20,-10,-10,-10,-10,-10,-10,-20,
    };

    private final static int[] WHITE_ROOK_PREFERRED_COORDINATES = {
            0,  0,  0,  0,  0,  0,  0,  0,
            5, 20, 20, 20, 20, 20, 20,  5,
            -5,  0,  0,  0,  0,  0,  0, -5,
            -5,  0,  0,  0,  0,  0,  0, -5,
            -5,  0,  0,  0,  0,  0,  0, -5,
            -5,  0,  0,  0,  0,  0,  0, -5,
            -5,  0,  0,  0,  0,  0,  0, -5,
            0,  0,  0,  5,  5,  0,  0,  0
    };

    private final static int[] BLACK_ROOK_PREFERRED_COORDINATES = {
            0,  0,  0,  5,  5,  0,  0,  0,
            -5,  0,  0,  0,  0,  0,  0, -5,
            -5,  0,  0,  0,  0,  0,  0, -5,
            -5,  0,  0,  0,  0,  0,  0, -5,
            -5,  0,  0,  0,  0,  0,  0, -5,
            -5,  0,  0,  0,  0,  0,  0, -5,
            5, 20, 20, 20, 20, 20, 20,  5,
            0,  0,  0,  0,  0,  0,  0,  0,
    };

    private final static int[] WHITE_QUEEN_PREFERRED_COORDINATES = {
            -20,-10,-10, -5, -5,-10,-10,-20,
            -10,  0,  0,  0,  0,  0,  0,-10,
            -10,  0,  5,  5,  5,  5,  0,-10,
             -5,  0,  5,  5,  5,  5,  0, -5,
              0,  0,  5,  5,  5,  5,  0, -5,
            -10,  5,  5,  5,  5,  5,  0,-10,
            -10,  0,  5,  0,  0,  0,  0,-10,
            -20,-10,-10, -5, -5,-10,-10,-20
    };

    private final static int[] BLACK_QUEEN_PREFERRED_COORDINATES = {
            -20,-10,-10, -5, -5,-10,-10,-20,
            -10,  0,  5,  0,  0,  0,  0,-10,
            -10,  5,  5,  5,  5,  5,  0,-10,
              0,  0,  5,  5,  5,  5,  0, -5,
              0,  0,  5,  5,  5,  5,  0, -5,
            -10,  0,  5,  5,  5,  5,  0,-10,
            -10,  0,  0,  0,  0,  0,  0,-10,
            -20,-10,-10, -5, -5,-10,-10,-20
    };

    private final static int[] WHITE_KING_PREFERRED_COORDINATES = {
            -30,-40,-40,-50,-50,-40,-40,-30,
            -30,-40,-40,-50,-50,-40,-40,-30,
            -30,-40,-40,-50,-50,-40,-40,-30,
            -30,-40,-40,-50,-50,-40,-40,-30,
            -20,-30,-30,-40,-40,-30,-30,-20,
            -10,-20,-20,-20,-20,-20,-20,-10,
             20, 20,  0,  0,  0,  0, 20, 20,
             20, 30, 10,  0,  0, 10, 30, 20
    };

    private final static int[] BLACK_KING_PREFERRED_COORDINATES = {
             20, 30, 10,  0,  0, 10, 30, 20,
             20, 20,  0,  0,  0,  0, 20, 20,
            -10,-20,-20,-20,-20,-20,-20,-10,
            -20,-30,-30,-40,-40,-30,-30,-20,
            -30,-40,-40,-50,-50,-40,-40,-30,
            -30,-40,-40,-50,-50,-40,-40,-30,
            -30,-40,-40,-50,-50,-40,-40,-30,
            -30,-40,-40,-50,-50,-40,-40,-30
    };

    private static final int UP_DIRECTION = -1;

    private static final int DOWN_DIRECTION = 1;

}