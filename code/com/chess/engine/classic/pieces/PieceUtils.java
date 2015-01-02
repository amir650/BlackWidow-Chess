package com.chess.engine.classic.pieces;

import com.chess.engine.classic.Alliance;
import com.chess.engine.classic.board.BoardUtils;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;

public class PieceUtils {

    public static final Table<Alliance, Integer, Queen> ALL_POSSIBLE_QUEENS = PieceUtils.createAllPossibleMovedQueens();
    public static final Table<Alliance, Integer, Rook> ALL_POSSIBLE_ROOKS = PieceUtils.createAllPossibleMovedRooks();
    public static final Table<Alliance, Integer, Knight> ALL_POSSIBLE_KNIGHTS = PieceUtils.createAllPossibleMovedKnights();
    public static final Table<Alliance, Integer, Bishop> ALL_POSSIBLE_BISHOPS = PieceUtils.createAllPossibleMovedBishops();
    public static final Table<Alliance, Integer, Pawn> ALL_POSSIBLE_PAWNS = PieceUtils.createAllPossibleMovedPawns();

    private PieceUtils() {
        throw new RuntimeException("Not instantiable!");
    }

    static Table<Alliance, Integer, Pawn> createAllPossibleMovedPawns() {

        final ImmutableTable.Builder<Alliance, Integer, Pawn> pieces = ImmutableTable.builder();

        for(int i = 0; i < BoardUtils.NUM_TILES; i++) {
            pieces.put(Alliance.WHITE, i, new Pawn(Alliance.WHITE, i, false));
        }

        for(int i = 0; i < BoardUtils.NUM_TILES; i++) {
            pieces.put(Alliance.BLACK, i, new Pawn(Alliance.BLACK, i, false));
        }

        return pieces.build();

    }

    static Table<Alliance, Integer, Knight> createAllPossibleMovedKnights() {

        final ImmutableTable.Builder<Alliance, Integer, Knight> pieces = ImmutableTable.builder();

        for(int i = 0; i < BoardUtils.NUM_TILES; i++) {
            pieces.put(Alliance.WHITE, i, new Knight(Alliance.WHITE, i, false));
        }

        for(int i = 0; i < BoardUtils.NUM_TILES; i++) {
            pieces.put(Alliance.BLACK, i, new Knight(Alliance.BLACK, i, false));
        }

        return pieces.build();

    }

    static Table<Alliance, Integer, Bishop> createAllPossibleMovedBishops() {

        final ImmutableTable.Builder<Alliance, Integer, Bishop> pieces = ImmutableTable.builder();

        for(int i = 0; i < BoardUtils.NUM_TILES; i++) {
            pieces.put(Alliance.WHITE, i, new Bishop(Alliance.WHITE, i, false));
        }

        for(int i = 0; i < BoardUtils.NUM_TILES; i++) {
            pieces.put(Alliance.BLACK, i, new Bishop(Alliance.BLACK, i, false));
        }

        return pieces.build();

    }

    static Table<Alliance, Integer, Rook> createAllPossibleMovedRooks() {

        final ImmutableTable.Builder<Alliance, Integer, Rook> pieces = ImmutableTable.builder();

        for(int i = 0; i < BoardUtils.NUM_TILES; i++) {
            pieces.put(Alliance.WHITE, i, new Rook(Alliance.WHITE, i, false));
        }

        for(int i = 0; i < BoardUtils.NUM_TILES; i++) {
            pieces.put(Alliance.BLACK, i, new Rook(Alliance.BLACK, i, false));
        }

        return pieces.build();

    }

    static Table<Alliance, Integer, Queen> createAllPossibleMovedQueens() {

        final ImmutableTable.Builder<Alliance, Integer, Queen> pieces = ImmutableTable.builder();

        for(int i = 0; i < BoardUtils.NUM_TILES; i++) {
            pieces.put(Alliance.WHITE, i, new Queen(Alliance.WHITE, i, false));
        }

        for(int i = 0; i < BoardUtils.NUM_TILES; i++) {
            pieces.put(Alliance.BLACK, i, new Queen(Alliance.BLACK, i, false));
        }

        return pieces.build();

    }

}
