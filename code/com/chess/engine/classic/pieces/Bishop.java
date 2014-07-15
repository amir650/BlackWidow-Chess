package com.chess.engine.classic.pieces;

import java.util.ArrayList;
import java.util.List;

import com.chess.engine.classic.Alliance;
import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.board.Move;
import com.chess.engine.classic.board.Move.AttackMove;
import com.chess.engine.classic.board.Tile;

public final class Bishop
        extends Piece {

    private final static int[] candidateMoveCoordinates = {-9, -7, 7, 9};

    public Bishop(final Alliance alliance) {
        super(Type.BISHOP, alliance);
    }

    private Bishop(final Bishop b) {
        super(b);
    }

    @Override
    public List<Move> calculateLegalMoves(final Board board) {
        final List<Move> legalMoves = new ArrayList<>();
        int candidateDestinationCoordinate;
        for (final int currentCandidate : candidateMoveCoordinates) {
            candidateDestinationCoordinate = this.piecePosition;
            while (true) {
                if (isDiagonalExclusion(currentCandidate, candidateDestinationCoordinate)) {
                    break;
                }
                candidateDestinationCoordinate += currentCandidate;
                if (Board.isValidTileCoordinate(candidateDestinationCoordinate)) {
                    final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
                    if (!candidateDestinationTile.isTileOccupied()) {
                        legalMoves.add(new Move(this.piecePosition, candidateDestinationCoordinate, this));
                    }
                    else {
                        final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                        final Alliance piece_at_destination_allegiance = pieceAtDestination.getPieceAllegiance();
                        if (this.pieceAlliance != piece_at_destination_allegiance) {
                            legalMoves.add(new AttackMove(this.piecePosition, candidateDestinationCoordinate, this,
                                    pieceAtDestination));
                        }
                        break;
                    }
                }
                else {
                    break;
                }
            }
        }
        return legalMoves;
    }

    @Override
    public int getPieceValue() {
        return Type.BISHOP.getPieceValue();
    }

    @Override
    public int locationBonus() {
        return this.pieceAlliance.bishopBonus(this.piecePosition);
    }

    @Override
    public Bishop createCopy() {
        return new Bishop(this);
    }

    @Override
    public String toString() {
        return Type.BISHOP.toString();
    }

    private static boolean isDiagonalExclusion(final int currentCandidate, final int candidateDestinationCoordinate) {
        return (Board.FIRST_COLUMN[candidateDestinationCoordinate] &&
                ((currentCandidate == -9) || (currentCandidate == 7))) ||
                (Board.EIGHTH_COLUMN[candidateDestinationCoordinate] &&
                        ((currentCandidate == -7) || (currentCandidate == 9)));
    }

}