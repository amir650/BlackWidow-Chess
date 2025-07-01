package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public final class Pawn
        extends Piece {

    private final static int[] CANDIDATE_MOVE_COORDINATES = {8, 16, 7, 9};

    Pawn(final Alliance alliance,
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
            if (currentCandidateOffset == 8 && board.getPiece(candidateDestinationCoordinate) == null) {
                if (this.pieceAlliance.isPawnPromotionSquare(candidateDestinationCoordinate)) {
                    addPromotions(legalMoves, new PawnMove(board, this, candidateDestinationCoordinate), this.pieceAlliance, candidateDestinationCoordinate);
                }
                else {
                    legalMoves.add(new PawnMove(board, this, candidateDestinationCoordinate));
                }
            }
            else if (currentCandidateOffset == 16 && this.isFirstMove() &&
                    ((BoardUtils.SECOND_ROW.get(this.piecePosition) && this.pieceAlliance.isBlack()) ||
                     (BoardUtils.SEVENTH_ROW.get(this.piecePosition) && this.pieceAlliance.isWhite()))) {
                final int behindCandidateDestinationCoordinate =
                        this.piecePosition + (this.pieceAlliance.getDirection() * 8);
                if (board.getPiece(candidateDestinationCoordinate) == null &&
                    board.getPiece(behindCandidateDestinationCoordinate) == null) {
                    legalMoves.add(new PawnJump(board, this, candidateDestinationCoordinate));
                }
            }
            else if (currentCandidateOffset == 7 &&
                    !((BoardUtils.EIGHTH_COLUMN.get(this.piecePosition) && this.pieceAlliance.isWhite()) ||
                      (BoardUtils.FIRST_COLUMN.get(this.piecePosition) && this.pieceAlliance.isBlack()))) {
                if(board.getPiece(candidateDestinationCoordinate) != null) {
                    final Piece pieceOnCandidate = board.getPiece(candidateDestinationCoordinate);
                    if (this.pieceAlliance != pieceOnCandidate.getPieceAllegiance()) {
                        if (this.pieceAlliance.isPawnPromotionSquare(candidateDestinationCoordinate)) {
                            addPromotions(legalMoves, new PawnMove(board, this, candidateDestinationCoordinate), this.pieceAlliance, candidateDestinationCoordinate);
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
                        legalMoves.add(new PawnEnPassantAttack(board, this, candidateDestinationCoordinate, pieceOnCandidate));

                    }
                }
            }
            else if (currentCandidateOffset == 9 &&
                    !((BoardUtils.FIRST_COLUMN.get(this.piecePosition) && this.pieceAlliance.isWhite()) ||
                      (BoardUtils.EIGHTH_COLUMN.get(this.piecePosition) && this.pieceAlliance.isBlack()))) {
                if(board.getPiece(candidateDestinationCoordinate) != null) {
                    if (this.pieceAlliance !=
                            board.getPiece(candidateDestinationCoordinate).getPieceAllegiance()) {
                        if (this.pieceAlliance.isPawnPromotionSquare(candidateDestinationCoordinate)) {
                            addPromotions(legalMoves, new PawnMove(board, this, candidateDestinationCoordinate), this.pieceAlliance, candidateDestinationCoordinate);
                        }
                        else {
                            legalMoves.add(new PawnAttackMove(board, this, candidateDestinationCoordinate,
                                            board.getPiece(candidateDestinationCoordinate)));
                        }
                    }
                } else if (board.getEnPassantPawn() != null && board.getEnPassantPawn().getPiecePosition() ==
                        (this.piecePosition - (this.pieceAlliance.getOppositeDirection()))) {
                    final Piece pieceOnCandidate = board.getEnPassantPawn();
                    if (this.pieceAlliance != pieceOnCandidate.getPieceAllegiance()) {
                        legalMoves.add(
                                new PawnEnPassantAttack(board, this, candidateDestinationCoordinate, pieceOnCandidate));

                    }
                }
            }
        }
        return Collections.unmodifiableList(legalMoves);
    }

    private static void addPromotions(final List<Move> moves,
                                      final Move baseMove,
                                      final Alliance alliance,
                                      final int destination) {
        moves.add(new PawnPromotion(baseMove, PieceUtils.INSTANCE.getQueen(alliance, destination, true)));
        moves.add(new PawnPromotion(baseMove, PieceUtils.INSTANCE.getRook(alliance, destination, true)));
        moves.add(new PawnPromotion(baseMove, PieceUtils.INSTANCE.getBishop(alliance, destination, true)));
        moves.add(new PawnPromotion(baseMove, PieceUtils.INSTANCE.getKnight(alliance, destination, true)));
    }

    @Override
    public String toString() {
        return this.pieceType.toString();
    }

    @Override
    public Pawn getMovedPiece(final Move move) {
        return PieceUtils.INSTANCE.getPawn(move.getMovedPiece().getPieceAllegiance(), move.getDestinationCoordinate(), true);
    }

    @Override
    public Pawn getMovedPiece(final Alliance alliance, final int to) {
        return PieceUtils.INSTANCE.getPawn(alliance, to, true);
    }

}