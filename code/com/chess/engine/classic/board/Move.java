package com.chess.engine.classic.board;

import com.chess.engine.classic.pieces.King;
import com.chess.engine.classic.pieces.Pawn;
import com.chess.engine.classic.pieces.Piece;
import com.chess.engine.classic.pieces.Piece.Type;
import com.chess.engine.classic.pieces.Queen;
import com.chess.engine.classic.pieces.Rook;
import com.chess.engine.classic.player.Player;

public class Move {

    protected final int currentCoordinate;
    protected final int destinationCoordinate;
    protected final Piece movedPiece;
    protected final boolean isFirstMove;

    public Move(final int currentCoordinate,
                final int destinationCoordinate,
                final Piece pieceMoved) {
        this.currentCoordinate = currentCoordinate;
        this.destinationCoordinate = destinationCoordinate;
        this.movedPiece = pieceMoved;
        this.isFirstMove = pieceMoved.isFirstMove();
    }

    public Move(final int currentCoordinate, final int destinationCoordinate) {
        this.currentCoordinate = currentCoordinate;
        this.destinationCoordinate = destinationCoordinate;
        this.movedPiece = null;
        this.isFirstMove = false;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + this.currentCoordinate;
        result = prime * result + this.destinationCoordinate;
        result = prime * result
                + ((this.movedPiece == null) ? 0 : this.movedPiece.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object aThat) {
        if (this == aThat) {
            return true;
        }
        if (!(aThat instanceof Move) ) {
            return false;
        }
        final Move that = (Move)aThat;

        return((getCurrentCoordinate() == that.getCurrentCoordinate()) &&
               (getDestinationCoordinate() == that.getDestinationCoordinate()) &&
               (getMovedPiece().equals(that.getMovedPiece())));
    }

    public int getCurrentCoordinate() {
        return this.currentCoordinate;
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

    public Piece getAttackedPiece() {
        return null;
    }

    public void execute(final Board board, final Player player) {
        final Tile destinationLocation = board.getTile(this.destinationCoordinate);
        final Piece movedPiece = board.getTile(this.currentCoordinate).removePiece();
        destinationLocation.setPiece(movedPiece);
        movedPiece.setIsFirstMove(false);
        board.setEnPassantCapture(null);
    }

    public void undo(final Board board, final Player player) {
        final Tile startLocation = board.getTile(this.destinationCoordinate);
        final Tile destinationLocation = board.getTile(this.currentCoordinate);
        final Piece p = startLocation.removePiece();
        destinationLocation.setPiece(p);
        p.setIsFirstMove(this.isFirstMove);
    }

    @Override
    public String toString() {
        final StringBuilder s = new StringBuilder(Board.getPositionAtCoordinate(this.currentCoordinate));
        s.append("-");
        s.append(Board.getPositionAtCoordinate(this.destinationCoordinate));
        return s.toString();
    }

    public static class PawnPromotion extends Move {

        final Move decoratedMove;
        final Pawn promotedPawn;

        public PawnPromotion(final Move decoratedMove) {
            super(decoratedMove.getCurrentCoordinate(), decoratedMove.getDestinationCoordinate(), decoratedMove.getMovedPiece());
            this.decoratedMove = decoratedMove;
            this.promotedPawn = (Pawn)decoratedMove.getMovedPiece();
        }

        @Override
        public int hashCode() {
            return decoratedMove.hashCode() + (31 * promotedPawn.hashCode());
        }

        @Override
        public boolean equals(final Object aThat) {
            return this == aThat || aThat instanceof PawnPromotion && (super.equals(aThat));
        }

        @Override
        public void execute(final Board board, final Player player) {
            decoratedMove.execute(board, player);
            final Queen promotion = new Queen(player.getAlliance());
            board.setPiece(decoratedMove.getDestinationCoordinate(), promotion);
            player.getActivePieces().add(promotion);
        }

        @Override
        public void undo(final Board board, final Player player) {
            final Piece queen = board.getTile(this.getDestinationCoordinate()).getPiece();
            player.getActivePieces().remove(queen);
            decoratedMove.undo(board, player);
        }

        @Override
        public boolean isAttack() {
            return decoratedMove.isAttack();
        }

        @Override
        public Piece getAttackedPiece() {
            return this.decoratedMove.getAttackedPiece();
        }

        @Override
        public String toString() {
            final StringBuilder s = new StringBuilder(Board.getPositionAtCoordinate(this.currentCoordinate));
            s.append("-");
            s.append(Board.getPositionAtCoordinate(this.destinationCoordinate));
            s.append("=");
            s.append(Type.QUEEN.toString());
            return s.toString();
        }

    }

    public static class PawnJump extends Move {

        public PawnJump(final int currentCoordinate,
                        final int destinationCoordinate,
                        final Pawn pieceMoved) {
            super(currentCoordinate, destinationCoordinate, pieceMoved);
        }

        @Override
        public boolean equals(final Object aThat) {
            return this == aThat || aThat instanceof PawnJump && (super.equals(aThat));
        }

        @Override
        public void execute(final Board board, final Player player) {
            final Tile destinationLocation = board.getTile(this.destinationCoordinate);
            final Piece movedPiece = board.getTile(this.currentCoordinate).removePiece();
            destinationLocation.setPiece(movedPiece);
            movedPiece.setIsFirstMove(false);
            board.setEnPassantCapture((Pawn)movedPiece);
        }

        @Override
        public void undo(final Board board, final Player player) {
            final Tile startLocation = board.getTile(this.destinationCoordinate);
            final Tile destinationLocation = board.getTile(this.currentCoordinate);
            final Piece p = startLocation.removePiece();
            destinationLocation.setPiece(p);
            p.setIsFirstMove(this.isFirstMove);
            board.setEnPassantCapture(null);
        }

    }

    static abstract class CastleMove extends Move {

        protected final Rook castleRook;
        protected  final int castleRookStart;
        protected final int castleRookDestination;

        CastleMove(final int currentCoordinate,
                   final int destinationCoordinate,
                   final Piece pieceMoved,
                   final Rook castleRook,
                   final int castleRookStart,
                   final int castleRookDestination) {
            super(currentCoordinate, destinationCoordinate, pieceMoved);
            this.castleRook = castleRook;
            this.castleRookStart = castleRookStart;
            this.castleRookDestination = castleRookDestination;
        }

        public Rook getCastleRook() {
            return this.castleRook;
        }

        @Override
        public void execute(final Board board, final Player player) {
            final Move p1 = new Move(this.currentCoordinate, this.destinationCoordinate, this.movedPiece);
            p1.execute(board, player);
            final Move p2 = new Move(this.castleRook.getPiecePosition(), this.castleRookDestination, this.castleRook);
            p2.execute(board, player);
            player.getPlayerKing().setIsCastled(true);
        }

        @Override
        public void undo(final Board board, final Player player) {
            final Move p1 = new Move(this.destinationCoordinate, this.currentCoordinate, this.movedPiece);
            p1.execute(board, player);
            final Move p2 = new Move(this.castleRookDestination, this.castleRookStart, this.castleRook);
            p2.execute(board, player);
            final King king = player.getPlayerKing();
            king.setIsCastled(false);
            king.setIsFirstMove(true);
            this.castleRook.setIsFirstMove(true);
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
        public boolean equals(final Object aThat) {
            if (this == aThat) {
                return true;
            }
            if (!(aThat instanceof CastleMove) ) {
                return false;
            }
            final CastleMove that = (CastleMove)aThat;
            return(super.equals(that) && this.castleRook.equals(that.getCastleRook()));
        }

    }

    public static class KingSideCastleMove extends CastleMove {


        public KingSideCastleMove(final int currentCoordinate,
                                  final int destinationCoordinate,
                                  final Piece pieceMoved,
                                  final Rook castleRook,
                                  final int castleRookStart,
                                  final int castleRookDestination) {
            super(currentCoordinate, destinationCoordinate, pieceMoved, castleRook, castleRookStart, castleRookDestination);
        }

        @Override
        public boolean equals(final Object aThat) {
            if (this == aThat) {
                return true;
            }
            if (!(aThat instanceof KingSideCastleMove)) {
                return false;
            }
            final KingSideCastleMove that = (KingSideCastleMove)aThat;
            return(super.equals(that) && this.castleRook.equals(that.getCastleRook()));
        }

        @Override
        public String toString() {
            return "0-0";
        }

    }

    public static class QueenSideCastleMove extends CastleMove {

        public QueenSideCastleMove(final int currentCoordinate,
                                   final int destinationCoordinate,
                                   final Piece pieceMoved,
                                   final Rook castleRook,
                                   final int castleRookStart,
                                   final int rookCastleDestination) {
            super(currentCoordinate, destinationCoordinate, pieceMoved, castleRook, castleRookStart, rookCastleDestination);
        }

        @Override
        public boolean equals(final Object aThat) {
            if (this == aThat) {
                return true;
            }
            if (!(aThat instanceof QueenSideCastleMove) ) {
                return false;
            }
            final QueenSideCastleMove that = (QueenSideCastleMove)aThat;
            return(super.equals(that) && castleRook.equals(that.getCastleRook()));
        }

        @Override
        public String toString() {
            return "0-0-0";
        }

    }

    public static class AttackMove extends Move {

        private final Piece attackedPiece;

        public AttackMove(final int currentCoordinate,
                          final int destinationCoordinate,
                          final Piece pieceMoved,
                          final Piece pieceAttacked) {
            super(currentCoordinate, destinationCoordinate, pieceMoved);
            this.attackedPiece = pieceAttacked;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + this.currentCoordinate;
            result = prime * result + this.destinationCoordinate;
            result = prime * result
                    + ((this.movedPiece == null) ? 0 : this.movedPiece.hashCode());
            result = prime * result + this.attackedPiece.hashCode();
            return result;
        }

        @Override
        public boolean equals(final Object aThat) {
            if (this == aThat) {
                return true;
            }
            if (!(aThat instanceof AttackMove)) {
                return false;
            }
            final AttackMove that = (AttackMove)aThat;
            return (super.equals(that) && (getAttackedPiece().equals(that.getAttackedPiece())));
        }

        @Override
        public String toString() {
            final StringBuilder s = new StringBuilder(Board.getPositionAtCoordinate(this.currentCoordinate));
            s.append("x");
            s.append(Board.getPositionAtCoordinate(this.destinationCoordinate));
            return s.toString();
        }

        @Override
        public Piece getAttackedPiece() {
            return this.attackedPiece;
        }

        @Override
        public boolean isAttack() {
            return true;
        }

        @Override
        public void execute(final Board board, final Player player) {
            final Tile destinationLocation = board.getTile(this.destinationCoordinate);
            final Piece movedPiece = board.getTile(this.currentCoordinate).removePiece();
            final Piece attackedPiece = destinationLocation.getPiece();
            destinationLocation.setPiece(movedPiece);
            player.getOpponent().getActivePieces().remove(attackedPiece);
            movedPiece.setIsFirstMove(false);
        }

        @Override
        public void undo(final Board board, final Player player) {
            final Tile startLocation = board.getTile(this.destinationCoordinate);
            final Tile destinationLocation = board.getTile(this.currentCoordinate);
            destinationLocation.setPiece(this.movedPiece);
            startLocation.setPiece(this.attackedPiece);
            player.getOpponent().getActivePieces().add(this.attackedPiece);
            this.movedPiece.setIsFirstMove(this.isFirstMove);
        }

    }

    static class NullMove extends Move {

        public NullMove(final int currentCoordinate, final int destinationCoordinate) {
            super(currentCoordinate, destinationCoordinate);
        }

        @Override
        public void execute(final Board board, final Player player) {
            throw new RuntimeException("cannot execute null move!");
        }

        @Override
        public void undo(final Board board, final Player player) {
            throw new RuntimeException("cannot undo null move!");
        }

        @Override
        public String toString() {
            final StringBuilder s = new StringBuilder(Board.getPositionAtCoordinate(this.currentCoordinate));
            s.append("-");
            s.append(Board.getPositionAtCoordinate(this.destinationCoordinate));
            return s.toString();
        }
    }

        public static class MoveFactory {

        private MoveFactory() {
        }

        public static Move createMove(final Board board,
                                      final int currentCoordinate,
                                      final int destinationCoordinate) {
            for(final Move m : board.whitePlayer().getLegalMoves()) {
                if(m.getCurrentCoordinate() == currentCoordinate && m.getDestinationCoordinate() == destinationCoordinate) {
                    return m;
                }
            }
            for(final Move m : board.blackPlayer().getLegalMoves()) {
                if(m.getCurrentCoordinate() == currentCoordinate && m.getDestinationCoordinate() == destinationCoordinate) {
                    return m;
                }
            }
            return new NullMove(currentCoordinate, destinationCoordinate);
        }
    }
}