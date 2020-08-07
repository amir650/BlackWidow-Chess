package com.chess.engine.classic.board;

import com.chess.engine.classic.Alliance;
import com.chess.engine.classic.board.Move.MoveFactory;
import com.chess.engine.classic.pieces.*;
import com.chess.engine.classic.player.BlackPlayer;
import com.chess.engine.classic.player.Player;
import com.chess.engine.classic.player.WhitePlayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Board {

    private final Map<Integer, Piece> boardConfig;
    private final Collection<Piece> whitePieces;
    private final Collection<Piece> blackPieces;
    private final WhitePlayer whitePlayer;
    private final BlackPlayer blackPlayer;
    private final Player currentPlayer;
    private final Pawn enPassantPawn;
    private final Move transitionMove;

    private static final Board STANDARD_BOARD = createStandardBoardImpl();

    private Board(final Builder builder) {
        this.boardConfig = Collections.unmodifiableMap(builder.boardConfig);
        this.whitePieces = calculateActivePieces(builder, Alliance.WHITE);
        this.blackPieces = calculateActivePieces(builder, Alliance.BLACK);
        this.enPassantPawn = builder.enPassantPawn;
        final Collection<Move> whiteStandardMoves = calculateLegalMoves(this.whitePieces);
        final Collection<Move> blackStandardMoves = calculateLegalMoves(this.blackPieces);
        this.whitePlayer = new WhitePlayer(this, whiteStandardMoves, blackStandardMoves);
        this.blackPlayer = new BlackPlayer(this, whiteStandardMoves, blackStandardMoves);
        this.currentPlayer = builder.nextMoveMaker.choosePlayerByAlliance(this.whitePlayer, this.blackPlayer);
        this.transitionMove = builder.transitionMove != null ? builder.transitionMove : MoveFactory.getNullMove();
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
            final String tileText = prettyPrint(this.boardConfig.get(i));
            builder.append(String.format("%3s", tileText));
            if ((i + 1) % 8 == 0) {
                builder.append("\n");
            }
        }
        return builder.toString();
    }

    private static String prettyPrint(final Piece piece) {
        if(piece != null) {
            return piece.getPieceAllegiance().isBlack() ?
                   piece.toString().toLowerCase() : piece.toString();
        }
        return "-";
    }

    public Collection<Piece> getBlackPieces() {
        return this.blackPieces;
    }

    public Collection<Piece> getWhitePieces() {
        return this.whitePieces;
    }

    public Collection<Piece> getAllPieces() {
        return Stream.concat(this.whitePieces.stream(),
                             this.blackPieces.stream()).collect(Collectors.toList());
    }

    public Collection<Move> getAllLegalMoves() {
        return Stream.concat(this.whitePlayer.getLegalMoves().stream(),
                             this.blackPlayer.getLegalMoves().stream()).collect(Collectors.toList());
    }

    public WhitePlayer whitePlayer() {
        return this.whitePlayer;
    }

    public BlackPlayer blackPlayer() {
        return this.blackPlayer;
    }

    public Player currentPlayer() {
        return this.currentPlayer;
    }

    public Piece getPiece(final int coordinate) {
        return this.boardConfig.get(coordinate);
    }

    public Pawn getEnPassantPawn() {
        return this.enPassantPawn;
    }

    public Move getTransitionMove() {
        return this.transitionMove;
    }

    public static Board createStandardBoard() {
    	return STANDARD_BOARD;
    }
    
    public static Board create960Board() {
    	return create960BoardImpl();
    }

    private static Board createStandardBoardImpl() {
        final Builder builder = new Builder();
        // Black Layout
        builder.setPiece(new Rook(Alliance.BLACK, 0));
        builder.setPiece(new Knight(Alliance.BLACK, 1));
        builder.setPiece(new Bishop(Alliance.BLACK, 2));
        builder.setPiece(new Queen(Alliance.BLACK, 3));
        builder.setPiece(new King(Alliance.BLACK, 4, true, true));
        builder.setPiece(new Bishop(Alliance.BLACK, 5));
        builder.setPiece(new Knight(Alliance.BLACK, 6));
        builder.setPiece(new Rook(Alliance.BLACK, 7));
        builder.setPiece(new Pawn(Alliance.BLACK, 8));
        builder.setPiece(new Pawn(Alliance.BLACK, 9));
        builder.setPiece(new Pawn(Alliance.BLACK, 10));
        builder.setPiece(new Pawn(Alliance.BLACK, 11));
        builder.setPiece(new Pawn(Alliance.BLACK, 12));
        builder.setPiece(new Pawn(Alliance.BLACK, 13));
        builder.setPiece(new Pawn(Alliance.BLACK, 14));
        builder.setPiece(new Pawn(Alliance.BLACK, 15));
        // White Layout
        builder.setPiece(new Pawn(Alliance.WHITE, 48));
        builder.setPiece(new Pawn(Alliance.WHITE, 49));
        builder.setPiece(new Pawn(Alliance.WHITE, 50));
        builder.setPiece(new Pawn(Alliance.WHITE, 51));
        builder.setPiece(new Pawn(Alliance.WHITE, 52));
        builder.setPiece(new Pawn(Alliance.WHITE, 53));
        builder.setPiece(new Pawn(Alliance.WHITE, 54));
        builder.setPiece(new Pawn(Alliance.WHITE, 55));
        builder.setPiece(new Rook(Alliance.WHITE, 56));
        builder.setPiece(new Knight(Alliance.WHITE, 57));
        builder.setPiece(new Bishop(Alliance.WHITE, 58));
        builder.setPiece(new Queen(Alliance.WHITE, 59));
        builder.setPiece(new King(Alliance.WHITE, 60, true, true));
        builder.setPiece(new Bishop(Alliance.WHITE, 61));
        builder.setPiece(new Knight(Alliance.WHITE, 62));
        builder.setPiece(new Rook(Alliance.WHITE, 63));
        //white to move
        builder.setMoveMaker(Alliance.WHITE);
        //build the board
        return builder.build();
    }
    
    private static Board create960BoardImpl() {
    	final Builder builder = new Builder();
    	// White Layout - Pawns
    	Random rng = new Random();
    	int[] blackLayout = new int[8];
    	ArrayList<Integer> whiteLayout = new ArrayList<>(Arrays.asList(56,57,58,59,60,61,62,63));
    	
    	builder.setPiece(new Pawn(Alliance.WHITE, 48));
    	builder.setPiece(new Pawn(Alliance.WHITE, 49));
    	builder.setPiece(new Pawn(Alliance.WHITE, 50));
    	builder.setPiece(new Pawn(Alliance.WHITE, 51));
    	builder.setPiece(new Pawn(Alliance.WHITE, 52));
    	builder.setPiece(new Pawn(Alliance.WHITE, 53));
    	builder.setPiece(new Pawn(Alliance.WHITE, 54));
    	builder.setPiece(new Pawn(Alliance.WHITE, 55));
    	
    	int firstBishop, secondBishop;
    	
    	do {
    		firstBishop = whiteLayout.get(rng.nextInt(whiteLayout.size()));
    		secondBishop = whiteLayout.get(rng.nextInt(whiteLayout.size()));
    	} while(!checkBishopPlacements(firstBishop, secondBishop));
    	
    	blackLayout[2] = firstBishop - 56;
    	blackLayout[5] = secondBishop - 56;
    	
    	builder.setPiece(new Bishop(Alliance.WHITE, firstBishop));
    	whiteLayout.remove(new Integer(firstBishop));
    	builder.setPiece(new Bishop(Alliance.WHITE, secondBishop));
    	whiteLayout.remove(new Integer(secondBishop));
    	
    	int queenSpot = whiteLayout.get(rng.nextInt(whiteLayout.size()));
    	blackLayout[3] = queenSpot - 56;
    	builder.setPiece(new Queen(Alliance.WHITE, queenSpot));
    	whiteLayout.remove(new Integer(queenSpot));
    	
    	int firstKnight = whiteLayout.get(rng.nextInt(whiteLayout.size()));
    	blackLayout[1] = firstKnight - 56;
    	builder.setPiece(new Knight(Alliance.WHITE, firstKnight));    	
    	whiteLayout.remove(new Integer(firstKnight));
    	
    	int secondKnight = whiteLayout.get(rng.nextInt(whiteLayout.size()));
    	blackLayout[6] = secondKnight - 56;
    	builder.setPiece(new Knight(Alliance.WHITE, secondKnight));
    	whiteLayout.remove(new Integer(secondKnight));
    	
    	builder.setPiece(new Rook(Alliance.WHITE, whiteLayout.get(0)));
    	blackLayout[0] = whiteLayout.get(0) - 56;
    	whiteLayout.remove(0);
    	builder.setPiece(new King(Alliance.WHITE, whiteLayout.get(0), false, false));
    	blackLayout[4] = whiteLayout.get(0) - 56;
    	whiteLayout.remove(0);
    	builder.setPiece(new Rook(Alliance.WHITE, whiteLayout.get(0)));
    	blackLayout[7] = whiteLayout.get(0) - 56;
    	whiteLayout.remove(0);
    	
    	// Black Layout - Pawns
        builder.setPiece(new Pawn(Alliance.BLACK, 8));
        builder.setPiece(new Pawn(Alliance.BLACK, 9));
        builder.setPiece(new Pawn(Alliance.BLACK, 10));
        builder.setPiece(new Pawn(Alliance.BLACK, 11));
        builder.setPiece(new Pawn(Alliance.BLACK, 12));
        builder.setPiece(new Pawn(Alliance.BLACK, 13));
        builder.setPiece(new Pawn(Alliance.BLACK, 14));
        builder.setPiece(new Pawn(Alliance.BLACK, 15));
        
        
        
        builder.setPiece(new Rook(Alliance.BLACK, blackLayout[0]));
        builder.setPiece(new Knight(Alliance.BLACK, blackLayout[1]));
        builder.setPiece(new Bishop(Alliance.BLACK, blackLayout[2]));
        builder.setPiece(new Queen(Alliance.BLACK, blackLayout[3]));
        builder.setPiece(new King(Alliance.BLACK, blackLayout[4], false, false));
        builder.setPiece(new Bishop(Alliance.BLACK, blackLayout[5]));
        builder.setPiece(new Knight(Alliance.BLACK, blackLayout[6]));
        builder.setPiece(new Rook(Alliance.BLACK, blackLayout[7]));
        //white to move
        builder.setMoveMaker(Alliance.WHITE);
    	return builder.build();
    }
    
    private static boolean checkBishopPlacements(int firstBishop, int secondBishop) {
    	if(firstBishop == secondBishop) {
    		return false;
    	}
    	if(firstBishop % 2 == 0) {
    		if(secondBishop % 2 == 1) {
    			return true;
    		}
    		else {
    			return false;
    		}
    	}
    	else {
    		if(secondBishop % 2 == 0) {
    			return true;
    		}
    		else {
    			return false;
    		}
    	}
    }

    private Collection<Move> calculateLegalMoves(final Collection<Piece> pieces) {
        return pieces.stream().flatMap(piece -> piece.calculateLegalMoves(this).stream())
                      .collect(Collectors.toList());
    }

    private static Collection<Piece> calculateActivePieces(final Builder builder,
                                                           final Alliance alliance) {
        return builder.boardConfig.values().stream()
               .filter(piece -> piece.getPieceAllegiance() == alliance)
               .collect(Collectors.toList());
    }

    public static class Builder {

        Map<Integer, Piece> boardConfig;
        Alliance nextMoveMaker;
        Pawn enPassantPawn;
        Move transitionMove;

        public Builder() {
            this.boardConfig = new HashMap<>(32, 1.0f);
        }

        public Builder setPiece(final Piece piece) {
            this.boardConfig.put(piece.getPiecePosition(), piece);
            return this;
        }

        public Builder setMoveMaker(final Alliance nextMoveMaker) {
            this.nextMoveMaker = nextMoveMaker;
            return this;
        }

        public Builder setEnPassantPawn(final Pawn enPassantPawn) {
            this.enPassantPawn = enPassantPawn;
            return this;
        }

        public Builder setMoveTransition(final Move transitionMove) {
            this.transitionMove = transitionMove;
            return this;
        }

        public Board build() {
            return new Board(this);
        }

    }

}
