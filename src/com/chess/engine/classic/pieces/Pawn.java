package com.chess.engine.classic.pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.chess.engine.classic.Alliance;
import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.board.BoardUtils;
import com.chess.engine.classic.board.Move;
import com.chess.engine.classic.board.Move.*;
import com.google.common.collect.ImmutableList;

public final class Pawn
        extends Piece {

    private final static int[] CANDIDATE_MOVE_COORDINATES = {8, 16, 7, 9};

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
    public int locationBonus() {
        return this.pieceAlliance.pawnBonus(this.piecePosition);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {
        final List<Move> legalMoves = new ArrayList<>();
        for (final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATES) {
            int candidateDestinationCoordinate =
                    this.piecePosition + (this.pieceAlliance.getDirection() * currentCandidateOffset);
            if (!BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                continue;
            }
            if (currentCandidateOffset == 8 && !board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
                if (this.pieceAlliance.isPawnPromotionSquare(candidateDestinationCoordinate)) {
                    legalMoves.add(new PawnPromotion(
                            new PawnMove(board, this, candidateDestinationCoordinate)));
                }
                else {
                    legalMoves.add(new PawnMove(board, this, candidateDestinationCoordinate));
                }
            }
            else if (currentCandidateOffset == 16 && this.isFirstMove() &&
                    ((BoardUtils.INSTANCE.SECOND_ROW.get(this.piecePosition) && this.pieceAlliance.isBlack()) ||
                     (BoardUtils.INSTANCE.SEVENTH_ROW.get(this.piecePosition) && this.pieceAlliance.isWhite()))) {
                final int behindCandidateDestinationCoordinate =
                        this.piecePosition + (this.pieceAlliance.getDirection() * 8);
                if (!board.getTile(candidateDestinationCoordinate).isTileOccupied() &&
                    !board.getTile(behindCandidateDestinationCoordinate).isTileOccupied()) {
                    legalMoves.add(new PawnJump(board, this, candidateDestinationCoordinate));
                }
            }
            else if (currentCandidateOffset == 7 &&
                    !((BoardUtils.INSTANCE.EIGHTH_COLUMN.get(this.piecePosition) && this.pieceAlliance.isWhite()) ||
                      (BoardUtils.INSTANCE.FIRST_COLUMN.get(this.piecePosition) && this.pieceAlliance.isBlack()))) {
                if(board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
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
                } else if (board.getEnPassantPawn() != null && board.getEnPassantPawn().getPiecePosition() ==
                           (this.piecePosition + (this.pieceAlliance.getOppositeDirection()))) {
                    final Piece pieceOnCandidate = board.getEnPassantPawn();
                    if (this.pieceAlliance != pieceOnCandidate.getPieceAllegiance()) {
                        if (this.pieceAlliance.isPawnPromotionSquare(candidateDestinationCoordinate)) {
                            legalMoves.add(new PawnPromotion(
                                    new PawnEnPassantAttack(board, this, candidateDestinationCoordinate, pieceOnCandidate)));
                        } else {
                            legalMoves.add(
                                    new PawnEnPassantAttack(board, this, candidateDestinationCoordinate, pieceOnCandidate));
                        }
                    }
                }
            }
            else if (currentCandidateOffset == 9 &&
                    !((BoardUtils.INSTANCE.FIRST_COLUMN.get(this.piecePosition) && this.pieceAlliance.isWhite()) ||
                      (BoardUtils.INSTANCE.EIGHTH_COLUMN.get(this.piecePosition) && this.pieceAlliance.isBlack()))) {
                if(board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
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
                } else if (board.getEnPassantPawn() != null && board.getEnPassantPawn().getPiecePosition() ==
                        (this.piecePosition - (this.pieceAlliance.getOppositeDirection()))) {
                    final Piece pieceOnCandidate = board.getEnPassantPawn();
                    if (this.pieceAlliance != pieceOnCandidate.getPieceAllegiance()) {
                        if (this.pieceAlliance.isPawnPromotionSquare(candidateDestinationCoordinate)) {
                            legalMoves.add(new PawnPromotion(
                                    new PawnEnPassantAttack(board, this, candidateDestinationCoordinate, pieceOnCandidate)));
                        } else {
                            legalMoves.add(
                                    new PawnEnPassantAttack(board, this, candidateDestinationCoordinate, pieceOnCandidate));
                        }
                    }

                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }

    @Override
    public String toString() {
        return this.pieceType.toString();
    }

    @Override
    public Pawn movePiece(final Move move) {
        return PieceUtils.INSTANCE.getMovedPawn(move);
    }

    public Piece getPromotionPiece() {
        return new Queen(this.pieceAlliance, this.piecePosition, false);
    }

}