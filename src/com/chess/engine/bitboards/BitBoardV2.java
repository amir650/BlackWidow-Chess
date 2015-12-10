package com.chess.engine.bitboards;

import java.util.BitSet;

public class BitBoardV2 {

    final long bits;

    BitBoardV2(long bits) {
        this.bits = bits;
    }

    @Override
    public String toString() {
        final String s = String.format("%64s", Long.toBinaryString(this.bits)).replace(' ', '0');
        final StringBuilder builder = new StringBuilder();
        int i = 1;
        for(final char c : s.toCharArray()) {
            builder.append(c);
            if(i % 8 == 0) {
                builder.append("\n");
            }
            i++;
        }
        return builder.toString();
    }

    public static void main(String[] args) {
        BitBoardV2 board = new BitBoardV2(4);
        System.out.println(board);
    }
}
