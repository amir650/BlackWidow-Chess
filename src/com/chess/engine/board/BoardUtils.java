package com.chess.engine.board;

import com.chess.engine.pieces.King;
import com.chess.engine.pieces.Piece;

import java.util.*;


public enum  BoardUtils {

    INSTANCE;

    public static final List<Boolean> FIRST_COLUMN = initColumn(0);
    public static final List<Boolean> SECOND_COLUMN = initColumn(1);
    public static final List<Boolean> THIRD_COLUMN = initColumn(2);
    public static final List<Boolean> FOURTH_COLUMN = initColumn(3);
    public static final List<Boolean> FIFTH_COLUMN = initColumn(4);
    public static final List<Boolean> SIXTH_COLUMN = initColumn(5);
    public static final List<Boolean> SEVENTH_COLUMN = initColumn(6);
    public static final List<Boolean> EIGHTH_COLUMN = initColumn(7);
    public static final List<Boolean> FIRST_ROW = initRow(0);
    public static final List<Boolean> SECOND_ROW = initRow(8);
    public static final List<Boolean> THIRD_ROW = initRow(16);
    public static final List<Boolean> FOURTH_ROW = initRow(24);
    public static final List<Boolean> FIFTH_ROW = initRow(32);
    public static final List<Boolean> SIXTH_ROW = initRow(40);
    public static final List<Boolean> SEVENTH_ROW = initRow(48);
    public static final List<Boolean> EIGHTH_ROW = initRow(56);
    public static final List<String> ALGEBRAIC_NOTATION = initializeAlgebraicNotation();
    public static final Map<String, Integer> POSITION_TO_COORDINATE = initializePositionToCoordinateMap();

    public static final int START_TILE_INDEX = 0;
    public static final int NUM_TILES_PER_ROW = 8;
    public static final int NUM_TILES = 64;
    // Pawn tables remain the same (8/10 rating)
    public final static int[] WHITE_PAWN_PREFERRED_COORDINATES = {
            0,  0,  0,  0,  0,  0,  0,  0,
            90, 90, 90, 90, 90, 90, 90, 90,
            30, 30, 40, 60, 60, 40, 30, 30,
            10, 10, 20, 40, 40, 20, 10, 10,
            5,  5, 10, 20, 20, 10,  5,  5,
            0,  0,  0,-10,-10,  0,  0,  0,
            5, -5,-10,  0,  0,-10, -5,  5,
            0,  0,  0,  0,  0,  0,  0,  0
    };
    public final static int[] BLACK_PAWN_PREFERRED_COORDINATES = {
            0,  0,  0,  0,  0,  0,  0,  0,
            5, -5,-10,  0,  0,-10, -5,  5,
            0,  0,  0,-10,-10,  0,  0,  0,
            5,  5, 10, 20, 20, 10,  5,  5,
            10, 10, 20, 40, 40, 20, 10, 10,
            30, 30, 40, 60, 60, 40, 30, 30,
            90, 90, 90, 90, 90, 90, 90, 90,
            0,  0,  0,  0,  0,  0,  0,  0
    };
    // Knight tables remain the same (7/10 rating)
    public final static int[] WHITE_KNIGHT_PREFERRED_COORDINATES = {
            -50,-40,-30,-30,-30,-30,-40,-50,
            -40,-20,  0,  5,  5,  0,-20,-40,
            -30,  5, 10, 15, 15, 10,  5,-30,
            -30,  5, 15, 20, 20, 15,  5,-30,
            -30,  5, 15, 20, 20, 15,  5,-30,
            -30,  5, 10, 15, 15, 10,  5,-30,
            -40,-20,  0,  0,  0,  0,-20,-40,
            -50,-40,-30,-30,-30,-30,-40,-50
    };
    public final static int[] BLACK_KNIGHT_PREFERRED_COORDINATES = {
            -50,-40,-30,-30,-30,-30,-40,-50,
            -40,-20,  0,  0,  0,  0,-20,-40,
            -30,  5, 10, 15, 15, 10,  5,-30,
            -30,  5, 15, 20, 20, 15,  5,-30,
            -30,  5, 15, 20, 20, 15,  5,-30,
            -30,  5, 10, 15, 15, 10,  5,-30,
            -40,-20,  0,  5,  5,  0,-20,-40,
            -50,-40,-30,-30,-30,-30,-40,-50,
    };
    // IMPROVED Bishop tables - favor long diagonals and fianchetto positions
    public final static int[] WHITE_BISHOP_PREFERRED_COORDINATES = {
            -20,-10,-10,-10,-10,-10,-10,-20,
            -10,  5,  0,  0,  0,  0,  5,-10,
            -10, 10, 10, 10, 10, 10, 10,-10,
            -10,  0, 10, 15, 15, 10,  0,-10,
            -10,  5, 10, 15, 15, 10,  5,-10,
            -10,  0, 10, 10, 10, 10,  0,-10,
            -10, 10,  5,  0,  0,  5, 10,-10,  // Fianchetto bonus
            -20,-10,-10,-10,-10,-10,-10,-20
    };
    public final static int[] BLACK_BISHOP_PREFERRED_COORDINATES = {
            -20,-10,-10,-10,-10,-10,-10,-20,
            -10, 10,  5,  0,  0,  5, 10,-10,  // Fianchetto bonus
            -10,  0, 10, 10, 10, 10,  0,-10,
            -10,  5, 10, 15, 15, 10,  5,-10,
            -10,  0, 10, 15, 15, 10,  0,-10,
            -10, 10, 10, 10, 10, 10, 10,-10,
            -10,  5,  0,  0,  0,  0,  5,-10,
            -20,-10,-10,-10,-10,-10,-10,-20
    };
    // IMPROVED Rook tables - favor central files and 7th rank
    public final static int[] WHITE_ROOK_PREFERRED_COORDINATES = {
            0,  0,  0,  5,  5,  0,  0,  0,   // Back rank connection bonus
            5, 10, 10, 10, 10, 10, 10,  5,
            -5,  0,  0,  0,  0,  0,  0, -5,
            -5,  0,  0,  0,  0,  0,  0, -5,
            -5,  0,  0,  0,  0,  0,  0, -5,
            -5,  0,  0,  5,  5,  0,  0, -5,   // Central files bonus
            5, 20, 20, 25, 25, 20, 20,  5,   // 7th rank extra strong
            0,  0,  5, 10, 10,  5,  0,  0    // d/e files preferred
    };
    public final static int[] BLACK_ROOK_PREFERRED_COORDINATES = {
            0,  0,  5, 10, 10,  5,  0,  0,   // d/e files preferred
            5, 20, 20, 25, 25, 20, 20,  5,   // 2nd rank extra strong
            -5,  0,  0,  5,  5,  0,  0, -5,   // Central files bonus
            -5,  0,  0,  0,  0,  0,  0, -5,
            -5,  0,  0,  0,  0,  0,  0, -5,
            -5,  0,  0,  0,  0,  0,  0, -5,
            5, 10, 10, 10, 10, 10, 10,  5,
            0,  0,  0,  5,  5,  0,  0,  0    // Back rank connection bonus
    };
    // IMPROVED Queen tables - asymmetric, favors kingside and mobility
    public final static int[] WHITE_QUEEN_PREFERRED_COORDINATES = {
            -20,-10,-10, -5, -5,-10,-10,-20,
            -10,  0,  0,  0,  0,  5,  0,-10,  // Slight kingside preference
            -10,  0,  5,  5,  5,  5,  5,-10,
            -5,  0,  5,  5,  5,  5,  0, -5,
            0,  0,  5,  5,  5,  5,  0, -5,
            -10,  5,  5,  5,  5,  5,  0,-10,
            -10,  0,  5,  0,  0,  0,  0,-10,
            -20,-10,-10, -5, -5,-10,-10,-20
    };
    public final static int[] BLACK_QUEEN_PREFERRED_COORDINATES = {
            -20,-10,-10, -5, -5,-10,-10,-20,
            -10,  0,  5,  0,  0,  0,  0,-10,
            -10,  5,  5,  5,  5,  5,  0,-10,
            0,  0,  5,  5,  5,  5,  0, -5,
            -5,  0,  5,  5,  5,  5,  0, -5,
            -10,  0,  5,  5,  5,  5,  5,-10,
            -10,  0,  0,  0,  0,  5,  0,-10,  // Slight kingside preference
            -20,-10,-10, -5, -5,-10,-10,-20
    };
    // IMPROVED King tables - balanced for safety and endgame activity
    public final static int[] WHITE_KING_PREFERRED_COORDINATES = {
            -30,-40,-40,-50,-50,-40,-40,-30,
            -30,-40,-40,-50,-50,-40,-40,-30,
            -30,-40,-40,-50,-50,-40,-40,-30,
            -30,-40,-40,-50,-50,-40,-40,-30,
            -20,-30,-30,-40,-40,-30,-30,-20,
            -10,-20,-20,-20,-20,-20,-20,-10,
            20, 20,  0,  0,  0,  0, 20, 20,   // Castling positions
            20, 30, 10,  0,  0, 10, 30, 20    // g1/b1 strongly preferred
    };
    public final static int[] BLACK_KING_PREFERRED_COORDINATES = {
            20, 30, 10,  0,  0, 10, 30, 20,   // g8/b8 strongly preferred
            20, 20,  0,  0,  0,  0, 20, 20,   // Castling positions
            -10,-20,-20,-20,-20,-20,-20,-10,
            -20,-30,-30,-40,-40,-30,-30,-20,
            -30,-40,-40,-50,-50,-40,-40,-30,
            -30,-40,-40,-50,-50,-40,-40,-30,
            -30,-40,-40,-50,-50,-40,-40,-30,
            -30,-40,-40,-50,-50,-40,-40,-30
    };

    private static List<Boolean> initColumn(int columnNumber) {
        final Boolean[] column = new Boolean[NUM_TILES];
        Arrays.fill(column, false);
        do {
            column[columnNumber] = true;
            columnNumber += NUM_TILES_PER_ROW;
        } while(columnNumber < NUM_TILES);
        return Collections.unmodifiableList(Arrays.asList((column)));
    }

    private static List<Boolean> initRow(int rowNumber) {
        final Boolean[] row = new Boolean[NUM_TILES];
        Arrays.fill(row, false);
        do {
            row[rowNumber] = true;
            rowNumber++;
        } while(rowNumber % NUM_TILES_PER_ROW != 0);
        return Collections.unmodifiableList(Arrays.asList(row));
    }

    public static Collection<Move> calculateAttacksOnTile(final int tile,
                                                          final Collection<Move> moves) {
        final List<Move> attackMoves = new ArrayList<>();
        for (final Move move : moves) {
            if (move.getDestinationCoordinate() == tile) {
                attackMoves.add(move);
            }
        }
        return Collections.unmodifiableList(attackMoves);
    }

    private static Map<String, Integer> initializePositionToCoordinateMap() {
        final Map<String, Integer> positionToCoordinate = new HashMap<>();
        for (int i = START_TILE_INDEX; i < NUM_TILES; i++) {
            positionToCoordinate.put(ALGEBRAIC_NOTATION.get(i), i);
        }
        return Collections.unmodifiableMap(positionToCoordinate);
    }

    private static List<String> initializeAlgebraicNotation() {
        return List.of("a8", "b8", "c8", "d8", "e8", "f8", "g8", "h8",
                                 "a7", "b7", "c7", "d7", "e7", "f7", "g7", "h7",
                                 "a6", "b6", "c6", "d6", "e6", "f6", "g6", "h6",
                                 "a5", "b5", "c5", "d5", "e5", "f5", "g5", "h5",
                                 "a4", "b4", "c4", "d4", "e4", "f4", "g4", "h4",
                                 "a3", "b3", "c3", "d3", "e3", "f3", "g3", "h3",
                                 "a2", "b2", "c2", "d2", "e2", "f2", "g2", "h2",
                                 "a1", "b1", "c1", "d1", "e1", "f1", "g1", "h1");
    }

    public static boolean isValidTileCoordinate(final int coordinate) {
        return coordinate >= START_TILE_INDEX && coordinate < NUM_TILES;
    }

    public int getCoordinateAtPosition(final String position) {
        return POSITION_TO_COORDINATE.get(position);
    }

    public String getPositionAtCoordinate(final int coordinate) {
        return ALGEBRAIC_NOTATION.get(coordinate);
    }

    public static boolean isThreatenedBoardImmediate(final Board board) {
        return board.whitePlayer().isInCheck() || board.blackPlayer().isInCheck();
    }

    public static boolean isKingPawnTrap(final Board board,
                                         final King king,
                                         final int frontTile) {
        final Piece piece = board.getPiece(frontTile);
        return piece != null &&
               piece.getPieceType() == Piece.PieceType.PAWN &&
               piece.getPieceAllegiance() != king.getPieceAllegiance();
    }

    public static int mvvlva(final Move move) {
        if (!move.isAttack()) {
            return 0;
        }
        final int victimValue = move.getAttackedPiece().getPieceValue();
        final int attackerValue = move.getMovedPiece().getPieceValue();
        return (victimValue * 10) - attackerValue + (move.getMovedPiece().getPieceType() == Piece.PieceType.PAWN ? 10 : 0);
    }

    public static boolean isEndGame(final Board board) {
        return board.currentPlayer().isInCheckMate() ||
               board.currentPlayer().isInStaleMate();
    }

    public static String humanReadableElapsedTime(long millis) {
        if (millis < 0) {
            throw new IllegalArgumentException("Duration must be non-negative");
        }

        long seconds = millis / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;

        seconds %= 60;
        minutes %= 60;
        hours %= 24;

        StringBuilder sb = new StringBuilder();
        if (days > 0) sb.append(days).append(days == 1 ? " day" : " days");
        if (hours > 0) {
            if (!sb.isEmpty()) sb.append(", ");
            sb.append(hours).append(hours == 1 ? " hour" : " hours");
        }
        if (minutes > 0) {
            if (!sb.isEmpty()) sb.append(", ");
            sb.append(minutes).append(minutes == 1 ? " minute" : " minutes");
        }
        if (seconds > 0 || sb.isEmpty()) {
            if (!sb.isEmpty()) sb.append(", ");
            sb.append(seconds).append(seconds == 1 ? " second" : " seconds");
        }
        return sb.toString();
    }

}
