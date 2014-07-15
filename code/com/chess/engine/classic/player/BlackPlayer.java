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

public class BlackPlayer extends Player {

    private final King blackKing;

    public BlackPlayer(final Board board) {
        super(board);
        for(final Piece p : board.getBlackPieces()) {
            if(p.isKing()) {
                this.blackKing = (King) p;
                return;
            }
        }
        throw new RuntimeException("Should not reach here! Black King could not be established!");
    }

    @Override
    public void calculateLegalMoves() {
        this.legalMoves.clear();
        for(final Piece p : this.board.getBlackPieces()) {
            this.legalMoves.addAll(p.calculateLegalMoves(this.board));
        }
        calculateKingCastles();
    }

    @Override
    public void calculateKingCastles() {
        if(this.blackKing.isFirstMove() && !this.blackKing.isInCheck()) {
            //blacks king side castle
            if(!this.board.getTile(5).isTileOccupied() && !this.board.getTile(6).isTileOccupied()) {
                final Tile rookTile = this.board.getTile(7);
                if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) {
                    if(calculateAttacksOnTile(5).isEmpty() && calculateAttacksOnTile(6).isEmpty() &&
                            rookTile.getPiece() instanceof Rook) {
                        this.legalMoves.add(new KingSideCastleMove(blackKing.getPiecePosition(), 6, blackKing, (Rook)rookTile.getPiece(), rookTile.getTileCoordinate(), 5));
                    }
                }
            }
            //blacks queen side castle
            if(!this.board.getTile(1).isTileOccupied() && !this.board.getTile(2).isTileOccupied() &&
                    !this.board.getTile(3).isTileOccupied()) {
                final Tile rookTile = this.board.getTile(0);
                if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) {
                    if(calculateAttacksOnTile(1).isEmpty() && calculateAttacksOnTile(2).isEmpty() &&
                            calculateAttacksOnTile(3).isEmpty() && rookTile.getPiece() instanceof Rook) {
                        this.legalMoves.add(new QueenSideCastleMove(blackKing.getPiecePosition(), 2, blackKing, (Rook)rookTile.getPiece(), rookTile.getTileCoordinate(), 3));
                    }
                }
            }
        }
    }

    @Override
    public WhitePlayer getOpponent() {
        return this.board.whitePlayer();
    }

    @Override
    public void switchPlayer() {
        this.board.setCurrentPlayer(this.board.whitePlayer());
    }

    @Override
    public King getPlayerKing() {
        return this.blackKing;
    }

    @Override
    public List<Piece> getActivePieces() {
        return this.board.getBlackPieces();
    }

    @Override
    public Alliance getAlliance() {
        return Alliance.BLACK;
    }

    @Override
    public String toString() {
        return Alliance.BLACK.toString();
    }

}
