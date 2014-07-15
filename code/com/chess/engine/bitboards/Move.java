package com.chess.engine.bitboards;

import com.chess.engine.bitboards.BitBoard.Piece;

public class Move {

	final int currentLocation;
	final int destinationLocation;
	final Piece movedPiece;

	public Move(final int current, final int destination, final Piece moved) {
		this.currentLocation = current;
		this.destinationLocation = destination;
		this.movedPiece = moved;
	}

	@Override
	public String toString() {
		return BitBoard.getPositionAtCoordinate(this.currentLocation) + "-"
		        + BitBoard.getPositionAtCoordinate(this.destinationLocation);
	}

	@Override
	public int hashCode() {
		int hash = 1;
		hash = hash * 31 + this.movedPiece.hashCode() + this.currentLocation
		        + this.destinationLocation;
		return hash;
	}

	@Override
	public boolean equals(final Object other) {

		if (this == other) {
			return true;
		}
		if (!(other instanceof Move)) {
			return false;
		}

		final Move otherMove = (Move) other;

		return (this.movedPiece == otherMove.getMovedPiece())
		        && (this.currentLocation == otherMove.getCurrentLocation())
		        && (this.destinationLocation == otherMove.getDestinationLocation());

	}

	public int getDestinationLocation() {
		return this.destinationLocation;
	}

	public int getCurrentLocation() {
		return this.currentLocation;
	}

	public Piece getMovedPiece() {
		return this.movedPiece;
	}

}
