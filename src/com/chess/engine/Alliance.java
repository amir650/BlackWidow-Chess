package com.chess.engine;

import com.chess.engine.board.BoardUtils;
import com.chess.engine.player.BlackPlayer;
import com.chess.engine.player.Player;
import com.chess.engine.player.WhitePlayer;

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
            return BoardUtils.FIRST_ROW.get(position);
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
            return BoardUtils.WHITE_PAWN_PREFERRED_COORDINATES[position];
        }

        @Override
        public int knightBonus(final int position) {
            return BoardUtils.WHITE_KNIGHT_PREFERRED_COORDINATES[position];
        }

        @Override
        public int bishopBonus(final int position) {
            return BoardUtils.WHITE_BISHOP_PREFERRED_COORDINATES[position];
        }

        @Override
        public int rookBonus(final int position) {
            return BoardUtils.WHITE_ROOK_PREFERRED_COORDINATES[position];
        }

        @Override
        public int queenBonus(final int position) {
            return BoardUtils.WHITE_QUEEN_PREFERRED_COORDINATES[position];
        }

        @Override
        public int kingBonus(final int position) {
            return BoardUtils.WHITE_KING_PREFERRED_COORDINATES[position];
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
            return BoardUtils.EIGHTH_ROW.get(position);
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
            return BoardUtils.BLACK_PAWN_PREFERRED_COORDINATES[position];
        }

        @Override
        public int knightBonus(final int position) {
            return BoardUtils.BLACK_KNIGHT_PREFERRED_COORDINATES[position];
        }

        @Override
        public int bishopBonus(final int position) {
            return BoardUtils.BLACK_BISHOP_PREFERRED_COORDINATES[position];
        }

        @Override
        public int rookBonus(final int position) {
            return BoardUtils.BLACK_ROOK_PREFERRED_COORDINATES[position];
        }

        @Override
        public int queenBonus(final int position) {
            return BoardUtils.BLACK_QUEEN_PREFERRED_COORDINATES[position];
        }

        @Override
        public int kingBonus(final int position) {
            return BoardUtils.BLACK_KING_PREFERRED_COORDINATES[position];
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

    private static final int UP_DIRECTION = -1;

    private static final int DOWN_DIRECTION = 1;

}