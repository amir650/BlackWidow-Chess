package com.chess.engine.classic.pieces;

import java.util.List;

import com.chess.engine.classic.Alliance;
import com.chess.engine.classic.Copyable;
import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.board.Move;

public abstract class Piece implements Copyable<Piece>{

    protected final Type pieceType;
    protected final Alliance pieceAlliance;
    protected final int piecePosition;
    protected boolean isFirstMove;

    Piece(final Type type,
          final Alliance alliance,
          final int piecePosition,
          final boolean isFirstMove) {
        this.pieceType = type;
        this.piecePosition = piecePosition;
        this.pieceAlliance = alliance;
        this.isFirstMove = isFirstMove;
    }

    Piece(final Piece p) {
        this.pieceType = p.getPieceType();
        this.pieceAlliance = p.getPieceAllegiance();
        this.piecePosition = p.getPiecePosition();
        this.isFirstMove = p.isFirstMove();
    }

    public Type getPieceType() {
        return this.pieceType;
    }

    public Alliance getPieceAllegiance() {
        return this.pieceAlliance;
    }

    public int getPiecePosition() {
        return this.piecePosition;
    }

    public boolean isFirstMove() {
        return this.isFirstMove;
    }

    public abstract int getPieceValue();

    public abstract int locationBonus();

    public abstract Piece createTransitionedPiece(Move move);

    public boolean isKing() {
        return this.pieceType == Type.KING;
    }

    @Override
    public int hashCode() {
        int hash = 1;
        hash = hash * 31 + this.pieceType.hashCode() + this.pieceAlliance.hashCode()
                + this.piecePosition;
        return hash;
    }

    @Override
    public boolean equals(final Object other) {

        if (this == other) {
            return true;
        }
        if (!(other instanceof Piece)) {
            return false;
        }

        final Piece otherPiece = (Piece) other;

        return (this.pieceType == otherPiece.getPieceType())
                && (this.pieceAlliance == otherPiece.getPieceAllegiance())
                && (this.piecePosition == otherPiece.getPiecePosition());

    }

    public abstract List<Move> calculateLegalMoves(final Board b);

    public enum Type {

        PAWN(100, "P"),
        KNIGHT(300, "N"),
        BISHOP(300, "B"),
        ROOK(500, "R"),
        QUEEN(900, "Q"),
        KING(10000, "K");

        private final int value;
        private final String pieceName;

        public int getPieceValue() {
            return this.value;
        }

        @Override
        public String toString() {
            return this.pieceName;
        }

        Type(final int val, final String pieceName) {
            this.value = val;
            this.pieceName = pieceName;
        }

    }

}