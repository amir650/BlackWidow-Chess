package com.chess.engine.classic.pieces;

import com.chess.engine.classic.Alliance;
import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.board.Move;

import java.util.Collection;

public abstract class Piece {

    final PieceType pieceType;
    final Alliance pieceAlliance;
    final int piecePosition;
    private final boolean isFirstMove;
    private final int cachedHashCode;

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
        return this.piecePosition == otherPiece.piecePosition && this.pieceType == otherPiece.pieceType &&
               this.pieceAlliance == otherPiece.pieceAlliance && this.isFirstMove == otherPiece.isFirstMove;
    }

    @Override
    public int hashCode() {
        return this.cachedHashCode;
    }

    private int computeHashCode() {
        int result = this.pieceType.hashCode();
        result = 31 * result + this.pieceAlliance.hashCode();
        result = 31 * result + this.piecePosition;
        result = 31 * result + (this.isFirstMove ? 1 : 0);
        return result;
    }

    public enum PieceType {

        PAWN(100, "P") {
            @Override
            public boolean isPawn() {
                return true;
            }

            @Override
            public boolean isBishop() {
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
        KNIGHT(320, "N") {
            @Override
            public boolean isPawn() {
                return false;
            }

            @Override
            public boolean isBishop() {
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
        BISHOP(350, "B") {
            @Override
            public boolean isPawn() {
                return false;
            }

            @Override
            public boolean isBishop() {
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
        ROOK(500, "R") {
            @Override
            public boolean isPawn() {
                return false;
            }

            @Override
            public boolean isBishop() {
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
            public boolean isBishop() {
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
        KING(20000, "K") {
            @Override
            public boolean isPawn() {
                return false;
            }

            @Override
            public boolean isBishop() {
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

        PieceType(final int val,
                  final String pieceName) {
            this.value = val;
            this.pieceName = pieceName;
        }

        public abstract boolean isPawn();
        public abstract boolean isBishop();
        public abstract boolean isRook();
        public abstract boolean isKing();

    }

}