package com.chess.engine.classic.player;

import com.chess.engine.classic.Alliance;
import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.board.BoardUtils;
import com.chess.engine.classic.board.Move;
import com.chess.engine.classic.board.Move.KingSideCastleMove;
import com.chess.engine.classic.board.Move.QueenSideCastleMove;
import com.chess.engine.classic.pieces.Piece;
import com.chess.engine.classic.pieces.Rook;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static com.chess.engine.classic.pieces.Piece.PieceType.ROOK;

public final class BlackPlayer extends Player {

    public BlackPlayer(final Board board,
                       final Collection<Move> whiteStandardLegals,
                       final Collection<Move> blackStandardLegals) {
        super(board, blackStandardLegals, whiteStandardLegals);
    }

    @Override
    protected Collection<Move> calculateKingCastles(final Collection<Move> playerLegals,
                                                    final Collection<Move> opponentLegals) {

        if (!hasCastleOpportunities()) {
            return Collections.emptyList();
        }

        final List<Move> kingCastles = new ArrayList<>();

        if (this.playerKing.isFirstMove() && this.playerKing.getPiecePosition() == 4 && !this.isInCheck) {
            //blacks king side castle
            if (this.board.getPiece(5) == null && this.board.getPiece(6) == null) {
                final Piece kingSideRook = this.board.getPiece(7);
                if (kingSideRook != null && kingSideRook.isFirstMove() &&
                        Player.calculateAttacksOnTile(5, opponentLegals).isEmpty() &&
                        Player.calculateAttacksOnTile(6, opponentLegals).isEmpty() &&
                        kingSideRook.getPieceType() == ROOK) {
                    if (!BoardUtils.isKingPawnTrap(this.board, this.playerKing, 12)) {
                        kingCastles.add(
                                new KingSideCastleMove(this.board, this.playerKing, 6, (Rook) kingSideRook, kingSideRook.getPiecePosition(), 5));

                    }
                }
            }
            //blacks queen side castle
            if (this.board.getPiece(1) == null && this.board.getPiece(2) == null &&
                    this.board.getPiece(3) == null) {
                final Piece queenSideRook = this.board.getPiece(0);
                if (queenSideRook != null && queenSideRook.isFirstMove() &&
                        Player.calculateAttacksOnTile(2, opponentLegals).isEmpty() &&
                        Player.calculateAttacksOnTile(3, opponentLegals).isEmpty() &&
                        queenSideRook.getPieceType() == ROOK) {
                    if (!BoardUtils.isKingPawnTrap(this.board, this.playerKing, 12)) {
                        kingCastles.add(
                                new QueenSideCastleMove(this.board, this.playerKing, 2, (Rook) queenSideRook, queenSideRook.getPiecePosition(), 3));
                    }
                }
            }
        }
        return Collections.unmodifiableList(kingCastles);
    }

    @Override
    public WhitePlayer getOpponent() {
        return this.board.whitePlayer();
    }

    @Override
    public Collection<Piece> getActivePieces() {
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
