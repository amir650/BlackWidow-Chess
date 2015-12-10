package com.chess.engine.bitboards;
import java.util.BitSet;

public final class ChessBitSet extends BitSet {

	public ChessBitSet() {
		super(64);
	}

	public ChessBitSet(final ChessBitSet bSet) {
		super(64);
		or(bSet);
	}

	public BitSet shiftLeft(BitSet bits, int n) {
		final long[] words = bits.toLongArray();
		// Do the shift
		for (int i = 0; i < words.length - 1; i++) {
			words[i] >>>= n; // Shift current word
			words[i] |= words[i + 1] << (64 - n); // Do the carry
		}
		words[words.length - 1] >>>= n; // shift [words.length-1] separately, since no carry
		return BitSet.valueOf(words);
	}

    public BitSet shiftRight(BitSet bits, int n) {
        long[] words = bits.toLongArray();
        // Expand array if there will be carry bits
        if (words[words.length - 1] >>> n > 0) {
            long[] tmp = new long[words.length + 1];
            System.arraycopy(words, 0, tmp, 0, words.length);
            words = tmp;
        }
        // Do the shift
        for (int i = words.length - 1; i > 0; i--) {
            words[i] <<= n; // Shift current word
            words[i] |= words[i - 1] >>> (64 - n); // Do the carry
        }
        words[0] <<= n; // shift [0] separately, since no carry
        return BitSet.valueOf(words);
    }

	public ChessBitSet shift(final int shiftValue) {
		final int len = length();
		if (shiftValue > 0) {
			if (len + shiftValue < 64) {
				for (int bitIndex = len; bitIndex >= 0; bitIndex--) {
					set(bitIndex + shiftValue, get(bitIndex));
				}
				clear(0, shiftValue);
			} else {
				clear(shiftValue, len + shiftValue);
			}
		} else if (shiftValue < 0) {
			if (len < -shiftValue) {
				clear();
			} else {
				for (int bitIndex = -shiftValue; bitIndex < length(); bitIndex++) {
					set(bitIndex + shiftValue, get(bitIndex));
				}
				clear(len + shiftValue, len);
			}
		}

		return this;
	}

	@Override
	public String toString() {
		final StringBuilder s = new StringBuilder();
		for (int i = 0; i < size(); i++) {
			final boolean bit_is_set = get(i);
			if (bit_is_set) {
				s.append(" 1 ");
			} else {
				s.append(" . ");
			}
			if ((i + 1) % 8 == 0) {
				s.append("\n");
			}
		}
		return s.toString();
	}

}
