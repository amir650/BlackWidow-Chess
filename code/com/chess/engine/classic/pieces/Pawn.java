package com.chess.engine.classic.pieces;

import java.util.List;

import com.chess.engine.classic.Alliance;
import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.board.Move;
import com.chess.engine.classic.board.Move.PawnAttackMove;
import com.chess.engine.classic.board.Move.PawnJump;
import com.chess.engine.classic.board.Move.PawnMove;
import com.chess.engine.classic.board.Move.PawnPromotion;
import com.chess.engine.classic.board.Tile;
import com.google.common.collect.ImmutableList;

public final class Pawn
        extends Piece {

    private final static int[] CANDIDATE_MOVE_COORDINATES = {8, 16, 7, 9};

    public Pawn(final Alliance allegiance, final int piecePosition) {
        super(PieceType.PAWN, allegiance, piecePosition, true);
    }

    public Pawn(final Alliance alliance, final int piecePosition, final boolean isFirstMove) {
        super(PieceType.PAWN, alliance, piecePosition, isFirstMove);
    }

    private Pawn(final Pawn pawn) {
        super(pawn);
    }

    @Override
    public int locationBonus() {
        return this.pieceAlliance.pawnBonus(this.piecePosition);
    }

    @Override
    public List<Move> calculateLegalMoves(final Board board) {
        final ImmutableList.Builder<Move> legalMoves = ImmutableList.builder();
        for (final int currentCandidate : CANDIDATE_MOVE_COORDINATES) {
            int candidateDestinationCoordinate =
                    this.piecePosition + (this.pieceAlliance.getDirection() * currentCandidate * -1);
            if (Board.isValidTileCoordinate(candidateDestinationCoordinate)) {
                final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
                if (currentCandidate == 8) {
                    if (!candidateDestinationTile.isTileOccupied()) {
                        if (this.pieceAlliance.isPawnPromotionSquare(candidateDestinationCoordinate)) {
                            legalMoves.add(new PawnPromotion(
                                    new PawnMove(board, this.piecePosition, candidateDestinationCoordinate, this)));
                        }
                        else {
                            legalMoves.add(new PawnMove(board, this.piecePosition, candidateDestinationCoordinate, this));
                        }
                    }
                }
                else if (currentCandidate == 16 && this.isFirstMove() &&
                        ((Board.SECOND_ROW[this.piecePosition] && this.pieceAlliance.isBlack()) ||
                        (Board.SEVENTH_ROW[this.piecePosition] && this.pieceAlliance.isWhite()))) {
                    final int behindCandidateDestinationCoordinate =
                            this.piecePosition + (this.pieceAlliance.getDirection() * 8 * -1);
                    final Tile behindCandidateDestinationTile = board.getTile(behindCandidateDestinationCoordinate);
                    if (!candidateDestinationTile.isTileOccupied() &&
                            !behindCandidateDestinationTile.isTileOccupied()) {
                        legalMoves.add(new PawnJump(board, this.piecePosition, candidateDestinationCoordinate, this));
                    }
                }
                else if (currentCandidate == 7 && candidateDestinationTile.isTileOccupied() &&
                        !((Board.EIGHTH_COLUMN[this.piecePosition] && this.pieceAlliance.isWhite()) ||
                        (Board.FIRST_COLUMN[this.piecePosition] && this.pieceAlliance.isBlack()))) {
                    final Piece pieceOnCandidate = candidateDestinationTile.getPiece();
                    if (this.pieceAlliance != pieceOnCandidate.getPieceAllegiance()) {
                        if (this.pieceAlliance.isPawnPromotionSquare(candidateDestinationCoordinate)) {
                            legalMoves.add(new PawnPromotion(
                                    new PawnAttackMove(board, this.piecePosition, candidateDestinationCoordinate, this,
                                            pieceOnCandidate)));
                        }
                        else {
                            legalMoves.add(
                                    new PawnAttackMove(board, this.piecePosition, candidateDestinationCoordinate, this,
                                            pieceOnCandidate));
                        }
                    }
                }
                else if (currentCandidate == 9 && candidateDestinationTile.isTileOccupied() &&
                        !((Board.FIRST_COLUMN[this.piecePosition] && this.pieceAlliance.isWhite()) ||
                                (Board.EIGHTH_COLUMN[this.piecePosition] && this.pieceAlliance.isBlack()))) {
                    final Piece pieceOnCandidate = candidateDestinationTile.getPiece();
                    if (this.pieceAlliance != pieceOnCandidate.getPieceAllegiance()) {
                        if (this.pieceAlliance.isPawnPromotionSquare(candidateDestinationCoordinate)) {
                            legalMoves.add(new PawnPromotion(
                                    new PawnAttackMove(board, this.piecePosition, candidateDestinationCoordinate, this,
                                            pieceOnCandidate)));
                        }
                        else {
                            legalMoves.add(
                                    new PawnAttackMove(board, this.piecePosition, candidateDestinationCoordinate, this,
                                            pieceOnCandidate));
                        }
                    }
                }

            }
        }
        return legalMoves.build();
    }

    @Override
    public int getPieceValue() {
        return PieceType.PAWN.getPieceValue();
    }

    @Override
    public String toString() {
        return PieceType.PAWN.toString();
    }

    @Override
    public Pawn createCopy() {
        return new Pawn(this);
    }

    @Override
    public Pawn movePiece(final Move move) {
        return new Pawn(this.pieceAlliance, move.getDestinationCoordinate(), false);
    }

    public Piece getPromotionPiece() {
        return new Queen(this.pieceAlliance, this.piecePosition, false);
    }

}