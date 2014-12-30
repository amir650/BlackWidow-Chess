package com.chess.engine.classic.board;

import java.util.HashMap;
import java.util.Map;

import com.chess.engine.classic.Copyable;
import com.chess.engine.classic.pieces.Piece;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Table;

abstract public class Tile {

    protected final int tileCoordinate;
    private static final Map<Integer, EmptyTile> EMPTY_TILES = createAllPossibleEmptyTiles();
    private static final Table<Integer, Piece, OccupiedTile> OCCUPIED_TILES = HashBasedTable.create();

    private Tile(final int coordinate) {
        this.tileCoordinate = coordinate;
    }

    public abstract boolean isTileOccupied();

    public abstract Piece getPiece();

    public static Tile createTile(final int coordinate,
                                  final Piece piece) {
        if(piece != null) {
            //return new OccupiedTile(coordinate, piece);
            final OccupiedTile occupiedTile = OCCUPIED_TILES.get(coordinate, piece);
            if(occupiedTile == null) {
                OCCUPIED_TILES.put(coordinate, piece, new OccupiedTile(coordinate, piece));
            }
            return OCCUPIED_TILES.get(coordinate, piece);
        }
        return EMPTY_TILES.get(coordinate);
    }

    public int getTileCoordinate() {
        return this.tileCoordinate;
    }

    private static Map<Integer,EmptyTile> createAllPossibleEmptyTiles() {
        final Map<Integer, EmptyTile> emptyTileMap = new HashMap<>();
        for(int i = 0; i < Board.NUM_TILES; i++) {
            emptyTileMap.put(i, new EmptyTile(i));
        }
        return ImmutableMap.copyOf(emptyTileMap);
    }

    public static final class EmptyTile extends Tile implements Copyable<EmptyTile> {

        EmptyTile(final int coordinate) {
            super(coordinate);
        }

        @Override
        public EmptyTile createCopy() {
            return new EmptyTile(this.tileCoordinate);
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

    public static final class OccupiedTile extends Tile implements Copyable<OccupiedTile> {

        private final Piece pieceOnTile;

        OccupiedTile(final int coordinate, final Piece pieceOnTile) {
            super(coordinate);
            this.pieceOnTile = pieceOnTile;
        }

        @Override
        public OccupiedTile createCopy() {
            return new OccupiedTile(tileCoordinate, pieceOnTile.createCopy());
        }

        @Override
        public String toString() {
            return this.pieceOnTile.toString();
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