package com.chess.engine.classic.player;

import java.util.List;

import com.chess.engine.classic.Alliance;
import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.board.Move.KingSideCastleMove;
import com.chess.engine.classic.board.Move.QueenSideCastleMove;
import com.chess.engine.classic.board.Tile;
import com.chess.engine.classic.pieces.King;
import com.chess.engine.classic.pieces.Piece;
import com.chess.engine.classic.pieces.Rook;

public class WhitePlayer extends Player {

    final King whiteKing;

    public WhitePlayer(final Board board) {
        super(board);
        for(final Piece p : board.getWhitePieces()) {
            if(p.isKing()) {
                this.whiteKing = (King) p;
                return;
            }
        }
        throw new RuntimeException("White King could not be established!");
    }

    @Override
    public void calculateLegalMoves() {
        this.legalMoves.clear();
        for(final Piece p : this.board.getWhitePieces()) {
            this.legalMoves.addAll(p.calculateLegalMoves(this.board));
        }
        calculateKingCastles();
    }

    @Override
    public void calculateKingCastles() {
        if(whiteKing.isFirstMove() && !whiteKing.isInCheck()) {
            //whites king side castle
            if(!this.board.getTile(61).isTileOccupied() && !this.board.getTile(62).isTileOccupied()) {
                final Tile rookTile = this.board.getTile(63);
                if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) {
                    if(calculateAttacksOnTile(61).isEmpty() && calculateAttacksOnTile(62).isEmpty() &&
                       rookTile.getPiece() instanceof Rook) {
                        this.legalMoves.add(new KingSideCastleMove(whiteKing.getPiecePosition(), 62, whiteKing, (Rook)rookTile.getPiece(), rookTile.getTileCoordinate(), 61));
                    }
                }
            }
            //whites queen side castle
            if(!this.board.getTile(59).isTileOccupied() && !this.board.getTile(58).isTileOccupied() &&
               !this.board.getTile(57).isTileOccupied()) {
                final Tile rookTile = this.board.getTile(56);
                if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) {
                    if(calculateAttacksOnTile(57).isEmpty() && calculateAttacksOnTile(58).isEmpty() &&
                       calculateAttacksOnTile(59).isEmpty() && rookTile.getPiece() instanceof Rook) {
                        this.legalMoves.add(new QueenSideCastleMove(whiteKing.getPiecePosition(), 58, whiteKing, (Rook)rookTile.getPiece(), rookTile.getTileCoordinate(), 59));
                    }
                }
            }
        }
    }

    @Override
    public BlackPlayer getOpponent() {
        return this.board.blackPlayer();
    }

    @Override
    public void switchPlayer() {
        board.setCurrentPlayer(this.board.blackPlayer());
    }

    @Override
    public King getPlayerKing() {
        return this.whiteKing;
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
}
