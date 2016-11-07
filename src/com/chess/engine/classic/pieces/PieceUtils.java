package com.chess.engine.classic.pieces;

import com.chess.engine.classic.Alliance;
import com.chess.engine.classic.board.BoardUtils;
import com.chess.engine.classic.board.Move;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;

enum PieceUtils {

    INSTANCE;

    private final Table<Alliance, Integer, Queen> ALL_POSSIBLE_QUEENS = PieceUtils.createAllPossibleMovedQueens();
    private final Table<Alliance, Integer, Rook> ALL_POSSIBLE_ROOKS = PieceUtils.createAllPossibleMovedRooks();
    private final Table<Alliance, Integer, Knight> ALL_POSSIBLE_KNIGHTS = PieceUtils.createAllPossibleMovedKnights();
    private final Table<Alliance, Integer, Bishop> ALL_POSSIBLE_BISHOPS = PieceUtils.createAllPossibleMovedBishops();
    private final Table<Alliance, Integer, Pawn> ALL_POSSIBLE_PAWNS = PieceUtils.createAllPossibleMovedPawns();

    Pawn getMovedPawn(final Move move) {
        return ALL_POSSIBLE_PAWNS.get(move.getMovedPiece().getPieceAllegiance(), move.getDestinationCoordinate());
    }

    Knight getMovedKnight(final Move move) {
        return ALL_POSSIBLE_KNIGHTS.get(move.getMovedPiece().getPieceAllegiance(), move.getDestinationCoordinate());
    }

    Bishop getMovedBishop(final Move move) {
        return ALL_POSSIBLE_BISHOPS.get(move.getMovedPiece().getPieceAllegiance(), move.getDestinationCoordinate());
    }

    Rook getMovedRook(final Move move) {
        return ALL_POSSIBLE_ROOKS.get(move.getMovedPiece().getPieceAllegiance(), move.getDestinationCoordinate());
    }

    Queen getMovedQueen(final Move move) {
        return ALL_POSSIBLE_QUEENS.get(move.getMovedPiece().getPieceAllegiance(), move.getDestinationCoordinate());
    }

    private static Table<Alliance, Integer, Pawn> createAllPossibleMovedPawns() {
        final ImmutableTable.Builder<Alliance, Integer, Pawn> pieces = ImmutableTable.builder();
        for(final Alliance alliance : Alliance.values()) {
            for(int i = 0; i < BoardUtils.NUM_TILES; i++) {
                pieces.put(alliance, i, new Pawn(alliance, i, false));
            }
        }
        return pieces.build();
    }

    private static Table<Alliance, Integer, Knight> createAllPossibleMovedKnights() {
        final ImmutableTable.Builder<Alliance, Integer, Knight> pieces = ImmutableTable.builder();
        for(final Alliance alliance : Alliance.values()) {
            for(int i = 0; i < BoardUtils.NUM_TILES; i++) {
                pieces.put(alliance, i, new Knight(alliance, i, false));
            }
        }
        return pieces.build();
    }

    private static Table<Alliance, Integer, Bishop> createAllPossibleMovedBishops() {
        final ImmutableTable.Builder<Alliance, Integer, Bishop> pieces = ImmutableTable.builder();
        for(final Alliance alliance : Alliance.values()) {
            for(int i = 0; i < BoardUtils.NUM_TILES; i++) {
                pieces.put(alliance, i, new Bishop(alliance, i, false));
            }
        }
        return pieces.build();
    }

    private static Table<Alliance, Integer, Rook> createAllPossibleMovedRooks() {
        final ImmutableTable.Builder<Alliance, Integer, Rook> pieces = ImmutableTable.builder();
        for(final Alliance alliance : Alliance.values()) {
            for(int i = 0; i < BoardUtils.NUM_TILES; i++) {
                pieces.put(alliance, i, new Rook(alliance, i, false));
            }
        }
        return pieces.build();
    }

    private static Table<Alliance, Integer, Queen> createAllPossibleMovedQueens() {
        final ImmutableTable.Builder<Alliance, Integer, Queen> pieces = ImmutableTable.builder();
        for(final Alliance alliance : Alliance.values()) {
            for(int i = 0; i < BoardUtils.NUM_TILES; i++) {
                pieces.put(alliance, i, new Queen(alliance, i, false));
            }
        }
        return pieces.build();
    }

}
