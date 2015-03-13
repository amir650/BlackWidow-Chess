package com.chess.engine.classic.player.ai;

import java.util.List;

import com.chess.engine.classic.board.BoardUtils;
import com.chess.engine.classic.pieces.Piece;
import com.chess.engine.classic.player.Player;
import com.google.common.collect.ImmutableList.Builder;

public class PawnStructureAnalyzer {

    private static final PawnStructureAnalyzer INSTANCE = new PawnStructureAnalyzer();
    private static final List<boolean[]> BOARD_COLUMNS = initColumns();

    private PawnStructureAnalyzer() {
    }

    public static PawnStructureAnalyzer get() {
        return INSTANCE;
    }

    private static List<boolean[]> initColumns() {
        final Builder<boolean[]> columns = new Builder<>();
        columns.add(BoardUtils.FIRST_COLUMN);
        columns.add(BoardUtils.SECOND_COLUMN);
        columns.add(BoardUtils.THIRD_COLUMN);
        columns.add(BoardUtils.FOURTH_COLUMN);
        columns.add(BoardUtils.FIFTH_COLUMN);
        columns.add(BoardUtils.SIXTH_COLUMN);
        columns.add(BoardUtils.SEVENTH_COLUMN);
        columns.add(BoardUtils.EIGHTH_COLUMN);
        return columns.build();
    }

    public int pawnStructureScore(final Player player) {
        final List<Integer> pawnLocations = calculatePawnLocations(player);
        return calculatePawnStacksPenalty(pawnLocations) + calculateIsolatedPawnPenalty(pawnLocations);
    }

    private static List<Integer> calculatePawnLocations(final Player player) {
        final Builder<Integer> playerPawnLocations = new Builder<>();
        for(final Piece piece : player.getActivePieces()) {
            if(piece.isPawn()) {
                playerPawnLocations.add(piece.getPiecePosition());
            }
        }
        return playerPawnLocations.build();
    }

    private static int calculatePawnStacksPenalty(final List<Integer> pawnLocations) {
        final int[] pawnsOnColumnTable = createPawnsOnColumnTable(pawnLocations);
        int stackedPawnPenalty = 0;
        for(final int pawnsOnColumn : pawnsOnColumnTable) {
            if(pawnsOnColumn > 1) {
                stackedPawnPenalty += (10 * pawnsOnColumn);
            }
        }
        return -stackedPawnPenalty;
    }

    private static int calculateIsolatedPawnPenalty(final List<Integer> pawnLocations) {
        final int[] pawnsOnColumnTable = createPawnsOnColumnTable(pawnLocations);
        int isolatedPawnPenalty = 0;
        if(pawnsOnColumnTable[0] > 0 && pawnsOnColumnTable[1] == 0) {
            isolatedPawnPenalty += 25;
        }
        for(int i = 1; i < BOARD_COLUMNS.size() - 1; i++) {
            if(pawnsOnColumnTable[i] > 0 && (pawnsOnColumnTable[i-1] == 0 && pawnsOnColumnTable[i+1] == 0)) {
                isolatedPawnPenalty += 25;
            }
        }
        if(pawnsOnColumnTable[BOARD_COLUMNS.size() - 1] > 0 && pawnsOnColumnTable[BOARD_COLUMNS.size() - 2] == 0) {
            isolatedPawnPenalty += 25;
        }
        return -isolatedPawnPenalty;
    }

    private static int[] createPawnsOnColumnTable(final List<Integer> pawnLocations) {
        final int[] pawnsOnColumnTable = new int[BOARD_COLUMNS.size()];
        for(int i = 0; i < BOARD_COLUMNS.size(); i++) {
            for(final Integer location : pawnLocations) {
                if(BOARD_COLUMNS.get(i)[location]) {
                    pawnsOnColumnTable[i]++;
                }
            }
        }
        return pawnsOnColumnTable;
    }

}
