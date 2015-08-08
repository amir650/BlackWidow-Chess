package com.chess.engine.classic.pieces;

import java.util.Collection;

import com.chess.engine.classic.Alliance;
import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.board.BoardUtils;
import com.chess.engine.classic.board.Move;
import com.chess.engine.classic.board.Move.PawnAttackMove;
import com.chess.engine.classic.board.Move.PawnJump;
import com.chess.engine.classic.board.Move.PawnMove;
import com.chess.engine.classic.board.Move.PawnPromotion;
import com.google.common.collect.ImmutableList;

public final class Pawn
        extends Piece {

    private final static int[] CANDIDATE_MOVE_COORDINATES = {8, 16, 7, 9, 1, -1};

    public Pawn(final Alliance allegiance,
                final int piecePosition) {
        super(PieceType.PAWN, allegiance, piecePosition, true);
    }

    public Pawn(final Alliance alliance,
                final int piecePosition,
                final boolean isFirstMove) {
        super(PieceType.PAWN, alliance, piecePosition, isFirstMove);
    }

    @Override
    public boolean equals(final Object other) {
        return this == other || other instanceof Pawn && (super.equals(other));
    }

    @Override
    public int locationBonus() {
        return this.pieceAlliance.pawnBonus(this.piecePosition);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {
        final ImmutableList.Builder<Move> legalMoves = ImmutableList.builder();
        for (final int currentCandidate : CANDIDATE_MOVE_COORDINATES) {
            int candidateDestinationCoordinate =
                    this.piecePosition + (this.pieceAlliance.getDirection() * currentCandidate * -1);
            if (!BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                continue;
            }
            if (currentCandidate == 8 && !board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
                if (this.pieceAlliance.isPawnPromotionSquare(candidateDestinationCoordinate)) {
                    legalMoves.add(new PawnPromotion(
                            new PawnMove(board, this, candidateDestinationCoordinate)));
                }
                else {
                    legalMoves.add(new PawnMove(board, this, candidateDestinationCoordinate));
                }
            }
            else if (currentCandidate == 16 && this.isFirstMove() &&
                    ((BoardUtils.SECOND_ROW[this.piecePosition] && this.pieceAlliance.isBlack()) ||
                            (BoardUtils.SEVENTH_ROW[this.piecePosition] && this.pieceAlliance.isWhite()))) {
                final int behindCandidateDestinationCoordinate =
                        this.piecePosition + (this.pieceAlliance.getDirection() * 8 * -1);
                if (!board.getTile(candidateDestinationCoordinate).isTileOccupied() &&
                        !board.getTile(behindCandidateDestinationCoordinate).isTileOccupied()) {
                    legalMoves.add(new PawnJump(board, this, candidateDestinationCoordinate));
                }
            }
            else if (currentCandidate == 7 && board.getTile(candidateDestinationCoordinate).isTileOccupied() &&
                    !((BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.pieceAlliance.isWhite()) ||
                            (BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceAlliance.isBlack()))) {
                final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
                if (this.pieceAlliance != pieceOnCandidate.getPieceAllegiance()) {
                    if (this.pieceAlliance.isPawnPromotionSquare(candidateDestinationCoordinate)) {
                        legalMoves.add(new PawnPromotion(
                                new PawnAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate)));
                    }
                    else {
                        legalMoves.add(
                                new PawnAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate));
                    }
                }
            }
            else if (currentCandidate == 9 && board.getTile(candidateDestinationCoordinate).isTileOccupied() &&
                    !((BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceAlliance.isWhite()) ||
                            (BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.pieceAlliance.isBlack()))) {
                if (this.pieceAlliance !=
                        board.getTile(candidateDestinationCoordinate).getPiece().getPieceAllegiance()) {
                    if (this.pieceAlliance.isPawnPromotionSquare(candidateDestinationCoordinate)) {
                        legalMoves.add(new PawnPromotion(
                                new PawnAttackMove(board, this, candidateDestinationCoordinate,
                                        board.getTile(candidateDestinationCoordinate).getPiece())));
                    }
                    else {
                        legalMoves.add(
                                new PawnAttackMove(board, this, candidateDestinationCoordinate,
                                        board.getTile(candidateDestinationCoordinate).getPiece()));
                    }
                }
            }
//            else if (currentCandidate == 7 && board.getEnPassantPawn() != null &&
//                     board.getEnPassantPawn().getPiecePosition() == candidateDestinationCoordinate &&
//                    !((BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.pieceAlliance.isWhite()) ||
//                            (BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceAlliance.isBlack()))) {
//                final Piece pieceOnCandidate = board.getEnPassantPawn();
//                if (this.pieceAlliance != pieceOnCandidate.getPieceAllegiance()) {
//                    if (this.pieceAlliance.isPawnPromotionSquare(candidateDestinationCoordinate)) {
//                        legalMoves.add(new PawnPromotion(
//                                new PawnAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate)));
//                    }
//                    else {
//                        legalMoves.add(
//                                new PawnAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate));
//                    }
//                }
//            }
//            else if (currentCandidate == 9 && board.getEnPassantPawn() != null &&
//                     board.getEnPassantPawn().getPiecePosition() == (this.piecePosition - 1) &&
//                    !((BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceAlliance.isWhite()) ||
//                            (BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.pieceAlliance.isBlack()))) {
//                if (this.pieceAlliance !=
//                        board.getTile(candidateDestinationCoordinate).getPiece().getPieceAllegiance()) {
//                    if (this.pieceAlliance.isPawnPromotionSquare(candidateDestinationCoordinate)) {
//                        legalMoves.add(new PawnPromotion(
//                                new PawnAttackMove(board, this, candidateDestinationCoordinate,
//                                        board.getTile(candidateDestinationCoordinate).getPiece())));
//                    }
//                    else {
//                        legalMoves.add(
//                                new PawnAttackMove(board, this, candidateDestinationCoordinate,
//                                        board.getTile(candidateDestinationCoordinate).getPiece()));
//                    }
//                }
//            }
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
    public Pawn movePiece(final Move move) {
        return PieceUtils.getMovedPawn(move);
    }

    public Piece getPromotionPiece() {
        return new Queen(this.pieceAlliance, this.piecePosition, false);
    }

}