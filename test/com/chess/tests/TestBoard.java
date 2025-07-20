package com.chess.tests;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.MoveFactory;
import com.chess.engine.board.MoveTransition;
import com.chess.engine.pieces.Piece;
import com.chess.engine.player.ai.StandardBoardEvaluator;
import com.chess.pgn.FenUtilities;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TestBoard {

    @Test
    public void initialBoard() {

        final Board board = Board.createStandardBoard();
        assertEquals(20, board.currentPlayer().getLegalMoves().size());
        assertEquals(20, board.currentPlayer().getOpponent().getLegalMoves().size());
        assertFalse(board.currentPlayer().isInCheck());
        assertFalse(board.currentPlayer().isInCheckMate());
        assertFalse(board.currentPlayer().isCastled());
        assertTrue(board.currentPlayer().isKingSideCastleCapable());
        assertTrue(board.currentPlayer().isQueenSideCastleCapable());
        assertEquals(board.currentPlayer(), board.whitePlayer());
        assertEquals(board.currentPlayer().getOpponent(), board.blackPlayer());
        assertFalse(board.currentPlayer().getOpponent().isInCheck());
        assertFalse(board.currentPlayer().getOpponent().isInCheckMate());
        assertFalse(board.currentPlayer().getOpponent().isCastled());
        assertTrue(board.currentPlayer().getOpponent().isKingSideCastleCapable());
        assertTrue(board.currentPlayer().getOpponent().isQueenSideCastleCapable());
        Assert.assertEquals("White", board.whitePlayer().toString());
        Assert.assertEquals("Black", board.blackPlayer().toString());

        final Iterable<Piece> allPieces = board.getAllPieces();

        List<Move> allMoves = new ArrayList<>();
        allMoves.addAll(board.whitePlayer().getLegalMoves());
        allMoves.addAll(board.blackPlayer().getLegalMoves());

        for(final Move move : allMoves) {
            assertFalse(move.isAttack());
            assertFalse(move.isCastlingMove());
        }

        assertEquals(40, allMoves.size());

        int allPiecesCount = 0;
        for (Piece ignored : allPieces) {
            allPiecesCount++;
        }
        assertEquals(32, allPiecesCount);

        assertFalse(BoardUtils.isEndGame(board));
        assertFalse(BoardUtils.isThreatenedBoardImmediate(board));
        assertEquals(0, StandardBoardEvaluator.get().evaluate(board, 0));
        assertNull(board.getPiece(35));
    }

    @Test
    public void testPlainKingMove() {
        final Board board = FenUtilities.createGameFromFEN("4k3/4p3/8/8/8/8/4P3/4K3 w - - 0 1");
        System.out.println(FenUtilities.createFENFromGame(board));
        assertEquals(6, board.whitePlayer().getLegalMoves().size());
        assertEquals(6, board.blackPlayer().getLegalMoves().size());
        assertFalse(board.currentPlayer().isInCheck());
        assertFalse(board.currentPlayer().isInCheckMate());
        assertFalse(board.currentPlayer().getOpponent().isInCheck());
        assertFalse(board.currentPlayer().getOpponent().isInCheckMate());
        assertEquals(board.currentPlayer(), board.whitePlayer());
        assertEquals(board.currentPlayer().getOpponent(), board.blackPlayer());
        assertEquals(0, StandardBoardEvaluator.get().evaluate(board, 0));

        final Move move = MoveFactory.createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("e1"),
                BoardUtils.INSTANCE.getCoordinateAtPosition("f1"));

        final MoveTransition moveTransition = board.currentPlayer()
                .makeMove(move);

        assertEquals(moveTransition.getTransitionMove(), move);
        assertEquals(moveTransition.getFromBoard(), board);
        assertEquals(moveTransition.getToBoard().currentPlayer(), moveTransition.getToBoard().blackPlayer());
        assertTrue(moveTransition.getMoveStatus().isDone());
        assertEquals(61, moveTransition.getToBoard().whitePlayer().getPlayerKing().getPiecePosition());
    }

    @Test
    public void testBoardConsistency() {
        final Board board = Board.createStandardBoard();
        assertEquals(board.currentPlayer(), board.whitePlayer());

        final MoveTransition t1 = board.currentPlayer()
                .makeMove(MoveFactory.createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("e2"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("e4")));
        final MoveTransition t2 = t1.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t1.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("e7"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("e5")));

        final MoveTransition t3 = t2.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t2.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("g1"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("f3")));
        final MoveTransition t4 = t3.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t3.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("d7"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("d5")));

        final MoveTransition t5 = t4.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t4.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("e4"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("d5")));
        final MoveTransition t6 = t5.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t5.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("d8"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("d5")));

        final MoveTransition t7 = t6.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t6.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("f3"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("g5")));
        final MoveTransition t8 = t7.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t7.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("f7"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("f6")));

        final MoveTransition t9 = t8.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t8.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("d1"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("h5")));
        final MoveTransition t10 = t9.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t9.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("g7"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("g6")));

        final MoveTransition t11 = t10.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t10.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("h5"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("h4")));
        final MoveTransition t12 = t11.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t11.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("f6"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("g5")));

        final MoveTransition t13 = t12.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t12.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("h4"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("g5")));
        final MoveTransition t14 = t13.getToBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t13.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("d5"),
                        BoardUtils.INSTANCE.getCoordinateAtPosition("e4")));

        Assert.assertEquals(t14.getToBoard().whitePlayer().getActivePieces().length, calculatedActivesFor(t14.getToBoard(), Alliance.WHITE));
        Assert.assertEquals(t14.getToBoard().blackPlayer().getActivePieces().length, calculatedActivesFor(t14.getToBoard(), Alliance.BLACK));
    }

//    @Test(expected=RuntimeException.class)
//    public void testInvalidBoard() {
//
//        final Builder builder = new Builder();
//        // Black Layout
//        builder.setPiece(new Rook(Alliance.BLACK, 0));
//        builder.setPiece(new Knight(Alliance.BLACK, 1));
//        builder.setPiece(new Bishop(Alliance.BLACK, 2));
//        builder.setPiece(new Queen(Alliance.BLACK, 3));
//        builder.setPiece(new Bishop(Alliance.BLACK, 5));
//        builder.setPiece(new Knight(Alliance.BLACK, 6));
//        builder.setPiece(new Rook(Alliance.BLACK, 7));
//        builder.setPiece(new Pawn(Alliance.BLACK, 8));
//        builder.setPiece(new Pawn(Alliance.BLACK, 9));
//        builder.setPiece(new Pawn(Alliance.BLACK, 10));
//        builder.setPiece(new Pawn(Alliance.BLACK, 11));
//        builder.setPiece(new Pawn(Alliance.BLACK, 12));
//        builder.setPiece(new Pawn(Alliance.BLACK, 13));
//        builder.setPiece(new Pawn(Alliance.BLACK, 14));
//        builder.setPiece(new Pawn(Alliance.BLACK, 15));
//        // White Layout
//        builder.setPiece(new Pawn(Alliance.WHITE, 48));
//        builder.setPiece(new Pawn(Alliance.WHITE, 49));
//        builder.setPiece(new Pawn(Alliance.WHITE, 50));
//        builder.setPiece(new Pawn(Alliance.WHITE, 51));
//        builder.setPiece(new Pawn(Alliance.WHITE, 52));
//        builder.setPiece(new Pawn(Alliance.WHITE, 53));
//        builder.setPiece(new Pawn(Alliance.WHITE, 54));
//        builder.setPiece(new Pawn(Alliance.WHITE, 55));
//        builder.setPiece(new Rook(Alliance.WHITE, 56));
//        builder.setPiece(new Knight(Alliance.WHITE, 57));
//        builder.setPiece(new Bishop(Alliance.WHITE, 58));
//        builder.setPiece(new Queen(Alliance.WHITE, 59));
//        builder.setPiece(new Bishop(Alliance.WHITE, 61));
//        builder.setPiece(new Knight(Alliance.WHITE, 62));
//        builder.setPiece(new Rook(Alliance.WHITE, 63));
//        //white to move
//        builder.setMoveMaker(Alliance.WHITE);
//        //build the board
//        builder.build();
//    }

    @Test
    public void testTheBoard() {
        System.out.println("=== BOARD STATE ===");
        final Board board = Board.createStandardBoard();
        for (Piece piece : board.getAllPieces()) {
            System.out.println(
                    piece.getPieceType() + " at " +
                            piece.getPiecePosition() +
                            " isFirstMove: " + piece.isFirstMove()
            );
        }
        System.out.println("FEN: " + FenUtilities.createFENFromGame(board));
        System.out.println("==================");
    }

    @Test
    public void testAlgebreicNotation() {
        assertEquals("a8", BoardUtils.INSTANCE.getPositionAtCoordinate(0));
        assertEquals("b8", BoardUtils.INSTANCE.getPositionAtCoordinate(1));
        assertEquals("c8", BoardUtils.INSTANCE.getPositionAtCoordinate(2));
        assertEquals("d8", BoardUtils.INSTANCE.getPositionAtCoordinate(3));
        assertEquals("e8", BoardUtils.INSTANCE.getPositionAtCoordinate(4));
        assertEquals("f8", BoardUtils.INSTANCE.getPositionAtCoordinate(5));
        assertEquals("g8", BoardUtils.INSTANCE.getPositionAtCoordinate(6));
        assertEquals("h8", BoardUtils.INSTANCE.getPositionAtCoordinate(7));
    }

    private static int calculatedActivesFor(final Board board,
                                            final Alliance alliance) {
        int count = 0;
        for (final Piece p : board.getAllPieces()) {
            if (p.getPieceAllegiance().equals(alliance)) {
                count++;
            }
        }
        return count;
    }
}
