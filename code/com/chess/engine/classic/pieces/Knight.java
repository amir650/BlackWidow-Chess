package com.chess.engine.classic.pieces;

import java.util.List;

import com.chess.engine.classic.Alliance;
import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.board.Move;
import com.chess.engine.classic.board.Move.MajorAttackMove;
import com.chess.engine.classic.board.Move.MajorMove;
import com.chess.engine.classic.board.Tile;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;

public final class Knight extends Piece {

    private final static int[] CANDIDATE_MOVE_COORDINATES = { -17, -15, -10, -6,
        6, 10, 15, 17 };

    public Knight(final Alliance alliance, final int piecePosition) {
        super(PieceType.KNIGHT, alliance, piecePosition, true);
    }

    public Knight(final Alliance alliance,
                  final int piecePosition,
                  final boolean isFirstMove) {
        super(PieceType.KNIGHT, alliance, piecePosition, isFirstMove);
    }

    private Knight(final Knight knight) {
        super(knight);
    }

    @Override
    public List<Move> calculateLegalMoves(final Board board) {
        final Builder<Move> legalMoves = ImmutableList.builder();
        int candidateDestinationCoordinate;
        for (final int currentCandidate : CANDIDATE_MOVE_COORDINATES) {
            if(isFirstColumnExclusion(this.piecePosition, currentCandidate) ||
               isSecondColumnExclusion(this.piecePosition, currentCandidate) ||
               isSeventhColumnExclusion(this.piecePosition, currentCandidate) ||
               isEighthColumnExclusion(this.piecePosition, currentCandidate)) {
                continue;
            }
            candidateDestinationCoordinate = this.piecePosition + currentCandidate;
            if (Board.isValidTileCoordinate(candidateDestinationCoordinate)) {
                final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
                if (!candidateDestinationTile.isTileOccupied()) {
                    legalMoves.add(new MajorMove(board, this.piecePosition, candidateDestinationCoordinate, this));
                } else {
                    final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                    final Alliance pieceAtDestinationAllegiance = pieceAtDestination.getPieceAllegiance();
                    if (this.pieceAlliance != pieceAtDestinationAllegiance) {
                        legalMoves.add(new MajorAttackMove(board, this.piecePosition, candidateDestinationCoordinate, this,
                                pieceAtDestination));
                    }
                }
            }
        }
        return legalMoves.build();
    }

    @Override
    public int getPieceValue() {
        return PieceType.KNIGHT.getPieceValue();
    }

    @Override
    public int locationBonus() {
        return this.pieceAlliance.knightBonus(this.piecePosition);
    }

    @Override
    public Knight createCopy() {
        return new Knight(this);
    }

    @Override
    public Knight movePiece(final Move move) {
        return new Knight(this.pieceAlliance, move.getDestinationCoordinate(), false);
    }

    @Override
    public String toString() {
        return PieceType.KNIGHT.toString();
    }

    private static boolean isFirstColumnExclusion(final int currentPosition,
                                                  final int candidatePosition) {
        return Board.FIRST_COLUMN[currentPosition] && ((candidatePosition == -17) ||
                (candidatePosition == -10) || (candidatePosition == 6) || (candidatePosition == 15));
    }

    private static boolean isSecondColumnExclusion(final int currentPosition,
                                                   final int candidatePosition) {
        return Board.SECOND_COLUMN[currentPosition] && ((candidatePosition == -10) || (candidatePosition == 6));
    }

    private static boolean isSeventhColumnExclusion(final int currentPosition,
                                                    final int candidatePosition) {
        return Board.SEVENTH_COLUMN[currentPosition] && ((candidatePosition == -6) || (candidatePosition == 10));
    }

    private static boolean isEighthColumnExclusion(final int currentPosition,
                                                   final int candidatePosition) {
        return Board.EIGHTH_COLUMN[currentPosition] && ((candidatePosition == -15) || (candidatePosition == -6) ||
                (candidatePosition == 10) || (candidatePosition == 17));
    }

}