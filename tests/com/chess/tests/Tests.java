package com.chess.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;

import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.pieces.Piece;
import com.chess.engine.classic.pieces.Piece.PieceType;
import com.chess.gui.Table;

import org.junit.Assert; 

public class Tests {

	@Test
	public void testBlackBishopPlacement() {
		Board board = Board.create960Board();
		Collection<Piece> pieces = board.getBlackPieces();
		Piece firstBishop = null, secondBishop = null;
		boolean firstFound = false;
		for(Piece piece : pieces) {
			if(piece.getPieceType() == PieceType.BISHOP && !firstFound) {
				firstBishop = piece;
				firstFound = true;
			}
			else if(piece.getPieceType() == PieceType.BISHOP && firstFound) {
				secondBishop = piece;
			}
		}
		
		Assert.assertNotNull(firstBishop);
		Assert.assertNotNull(secondBishop);
		
		if(firstBishop.getPiecePosition() % 2 == 0) {
			Assert.assertEquals(1, secondBishop.getPiecePosition() % 2);
		}
		else {
			Assert.assertEquals(0, secondBishop.getPiecePosition() % 2);
		}
	}

	@Test
	public void testWhiteBishopPlacement() {
		Board board = Board.create960Board();
		Collection<Piece> pieces = board.getWhitePieces();
		Piece firstBishop = null, secondBishop = null;
		boolean firstFound = false;
		for(Piece piece : pieces) {
			if(piece.getPieceType() == PieceType.BISHOP && !firstFound) {
				firstBishop = piece;
				firstFound = true;
			}
			else if(piece.getPieceType() == PieceType.BISHOP && firstFound) {
				secondBishop = piece;
			}
		}
		
		Assert.assertNotNull(firstBishop);
		Assert.assertNotNull(secondBishop);
		
		if(firstBishop.getPiecePosition() % 2 == 0) {
			Assert.assertEquals(1, secondBishop.getPiecePosition() % 2);
		}
		else {
			Assert.assertEquals(0, secondBishop.getPiecePosition() % 2);
		}
	}
	
	@Test
	public void testBlackRookPlacement() {
		Board board = Board.create960Board();
		Collection<Piece> pieces = board.getBlackPieces();
		Piece firstRook = null, secondRook = null, king = null;
		boolean firstFound = false;
		for(Piece piece : pieces) {
			if(piece.getPieceType() == PieceType.ROOK && !firstFound) {
				firstRook = piece;
				firstFound = true;
			}
			else if(piece.getPieceType() == PieceType.ROOK && firstFound) {
				secondRook = piece;
			}
			else if(piece.getPieceType() == PieceType.KING) {
				king = piece;
			}
		}
		
		Assert.assertNotNull(firstRook);
		Assert.assertNotNull(secondRook);
		Assert.assertNotNull(king);
		
		Assert.assertTrue("King is not in between the rooks", firstRook.getPiecePosition() < king.getPiecePosition() && king.getPiecePosition() < secondRook.getPiecePosition());
		
	}
	
	@Test
	public void testWhiteRookPlacement() {
		Board board = Board.create960Board();
		Collection<Piece> pieces = board.getWhitePieces();
		Piece firstRook = null, secondRook = null, king = null;
		boolean firstFound = false;
		for(Piece piece : pieces) {
			if(piece.getPieceType() == PieceType.ROOK && !firstFound) {
				firstRook = piece;
				firstFound = true;
			}
			else if(piece.getPieceType() == PieceType.ROOK && firstFound) {
				secondRook = piece;
			}
			else if(piece.getPieceType() == PieceType.KING) {
				king = piece;
			}
		}
		
		Assert.assertNotNull(firstRook);
		Assert.assertNotNull(secondRook);
		Assert.assertNotNull(king);
		
		Assert.assertTrue("King is not in between the rooks", firstRook.getPiecePosition() < king.getPiecePosition() && king.getPiecePosition() < secondRook.getPiecePosition());
		
	}
	
	@Test
	public void testMirroredPiecePlacement() {
		Board board = Board.create960Board();
		Collection<Piece> whiteCollection = board.getWhitePieces();
		Collection<Piece> blackCollection = board.getBlackPieces();
		
		ArrayList<Piece> whitePieces = new ArrayList<>(whiteCollection);
		ArrayList<Piece> blackPieces = new ArrayList<>(blackCollection);
		
		for(int i = 0; i < 8; i++) {
			Assert.assertTrue("Pieces are not mirrored", (whitePieces.get(i + 8).getPiecePosition() - 56) == blackPieces.get(i).getPiecePosition() );
		}
	}
}
