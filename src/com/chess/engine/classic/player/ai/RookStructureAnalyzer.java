//package com.chess.engine.classic.player.ai;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//
//import com.chess.engine.classic.board.Board;
//import com.chess.engine.classic.board.BoardUtils;
//import com.chess.engine.classic.pieces.Piece;
//import com.chess.engine.classic.player.Player;
//import com.google.common.collect.ImmutableList;
//import com.google.common.collect.ImmutableList.Builder;
//
//public final class RookStructureAnalyzer {
//
//    private static final RookStructureAnalyzer INSTANCE = new RookStructureAnalyzer();
//    private static final  List<List<Boolean>> BOARD_COLUMNS = initColumns();
//    private static final int OPEN_COLUMN_ROOK_BONUS = 25;
//    private static final int NO_BONUS = 0;
//
//    private RookStructureAnalyzer() {
//    }
//
//    public static RookStructureAnalyzer get() {
//        return INSTANCE;
//    }
//
//    private static  List<List<Boolean>> initColumns() {
//        final List<List<Boolean>> columns = new ArrayList<>();
//        columns.add(BoardUtils.INSTANCE.FIRST_COLUMN);
//        columns.add(BoardUtils.INSTANCE.SECOND_COLUMN);
//        columns.add(BoardUtils.INSTANCE.THIRD_COLUMN);
//        columns.add(BoardUtils.INSTANCE.FOURTH_COLUMN);
//        columns.add(BoardUtils.INSTANCE.FIFTH_COLUMN);
//        columns.add(BoardUtils.INSTANCE.SIXTH_COLUMN);
//        columns.add(BoardUtils.INSTANCE.SEVENTH_COLUMN);
//        columns.add(BoardUtils.INSTANCE.EIGHTH_COLUMN);
//        return ImmutableList.copyOf(columns);
//    }
//
//    public int rookStructureScore(final Player player) {
//        final int[] rookOnColumnTable = createRookColumnTable(calculatePlayerRooks(player));
//        return calculateOpenFileRookBonus(rookOnColumnTable);
//    }
//
//    private static int[] createRookColumnTable(final Collection<Piece> playerRooks) {
//        final int[] table = new int[8];
//        for(final Piece playerRook : playerRooks) {
//            table[playerRook.getPiecePosition() % 8]++;
//        }
//        return table;
//    }
//
//    private static Collection<Piece> calculatePlayerRooks(final Player player) {
//        final List<Piece> playerRooks = new ArrayList<>();
//        for(final Piece piece : player.getActivePieces()) {
//            if(piece.getPieceType().isRook()) {
//                playerRooks.add(piece);
//            }
//        }
//        return ImmutableList.copyOf(playerRooks);
//    }
//
//    private static int calculateOpenFileRookBonus(final int[] rookOnColumnTable) {
//        int bonus = NO_BONUS;
//        for(final int rookLocation : rookOnColumnTable) {
//            final int[] piecesOnColumn = createPiecesOnColumnTable(board);
//            final int rookColumn = rookLocation/8;
//            for(int i = 0; i < piecesOnColumn.length; i++) {
//                if(piecesOnColumn[i] == 1 && i == rookColumn){
//                    bonus += OPEN_COLUMN_ROOK_BONUS;
//                }
//
//            }
//        }
//        return bonus;
//    }
//
//    private static int[] createPiecesOnColumnTable(final Board board) {
//        final int[] piecesOnColumnTable = new int[BOARD_COLUMNS.size()];
//        for(final Piece piece : board.getAllPieces()) {
//            for(int i = 0 ; i < BOARD_COLUMNS.size(); i++) {
//                if(BOARD_COLUMNS.get(i).get(piece.getPiecePosition())) {
//                    piecesOnColumnTable[i]++;
//                }
//            }
//        }
//        return piecesOnColumnTable;
//    }
//
//
//}