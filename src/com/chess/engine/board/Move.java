package com.chess.engine.board;

import com.chess.engine.board.Board.Builder;
import com.chess.engine.pieces.King;
import com.chess.engine.pieces.Pawn;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Rook;

import java.util.Objects;

public abstract class Move {

    protected final Board board;
    protected final Piece movedPiece;
    protected final int destinationCoordinate;
    protected final boolean isFirstMove;

    private Move(final Board board,
                 final Piece pieceMoved,
                 final int destinationCoordinate) {
        this.board = board;
        this.destinationCoordinate = destinationCoordinate;
        this.movedPiece = pieceMoved;
        this.isFirstMove = pieceMoved.isFirstMove();
    }

    private Move(final Board board,
                 final int destinationCoordinate) {
        this.board = board;
        this.destinationCoordinate = destinationCoordinate;
        this.movedPiece = null;
        this.isFirstMove = false;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + this.destinationCoordinate;
        result = 31 * result + this.movedPiece.hashCode();
        result = result + (isFirstMove ? 1 : 0);
        return result;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Move)) {
            return false;
        }
        final Move otherMove = (Move) other;
        return getCurrentCoordinate() == otherMove.getCurrentCoordinate() &&
                getDestinationCoordinate() == otherMove.getDestinationCoordinate() &&
                getMovedPiece().equals(otherMove.getMovedPiece());
    }

    public Board getBoard() {
        return this.board;
    }

    public int getCurrentCoordinate() {
        return this.movedPiece.getPiecePosition();
    }

    public int getDestinationCoordinate() {
        return this.destinationCoordinate;
    }

    public Piece getMovedPiece() {
        return this.movedPiece;
    }

    public boolean isAttack() {
        return false;
    }

    public boolean isCastlingMove() {
        return false;
    }

    public Piece getAttackedPiece() {
        return null;
    }

    public Board execute() {
        final Piece[] newBoardConfig = board.getBoardCopy();
        newBoardConfig[this.movedPiece.getPiecePosition()] = null;
        newBoardConfig[this.destinationCoordinate] = this.movedPiece.getMovedPiece(this);
        final Builder builder = new Builder();
        return builder.setBoardConfiguration(newBoardConfig)
                .setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance())
                .build();
    }

    public Board undo() {
        final Piece[] newBoardConfig = board.getBoardCopy();
        final Builder builder = new Builder();
        return builder.setBoardConfiguration(newBoardConfig)
                .setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance())
                .build();
    }

    String disambiguation() {
        final Piece movedPiece = this.getMovedPiece();
        final String from = BoardUtils.INSTANCE.getPositionAtCoordinate(this.getCurrentCoordinate());
        final char fromFile = from.charAt(0);
        final char fromRank = from.charAt(1);
        boolean fileNeeded = false;
        boolean rankNeeded = false;

        for (final Move move : board.currentPlayer().getLegalMoves()) {
            if (move == this) {
                continue;
            }
            if (move.getMovedPiece().getPieceType() == movedPiece.getPieceType() &&
                move.getDestinationCoordinate() == this.getDestinationCoordinate()) {
                final MoveTransition otherTransition = board.currentPlayer().makeMove(move);
                if (!otherTransition.getMoveStatus().isDone()) {
                    continue;
                }
                final String otherFrom = BoardUtils.INSTANCE.getPositionAtCoordinate(move.getCurrentCoordinate());
                final char otherFile = otherFrom.charAt(0);
                final char otherRank = otherFrom.charAt(1);
                if (fromFile != otherFile) {
                    fileNeeded = true;
                }
                if (fromRank != otherRank) {
                    rankNeeded = true;
                }
            }
        }

        if (fileNeeded) {
            return "" + fromFile;
        }
        if (rankNeeded) {
            return "" + fromRank;
        }
        return "";
    }

    public enum MoveStatus {

        DONE {
            @Override
            public boolean isDone() {
                return true;
            }
        },
        ILLEGAL_MOVE {
            @Override
            public boolean isDone() {
                return false;
            }
        },
        LEAVES_PLAYER_IN_CHECK {
            @Override
            public boolean isDone() {
                return false;
            }
        };
        public abstract boolean isDone();
    }

    public static class PawnPromotion
            extends PawnMove {

        final Move decoratedMove;
        final Pawn promotedPawn;
        final Piece promotionPiece;

        public PawnPromotion(final Move decoratedMove,
                             final Piece promotionPiece) {
            super(decoratedMove.getBoard(), decoratedMove.getMovedPiece(), decoratedMove.getDestinationCoordinate());
            this.decoratedMove = decoratedMove;
            this.promotedPawn = (Pawn) decoratedMove.getMovedPiece();
            this.promotionPiece = promotionPiece;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;
            final PawnPromotion that = (PawnPromotion) o;
            return Objects.equals(decoratedMove, that.decoratedMove) && Objects.equals(promotedPawn, that.promotedPawn) && Objects.equals(promotionPiece, that.promotionPiece);
        }

        @Override
        public int hashCode() {
            return Objects.hash(super.hashCode(), decoratedMove, promotedPawn, promotionPiece);
        }

        @Override
        public Board execute() {
            final Board pawnMovedBoard = this.decoratedMove.execute();
            final Builder builder = new Builder();
            final Piece[] boardConfig = pawnMovedBoard.getBoardCopy();
            final int[] currentActive = pawnMovedBoard.currentPlayer().getActivePieces();
            final int[] opponentActive = pawnMovedBoard.currentPlayer().getOpponent().getActivePieces();

            for (final int index : currentActive) {
                final Piece piece = boardConfig[index];
                if (!this.promotedPawn.equals(piece)) {
                    builder.setPiece(piece);
                }
            }

            for (final int index : opponentActive) {
                builder.setPiece(boardConfig[index]);
            }

            builder.setPiece(this.promotionPiece.getMovedPiece(this));
            builder.setMoveMaker(pawnMovedBoard.currentPlayer().getAlliance());
            return builder.build();
        }


        @Override
        public boolean isAttack() {
            return this.decoratedMove.isAttack();
        }

        @Override
        public Piece getAttackedPiece() {
            return this.decoratedMove.getAttackedPiece();
        }

        @Override
        public String toString() {
            final Move decorated = this.decoratedMove; // The underlying pawn move or attack
            final StringBuilder san = new StringBuilder();
            if (decorated.isAttack()) {
                // For pawn captures, use file of from-square, 'x', and destination
                final String fromFile = BoardUtils.INSTANCE.getPositionAtCoordinate(decorated.getCurrentCoordinate()).substring(0,1);
                final String toSquare = BoardUtils.INSTANCE.getPositionAtCoordinate(decorated.getDestinationCoordinate());
                san.append(fromFile).append("x").append(toSquare).append("=").append(this.promotionPiece.getPieceType());
            } else {
                // For pawn push, just use destination
                final String toSquare = BoardUtils.INSTANCE.getPositionAtCoordinate(decorated.getDestinationCoordinate());
                san.append(toSquare).append("=").append(this.promotionPiece.getPieceType());
            }
            return san.toString();
        }

    }

    public static class MajorMove
            extends Move {

        public MajorMove(final Board board,
                         final Piece pieceMoved,
                         final int destinationCoordinate) {
            super(board, pieceMoved, destinationCoordinate);
        }

        @Override
        public boolean equals(final Object other) {
            return this == other || other instanceof MajorMove && super.equals(other);
        }

        @Override
        public String toString() {
            return movedPiece.getPieceType().toString() + disambiguation() +
                    BoardUtils.INSTANCE.getPositionAtCoordinate(this.destinationCoordinate);
        }

    }

    public static class PawnMove
            extends Move {

        public PawnMove(final Board board,
                        final Piece pieceMoved,
                        final int destinationCoordinate) {
            super(board, pieceMoved, destinationCoordinate);
        }

        @Override
        public boolean equals(final Object other) {
            return this == other || other instanceof PawnMove && super.equals(other);
        }

        @Override
        public String toString() {
            return BoardUtils.INSTANCE.getPositionAtCoordinate(this.destinationCoordinate);
        }

    }

    public static class MajorAttackMove
            extends AttackMove {

        public MajorAttackMove(final Board board,
                               final Piece pieceMoved,
                               final int destinationCoordinate,
                               final Piece pieceAttacked) {
            super(board, pieceMoved, destinationCoordinate, pieceAttacked);
        }

        @Override
        public boolean equals(final Object other) {
            return this == other || other instanceof MajorAttackMove && super.equals(other);
        }

        @Override
        public String toString() {
            return movedPiece.getPieceType() +
                              disambiguation() + "x" +
                              BoardUtils.INSTANCE.getPositionAtCoordinate(this.destinationCoordinate);
        }

    }

    public static class PawnAttackMove
            extends AttackMove {

        public PawnAttackMove(final Board board,
                              final Piece pieceMoved,
                              final int destinationCoordinate,
                              final Piece pieceAttacked) {
            super(board, pieceMoved, destinationCoordinate, pieceAttacked);
        }

        @Override
        public boolean equals(final Object other) {
            return this == other || other instanceof PawnAttackMove && super.equals(other);
        }

        @Override
        public String toString() {
            return BoardUtils.INSTANCE.getPositionAtCoordinate(this.movedPiece.getPiecePosition()).charAt(0) + "x" +
                   BoardUtils.INSTANCE.getPositionAtCoordinate(this.destinationCoordinate);
        }

    }

    public static class PawnEnPassantAttack extends PawnAttackMove {

        public PawnEnPassantAttack(final Board board,
                                   final Piece pieceMoved,
                                   final int destinationCoordinate,
                                   final Piece pieceAttacked) {
            super(board, pieceMoved, destinationCoordinate, pieceAttacked);
        }

        @Override
        public boolean equals(final Object other) {
            return this == other || other instanceof PawnEnPassantAttack && super.equals(other);
        }

        @Override
        public Board execute() {
            final Builder builder = new Builder();
            final Piece[] boardConfig = this.board.getBoardCopy();
            final int[] currentActive = this.board.currentPlayer().getAlliance().isWhite()
                    ? this.board.getWhitePieceCoordinates()
                    : this.board.getBlackPieceCoordinates();
            final int[] opponentActive = this.board.currentPlayer().getAlliance().isWhite()
                    ? this.board.getBlackPieceCoordinates()
                    : this.board.getWhitePieceCoordinates();
            // Add current player's pieces (excluding the moved one)
            for (int index : currentActive) {
                final Piece piece = boardConfig[index];
                if (!this.movedPiece.equals(piece)) {
                    builder.setPiece(piece);
                }
            }
            // Add opponent pieces (excluding the one being captured)
            for (int index : opponentActive) {
                final Piece piece = boardConfig[index];
                if (!piece.equals(this.getAttackedPiece())) {
                    builder.setPiece(piece);
                }
            }
            // Add moved piece to destination
            builder.setPiece(this.movedPiece.getMovedPiece(this));
            builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());
            return builder.build();
        }


        @Override
        public Board undo() {
            final Piece[] newBoardConfig = board.getBoardCopy();
            final Builder builder = new Builder();
            return builder.setBoardConfiguration(newBoardConfig)
                    .setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance())
                    .setEnPassantPawn((Pawn)this.getAttackedPiece())
                    .build();
        }

        @Override
        public String toString() {
            return "EP" + super.toString();
        }

    }

    public static class PawnJump
            extends Move {

        public PawnJump(final Board board,
                        final Pawn pieceMoved,
                        final int destinationCoordinate) {
            super(board, pieceMoved, destinationCoordinate);
        }

        @Override
        public boolean equals(final Object other) {
            return this == other || other instanceof PawnJump && super.equals(other);
        }

        @Override
        public Board execute() {
            final Piece[] newBoardConfig = board.getBoardCopy();
            newBoardConfig[this.movedPiece.getPiecePosition()] = null;
            final Pawn movedPawn = (Pawn)this.movedPiece.getMovedPiece(this);
            newBoardConfig[this.destinationCoordinate] = movedPawn;
            final Builder builder = new Builder();
            return builder.setBoardConfiguration(newBoardConfig)
                          .setEnPassantPawn(movedPawn)
                          .setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance())
                          .build();
        }

        @Override
        public String toString() {
            return BoardUtils.INSTANCE.getPositionAtCoordinate(this.destinationCoordinate);
        }

    }

    static abstract class CastleMove extends Move {

        final Rook castleRook;
        final int castleRookDestination;

        CastleMove(final Board board,
                   final King movedKing,
                   final int destinationCoordinate,
                   final Rook castleRook,
                   final int castleRookDestination) {
            super(board, movedKing, destinationCoordinate);
            this.castleRook = castleRook;
            this.castleRookDestination = castleRookDestination;
        }

        Rook getCastleRook() {
            return this.castleRook;
        }

        @Override
        public boolean isCastlingMove() {
            return true;
        }

        @Override
        public Board execute() {
            final Piece[] newBoardConfig = board.getBoardCopy();
            newBoardConfig[this.movedPiece.getPiecePosition()] = null;
            newBoardConfig[this.castleRook.getPiecePosition()] = null;
            final Piece newKing = this.movedPiece.getMovedPiece(this);
            newBoardConfig[this.destinationCoordinate] = newKing;
            final Move syntheticMove = new MajorMove(this.board, this.castleRook, this.castleRookDestination);
            final Piece newRook = this.castleRook.getMovedPiece(syntheticMove);
            newBoardConfig[this.castleRookDestination] = newRook;
            final Builder builder = new Builder();
            return builder.setBoardConfiguration(newBoardConfig)
                          .setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance())
                          .build();
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = super.hashCode();
            result = prime * result + this.castleRook.hashCode();
            result = prime * result + this.castleRookDestination;
            return result;
        }

        @Override
        public boolean equals(final Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof CastleMove)) {
                return false;
            }
            final CastleMove otherCastleMove = (CastleMove) other;
            return super.equals(otherCastleMove) && this.castleRook.equals(otherCastleMove.getCastleRook());
        }
    }

    public static class KingSideCastleMove
            extends CastleMove {

        public KingSideCastleMove(final Board board,
                                  final King movedKing,
                                  final int kingDestination,
                                  final Rook castleRook,
                                  final int rookDestination) {
            super(board, movedKing, kingDestination, castleRook, rookDestination);
        }

        @Override
        public boolean equals(final Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof KingSideCastleMove)) {
                return false;
            }
            final KingSideCastleMove otherKingSideCastleMove = (KingSideCastleMove) other;
            return super.equals(otherKingSideCastleMove) && this.castleRook.equals(otherKingSideCastleMove.getCastleRook());
        }

        @Override
        public String toString() {
            return "O-O";
        }

    }

    public static class QueenSideCastleMove
            extends CastleMove {

        public QueenSideCastleMove(final Board board,
                                   final King pieceMoved,
                                   final int kingDestination,
                                   final Rook castleRook,
                                   final int rookCastleDestination) {
            super(board, pieceMoved, kingDestination, castleRook, rookCastleDestination);
        }

        @Override
        public boolean equals(final Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof QueenSideCastleMove)) {
                return false;
            }
            final QueenSideCastleMove otherQueenSideCastleMove = (QueenSideCastleMove) other;
            return super.equals(otherQueenSideCastleMove) && this.castleRook.equals(otherQueenSideCastleMove.getCastleRook());
        }

        @Override
        public String toString() {
            return "O-O-O";
        }

    }

    static abstract class AttackMove
            extends Move {

        private final Piece attackedPiece;

        AttackMove(final Board board,
                   final Piece pieceMoved,
                   final int destinationCoordinate,
                   final Piece pieceAttacked) {
            super(board, pieceMoved, destinationCoordinate);
            this.attackedPiece = pieceAttacked;
        }

        @Override
        public int hashCode() {
            return this.attackedPiece.hashCode() + super.hashCode();
        }

        @Override
        public boolean equals(final Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof AttackMove)) {
                return false;
            }
            final AttackMove otherAttackMove = (AttackMove) other;
            return super.equals(otherAttackMove) && getAttackedPiece().equals(otherAttackMove.getAttackedPiece());
        }

        @Override
        public Piece getAttackedPiece() {
            return this.attackedPiece;
        }

        @Override
        public boolean isAttack() {
            return true;
        }

    }

    static class NullMove
            extends Move {

        NullMove() {
            super(null, -1);
        }

        @Override
        public int getCurrentCoordinate() {
            return -1;
        }

        @Override
        public int getDestinationCoordinate() {
            return -1;
        }

        @Override
        public Board execute() {
            throw new RuntimeException("cannot execute null move!");
        }

        @Override
        public String toString() {
            return "Null Move";
        }
    }

    public static class MoveFactory {

        private MoveFactory() {
            throw new RuntimeException("Not instantiatable!");
        }

        public static Move getNullMove() {
            return MoveUtils.NULL_MOVE;
        }

        public static Move createMove(final Board board,
                                      final int currentCoordinate,
                                      final int destinationCoordinate) {
            for (final Move move : board.getAllLegalMoves()) {
                if (move.getCurrentCoordinate() == currentCoordinate &&
                    move.getDestinationCoordinate() == destinationCoordinate) {
                    return move;
                }
            }
            return MoveUtils.NULL_MOVE;
        }

        public static Move createPromotionMove(final Board board,
                                               final int currentCoordinate,
                                               final int destinationCoordinate,
                                               final Piece.PieceType pieceType) {
            for (final Move move : MoveUtils.getPromotionOnlyMoves(board.currentPlayer())) {
                final PawnPromotion pawnPromotionMove = (PawnPromotion)move;
                if (move.getCurrentCoordinate() == currentCoordinate &&
                    move.getDestinationCoordinate() == destinationCoordinate && pawnPromotionMove.promotionPiece.getPieceType() == pieceType) {
                    return move;
                }
            }
            return MoveUtils.NULL_MOVE;
        }

    }
}
