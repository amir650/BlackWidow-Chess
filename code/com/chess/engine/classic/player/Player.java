package com.chess.engine.classic.player;

import java.util.List;

import com.chess.engine.classic.Alliance;
import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.board.Move;
import com.chess.engine.classic.pieces.King;
import com.chess.engine.classic.pieces.Piece;
import com.chess.engine.classic.player.ai.MoveStrategy;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

public abstract class Player {

    protected final Board board;
    protected final King playerKing;
    protected final List<Move> legalMoves;
    private MoveStrategy strategy;

    Player(final Board board,
           final List<Move> playerLegals,
           final List<Move> opponentLegals) {
        this.board = board;
        this.playerKing = establishKing();
        this.legalMoves = ImmutableList.copyOf(Iterables.concat(playerLegals, calculateKingCastles(playerLegals, opponentLegals)));
    }

    public boolean isMoveLegal(final Move move) {
        return this.legalMoves.contains(move);
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

    public List <Move> calculateAttacksOnTile(final int tile) {
        final ImmutableList.Builder<Move> attackMoves = ImmutableList.builder();
        for (final Move move : this.legalMoves) {
            if (tile == move.getDestinationCoordinate()) {
                attackMoves.add(move);
            }
        }
        return attackMoves.build();
    }

    public List<Move> getLegalMoves() {
        return this.legalMoves;
    }

    public void setMoveStrategy(final MoveStrategy strategy) {
        this.strategy = strategy;
    }

    public static List<Move> calculateAttacksOnTile(final int tile,
                                                    final List<Move> moves) {
        final ImmutableList.Builder<Move> attackMoves = ImmutableList.builder();
        for (final Move move : moves) {
            if (tile == move.getDestinationCoordinate()) {
                attackMoves.add(move);
            }
        }
        return attackMoves.build();
    }

    public abstract List<Piece> getActivePieces();
    public abstract Alliance getAlliance();
    public abstract Player getOpponent();
    protected abstract List<Move> calculateKingCastles(List<Move> playerLegals, List<Move> opponentLegals);
    protected abstract King establishKing();
}
