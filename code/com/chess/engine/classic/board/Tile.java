package com.chess.engine.classic.board;

import com.chess.engine.classic.pieces.Piece;

public final class Tile {

    private final Piece pieceOnTile;
    private final int tileCoordinate;

    public Tile(final int coordinate, final Piece pieceOnTile) {
        this.pieceOnTile = pieceOnTile;
        this.tileCoordinate = coordinate;
    }

    public Tile(final Tile tile) {
        this.tileCoordinate = tile.getTileCoordinate();
        this.pieceOnTile = tile.getPiece() != null ? tile.getPiece().createCopy() : null ;
    }

    public boolean isTileOccupied() {
        return this.pieceOnTile != null;
    }

    public Piece getPiece() {
        return this.pieceOnTile;
    }

    public int getTileCoordinate() {
        return this.tileCoordinate;
    }

    @Override
    public String toString() {
        return isTileOccupied() ? this.pieceOnTile.toString() : "-";
    }

}