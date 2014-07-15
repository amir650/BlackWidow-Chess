package com.chess.engine.classic.pieces;

import java.util.ArrayList;
import java.util.List;

import com.chess.engine.classic.Alliance;
import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.board.Move;
import com.chess.engine.classic.board.Move.AttackMove;
import com.chess.engine.classic.board.Tile;

public final class King extends Piece {

    private boolean isInCheck;
    private boolean isInCheckMate;
    private boolean isInStaleMate;
    private boolean isCastled;

    private final static int[] candidateMoveCoordinates = { -9, -8, -7, -1, 1, 7, 8, 9 };

    public King(final Alliance alliance) {
        super(Type.KING, alliance);
    }

    private King(final King king) {
        super(king);
        this.isInCheck = king.isInCheck();
        this.isInStaleMate = king.isInCheckMate();
        this.isInStaleMate = king.isInStaleMate();
        this.isCastled = king.isCastled();
    }

    public boolean isInCheck() {
        return this.isInCheck;
    }

    public boolean isInCheckMate() {
        return this.isInCheckMate;
    }

    public boolean isInStaleMate() {
        return this.isInStaleMate;
    }

    public boolean isCastled() {
        return this.isCastled;
    }

    @Override
    public List<Move> calculateLegalMoves(final Board board) {

        final List<Move> legalMoves = new ArrayList<>();
        int candidateDestinationCoordinate;

        for (final int currentCandidate : candidateMoveCoordinates) {
            if (Board.FIRST_COLUMN[this.piecePosition]
                    && ((currentCandidate == -9) || (currentCandidate == -1) || (currentCandidate == 7))) {
                continue;
            }
            if (Board.EIGHTH_COLUMN[this.piecePosition]
                    && ((currentCandidate == -7) || (currentCandidate == 1) || (currentCandidate == 9))) {
                continue;
            }
            candidateDestinationCoordinate = this.piecePosition
                    + currentCandidate;
            if (!(candidateDestinationCoordinate >= 0 && candidateDestinationCoordinate < Board.NUM_TILES)) {
                continue;
            }
            final Tile candidateDestinationTile = board
                    .getTile(candidateDestinationCoordinate);
            if (!candidateDestinationTile.isTileOccupied()) {
                legalMoves.add(new Move(this.piecePosition, candidateDestinationCoordinate, this));
            } else {
                final Piece pieceAtDestination = candidateDestinationTile
                        .getPiece();
                final Alliance pieceAtDestinationAllegiance = pieceAtDestination
                        .getPieceAllegiance();

                if (this.pieceAlliance != pieceAtDestinationAllegiance) {
                    legalMoves.add(new AttackMove(this.piecePosition, candidateDestinationCoordinate, this,
                            pieceAtDestination));
                }
            }
        }
        return legalMoves;
    }

    @Override
    public int getPieceValue() {
        return Type.KING.getPieceValue();
    }

    @Override
    public String toString() {
        return Type.KING.toString();
    }

    @Override
    public int locationBonus() {
        return this.pieceAlliance.kingBonus(this.piecePosition);
    }

    @Override
    public King createCopy() {
        return new King(this);
    }

    public void setInStaleMate(final boolean inStaleMate) {
        this.isInStaleMate = inStaleMate;
    }

    public void setInCheckMate(final boolean inCheckMate) {
        this.isInCheckMate = inCheckMate;
    }

    public void setInCheck(final boolean inCheck) {
        this.isInCheck = inCheck;
    }

    public void setIsCastled(final boolean isCastled) {
        this.isCastled = isCastled;
    }
}