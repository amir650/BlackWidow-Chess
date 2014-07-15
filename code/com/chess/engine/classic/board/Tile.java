package com.chess.engine.classic.board;

import com.chess.engine.classic.pieces.Piece;

public final class Tile {

    private Piece pieceOnTile;
    private final int tileCoordinate;

    public Tile(final int coordinate) {
        this.pieceOnTile = null;
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

    public Piece removePiece() {
        final Piece p = this.pieceOnTile;
        this.pieceOnTile = null;
        return p;
    }

    public void setPiece(final Piece p) {
        this.pieceOnTile = p;
        p.setPiecePosition(this.tileCoordinate);
    }

    @Override
    public String toString() {
        return isTileOccupied() ? this.pieceOnTile.toString() : "-";
    }

}