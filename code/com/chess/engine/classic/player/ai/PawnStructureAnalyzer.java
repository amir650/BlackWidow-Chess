package com.chess.engine.classic.player.ai;

import java.util.ArrayList;
import java.util.List;

import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.pieces.Piece;
import com.google.common.collect.ImmutableList.Builder;

public class PawnStructureAnalyzer {

    private static final PawnStructureAnalyzer INSTANCE = new PawnStructureAnalyzer();
    final List<boolean[]> COLUMNS = initColumns();

    private PawnStructureAnalyzer() {
    }

    public static PawnStructureAnalyzer get() {
        return INSTANCE;
    }

    private List<boolean[]> initColumns() {
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

    public int pawnStructureScore(final List<Piece> activePieces) {
        final List<Integer> pawnLocations = new ArrayList<>();
        for(final Piece piece : activePieces) {
            if(piece.isPawn()) {
                pawnLocations.add(piece.getPiecePosition());
            }
        }
        final int[] pawnsOnColumnTable = new int[COLUMNS.size()];
        for(int i = 0; i < COLUMNS.size(); i++) {
            for(final Integer location : pawnLocations) {
                if(COLUMNS.get(i)[location]) {
                    pawnsOnColumnTable[i]++;
                }
            }
        }
        int pawnStructurePenalty = 0;
        for(int pawnsOnColumn : pawnsOnColumnTable) {
            if(pawnsOnColumn > 1) {
                pawnStructurePenalty += (pawnsOnColumn * 10);
            }
        }
        //80 is a perfect score.  So subtract 80 from all possible penalties
        return (80 - pawnStructurePenalty);
    }

}
