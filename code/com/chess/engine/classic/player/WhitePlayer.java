package com.chess.engine.classic.player;

import java.util.List;

import com.chess.engine.classic.Alliance;
import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.board.Move;
import com.chess.engine.classic.board.Move.KingSideCastleMove;
import com.chess.engine.classic.board.Move.QueenSideCastleMove;
import com.chess.engine.classic.board.Tile;
import com.chess.engine.classic.pieces.King;
import com.chess.engine.classic.pieces.Piece;
import com.chess.engine.classic.pieces.Piece.PieceType;
import com.chess.engine.classic.pieces.Rook;
import com.google.common.collect.ImmutableList;

public class WhitePlayer extends Player {

    public WhitePlayer(final Board board,
                       final List<Move> whiteStandardLegals,
                       final List<Move> blackStandardLegals) {
        super(board, whiteStandardLegals, blackStandardLegals);
    }

    @Override
    protected List<Move> calculateKingCastles(final List<Move> playerLegals,
                                              final List<Move> opponentLegals) {

        final ImmutableList.Builder<Move> kingCastles = ImmutableList.builder();

        if(this.playerKing.isFirstMove() && !this.playerKing.isInCheck(opponentLegals)) {
            //whites king side castle
            if(!this.board.getTile(61).isTileOccupied() && !this.board.getTile(62).isTileOccupied()) {
                final Tile rookTile = this.board.getTile(63);
                if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) {
                    if(Player.calculateAttacksOnTile(61, opponentLegals).isEmpty() && Player.calculateAttacksOnTile(62, opponentLegals).isEmpty() &&
                       rookTile.getPiece().getPieceType() == PieceType.ROOK) {
                        kingCastles.add(new KingSideCastleMove(this.board, this.playerKing.getPiecePosition(), 62,
                                this.playerKing, (Rook) rookTile.getPiece(), rookTile.getTileCoordinate(), 61));
                    }
                }
            }
            //whites queen side castle
            if(!this.board.getTile(59).isTileOccupied() && !this.board.getTile(58).isTileOccupied() &&
               !this.board.getTile(57).isTileOccupied()) {
                final Tile rookTile = this.board.getTile(56);
                if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) {
                    if(Player.calculateAttacksOnTile(57, opponentLegals).isEmpty() && Player.calculateAttacksOnTile(58, opponentLegals).isEmpty() &&
                       Player.calculateAttacksOnTile(59, opponentLegals).isEmpty() && rookTile.getPiece().getPieceType() == PieceType.ROOK) {
                        kingCastles.add(new QueenSideCastleMove(this.board, this.playerKing.getPiecePosition(), 58,
                                this.playerKing, (Rook) rookTile.getPiece(), rookTile.getTileCoordinate(), 59));
                    }
                }
            }
        }
        return kingCastles.build();
    }

    @Override
    public BlackPlayer getOpponent() {
        return this.board.blackPlayer();
    }

    @Override
    public List<Piece> getActivePieces() {
        return this.board.getWhitePieces();
    }

    @Override
    public Alliance getAlliance() {
        return Alliance.WHITE;
    }

    @Override
    public String toString() {
        return Alliance.WHITE.toString();
    }

    @Override
    protected King findKing() {
        for(final Piece piece : this.board.getWhitePieces()) {
            if(piece.isKing()) {
                return (King) piece;
            }
        }
        throw new RuntimeException("Should not reach here! Black King could not be established!");
    }

}
