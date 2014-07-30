//package com.chess.tests;
//
//import static junit.framework.Assert.assertFalse;
//import static junit.framework.Assert.assertTrue;
//
//import java.util.List;
//
//import org.junit.Ignore;
//import org.junit.Test;
//
//import com.chess.engine.classic.Alliance;
//import com.chess.engine.classic.board.Board;
//import com.chess.engine.classic.board.BoardConfigurator;
//import com.chess.engine.classic.board.Move;
//import com.chess.engine.classic.pieces.King;
//import com.chess.engine.classic.pieces.Pawn;
//import com.chess.engine.classic.player.Player;
//
//public class TestPawn {
//
//    @Ignore
//    @Test
//    public void testEnPassantBlack() {
//        final Board board = new Board(new BoardConfigurator() {
//            @Override
//            public void configureBoard(final Board board) {
//                board.clearGameBoard(); //test
//                // Black Layout
//                board.setPiece(4, new King(Alliance.BLACK));
//                board.setPiece(35, new Pawn(Alliance.BLACK));
//                board.setPiece(34, new Pawn(Alliance.BLACK));
//                // White Layout
//                board.setPiece(52, new Pawn(Alliance.WHITE));
//                board.setPiece(49, new Pawn(Alliance.WHITE));
//                board.setPiece(59, new King(Alliance.WHITE));
//                board.setCurrentPlayer(board.whitePlayer());
//            }
//
//            @Override
//            public void setCurrentPlayer(final Board board) {
//                board.setCurrentPlayer(board.whitePlayer());
//            }
//        });
//
//        Player.makeMove(board, Move.MoveFactory
//                .createMove(board, Board.getCoordinateAtPosition("e2"), Board.getCoordinateAtPosition("e4")));
//        List<Move> blackLegals = board.blackPlayer().getLegalMoves();
//        final Move bm1 = Move.MoveFactory
//                .createMove(board, Board.getCoordinateAtPosition("d4"), Board.getCoordinateAtPosition("e3"));
//        assertTrue(blackLegals.contains(bm1));
//
//        Player.makeMove(board, bm1);
//        assertTrue(!board.getTile(36).isTileOccupied());
//
//        Player.unMakeMove(board, bm1);
//        blackLegals = board.blackPlayer().getLegalMoves();
//        assertTrue(blackLegals.contains(bm1));
//        Player.makeMove(board, Move.MoveFactory
//                .createMove(board, Board.getCoordinateAtPosition("e8"), Board.getCoordinateAtPosition("d8")));
//        Player.makeMove(board, Move.MoveFactory
//                .createMove(board, Board.getCoordinateAtPosition("d1"), Board.getCoordinateAtPosition("e1")));
//        blackLegals = board.blackPlayer().getLegalMoves();
//        assertFalse(blackLegals.contains(bm1));
//        Player.makeMove(board, Move.MoveFactory
//                .createMove(board, Board.getCoordinateAtPosition("d8"), Board.getCoordinateAtPosition("e8")));
//        Player.makeMove(board, Move.MoveFactory
//                .createMove(board, Board.getCoordinateAtPosition("b2"), Board.getCoordinateAtPosition("b4")));
//        final Move bm2 = Move.MoveFactory
//                .createMove(board, Board.getCoordinateAtPosition("c4"), Board.getCoordinateAtPosition("b3"));
//        blackLegals = board.blackPlayer().getLegalMoves();
//        assertTrue(blackLegals.contains(bm2));
//
//    }
//
//    @Ignore
//    @Test
//    public void testEnPassantWhite() {
//
//        final Board board = new Board(new BoardConfigurator() {
//            @Override
//            public void configureBoard(final Board board) {
//                board.clearGameBoard();
//                // Black Layout
//                board.setPiece(4, new King(Alliance.BLACK));
//                board.setPiece(11, new Pawn(Alliance.BLACK));
//                board.setPiece(14, new Pawn(Alliance.BLACK));
//                // White Layout
//                board.setPiece(28, new Pawn(Alliance.WHITE));
//                board.setPiece(29, new Pawn(Alliance.WHITE));
//                board.setPiece(59, new King(Alliance.WHITE));
//                board.setCurrentPlayer(board.whitePlayer());
//            }
//
//            @Override
//            public void setCurrentPlayer(final Board board) {
//                board.setCurrentPlayer(board.blackPlayer());
//            }
//        });
//
//        Player.makeMove(board, Move.MoveFactory
//                .createMove(board, Board.getCoordinateAtPosition("d7"), Board.getCoordinateAtPosition("d5")));
//        List<Move> whitelegals = board.whitePlayer().getLegalMoves();
//        final Move wm1 = Move.MoveFactory
//                .createMove(board, Board.getCoordinateAtPosition("e5"), Board.getCoordinateAtPosition("d6"));
//        assertTrue(whitelegals.contains(wm1));
//        Player.makeMove(board, wm1);
//        Player.makeMove(board, Move.MoveFactory
//                .createMove(board, Board.getCoordinateAtPosition("g7"), Board.getCoordinateAtPosition("g5")));
//        final Move wm2 = Move.MoveFactory
//                .createMove(board, Board.getCoordinateAtPosition("f5"), Board.getCoordinateAtPosition("g6"));
//        whitelegals = board.whitePlayer().getLegalMoves();
//        assertTrue(whitelegals.contains(wm2));
//    }
//}
