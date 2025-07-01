package com.chess.engine.player.ai;

import com.chess.engine.board.Board;
import com.chess.engine.pieces.Piece;
import com.chess.engine.player.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.chess.engine.pieces.Piece.PieceType.PAWN;

public final class PawnStructureAnalyzer {

    private static final PawnStructureAnalyzer INSTANCE = new PawnStructureAnalyzer();
    public static final int ISOLATED_PAWN_PENALTY = -10;
    public static final int DOUBLED_PAWN_PENALTY = -10;

    private PawnStructureAnalyzer() {
    }

    public static PawnStructureAnalyzer get() {
        return INSTANCE;
    }

    public int isolatedPawnPenalty(final Player player) {
        return calculateIsolatedPawnPenalty(createPawnColumnTable(player));
    }

    public int doubledPawnPenalty(final Player player) {
        return calculatePawnColumnStack(createPawnColumnTable(player));
    }

    public int pawnStructureScore(final Player player) {
        final int[] playerPawns = createPawnColumnTable(player);
        return calculatePawnColumnStack(playerPawns) +
               calculateIsolatedPawnPenalty(playerPawns);
    }

    private static Collection<Piece> calculatePlayerPawns(final Player player) {
        final Board board = player.getBoard();
        final int[] activeIndexes = player.getActivePieces();
        final List<Piece> pawns = new ArrayList<>();
        for (final int index : activeIndexes) {
            final Piece piece = board.getPiece(index);
            if (piece.getPieceType() == PAWN) {
                pawns.add(piece);
            }
        }
        return pawns;
    }

    private static int calculatePawnColumnStack(final int[] pawnsOnColumnTable) {
        int pawnStackPenalty = 0;
        for(final int pawnStack : pawnsOnColumnTable) {
            if(pawnStack > 1) {
                pawnStackPenalty += pawnStack;
            }
        }
        return pawnStackPenalty * DOUBLED_PAWN_PENALTY;
    }

    private static int calculateIsolatedPawnPenalty(final int[] pawnsOnColumnTable) {
        int numIsolatedPawns = 0;
        if(pawnsOnColumnTable[0] > 0 && pawnsOnColumnTable[1] == 0) {
            numIsolatedPawns += pawnsOnColumnTable[0];
        }
        if(pawnsOnColumnTable[7] > 0 && pawnsOnColumnTable[6] == 0) {
            numIsolatedPawns += pawnsOnColumnTable[7];
        }
        for(int i = 1; i < pawnsOnColumnTable.length - 1; i++) {
            if((pawnsOnColumnTable[i-1] == 0 && pawnsOnColumnTable[i+1] == 0)) {
                numIsolatedPawns += pawnsOnColumnTable[i];
            }
        }
        return numIsolatedPawns * ISOLATED_PAWN_PENALTY;
    }

    private static int[] createPawnColumnTable(final Player player) {
        final int[] table = new int[8];
        for(final Piece playerPawn : calculatePlayerPawns(player)) {
            table[playerPawn.getPiecePosition() % 8]++;
        }
        return table;
    }

}
