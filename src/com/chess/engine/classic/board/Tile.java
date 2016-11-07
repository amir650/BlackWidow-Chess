package com.chess.engine.classic.board;

import java.util.HashMap;
import java.util.Map;

import com.chess.engine.classic.Alliance;
import com.chess.engine.classic.pieces.*;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;

abstract public class Tile {

    protected final int tileCoordinate;

    private static final Map<Integer, EmptyTile> EMPTY_TILES = createAllPossibleEmptyTiles();

    private static final Table<Integer, Piece, OccupiedTile> OCCUPIED_TILES = createAllPossibleOccupiedTiles();

    private Tile(final int coordinate) {
        this.tileCoordinate = coordinate;
    }

    public abstract boolean isTileOccupied();

    public abstract Piece getPiece();

    public static Tile createTile(final int coordinate,
                                  final Piece piece) {

        if(piece == null) {
            return EMPTY_TILES.get(coordinate);
        }

        final OccupiedTile cachedOccupiedTile = OCCUPIED_TILES.get(coordinate, piece);

        if(cachedOccupiedTile != null) {
            return cachedOccupiedTile;
        }

        return new OccupiedTile(coordinate, piece);
    }

    public int getTileCoordinate() {
        return this.tileCoordinate;
    }

    private static Map<Integer,EmptyTile> createAllPossibleEmptyTiles() {
        final Map<Integer, EmptyTile> emptyTileMap = new HashMap<>();
        for(int i = 0; i < BoardUtils.NUM_TILES; i++) {
            emptyTileMap.put(i, new EmptyTile(i));
        }
        return ImmutableMap.copyOf(emptyTileMap);
    }

    private static Table<Integer, Piece, OccupiedTile> createAllPossibleOccupiedTiles() {

        final Table<Integer, Piece, OccupiedTile> occupiedTileTable = HashBasedTable.create();

        for (final Alliance alliance : Alliance.values()) {
            for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
                final Knight whiteKnightFirstMove = new Knight(alliance, i, true);
                final Knight whiteKnightMoved = new Knight(alliance, i, false);
                occupiedTileTable.put(i, whiteKnightFirstMove, new OccupiedTile(i, whiteKnightFirstMove));
                occupiedTileTable.put(i, whiteKnightMoved, new OccupiedTile(i, whiteKnightMoved));
            }

            for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
                final Bishop whiteBishopFirstMove = new Bishop(alliance, i, true);
                final Bishop whiteBishopMoved = new Bishop(alliance, i, false);
                occupiedTileTable.put(i, whiteBishopFirstMove, new OccupiedTile(i, whiteBishopFirstMove));
                occupiedTileTable.put(i, whiteBishopMoved, new OccupiedTile(i, whiteBishopMoved));
            }

            for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
                final Rook whiteRookFirstMove = new Rook(alliance, i, true);
                final Rook whiteRookMoved = new Rook(alliance, i, false);
                occupiedTileTable.put(i, whiteRookFirstMove, new OccupiedTile(i, whiteRookFirstMove));
                occupiedTileTable.put(i, whiteRookMoved, new OccupiedTile(i, whiteRookMoved));
            }

            for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
                final Queen whiteQueenFirstMove = new Queen(alliance, i, true);
                final Queen whiteQueenMoved = new Queen(alliance, i, false);
                occupiedTileTable.put(i, whiteQueenFirstMove, new OccupiedTile(i, whiteQueenFirstMove));
                occupiedTileTable.put(i, whiteQueenMoved, new OccupiedTile(i, whiteQueenMoved));
            }

            for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
                final Pawn whitePawnFirstMove = new Pawn(alliance, i, true);
                final Pawn whitePawnMoved = new Pawn(alliance, i, false);
                occupiedTileTable.put(i, whitePawnFirstMove, new OccupiedTile(i, whitePawnFirstMove));
                occupiedTileTable.put(i, whitePawnMoved, new OccupiedTile(i, whitePawnMoved));
            }

            for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
                final King whiteKingFirstMove = new King(alliance, i, true, true);
                final King whiteKingMoved = new King(alliance, i, false, false, false, false);
                final King whiteKingMovedCastled = new King(alliance, i, false, true, false, false);
                occupiedTileTable.put(i, whiteKingFirstMove, new OccupiedTile(i, whiteKingFirstMove));
                occupiedTileTable.put(i, whiteKingMoved, new OccupiedTile(i, whiteKingMoved));
                occupiedTileTable.put(i, whiteKingMovedCastled, new OccupiedTile(i, whiteKingMovedCastled));
            }

        }

        return ImmutableTable.copyOf(occupiedTileTable);
    }

    public static final class EmptyTile extends Tile {

        private EmptyTile(final int coordinate) {
            super(coordinate);
        }

        @Override
        public String toString() {
            return "-";
        }

        @Override
        public boolean isTileOccupied() {
            return false;
        }

        public Piece getPiece() {
            return null;
        }

    }

    public static final class OccupiedTile extends Tile {

        private final Piece pieceOnTile;

        private OccupiedTile(final int coordinate,
                             final Piece pieceOnTile) {
            super(coordinate);
            this.pieceOnTile = pieceOnTile;
        }

        @Override
        public String toString() {
            return this.pieceOnTile.getPieceAllegiance().isWhite() ?
                   this.pieceOnTile.toString() :
                   this.pieceOnTile.toString().toLowerCase();
        }

        @Override
        public boolean isTileOccupied() {
            return true;
        }

        @Override
        public Piece getPiece() {
            return pieceOnTile;
        }
    }

}