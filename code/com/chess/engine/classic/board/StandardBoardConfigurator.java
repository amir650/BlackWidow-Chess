package com.chess.engine.classic.board;

import com.chess.engine.classic.Alliance;
import com.chess.engine.classic.pieces.Bishop;
import com.chess.engine.classic.pieces.King;
import com.chess.engine.classic.pieces.Knight;
import com.chess.engine.classic.pieces.Pawn;
import com.chess.engine.classic.pieces.Queen;
import com.chess.engine.classic.pieces.Rook;

public class StandardBoardConfigurator
implements BoardConfigurator {

    public void setBoardPieces(final Board board) {
        board.clearGameBoard();
        // Black Layout
        board.setPiece(0, new Rook(Alliance.BLACK));
        board.setPiece(1, new Knight(Alliance.BLACK));
        board.setPiece(2, new Bishop(Alliance.BLACK));
        board.setPiece(3, new Queen(Alliance.BLACK));
        board.setPiece(4, new King(Alliance.BLACK));
        board.setPiece(5, new Bishop(Alliance.BLACK));
        board.setPiece(6, new Knight(Alliance.BLACK));
        board.setPiece(7, new Rook(Alliance.BLACK));
        board.setPiece(8, new Pawn(Alliance.BLACK));
        board.setPiece(9, new Pawn(Alliance.BLACK));
        board.setPiece(10, new Pawn(Alliance.BLACK));
        board.setPiece(11, new Pawn(Alliance.BLACK));
        board.setPiece(12, new Pawn(Alliance.BLACK));
        board.setPiece(13, new Pawn(Alliance.BLACK));
        board.setPiece(14, new Pawn(Alliance.BLACK));
        board.setPiece(15, new Pawn(Alliance.BLACK));
        // White Layout
        board.setPiece(48, new Pawn(Alliance.WHITE));
        board.setPiece(49, new Pawn(Alliance.WHITE));
        board.setPiece(50, new Pawn(Alliance.WHITE));
        board.setPiece(51, new Pawn(Alliance.WHITE));
        board.setPiece(52, new Pawn(Alliance.WHITE));
        board.setPiece(53, new Pawn(Alliance.WHITE));
        board.setPiece(54, new Pawn(Alliance.WHITE));
        board.setPiece(55, new Pawn(Alliance.WHITE));
        board.setPiece(56, new Rook(Alliance.WHITE));
        board.setPiece(57, new Knight(Alliance.WHITE));
        board.setPiece(58, new Bishop(Alliance.WHITE));
        board.setPiece(59, new Queen(Alliance.WHITE));
        board.setPiece(60, new King(Alliance.WHITE));
        board.setPiece(61, new Bishop(Alliance.WHITE));
        board.setPiece(62, new Knight(Alliance.WHITE));
        board.setPiece(63, new Rook(Alliance.WHITE));
    }

    @Override
    public void setCurrentPlayer(final Board board) {
        board.setCurrentPlayer(board.whitePlayer());
    }

}
