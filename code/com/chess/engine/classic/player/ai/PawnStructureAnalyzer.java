package com.chess.engine.classic.player.ai;

import java.util.List;

import com.chess.engine.classic.board.BoardUtils;
import com.chess.engine.classic.pieces.Pawn;
import com.chess.engine.classic.pieces.Piece;
import com.chess.engine.classic.player.Player;
import com.google.common.collect.ImmutableList.Builder;

public class PawnStructureAnalyzer {

    private static final PawnStructureAnalyzer INSTANCE = new PawnStructureAnalyzer();
    private static final List<boolean[]> COLUMNS = initColumns();

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

        final List<Pawn> playerPawns = calculatePawns(player);
        final List<Integer> pawnLocations = calculatePawnLocations(playerPawns);

        return calculatePawnStacksPenalty(pawnLocations) + calculateIsolatedPawnPenalty(pawnLocations);
    }


    private static List<Integer> calculatePawnLocations(final List<Pawn> pawns) {
        final Builder<Integer> pawnLocations = new Builder<>();
        for (final Pawn pawn : pawns) {
            pawnLocations.add(pawn.getPiecePosition());
        }
        return pawnLocations.build();
    }

    private static List<Pawn> calculatePawns(final Player player) {
        final Builder<Pawn> playerPawns = new Builder<>();
        for(final Piece piece : player.getActivePieces()) {
            if(piece.isPawn()) {
                playerPawns.add((Pawn)piece);
            }
        }
        return playerPawns.build();
    }

    private static int calculatePawnStacksPenalty(final List<Integer> pawnLocations) {
        final int[] pawnsOnColumnTable = new int[COLUMNS.size()];
        for(int i = 0; i < COLUMNS.size(); i++) {
            for(final Integer location : pawnLocations) {
                if(COLUMNS.get(i)[location]) {
                    pawnsOnColumnTable[i]++;
                }
            }
        }
        int stackedPawnPenalty = 0;
        for(final int pawnsOnColumn : pawnsOnColumnTable) {
            if(pawnsOnColumn > 1) {
                stackedPawnPenalty += (10 * pawnsOnColumn);
            }
        }
        return -stackedPawnPenalty;
    }

    private static int calculateIsolatedPawnPenalty(final List<Integer> pawnLocations) {
        final int[] pawnsOnColumnTable = new int[COLUMNS.size()];
        for (int i = 0; i < COLUMNS.size(); i++) {
            for (final Integer location : pawnLocations) {
                if (COLUMNS.get(i)[location]) {
                    pawnsOnColumnTable[i]++;
                }
            }
        }
        int isolatedPawnPenalty = 0;

        if(pawnsOnColumnTable[0] > 0 && pawnsOnColumnTable[1] == 0) {
            isolatedPawnPenalty += 25;
        }
        for(int i = 1; i < COLUMNS.size() - 1; i++) {
            if(pawnsOnColumnTable[i] > 0 && (pawnsOnColumnTable[i-1] == 0 && pawnsOnColumnTable[i+1] == 0)) {
             isolatedPawnPenalty += 25;
            }
        }
        if(pawnsOnColumnTable[COLUMNS.size() - 1] > 0 && pawnsOnColumnTable[COLUMNS.size() - 2] == 0) {
            isolatedPawnPenalty += 25;
        }
        return -isolatedPawnPenalty;
    }

}
