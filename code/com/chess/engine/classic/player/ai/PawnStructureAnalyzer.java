package com.chess.engine.classic.player.ai;

import java.util.List;

import com.chess.engine.classic.board.Board;
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
        columns.add(Board.FIRST_COLUMN);
        columns.add(Board.SECOND_COLUMN);
        columns.add(Board.THIRD_COLUMN);
        columns.add(Board.FOURTH_COLUMN);
        columns.add(Board.FIFTH_COLUMN);
        columns.add(Board.SIXTH_COLUMN);
        columns.add(Board.SEVENTH_COLUMN);
        columns.add(Board.EIGHTH_COLUMN);
        return columns.build();
    }

    public int pawnStructureScore(final Player player) {
        return calculatePawnPenalties(calculatePawns(player));
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

    private static int calculatePawnPenalties(final List<Pawn> pawns) {
        final List<Integer> pawnLocations = calculatePawnLocations(pawns);
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
        //calculate doubled, tripled, quadrupled pawns.
        //80 is a perfect score.  So subtract 80 from all possible penalties
        final int stackedPawnScore = 80 - stackedPawnPenalty;

        int isolatedPawnPenalty = 0;

        for(int i = 0; i < COLUMNS.size(); i++) {
            for(final Integer location : pawnLocations) {
                if(COLUMNS.get(i)[location]) {
                    pawnsOnColumnTable[i]++;
                }
            }
        }

        return stackedPawnScore + isolatedPawnPenalty;
    }

}
