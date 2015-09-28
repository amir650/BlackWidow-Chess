package com.chess.engine.classic.player.ai;

import java.util.Collection;
import java.util.List;

import com.chess.engine.classic.board.BoardUtils;
import com.chess.engine.classic.pieces.Piece;
import com.chess.engine.classic.player.Player;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList.Builder;
import com.google.common.collect.ListMultimap;

public final class PawnStructureAnalyzer {

    private static final PawnStructureAnalyzer INSTANCE = new PawnStructureAnalyzer();
    private static final List<boolean[]> BOARD_COLUMNS = initColumns();

    public static final int ISOLATED_PAWN_PENALTY = -10;
    public static final int DOUBLED_PAWN_PENALTY = -5;

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

    public int isolatedPawnPenalty(final Player player) {
        return calculateIsolatedPawnPenalty(createPawnsOnColumnTable(calculatePawnLocations(player)));
    }

    public int doubledPawnPenalty(final Player player) {
        return calculateDoubledPawnPenalty(createPawnsOnColumnTable(calculatePawnLocations(player)));
    }

    public int pawnStructureScore(final Player player) {
        final ListMultimap<Integer, Piece> pawnsOnColumnTable = createPawnsOnColumnTable(calculatePawnLocations(player));
        return calculateDoubledPawnPenalty(pawnsOnColumnTable) + calculateIsolatedPawnPenalty(pawnsOnColumnTable);
    }

    private static Collection<Piece> calculatePawnLocations(final Player player) {
        final Builder<Piece> playerPawnLocations = new Builder<>();
        for(final Piece piece : player.getActivePieces()) {
            if(piece.getPieceType().isPawn()) {
                playerPawnLocations.add(piece);
            }
        }
        return playerPawnLocations.build();
    }

    private static int calculateDoubledPawnPenalty(final ListMultimap<Integer, Piece> pawnsOnColumnTable) {
        int numDoubledPawns = 0;
        for(int i = 0; i < BOARD_COLUMNS.size(); i++) {
            final int numPawnsOnColumn = pawnsOnColumnTable.get(i).size();
            if(numPawnsOnColumn > 1) {
                numDoubledPawns += pawnsOnColumnTable.get(i).size();
            }
        }
        return numDoubledPawns * DOUBLED_PAWN_PENALTY;
    }

    private static int calculateIsolatedPawnPenalty(final ListMultimap<Integer, Piece> pawnsOnColumnTable) {
        int numIsolatedPawns = 0;
        if(!pawnsOnColumnTable.get(0).isEmpty() &&
            pawnsOnColumnTable.get(1).isEmpty()) {
            numIsolatedPawns += pawnsOnColumnTable.get(0).size();
        }
        for(int i = 1; i < BOARD_COLUMNS.size() - 1; i++) {
            if(!pawnsOnColumnTable.get(i).isEmpty() &&
               (pawnsOnColumnTable.get(i-1).isEmpty() && pawnsOnColumnTable.get(i+1).isEmpty())) {
                numIsolatedPawns += pawnsOnColumnTable.get(i).size();
            }
        }
        if(!pawnsOnColumnTable.get(BOARD_COLUMNS.size() - 1).isEmpty() &&
            pawnsOnColumnTable.get(BOARD_COLUMNS.size() - 2).isEmpty()) {
            numIsolatedPawns += pawnsOnColumnTable.get(BOARD_COLUMNS.size() - 1).size();
        }
        return numIsolatedPawns * ISOLATED_PAWN_PENALTY;
    }

    private static ListMultimap<Integer, Piece> createPawnsOnColumnTable(final Collection<Piece> playerPawns) {
        final ListMultimap<Integer, Piece> table = ArrayListMultimap.create(8, 6);
        for(int i = 0; i < BOARD_COLUMNS.size(); i++) {
            for(final Piece playerPawn : playerPawns) {
                if(BOARD_COLUMNS.get(i)[playerPawn.getPiecePosition()]) {
                    table.put(i, playerPawn);
                }
            }
        }
        return table;
    }

}
