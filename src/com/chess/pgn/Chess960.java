package com.chess.pgn;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.pieces.King;
import com.chess.engine.pieces.PieceUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Chess960 {

    private final Random random = new Random();
    private char[] positions = new char[8];

    public Board placePieces() {

        final Board.Builder builder = new Board.Builder();

        positions = generateStartingPosition();
        placeWhitePieces(builder);
        placeBlackPieces(builder);
        placePawns(builder);

        //white to move
        builder.setMoveMaker(Alliance.WHITE);

        return builder.build();
    }


    public char[] generateStartingPosition() {
        /* Rules of Chess960
    Bishops must be on opposite colored squared
    The king must be between the rooks
    The rest of the pieces are placed randomly
    Each side is equal and opposite, so the board setup is the same on both sides
 */
        //reset the position array
        positions = new char[8];

        //holds the position of the pieces
        //ex - - - - B - - -
        int[] whiteDarkSquares = {0, 2, 4, 6};
        int[] whiteLightSquares = {1, 3, 5, 7};

        //white pieces

        //Bishops
        int randomNumber = random.nextInt(4);
        positions[whiteLightSquares[randomNumber]] = 'B';

        randomNumber = random.nextInt(4);
        positions[whiteDarkSquares[randomNumber]] = 'B';

        //Queens
        positions[findRandomEmptySpots(positions)] = 'Q';

        //Knights
        positions[findRandomEmptySpots(positions)] = 'N';
        positions[findRandomEmptySpots(positions)] = 'N';

        placeKingAndRooks(positions);

        return positions;

    }

    public void placeWhitePieces(Board.Builder builder) {
        int position = 56;
        for (int i = 0; i < positions.length; i++) {
            switch (positions[i]) {
                case 'R':
                    builder.setPiece(PieceUtils.INSTANCE.getRook(Alliance.WHITE, (position + i), false));
                    break;

                case 'N':
                    builder.setPiece(PieceUtils.INSTANCE.getKnight(Alliance.WHITE, (position + i), false));
                    break;
                case 'B':
                    builder.setPiece(PieceUtils.INSTANCE.getBishop(Alliance.WHITE, (position + i), false));
                    break;
                case 'Q':
                    builder.setPiece(PieceUtils.INSTANCE.getQueen(Alliance.WHITE, (position + i), false));
                    break;
                case 'K':
                    builder.setPiece(new King(Alliance.WHITE, (position + i), true, true));
                    break;
            }
        }
    }

    public void placeBlackPieces(Board.Builder builder) {
        int position = 0;
        for (int i = 0; i < positions.length; i++) {
            switch (positions[i]) {
                case 'R':
                    builder.setPiece(PieceUtils.INSTANCE.getRook(Alliance.BLACK, (position + i), false));
                    break;

                case 'N':
                    builder.setPiece(PieceUtils.INSTANCE.getKnight(Alliance.BLACK, (position + i), false));
                    break;
                case 'B':
                    builder.setPiece(PieceUtils.INSTANCE.getBishop(Alliance.BLACK, (position + i), false));
                    break;
                case 'Q':
                    builder.setPiece(PieceUtils.INSTANCE.getQueen(Alliance.BLACK, (position + i), false));
                    break;
                case 'K':
                    builder.setPiece(new King(Alliance.BLACK, (position + i), true, true));
                    break;
            }
        }
    }
    public void placePawns(Board.Builder builder) {
        builder.setPiece(PieceUtils.INSTANCE.getPawn(Alliance.WHITE, 48, false));
        builder.setPiece(PieceUtils.INSTANCE.getPawn(Alliance.WHITE, 49, false));
        builder.setPiece(PieceUtils.INSTANCE.getPawn(Alliance.WHITE, 50, false));
        builder.setPiece(PieceUtils.INSTANCE.getPawn(Alliance.WHITE, 51, false));
        builder.setPiece(PieceUtils.INSTANCE.getPawn(Alliance.WHITE, 52, false));
        builder.setPiece(PieceUtils.INSTANCE.getPawn(Alliance.WHITE, 53, false));
        builder.setPiece(PieceUtils.INSTANCE.getPawn(Alliance.WHITE, 54, false));
        builder.setPiece(PieceUtils.INSTANCE.getPawn(Alliance.WHITE, 55, false));

        builder.setPiece(PieceUtils.INSTANCE.getPawn(Alliance.BLACK, 8, false));
        builder.setPiece(PieceUtils.INSTANCE.getPawn(Alliance.BLACK, 9, false));
        builder.setPiece(PieceUtils.INSTANCE.getPawn(Alliance.BLACK, 10, false));
        builder.setPiece(PieceUtils.INSTANCE.getPawn(Alliance.BLACK, 11, false));
        builder.setPiece(PieceUtils.INSTANCE.getPawn(Alliance.BLACK, 12, false));
        builder.setPiece(PieceUtils.INSTANCE.getPawn(Alliance.BLACK, 13, false));
        builder.setPiece(PieceUtils.INSTANCE.getPawn(Alliance.BLACK, 14, false));
        builder.setPiece(PieceUtils.INSTANCE.getPawn(Alliance.BLACK, 15, false));

    }


    public int findRandomEmptySpots(char[] positions) {
        List<Integer> emptyIndices = new ArrayList<>();
        for (int i = 0; i < positions.length; i++) {
            if (positions[i] == 0) { // Change condition based on your definition of "empty"
                emptyIndices.add(i);
            }
        }

        // 2. Check if any empty spots exist
        if (!emptyIndices.isEmpty()) {
            // Pick a random position from our list of empty indices
            return emptyIndices.get(random.nextInt(emptyIndices.size()));

        } else {
            throw new IllegalStateException("The array is completely full!");
        }

    }

    public void placeKingAndRooks(char[] positions) {
        List<Integer> emptyIndices = new ArrayList<>();
        for (int i = 0; i < positions.length; i++) {
            if (positions[i] == 0) { // Change condition based on your definition of "empty"
                emptyIndices.add(i);
            }
        }

        // 2. Check if any empty spots exist
        if (emptyIndices.size() >= 3) {
            positions[emptyIndices.get(0)] = 'R';
            positions[emptyIndices.get(1)] = 'K';
            positions[emptyIndices.get(2)] = 'R';

        } else {
            System.out.println("Not enough open spaces to place a king and 2 rooks");
        }
    }


}