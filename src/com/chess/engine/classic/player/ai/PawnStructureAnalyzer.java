package com.chess.engine.classic.player.ai;

import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.pieces.Pawn;
import com.chess.engine.classic.pieces.Piece;
import com.chess.engine.classic.player.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.chess.engine.classic.pieces.Piece.PieceType.PAWN;

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

        final boolean[] occupancyTable = createOccupancyTable(player);
        final boolean[] enemyOccupancyTable = createOccupancyTable(player.getOpponent());
        final int[] playerPawns = createPawnColumnTable(player);
        final int[] enemyPawns = createPawnColumnTable(player.getOpponent());

        return calculatePawnColumnStack(playerPawns) +
               calculateIsolatedPawnPenalty(playerPawns); // +
               //passedPawnsBonus(player, playerPawns, occupancyTable, enemyPawns, enemyOccupancyTable);
    }

    private static Collection<Piece> calculatePlayerPawns(final Player player) {
        final Board board = player.getBoard();
        final int[] activeIndexes = player.getActivePieces();
        final List<Piece> pawns = new ArrayList<>();
        for (int index : activeIndexes) {
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
//    private static int passedPawnsBonus(final Player player,
//                                        final int[] playerPawns,
//                                        final boolean[] occupancyTable,
//                                        final int[] enemyPawns,
//                                        final boolean[] enemyOccupancyTable) {
//
//        final int direction = player.getAlliance().getDirection() * 8;
//        int bonus = 0;
//
//        // loop only over this player’s actual pawn squares
//        for (final int sq : player.getActivePieces()) {
//            final int file = sq % 8;
//            final int rank = sq / 8;
//            // Quick file-level check: if there are no enemy pawns at all in
//            // this file or its neighbours, it’s automatically passed.
//            int enemyBlockers = enemyPawns[file];
//            if (file > 0)   enemyBlockers += enemyPawns[file - 1];
//            if (file < 7)   enemyBlockers += enemyPawns[file + 1];
//
//            boolean isPassed = (enemyBlockers == 0);
//
//            if (!isPassed) {
//                // scan forward square-by-square for any blocking enemy pawn
//                int scan = sq + direction;
//                while (scan >= 0 && scan < 64) {
//                    int scanFile = scan % 8;
//                    if (Math.abs(scanFile - file) <= 1 &&
//                            enemyOccupancyTable[scan]) {
//                        isPassed = false;
//                        break;
//                    }
//                    scan += direction;
//                }
//                // if we never found a blocker, it's passed
//                if (!isPassed && enemyBlockers > 0) {
//                    // we found an enemy pawn in the files, but none ahead:
//                    isPassed = true;
//                }
//            }
//
//            if (isPassed) {
//                // scale bonus by how far it’s advanced toward promotion
//                int advancement = isWhite ? (7 - rank) : rank;
//                bonus += 10 + 5 * advancement;
//            }
//        }
//
//        return bonus;
//    }


    private static boolean isPassedPawn(final Pawn pawn) {
        return false;
    }

    private static int[] createPawnColumnTable(final Player player) {
        final int[] table = new int[8];
        for(final Piece playerPawn : calculatePlayerPawns(player)) {
            table[playerPawn.getPiecePosition() % 8]++;
        }
        return table;
    }

    private static boolean[] createOccupancyTable(final Player player) {
        boolean[] table = new boolean[64];
        for(final Piece playerPawn : calculatePlayerPawns(player)) {
            table[playerPawn.getPiecePosition()] = true;
        }
        return table;
    }

}
