package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.BoardUtils;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public enum PieceUtils {

    INSTANCE;

    private final Map<Alliance, Map<Integer, Queen>> ALL_POSSIBLE_MOVED_QUEENS = createAllPossibleMovedQueens(true);
    private final Map<Alliance, Map<Integer, Queen>> ALL_POSSIBLE_UNMOVED_QUEENS = createAllPossibleMovedQueens(false);
    private final Map<Alliance, Map<Integer, Rook>> ALL_POSSIBLE_MOVED_ROOKS = createAllPossibleMovedRooks(true);
    private final Map<Alliance, Map<Integer, Rook>> ALL_POSSIBLE_UNMOVED_ROOKS = createAllPossibleMovedRooks(false);
    private final Map<Alliance, Map<Integer, Knight>> ALL_POSSIBLE_MOVED_KNIGHTS = createAllPossibleMovedKnights(true);
    private final Map<Alliance, Map<Integer, Knight>> ALL_POSSIBLE_UNMOVED_KNIGHTS = createAllPossibleMovedKnights(false);
    private final Map<Alliance, Map<Integer, Bishop>> ALL_POSSIBLE_MOVED_BISHOPS = createAllPossibleMovedBishops(true);
    private final Map<Alliance, Map<Integer, Bishop>> ALL_POSSIBLE_UNMOVED_BISHOPS = createAllPossibleMovedBishops(false);
    private final Map<Alliance, Map<Integer, Pawn>> ALL_POSSIBLE_MOVED_PAWNS = createAllPossiblePawns(true);
    private final Map<Alliance, Map<Integer, Pawn>> ALL_POSSIBLE_UNMOVED_PAWNS = createAllPossiblePawns(false);
    private final Map<Alliance, Map<Integer, Map<Boolean, King>>> ALL_POSSIBLE_MOVED_KINGS = createAllPossibleMovedKings(true);
    private final Map<Alliance, Map<Integer, Map<Boolean, King>>> ALL_POSSIBLE_UNMOVED_KINGS = createAllPossibleMovedKings(false);


    public Pawn getPawn(final Alliance alliance,
                        final int destinationCoordinate,
                        final boolean hasMoved) {
        return hasMoved ? ALL_POSSIBLE_MOVED_PAWNS.get(alliance).get(destinationCoordinate) :
                          ALL_POSSIBLE_UNMOVED_PAWNS.get(alliance).get(destinationCoordinate);
    }

    public Knight getKnight(final Alliance alliance,
                            final int destinationCoordinate,
                            final boolean hasMoved) {
        return hasMoved ? ALL_POSSIBLE_MOVED_KNIGHTS.get(alliance).get(destinationCoordinate) :
                          ALL_POSSIBLE_UNMOVED_KNIGHTS.get(alliance).get(destinationCoordinate);
    }

    public Bishop getBishop(final Alliance alliance,
                            final int destinationCoordinate,
                            final boolean hasMoved) {
        return hasMoved ? ALL_POSSIBLE_MOVED_BISHOPS.get(alliance).get(destinationCoordinate) :
                          ALL_POSSIBLE_UNMOVED_BISHOPS.get(alliance).get(destinationCoordinate);
    }

    public  Rook getRook(final Alliance alliance,
                         final int destinationCoordinate,
                         final boolean hasMoved) {
        return hasMoved ? ALL_POSSIBLE_MOVED_ROOKS.get(alliance).get(destinationCoordinate) :
                          ALL_POSSIBLE_UNMOVED_ROOKS.get(alliance).get(destinationCoordinate);
    }

    public Queen getQueen(final Alliance alliance,
                          final int destinationCoordinate,
                          final boolean hasMoved) {
        return hasMoved ? ALL_POSSIBLE_MOVED_QUEENS.get(alliance).get(destinationCoordinate) :
                          ALL_POSSIBLE_UNMOVED_QUEENS.get(alliance).get(destinationCoordinate);
    }

    public King getKing(final Alliance alliance,
                        final int destinationCoordinate,
                        final boolean isCastled,
                        final boolean hasMoved) {
        return hasMoved ? ALL_POSSIBLE_MOVED_KINGS.get(alliance).get(destinationCoordinate).get(isCastled) :
                          ALL_POSSIBLE_UNMOVED_KINGS.get(alliance).get(destinationCoordinate).get(isCastled);
    }

    private static Map<Alliance, Map<Integer, Pawn>> createAllPossiblePawns(final boolean hasMoved) {
        final Map<Alliance, Map<Integer, Pawn>> pieces = new EnumMap<>(Alliance.class);
        for (final Alliance alliance : Alliance.values()) {
            final Map<Integer, Pawn> map = new HashMap<>();
            for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
                map.put(i, new Pawn(alliance, i, !hasMoved));
            }
            pieces.put(alliance, map);
        }
        return pieces;
    }

    private static Map<Alliance, Map<Integer, Knight>> createAllPossibleMovedKnights(final boolean hasMoved) {
        final Map<Alliance, Map<Integer, Knight>> pieces = new EnumMap<>(Alliance.class);
        for (final Alliance alliance : Alliance.values()) {
            final Map<Integer, Knight> map = new HashMap<>();
            for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
                map.put(i, new Knight(alliance, i, !hasMoved));
            }
            pieces.put(alliance, map);
        }
        return pieces;
    }

    private static Map<Alliance, Map<Integer, Bishop>> createAllPossibleMovedBishops(final boolean hasMoved) {
        final Map<Alliance, Map<Integer, Bishop>> pieces = new EnumMap<>(Alliance.class);
        for (final Alliance alliance : Alliance.values()) {
            final Map<Integer, Bishop> map = new HashMap<>();
            for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
                map.put(i, new Bishop(alliance, i, !hasMoved));
            }
            pieces.put(alliance, map);
        }
        return pieces;
    }

    private static Map<Alliance, Map<Integer, Rook>> createAllPossibleMovedRooks(final boolean hasMoved) {
        final Map<Alliance, Map<Integer, Rook>> pieces = new EnumMap<>(Alliance.class);
        for (final Alliance alliance : Alliance.values()) {
            final Map<Integer, Rook> map = new HashMap<>();
            for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
                map.put(i, new Rook(alliance, i, !hasMoved));
            }
            pieces.put(alliance, map);
        }
        return pieces;
    }

    private static Map<Alliance, Map<Integer, Queen>> createAllPossibleMovedQueens(final boolean hasMoved) {
        final Map<Alliance, Map<Integer, Queen>> pieces = new EnumMap<>(Alliance.class);
        for (final Alliance alliance : Alliance.values()) {
            final Map<Integer, Queen> map = new HashMap<>();
            for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
                map.put(i, new Queen(alliance, i, !hasMoved));
            }
            pieces.put(alliance, map);
        }
        return pieces;
    }

    private static Map<Alliance, Map<Integer, Map<Boolean, King>>> createAllPossibleMovedKings(final boolean hasMoved) {
        final Map<Alliance, Map<Integer, Map<Boolean, King>>> pieces = new EnumMap<>(Alliance.class);
        for (final Alliance alliance : Alliance.values()) {
            final Map<Integer, Map<Boolean, King>> map = new HashMap<>();
            for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
                final Map<Boolean, King> kingStates = new HashMap<>();
                kingStates.put(false, new King(alliance, i, false, false)); // Not castled
                kingStates.put(true, new King(alliance, i, false, true, false, false));
                map.put(i, kingStates);
            }
            pieces.put(alliance, map);
        }
        return pieces;
    }
}
