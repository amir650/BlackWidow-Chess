package com.chess.engine.classic.pieces;

import java.util.Collection;
import java.util.List;

import com.chess.engine.classic.Alliance;
import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.board.BoardUtils;
import com.chess.engine.classic.board.Move;
import com.chess.engine.classic.board.Move.MajorAttackMove;
import com.chess.engine.classic.board.Move.MajorMove;
import com.chess.engine.classic.board.Tile;
import com.google.common.collect.ImmutableList;
import org.magicwerk.brownies.collections.GapList;

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
    public Collection<Move> calculateLegalMoves(final Board board) {
        final List<Move> legalMoves = new GapList<>();
        for (final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATES) {
            int candidateDestinationCoordinate = this.piecePosition;
            while (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                if (isFirstColumnExclusion(currentCandidateOffset, candidateDestinationCoordinate) ||
                    isEighthColumnExclusion(currentCandidateOffset, candidateDestinationCoordinate)) {
                    break;
                }
                candidateDestinationCoordinate += currentCandidateOffset;
                if (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                    final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
                    if (!candidateDestinationTile.isTileOccupied()) {
                        legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
                    }
                    else {
                        final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                        final Alliance pieceAlliance = pieceAtDestination.getPieceAllegiance();
                        if (this.pieceAlliance != pieceAlliance) {
                            legalMoves.add(new MajorAttackMove(board, this, candidateDestinationCoordinate,
                                    pieceAtDestination));
                        }
                        break;
                    }
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }

    @Override
    public boolean equals(final Object other) {
        return this == other || other instanceof Bishop && (super.equals(other));
    }

    @Override
    public int getPieceValue() {
        return this.pieceType.getPieceValue();
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
        return this.pieceType.toString();
    }

    private static boolean isFirstColumnExclusion(final int currentCandidate,
                                                  final int candidateDestinationCoordinate) {
        return (BoardUtils.FIRST_COLUMN[candidateDestinationCoordinate] &&
                ((currentCandidate == -9) || (currentCandidate == 7)));
    }

    private static boolean isEighthColumnExclusion(final int currentCandidate,
                                                   final int candidateDestinationCoordinate) {
        return BoardUtils.EIGHTH_COLUMN[candidateDestinationCoordinate] &&
                        ((currentCandidate == -7) || (currentCandidate == 9));
    }

}