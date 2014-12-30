package com.chess.engine.classic.pieces;

import java.util.List;

import com.chess.engine.classic.Alliance;
import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.board.Board.MoveStatus;
import com.chess.engine.classic.board.Move;
import com.chess.engine.classic.board.Move.MajorAttackMove;
import com.chess.engine.classic.board.Move.MajorMove;
import com.chess.engine.classic.board.MoveTransition;
import com.chess.engine.classic.board.Tile;
import com.chess.engine.classic.player.Player;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;

public final class King extends Piece {

    private final static int[] CANDIDATE_MOVE_COORDINATES = { -9, -8, -7, -1, 1, 7, 8, 9 };
    private final boolean isCastled;

    public King(final Alliance alliance, final int piecePosition) {
        super(PieceType.KING, alliance, piecePosition, true);
        this.isCastled = false;
    }

    public King(final Alliance alliance,
                final int piecePosition,
                final boolean isFirstMove,
                final boolean isCastled) {
        super(PieceType.KING, alliance, piecePosition, isFirstMove);
        this.isCastled = isCastled;
    }

    private King(final King king) {
        super(king);
        this.isCastled = king.isCastled();
    }

    public boolean isInCheck(final List<Move> enemyMoves) {
        return !Player.calculateAttacksOnTile(this.piecePosition, enemyMoves).isEmpty();
    }

    public boolean isInCheckMate(final Board board) {
        return !Player.calculateAttacksOnTile(this.piecePosition, board.currentPlayer().getOpponent().getLegalMoves())
               .isEmpty() && !(hasEscapeMoves(board));
    }

    public boolean isInStaleMate(final Board board) {
        return Player.calculateAttacksOnTile(this.piecePosition, board.currentPlayer().getOpponent().getLegalMoves())
                .isEmpty() && !hasEscapeMoves(board);
    }

    public boolean isCastled() {
        return this.isCastled;
    }

    @Override
    public List<Move> calculateLegalMoves(final Board board) {
        final Builder<Move> legalMoves = ImmutableList.builder();
        int candidateDestinationCoordinate;
        for (final int currentCandidate : CANDIDATE_MOVE_COORDINATES) {
            if (isFirstColumnExclusion(this.piecePosition, currentCandidate) ||
                isEighthColumnExclusion(this.piecePosition, currentCandidate)) {
                continue;
            }
            candidateDestinationCoordinate = this.piecePosition + currentCandidate;
            if (Board.isValidTileCoordinate(candidateDestinationCoordinate)) {
                final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
                if (!candidateDestinationTile.isTileOccupied()) {
                    legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
                }
                else {
                    final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                    final Alliance pieceAtDestinationAllegiance = pieceAtDestination.getPieceAllegiance();
                    if (this.pieceAlliance != pieceAtDestinationAllegiance) {
                        legalMoves.add(new MajorAttackMove(board, this, candidateDestinationCoordinate,
                                pieceAtDestination));
                    }
                }
            }
        }
        return legalMoves.build();
    }

    @Override
    public int getPieceValue() {
        return PieceType.KING.getPieceValue();
    }

    @Override
    public String toString() {
        return PieceType.KING.toString();
    }

    @Override
    public int locationBonus() {
        return this.pieceAlliance.kingBonus(this.piecePosition);
    }

    @Override
    public King createCopy() {
        return new King(this);
    }

    @Override
    public King movePiece(final Move move) {
        return new King(this.pieceAlliance, move.getDestinationCoordinate(), false, move.isCastle());
    }

    private boolean hasEscapeMoves(final Board board) {
        for(final Move move : board.currentPlayer().getLegalMoves()) {
            final MoveTransition transition = board.makeMove(move);
            if (transition.getMoveStatus() == MoveStatus.DONE) {
                return true;
            }
        }
        return false;
    }

    private static boolean isFirstColumnExclusion(final int currentCandidate,
                                                  final int candidateDestinationCoordinate) {
        return Board.FIRST_COLUMN[currentCandidate]
                && ((candidateDestinationCoordinate == -9) || (candidateDestinationCoordinate == -1) ||
                   (candidateDestinationCoordinate == 7));
    }

    private static boolean isEighthColumnExclusion(final int currentCandidate,
                                                   final int candidateDestinationCoordinate) {
        return Board.EIGHTH_COLUMN[currentCandidate]
                && ((candidateDestinationCoordinate == -7) || (candidateDestinationCoordinate == 1) ||
                   (candidateDestinationCoordinate == 9));
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof King)) {
            return false;
        }
        if (!super.equals(other)) {
            return false;
        }

        final King king = (King) other;

        if (isCastled != king.isCastled) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (isCastled ? 1 : 0);
        return result;
    }
}