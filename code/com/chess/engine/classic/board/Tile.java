package com.chess.engine.classic.board;

import com.chess.engine.classic.Copyable;
import com.chess.engine.classic.pieces.Piece;

abstract public class Tile {

    protected final int tileCoordinate;

    private Tile(final int coordinate) {
        this.tileCoordinate = coordinate;
    }

    public abstract boolean isTileOccupied();

    public abstract Piece getPiece();

    public static Tile createTile(final int coordinate,
                                  final Piece piece) {
        if(piece != null) {
            return new OccupiedTile(coordinate, piece);
        }
        return new EmptyTile(coordinate);
    }

    public int getTileCoordinate() {
        return this.tileCoordinate;
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
            throw new RuntimeException("should not reach here!");
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