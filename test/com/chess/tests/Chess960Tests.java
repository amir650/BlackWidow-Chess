package com.chess.tests;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.pieces.Pawn;
import com.chess.engine.pieces.Piece;
import com.chess.pgn.Chess960;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


public class Chess960Tests {

    @Test
    public void testHasEightPieces() {
        Chess960 chess960 = new Chess960();

        char[] position = chess960.generateStartingPosition();
        assertEquals(8, position.length);

        for (char piece : position) {
            assertNotEquals('\0', piece);
        }
    }

    @Test
    public void testHasCorrectPieces() {

        Chess960 chess960 = new Chess960();

        char[] position = chess960.generateStartingPosition();

        assertEquals(2, countPiece(position, 'R'));
        assertEquals(2, countPiece(position, 'N'));
        assertEquals(2, countPiece(position, 'B'));
        assertEquals(1, countPiece(position, 'Q'));
        assertEquals(1, countPiece(position, 'K'));
    }

    @Test
    public void testBishopsOnOppositeColors() {
        Chess960 chess960 = new Chess960();
        char[] position = chess960.generateStartingPosition();

        int firstBishop = -1;
        int secondBishop = -1;

        for (int i = 0; i < position.length; i++) {
            if (position[i] == 'B') {
                if (firstBishop == -1) {
                    firstBishop = i;
                } else {
                    secondBishop = i;
                }
            }
        }

        assertNotEquals(firstBishop % 2, secondBishop % 2);

    }

    @Test
    public void testKingIsBetweenRooks() {
        Chess960 chess960 = new Chess960();
        char[] position = chess960.generateStartingPosition();

        int firstRook = -1;
        int secondRook = -1;
        int king = -1;

        for (int i = 0; i < position.length; i++) {
            if (position[i] == 'K') {
                king = i;
            }
            if (position[i] == 'R') {
                if (firstRook == -1) {
                    firstRook = i;
                } else {
                    secondRook = i;
                }
            }
        }

        assertTrue(firstRook < king);
        assertTrue(secondRook > king);
    }

    @Test
    public void testPawnPositionsAreNormal() {
        Chess960 chess960 = new Chess960();
        Board board = chess960.placePieces();


        for (int i = 48; i < 56; i++) {
            assertTrue(board.getPiece(i).getPieceType() == Piece.PieceType.PAWN);
            assertTrue(board.getPiece(i).getPieceAllegiance() == Alliance.WHITE);
        }
        for (int i = 8; i < 16; i++) {
            assertTrue(board.getPiece(i).getPieceType() == Piece.PieceType.PAWN);
            assertTrue(board.getPiece(i).getPieceAllegiance() == Alliance.BLACK);
        }
    }

    @Test
    public void testBothBackRanksAreTheSame(){
        Chess960 chess960 = new Chess960();
        Board board = chess960.placePieces();
        List<Piece.PieceType> pieces = new ArrayList<>();

//        TODO: Test all 8 positions on one side and then verify the other side has the same layout

        for (int i = 0; i < 8; i++) {
            pieces.add(board.getPiece(i).getPieceType());
        }

        assertTrue(board.getPiece(0).getPieceType() == board.getPiece(56).getPieceType());
        assertTrue(board.getPiece(1).getPieceType() == board.getPiece(57).getPieceType());
        assertTrue(board.getPiece(2).getPieceType() == board.getPiece(58).getPieceType());
        assertTrue(board.getPiece(3).getPieceType() == board.getPiece(59).getPieceType());
        assertTrue(board.getPiece(4).getPieceType() == board.getPiece(60).getPieceType());
        assertTrue(board.getPiece(5).getPieceType() == board.getPiece(61).getPieceType());
        assertTrue(board.getPiece(6).getPieceType() == board.getPiece(62).getPieceType());
        assertTrue(board.getPiece(7).getPieceType() == board.getPiece(63).getPieceType());
    }


    //Helper functs
    private int countPiece(char[] position, char piece) {
        int count = 0;

        for (char current : position) {
            if (current == piece) {
                count++;
            }
        }
        return count;
    }
}
