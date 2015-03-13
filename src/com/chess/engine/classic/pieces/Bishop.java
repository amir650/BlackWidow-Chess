package com.chess.engine.classic.pieces;

import java.util.List;

import com.chess.engine.classic.Alliance;
import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.board.BoardUtils;
import com.chess.engine.classic.board.Move;
import com.chess.engine.classic.board.Move.MajorAttackMove;
import com.chess.engine.classic.board.Move.MajorMove;
import com.chess.engine.classic.board.Tile;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;

public final class Bishop extends Piece {

    private final static int[] CANDIDATE_MOVE_COORDINATES = {-9, -7, 7, 9};

    public Bishop(final Alliance alliance,
                  final int piecePosition) {
         super(PieceType.BISHOP, alliance, piecePosition, true);
    }

    public Bishop(final Alliance alliance,
                  final int piecePosition,
                  final boolean isFirstMove) {
        super(PieceType.BISHOP, alliance, piecePosition, isFirstMove);
    }

    @Override
    public List<Move> calculateLegalMoves(final Board board) {
        final Builder<Move> legalMoves = ImmutableList.builder();
        int candidateDestinationCoordinate;
        for (final int currentCandidate : CANDIDATE_MOVE_COORDINATES) {
            candidateDestinationCoordinate = this.piecePosition;
            while (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                if (isDiagonalExclusion(currentCandidate, candidateDestinationCoordinate)) {
                    break;
                }
                candidateDestinationCoordinate += currentCandidate;
                if (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                    final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
                    if (!candidateDestinationTile.isTileOccupied()) {
                        legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
                    }
                    else {
                        final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                        final Alliance piece_at_destination_allegiance = pieceAtDestination.getPieceAllegiance();
                        if (this.pieceAlliance != piece_at_destination_allegiance) {
                            legalMoves.add(new MajorAttackMove(board, this, candidateDestinationCoordinate,
                                    pieceAtDestination));
                        }
                        break;
                    }
                }
            }
        }
        return legalMoves.build();
    }

    @Override
    public boolean equals(final Object other) {
        return this == other || other instanceof Bishop && (super.equals(other));
    }

    @Override
    public int getPieceValue() {
        return PieceType.BISHOP.getPieceValue();
    }

    @Override
    public int locationBonus() {
        return this.pieceAlliance.bishopBonus(this.piecePosition);
    }

    @Override
    public Bishop movePiece(final Move move) {
        return PieceUtils.getMovedBishop(move);
    }

    @Override
    public String toString() {
        return PieceType.BISHOP.toString();
    }

    private static boolean isDiagonalExclusion(final int currentCandidate,
                                               final int candidateDestinationCoordinate) {
        return (BoardUtils.FIRST_COLUMN[candidateDestinationCoordinate] &&
                ((currentCandidate == -9) || (currentCandidate == 7))) ||
                (BoardUtils.EIGHTH_COLUMN[candidateDestinationCoordinate] &&
                        ((currentCandidate == -7) || (currentCandidate == 9)));
    }

}