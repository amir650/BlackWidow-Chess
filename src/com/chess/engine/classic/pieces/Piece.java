package com.chess.engine.classic.pieces;

import java.util.Collection;

import com.chess.engine.classic.Alliance;
import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.board.Move;

public abstract class Piece {

    protected final PieceType pieceType;
    protected final Alliance pieceAlliance;
    protected final int piecePosition;
    protected final boolean isFirstMove;
    protected final int cachedHashCode;

    Piece(final PieceType type,
          final Alliance alliance,
          final int piecePosition,
          final boolean isFirstMove) {
        this.pieceType = type;
        this.piecePosition = piecePosition;
        this.pieceAlliance = alliance;
        this.isFirstMove = isFirstMove;
        this.cachedHashCode = computeHashCode();
    }

    public PieceType getPieceType() {
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

    public int getPieceValue() {
        return this.pieceType.getPieceValue();
    }
    public abstract int locationBonus();

    public abstract Piece movePiece(Move move);

    public abstract Collection<Move> calculateLegalMoves(final Board board);

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Piece)) {
            return false;
        }
        final Piece otherPiece = (Piece) other;
        return piecePosition == otherPiece.piecePosition && pieceType == otherPiece.pieceType &&
               pieceAlliance == otherPiece.pieceAlliance && isFirstMove == otherPiece.isFirstMove;
    }

    @Override
    public int hashCode() {
        return cachedHashCode;
    }

    private int computeHashCode() {
        int result = pieceType.hashCode();
        result = 31 * result + pieceAlliance.hashCode();
        result = 31 * result + piecePosition;
        result = 31 * result + (isFirstMove ? 1 : 0);
        return result;
    }

    public enum PieceType {

        PAWN(100, "P") {
            @Override
            public boolean isPawn() {
                return true;
            }

            @Override
            public boolean isRook() {
                return false;
            }

            @Override
            public boolean isKing() {
                return false;
            }
        },
        KNIGHT(320, "N") {
            @Override
            public boolean isPawn() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }

            @Override
            public boolean isKing() {
                return false;
            }
        },
        BISHOP(330, "B") {
            @Override
            public boolean isPawn() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }

            @Override
            public boolean isKing() {
                return false;
            }
        },
        ROOK(500, "R") {
            @Override
            public boolean isPawn() {
                return false;
            }

            @Override
            public boolean isRook() {
                return true;
            }

            @Override
            public boolean isKing() {
                return false;
            }
        },
        QUEEN(900, "Q") {
            @Override
            public boolean isPawn() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }

            @Override
            public boolean isKing() {
                return false;
            }
        },
        KING(10000, "K") {
            @Override
            public boolean isPawn() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }

            @Override
            public boolean isKing() {
                return true;
            }
        };

        private final int value;
        private final String pieceName;

        public int getPieceValue() {
            return this.value;
        }

        @Override
        public String toString() {
            return this.pieceName;
        }

        PieceType(final int val, final String pieceName) {
            this.value = val;
            this.pieceName = pieceName;
        }

        public abstract boolean isPawn();
        public abstract boolean isRook();
        public abstract boolean isKing();

    }

}