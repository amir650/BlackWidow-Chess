package com.chess.engine.classic.pieces;

import com.chess.engine.classic.Alliance;
import com.chess.engine.classic.board.BoardUtils;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

enum PieceUtils {

    INSTANCE;

    private final Map<Alliance, Map<Integer, Queen>> ALL_POSSIBLE_QUEENS = createAllPossibleMovedQueens();
    private final Map<Alliance, Map<Integer, Rook>> ALL_POSSIBLE_ROOKS = createAllPossibleMovedRooks();
    private final Map<Alliance, Map<Integer, Knight>> ALL_POSSIBLE_KNIGHTS = createAllPossibleMovedKnights();
    private final Map<Alliance, Map<Integer, Bishop>> ALL_POSSIBLE_BISHOPS = createAllPossibleMovedBishops();
    private final Map<Alliance, Map<Integer, Pawn>> ALL_POSSIBLE_PAWNS = createAllPossibleMovedPawns();

    Pawn getMovedPawn(final Alliance alliance, final int destinationCoordinate) {
        return ALL_POSSIBLE_PAWNS.get(alliance).get(destinationCoordinate);
    }

    Knight getMovedKnight(final Alliance alliance, final int destinationCoordinate) {
        return ALL_POSSIBLE_KNIGHTS.get(alliance).get(destinationCoordinate);
    }

    Bishop getMovedBishop(final Alliance alliance, final int destinationCoordinate) {
        return ALL_POSSIBLE_BISHOPS.get(alliance).get(destinationCoordinate);
    }

    Rook getMovedRook(final Alliance alliance, final int destinationCoordinate) {
        return ALL_POSSIBLE_ROOKS.get(alliance).get(destinationCoordinate);
    }

    Queen getMovedQueen(final Alliance alliance, final int destinationCoordinate) {
        return ALL_POSSIBLE_QUEENS.get(alliance).get(destinationCoordinate);
    }

    private static Map<Alliance, Map<Integer, Pawn>> createAllPossibleMovedPawns() {
        Map<Alliance, Map<Integer, Pawn>> pieces = new EnumMap<>(Alliance.class);
        for (Alliance alliance : Alliance.values()) {
            Map<Integer, Pawn> map = new HashMap<>();
            for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
                map.put(i, new Pawn(alliance, i, false));
            }
            pieces.put(alliance, map);
        }
        return pieces;
    }

    private static Map<Alliance, Map<Integer, Knight>> createAllPossibleMovedKnights() {
        Map<Alliance, Map<Integer, Knight>> pieces = new EnumMap<>(Alliance.class);
        for (Alliance alliance : Alliance.values()) {
            Map<Integer, Knight> map = new HashMap<>();
            for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
                map.put(i, new Knight(alliance, i, false));
            }
            pieces.put(alliance, map);
        }
        return pieces;
    }

    private static Map<Alliance, Map<Integer, Bishop>> createAllPossibleMovedBishops() {
        Map<Alliance, Map<Integer, Bishop>> pieces = new EnumMap<>(Alliance.class);
        for (Alliance alliance : Alliance.values()) {
            Map<Integer, Bishop> map = new HashMap<>();
            for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
                map.put(i, new Bishop(alliance, i, false));
            }
            pieces.put(alliance, map);
        }
        return pieces;
    }

    private static Map<Alliance, Map<Integer, Rook>> createAllPossibleMovedRooks() {
        Map<Alliance, Map<Integer, Rook>> pieces = new EnumMap<>(Alliance.class);
        for (Alliance alliance : Alliance.values()) {
            Map<Integer, Rook> map = new HashMap<>();
            for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
                map.put(i, new Rook(alliance, i, false));
            }
            pieces.put(alliance, map);
        }
        return pieces;
    }

    private static Map<Alliance, Map<Integer, Queen>> createAllPossibleMovedQueens() {
        Map<Alliance, Map<Integer, Queen>> pieces = new EnumMap<>(Alliance.class);
        for (Alliance alliance : Alliance.values()) {
            Map<Integer, Queen> map = new HashMap<>();
            for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
                map.put(i, new Queen(alliance, i, false));
            }
            pieces.put(alliance, map);
        }
        return pieces;
    }
}
