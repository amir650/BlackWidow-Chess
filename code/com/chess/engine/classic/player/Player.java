package com.chess.engine.classic.player;

import java.util.ArrayList;
import java.util.List;

import com.chess.engine.classic.Alliance;
import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.board.Board.MoveStatus;
import com.chess.engine.classic.board.Move;
import com.chess.engine.classic.pieces.King;
import com.chess.engine.classic.pieces.Piece;
import com.chess.engine.classic.player.ai.MoveStrategy;

public abstract class Player {

    protected final Board board;
    protected final List<Move> legalMoves;
    private MoveStrategy strategy;

    Player(final Board board) {
        this.board = board;
        this.legalMoves = new ArrayList<>();
    }

    public boolean isMoveLegal(final Move move) {
        return this.legalMoves.contains(move);
    }

    public boolean isInCheck() {
        return getPlayerKing().isInCheck();
    }

    public boolean isInCheckMate() {
       return getPlayerKing().isInCheckMate();
    }

    public boolean isInStaleMate() {
        return getPlayerKing().isInStaleMate();
    }

    public MoveStrategy getMoveStrategy() {
        return this.strategy;
    }

    public static MoveStatus makeMove(final Board board,
                                      final Move move) {
        if(!board.currentPlayer().isMoveLegal(move)) {
            return MoveStatus.ILLEGAL_NOT_IN_MOVES_LIST;
        }
        final Player currentPlayer = board.currentPlayer();
        final Player opponent = currentPlayer.getOpponent();
        move.execute(board, currentPlayer);
        currentPlayer.calculateLegalMoves();
        opponent.calculateLegalMoves();
        final List<Move> kingAttacks = currentPlayer.calculateAttacksOnTile(currentPlayer.getPlayerKing().getPiecePosition());
        if (!kingAttacks.isEmpty()) {
            move.undo(board, currentPlayer);
            currentPlayer.calculateLegalMoves();
            opponent.calculateLegalMoves();
            return MoveStatus.ILLEGAL_LEAVES_PLAYER_IN_CHECK;
        }
        opponent.calculateCheckStatus(board);
        opponent.calculateLegalMoves();
        currentPlayer.switchPlayer();
        return MoveStatus.DONE;
    }

    public static MoveStatus unMakeMove(final Board board,
                                        final Move move) {
        final Player currentPlayer = board.currentPlayer();
        final Player opponent = currentPlayer.getOpponent();
        move.undo(board, opponent);
        currentPlayer.calculateLegalMoves();
        opponent.calculateLegalMoves();
        opponent.calculateCheckStatus(board);
        currentPlayer.calculateLegalMoves();
        currentPlayer.switchPlayer();
        return MoveStatus.UNDONE;
    }

    private void calculateCheckStatus(final Board board) {
        final King playerKing = getPlayerKing();
        if(!calculateAttacksOnTile(getPlayerKing().getPiecePosition()).isEmpty()) {
            playerKing.setInCheck(true);
            playerKing.setInCheckMate(!(hasEscapeMoves(board)));
            playerKing.setInStaleMate(false);
        } else {
            playerKing.setInCheck(false);
            playerKing.setInCheckMate(false);
            playerKing.setInStaleMate(!hasEscapeMoves(board));
        }
    }

    public String playerInfo() {
        return ("Player is: " +this.getAlliance() + "\nlegal moves =" + getLegalMoves() + "\ninCheck = " +
               getPlayerKing().isInCheck() + "\nisInCheckMate = " +getPlayerKing().isInCheckMate() +
                "\nisCastled = " +getPlayerKing().isCastled())+ "\n";
    }

    private boolean hasEscapeMoves(final Board board) {
        for(final Move move : this.legalMoves) {
            move.execute(board, this);
            getOpponent().calculateLegalMoves();
            if(calculateAttacksOnTile(getPlayerKing().getPiecePosition()).isEmpty()) {
                move.undo(board, this);
                return true;
            }
            move.undo(board, this);
        }
        return false;
    }

    protected List <Move> calculateAttacksOnTile(final int tile) {
        final Player opponent = getOpponent();
        final List <Move> moves = new ArrayList<>();
        for (final Move move : opponent.getLegalMoves()) {
            if (tile == move.getDestinationCoordinate()) {
                moves.add(move);
            }
        }
        return (moves);
    }

    public List<Move> getLegalMoves() {
        return this.legalMoves;
    }

    public void setMoveStrategy(final MoveStrategy strategy) {
        this.strategy = strategy;
    }

    public abstract List<Piece> getActivePieces();
    public abstract Alliance getAlliance();
    public abstract Player getOpponent();
    public abstract King getPlayerKing();
    public abstract void calculateLegalMoves();
    public abstract void calculateKingCastles();
    public abstract void switchPlayer();

}
