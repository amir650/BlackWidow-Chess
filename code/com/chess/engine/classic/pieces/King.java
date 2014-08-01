package com.chess.engine.classic.pieces;

import java.util.List;

import com.chess.engine.classic.Alliance;
import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.board.Board.MoveStatus;
import com.chess.engine.classic.board.Move;
import com.chess.engine.classic.board.Move.AttackMove;
import com.chess.engine.classic.board.MoveTransition;
import com.chess.engine.classic.board.Tile;
import com.chess.engine.classic.player.Player;
import com.google.common.collect.ImmutableList.Builder;

public final class King extends Piece {

    private final static int[] CANDIDATE_MOVE_COORDINATES = { -9, -8, -7, -1, 1, 7, 8, 9 };
    private final boolean isCastled;

    public King(final Alliance alliance, final int piecePosition) {
        super(Type.KING, alliance, piecePosition, true);
        this.isCastled = false;
    }

    public King(final Alliance alliance,
                final int piecePosition,
                final boolean isFirstMove,
                final boolean isCastled) {
        super(Type.KING, alliance, piecePosition, isFirstMove);
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
        final Builder<Move> legalMoves = new Builder<>();
        int candidateDestinationCoordinate;
        for (final int currentCandidate : CANDIDATE_MOVE_COORDINATES) {
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
        return legalMoves.build();
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

    @Override
    public King movePiece(final Move move) {
        return new King(move.getMovedPiece().getPieceAllegiance(), move.getDestinationCoordinate(), false, move.isCastle());
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

}