package com.chess.engine.bitboards;

import com.google.common.collect.ImmutableList;
import com.google.common.primitives.Longs;

import java.util.List;

public class BitBoardV2 {

    private final long whitePawns;
    private final long whiteKnights;
    private final long whiteBishops;
    private final long whiteRooks;
    private final long whiteQueen;
    private final long whiteKing;

    private final long blackPawns;
    private final long blackKnights;
    private final long blackBishops;
    private final long blackRooks;
    private final long blackQueen;
    private final long blackKing;

    private final long whitePieces;
    private final long blackPieces;

    private static final long WHITE_PAWNS_INITIAL_POSITIONS = 0x000000000000FF00L;
    private static final long WHITE_KNIGHTS_INITIAL_POSITIONS = 0x0000000000000042L;
    private static final long WHITE_BISHOPS_INITIAL_POSITIONS = 0x0000000000000024L;
    private static final long WHITE_ROOKS_INITIAL_POSITIONS = 0x0000000000000081L;
    private static final long WHITE_QUEEN_INITIAL_POSITION = 0x0000000000000010L;
    private static final long WHITE_KING_INITIAL_POSITION = 0x0000000000000008L;
    private static final long BLACK_PAWNS_INITIAL_POSITIONS = 0x00FF000000000000L;
    private static final long BLACK_KNIGHTS_INITIAL_POSITIONS = 0x4200000000000000L;
    private static final long BLACK_BISHOPS_INITIAL_POSITIONS = 0x2400000000000000L;
    private static final long BLACK_ROOKS_INITIAL_POSITIONS = 0x8100000000000000L;
    private static final long BLACK_QUEEN_INITIAL_POSITION = 0x1000000000000000L;
    private static final long BLACK_KING_INITIAL_POSITION = 0x0800000000000000L;

    private static final long ALL_TILES = 0xFFFFFFFFFFFFFFFFL;
    private static final List<Long> KNIGHT_LOOKUP_TABLE = calculateKnightLookupTable();
    private final static byte[] BISHOP_DIAGONALS = { -9, -7, 7, 9 };

    private BitBoardV2(final long whitePawns,
                       final long whiteKnights,
                       final long whiteBishops,
                       final long whiteRooks,
                       final long whiteQueen,
                       final long whiteKing,
                       final long blackPawns,
                       final long blackKnights,
                       final long blackBishops,
                       final long blackRooks,
                       final long blackQueen,
                       final long blackKing) {
        this.whitePawns = whitePawns;
        this.whiteKnights = whiteKnights;
        this.whiteBishops = whiteBishops;
        this.whiteRooks = whiteRooks;
        this.whiteQueen = whiteQueen;
        this.whiteKing = whiteKing;
        this.blackPawns = blackPawns;
        this.blackKnights = blackKnights;
        this.blackBishops = blackBishops;
        this.blackRooks = blackRooks;
        this.blackQueen = blackQueen;
        this.blackKing = blackKing;

        this.whitePieces = calculateWhitePieces();
        this.blackPieces = calculateBlackPieces();
    }

    public static BitBoardV2 standardBoard() {
        return createStandardBoard();
    }

    private static BitBoardV2 createStandardBoard() {

        return new BitBoardV2(WHITE_PAWNS_INITIAL_POSITIONS,
                              WHITE_KNIGHTS_INITIAL_POSITIONS,
                              WHITE_BISHOPS_INITIAL_POSITIONS,
                              WHITE_ROOKS_INITIAL_POSITIONS,
                              WHITE_QUEEN_INITIAL_POSITION,
                              WHITE_KING_INITIAL_POSITION,
                              BLACK_PAWNS_INITIAL_POSITIONS,
                              BLACK_KNIGHTS_INITIAL_POSITIONS,
                              BLACK_BISHOPS_INITIAL_POSITIONS,
                              BLACK_ROOKS_INITIAL_POSITIONS,
                              BLACK_QUEEN_INITIAL_POSITION,
                              BLACK_KING_INITIAL_POSITION);
    }

    public BitBoardV2 calculateLegalMoves() {
        return new BitBoardV2(calculateWhitePawnLegals(),
                              calculateWhiteKnightLegals(),
                              calculateWhiteBishopLegals(),
                              calculateWhiteRookLegals(),
                              calculateWhiteQueenLegals(),
                              calculateWhiteKingLegals(),
                              calculateBlackPawnLegals(),
                              calculateBlackKnightLegals(),
                              calculateBlackBishopLegals(),
                              calculateBlackRookLegals(),
                              calculateBlackQueenLegals(),
                              calculateBlackKingLegals());
    }

    ////////// ////////// HELPER METHODS BELOW ////////// //////////
    private long calculateWhitePawnLegals() {
        final long allPieces = this.whitePieces & this.blackPieces;
        return (this.whitePawns << 8L & ~(allPieces)) | ((this.whitePawns & WHITE_PAWNS_INITIAL_POSITIONS) << 16L & ~(allPieces));
    }

    private long calculateBlackPawnLegals() {
        final long allPieces = this.whitePieces & this.blackPieces;
        return (this.whitePawns >> 8L & ~(allPieces)) | ((this.whitePawns & BLACK_PAWNS_INITIAL_POSITIONS) >> 16L & ~(allPieces));
    }

    private long calculateWhiteKnightLegals() {
        long legals = 0L;
        for(final byte position : bitPositions(this.whiteKnights)) {
            legals |= KNIGHT_LOOKUP_TABLE.get(position) & ~(this.whitePieces);
        }
        return legals;
    }

    private long calculateBlackKnightLegals() {
        long legals = 0L;
        for(final byte position : bitPositions(this.blackKnights)) {
            legals |= KNIGHT_LOOKUP_TABLE.get(position) & ~(this.blackPieces);
        }
        return legals;
    }

    private long calculateWhiteBishopLegals() {

        long legals = 0L;
        for(final byte position : bitPositions(this.whiteBishops)) {
            byte candidate = position;
            for(final byte diagonal : BISHOP_DIAGONALS) {
                candidate += diagonal;
                while(candidate >= 0 && candidate < 64 && (candidate | this.whitePawns) == 0L) {
                    legals |= candidate;
                    if((candidate | this.blackPieces) != 0L) {
                        break;
                    }
                    candidate += diagonal;
                }
            }
        }
        return legals;
    }

    private long calculateBlackBishopLegals() {
        long legals = 0L;
        for(final byte position : bitPositions(this.blackBishops)) {
            byte candidate = position;
            for(final byte diagonal : BISHOP_DIAGONALS) {
                candidate += diagonal;
                while(candidate >= 0 && candidate < 64 && (candidate | this.blackPieces) == 0L) {
                    legals |= candidate;
                    if((candidate | this.whitePieces) != 0L) {
                        break;
                    }
                    candidate += diagonal;
                }
            }
        }
        return legals;
    }

    private long calculateWhiteRookLegals() {
        long legals = 0L;
        for(final byte position : bitPositions(this.whiteRooks)) {
            legals |= position;
        }
        return legals;
    }

    private long calculateBlackRookLegals() {
        long legals = 0L;
        for(final byte position : bitPositions(this.blackRooks)) {
            legals |= position;
        }
        return legals;
    }

    private long calculateWhiteQueenLegals() {
        long legals = 0L;
        for(final byte position : bitPositions(this.whiteQueen)) {
            legals |= position;
        }
        return legals;
    }

    private long calculateBlackQueenLegals() {
        long legals = 0L;
        for(final byte position : bitPositions(this.blackQueen)) {
            legals |= position;
        }
        return legals;
    }

    private long calculateWhiteKingLegals() {
        long legals = 0L;
        for(final byte position : bitPositions(this.whiteKing)) {
            legals |= position;
        }
        return legals;    }

    private long calculateBlackKingLegals() {
        long legals = 0L;
        for(final byte position : bitPositions(this.blackKing)) {
            legals |= position;
        }
        return legals;
    }

    private long calculateWhitePieces() {
        return this.whitePawns | this.whiteKnights | this.whiteBishops | this.whiteRooks | this.whiteQueen | this.whiteKing;
    }

    private long calculateBlackPieces() {
        return this.blackPawns | this.blackKnights | this.blackBishops | this.blackRooks | this.blackQueen | this.blackKing;
    }

    @Override
    public String toString() {
        return toBinaryString("BLACK KING", this.blackKing) +
               toBinaryString("BLACK QUEEN", this.blackQueen) +
               toBinaryString("BLACK ROOKS", this.blackRooks) +
               toBinaryString("BLACK BISHOPS", this.blackBishops) +
               toBinaryString("BLACK KNIGHTS", this.blackKnights) +
               toBinaryString("BLACK PAWNS", this.blackPawns) +
               toBinaryString("WHITE KING", this.whiteKing) +
               toBinaryString("WHITE QUEEN", this.whiteQueen) +
               toBinaryString("WHITE ROOKS", this.whiteRooks) +
               toBinaryString("WHITE BISHOPS", this.whiteBishops) +
               toBinaryString("WHITE KNIGHTS", this.whiteKnights) +
               toBinaryString("WHITE PAWNS", this.whitePawns);
    }

    private static String toBinaryString(final String title,
                                         final long bits) {
        final String s = String.format("%64s", Long.toBinaryString(bits)).replace(' ', '0');
        final StringBuilder builder = new StringBuilder(title + "\n");
        int i = 1;
        for(final char c : s.toCharArray()) {
            builder.append(c);
            if(i % 8 == 0) {
                builder.append("\n");
            }
            i++;
        }
        return builder.toString() + "\n";
    }

    private static byte[] findBitPositions(long lTest)
    {
        final byte[] result = new byte[Long.bitCount(lTest)];
        byte iBit = 0;
        int iDelta, index;
        index = 0;
        while (lTest != 0L) {
            iDelta = Long.numberOfTrailingZeros(lTest);
            iBit += iDelta;
            result[index] = iBit;
            iBit++; // skip the 1 bit we just reported
            iDelta++; // shift off the 1 bit we just reported
            index++;
            lTest >>= iDelta;
        }
        return result;
    }

    private static byte[] bitPositions(long n) {
        final byte[] result = new byte[Long.bitCount(n)];
        int i = 0;
        for (byte bit = 0; n != 0L; bit++) {
            if ((n & 1L) != 0) result[i++] = bit;
            n >>>= 1;
        }
        return result;
    }

    @SuppressWarnings("NumericOverflow")
    private static List<Long> calculateKnightLookupTable() {

        final long[] knightLookupTable = new long[64];

        knightLookupTable[0] = 1L << 10L | 1L << 17L;
        knightLookupTable[1] = 1L << 11L | 1L << 16L | 1L << 18L;
        knightLookupTable[2] = 1L << 8L | 1L << 12L | 1L << 17L | 1L << 19L;
        knightLookupTable[3] = 1L << 9L | 1L << 13L | 1L << 18L | 1L << 20L;
        knightLookupTable[4] = 1L << 10L | 1L << 14L | 1L << 19L | 1L << 21L;
        knightLookupTable[5] = 1L << 11L | 1L << 15L | 1L << 20L | 1L << 22L;
        knightLookupTable[6] = 1L << 12L | 1L << 21L | 1L << 23L;
        knightLookupTable[7] = 1L << 13L | 1L << 22L;

        knightLookupTable[8] = 1L << 2L | 1L << 18L | 1L << 25L;
        knightLookupTable[9] = 1L << 3L | 1L << 19L | 1L << 24L | 1L << 26L;
        knightLookupTable[10] = 1L | 1L << 4L | 1L << 16L | 1L << 20L | 1L << 25L | 1L << 27L;
        knightLookupTable[11] = 1L << 1L | 1L << 5L | 1L << 17L | 1L << 21L | 1L << 26L | 1L << 28L;
        knightLookupTable[12] = 1L << 2L | 1L << 6L | 1L << 18L | 1L << 22L | 1L << 27L | 1L << 29L;
        knightLookupTable[13] = 1L << 3L | 1L << 7L | 1L << 19L | 1L << 23L | 1L << 28L | 1L << 30L;
        knightLookupTable[14] = 1L << 4L | 1L << 20L | 1L << 29L | 1L << 31L;
        knightLookupTable[15] = 1L << 5L | 1L << 21L | 1L << 30L;

        knightLookupTable[16] = 1L << 1L | 1L << 10L | 1L << 26L | 1L << 33L;
        knightLookupTable[17] = 1L | 1L << 2L | 1L << 11L | 1L << 27L | 1L << 32L | 1L << 34L;
        knightLookupTable[18] = 1L << 1L | 1L << 3L | 1L << 8L | 1L << 12L | 1L << 24L | 1L << 28L | 1L << 33L | 1L << 35L;
        knightLookupTable[19] = 1L << 2L | 1L << 4L | 1L << 9L | 1L << 13L | 1L << 25L | 1L << 29L | 1L << 34L | 1L << 36L;
        knightLookupTable[20] = 1L << 3L | 1L << 5L | 1L << 10L | 1L << 14L | 1L << 26L | 1L << 30L | 1L << 35L | 1L << 37L;
        knightLookupTable[21] = 1L << 4L | 1L << 6L | 1L << 11L | 1L << 15L | 1L << 27L | 1L << 31L | 1L << 36L | 1L << 38L;
        knightLookupTable[22] = 1L << 5L | 1L << 7L | 1L << 12L | 1L << 28L | 1L << 37L | 1L << 39L;
        knightLookupTable[23] = 1L << 6L | 1L << 13L | 1L << 29L | 1L << 38L;

        knightLookupTable[24] = 1L << 9L | 1L << 18L | 1L << 34L | 1L << 41L;
        knightLookupTable[25] = 1L << 8L | 1L << 10L | 1L << 19L | 1L << 35L | 1L << 40L | 1L << 42L;
        knightLookupTable[26] = 1L << 9L | 1L << 11L | 1L << 16L | 1L << 20L | 1L << 32L | 1L << 36 | 1L << 41L | 1L << 43L;
        knightLookupTable[27] = 1L << 10L | 1L << 12L | 1L << 17L | 1L << 21L | 1L << 33L | 1L << 37L | 1L << 42L | 1L << 44L;
        knightLookupTable[28] = 1L << 11L | 1L << 13L | 1L << 18L | 1L << 22L | 1L << 34L | 1L << 38L | 1L << 43L | 1L << 45L;
        knightLookupTable[29] = 1L << 12L | 1L << 14L | 1L << 19L | 1L << 23L | 1L << 35L | 1L << 39L | 1L << 44L | 1L << 46L;
        knightLookupTable[30] = 1L << 13L | 1L << 15L | 1L << 20L | 1L << 36L | 1L << 45L | 1L << 47L;
        knightLookupTable[31] = 1L << 14L | 1L << 21L | 1L << 37L | 1L << 46L;

        knightLookupTable[32] = 1L << 17L | 1L << 26L | 1L << 42L | 1L << 49L;
        knightLookupTable[33] = 1L << 16L | 1L << 18L | 1L << 27L | 1L << 43L | 1L << 48L | 1L << 50L;
        knightLookupTable[34] = 1L << 17L | 1L << 19L | 1L << 24L | 1L << 28L | 1L << 40L | 1L << 44L | 1L << 49L | 1L << 51L;
        knightLookupTable[35] = 1L << 18L | 1L << 20L | 1L << 25L | 1L << 29L | 1L << 41L | 1L << 45L | 1L << 50L | 1L << 52L;
        knightLookupTable[36] = 1L << 19L | 1L << 21L | 1L << 26L | 1L << 30L | 1L << 42L | 1L << 46L | 1L << 51L | 1L << 53L;
        knightLookupTable[37] = 1L << 20L | 1L << 22L | 1L << 27L | 1L << 31L | 1L << 43L | 1L << 47L | 1L << 52L | 1L << 54L;
        knightLookupTable[38] = 1L << 21L | 1L << 23L | 1L << 28L | 1L << 44L | 1L << 53L | 1L << 55L;
        knightLookupTable[39] = 1L << 22L | 1L << 29L | 1L << 45L | 1L << 54L;

        knightLookupTable[40] = 1L << 25L | 1L << 34L | 1L << 50L | 1L << 57L;
        knightLookupTable[41] = 1L << 24L | 1L << 26L | 1L << 35L | 1L << 51L | 1L << 56L | 1L << 58L;
        knightLookupTable[42] = 1L << 25L | 1L << 27L | 1L << 32L | 1L << 36L | 1L << 48L | 1L << 52L | 1L << 57L | 1L << 59L;
        knightLookupTable[43] = 1L << 26L | 1L << 28L | 1L << 33L | 1L << 37L | 1L << 49L | 1L << 53L | 1L << 58L | 1L << 60L;
        knightLookupTable[44] = 1L << 27L | 1L << 29L | 1L << 34L | 1L << 38L | 1L << 50L | 1L << 54L | 1L << 59L | 1L << 61L;
        knightLookupTable[45] = 1L << 28L | 1L << 30L | 1L << 35L | 1L << 39L | 1L << 51L | 1L << 55L | 1L << 60L | 1L << 62L;
        knightLookupTable[46] = 1L << 29L | 1L << 31L | 1L << 36L | 1L << 52L | 1L << 61L | 1L << 63L;
        knightLookupTable[47] = 1L << 30L | 1L << 37L | 1L << 53L | 1L << 62L;

        knightLookupTable[48] = 1L << 33L | 1L << 42L | 1L << 58L;
        knightLookupTable[49] = 1L << 32L | 1L << 34L | 1L << 43L | 1L << 59L;
        knightLookupTable[50] = 1L << 33L | 1L << 35L | 1L << 40L | 1L << 44L | 1L << 56L | 1L << 60L;
        knightLookupTable[51] = 1L << 34L | 1L << 36L | 1L << 41L | 1L << 45L | 1L << 57L | 1L << 61L;
        knightLookupTable[52] = 1L << 35L | 1L << 37L | 1L << 42L | 1L << 46L | 1L << 58L | 1L << 62L;
        knightLookupTable[53] = 1L << 36L | 1L << 38L | 1L << 43L | 1L << 47L | 1L << 59L | 1L << 63L;
        knightLookupTable[54] = 1L << 37L | 1L << 39L | 1L << 44L | 1L << 60L;
        knightLookupTable[55] = 1L << 38L | 1L << 45L | 1L << 61L;

        knightLookupTable[56] = 1L << 41L | 1L << 51L;
        knightLookupTable[57] = 1L << 40L | 1L << 42L | 1L << 52L;
        knightLookupTable[58] = 1L << 41L | 1L << 43L | 1L << 53L;
        knightLookupTable[59] = 1L << 42L | 1L << 44L | 1L << 47L | 1L << 54L;
        knightLookupTable[60] = 1L << 43L | 1L << 45L | 1L << 49L | 1L << 55L;
        knightLookupTable[61] = 1L << 44L | 1L << 46L | 1L << 50L | 1L << 55L;
        knightLookupTable[62] = 1L << 45L | 1L << 47L | 1L << 51L;
        knightLookupTable[63] = 1L << 46L | 1L << 53L;

        return ImmutableList.copyOf(Longs.asList(knightLookupTable));
    }

    public static void main(String[] args) {
        BitBoardV2 v2 = standardBoard().calculateLegalMoves();
        System.out.println(v2);
    }
}