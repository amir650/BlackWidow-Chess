package com.chess.engine.classic.player;

import java.util.Collection;
import java.util.List;

import com.chess.engine.classic.Alliance;
import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.board.Board.MoveStatus;
import com.chess.engine.classic.board.Move;
import com.chess.engine.classic.board.MoveTransition;
import com.chess.engine.classic.pieces.King;
import com.chess.engine.classic.pieces.Piece;
import com.chess.engine.classic.player.ai.MoveStrategy;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import org.magicwerk.brownies.collections.GapList;

public abstract class Player {

    protected final Board board;
    protected final King playerKing;
    protected final Collection<Move> legalMoves;
    private MoveStrategy strategy;

    Player(final Board board,
           final Collection<Move> playerLegals,
           final Collection<Move> opponentLegals) {
        this.board = board;
        this.playerKing = establishKing();
        this.legalMoves = ImmutableList.copyOf(
                Iterables.concat(playerLegals, calculateKingCastles(playerLegals, opponentLegals)));
    }

    public boolean isMoveLegal(final Move move) {
        return !(move.isCastlingMove() && isInCheck()) && this.legalMoves.contains(move);
    }

    public boolean isInCheck() {
        return this.playerKing.isInCheck(getOpponent().getLegalMoves());
    }

    public boolean isInCheckMate() {
       return this.playerKing.isInCheckMate(this.board);
    }

    public boolean isInStaleMate() {
        return this.playerKing.isInStaleMate(this.board);
    }

    public boolean isCastled() {
        return this.playerKing.isCastled();
    }

    public boolean isKingSideCastleCapable() {
        return this.playerKing.isKingSideCastleCapable();
    }

    public boolean isQueenSideCastleCapable() {
        return this.playerKing.isQueenSideCastleCapable();
    }

    public MoveStrategy getMoveStrategy() {
        return this.strategy;
    }

    public King getPlayerKing() {
        return this.playerKing;
    }

    public String playerInfo() {
        return ("Player is: " +this.getAlliance() + "\nlegal moves =" + getLegalMoves() + "\ninCheck = " +
                isInCheck() + "\nisInCheckMate = " +isInCheckMate() +
                "\nisCastled = " +isCastled())+ "\n";
    }

    protected King establishKing() {
        for(final Piece piece : getActivePieces()) {
            if(piece.getPieceType().isKing()) {
                return (King) piece;
            }
        }
        throw new RuntimeException("Should not reach here! " +this.getAlliance()+ " king could not be established!");
    }

    public Collection<Move> calculateAttacksOnPiece(final Piece piece) {
        final List<Move> attackMoves = new GapList<>();
        final int piecePosition = piece.getPiecePosition();
        for (final Move move : this.legalMoves) {
            if (piecePosition == move.getDestinationCoordinate()) {
                attackMoves.add(move);
            }
        }
        return ImmutableList.copyOf(attackMoves);
    }

    public Collection<Move> getLegalMoves() {
        return this.legalMoves;
    }

    public void setMoveStrategy(final MoveStrategy strategy) {
        this.strategy = strategy;
    }

    public static Collection<Move> calculateAttacksOnTile(final int tile,
                                                          final Collection<Move> moves) {
        final List<Move> attackMoves = new GapList<>();
        for (final Move move : moves) {
            if (tile == move.getDestinationCoordinate()) {
                attackMoves.add(move);
            }
        }
        return ImmutableList.copyOf(attackMoves);
    }

    public MoveTransition makeMove(final Move move) {
        if (!isMoveLegal(move)) {
            return new MoveTransition(this.board, move, MoveStatus.ILLEGAL_MOVE);
        }
        final Board transitionedBoard = move.execute();
        final Collection<Move> kingAttacks = transitionedBoard.currentPlayer()
                .calculateAttacksOnPiece(transitionedBoard.currentPlayer().getOpponent().getPlayerKing());
        if (!kingAttacks.isEmpty()) {
            return new MoveTransition(this.board, move, MoveStatus.LEAVES_PLAYER_IN_CHECK);
        }
        return new MoveTransition(transitionedBoard, move, MoveStatus.DONE);
    }

    public MoveTransition unMakeMove(final Move move) {
        return new MoveTransition(move.undo(), move, MoveStatus.DONE);
    }

    public abstract Collection<Piece> getActivePieces();
    public abstract Alliance getAlliance();
    public abstract Player getOpponent();
    protected abstract Collection<Move> calculateKingCastles(Collection<Move> playerLegals, Collection<Move> opponentLegals);

}
