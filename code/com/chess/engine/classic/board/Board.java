package com.chess.engine.classic.board;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chess.engine.classic.Alliance;
import com.chess.engine.classic.pieces.Bishop;
import com.chess.engine.classic.pieces.King;
import com.chess.engine.classic.pieces.Knight;
import com.chess.engine.classic.pieces.Pawn;
import com.chess.engine.classic.pieces.Piece;
import com.chess.engine.classic.pieces.Queen;
import com.chess.engine.classic.pieces.Rook;
import com.chess.engine.classic.player.BlackPlayer;
import com.chess.engine.classic.player.Player;
import com.chess.engine.classic.player.WhitePlayer;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

public final class Board {

    private final List<Tile> gameBoard;
    private final List<Piece> whitePieces;
    private final List<Piece> blackPieces;
    private final WhitePlayer whitePlayer;
    private final BlackPlayer blackPlayer;
    private final Player currentPlayer;
    private final Piece enPassantPawn;

    private static final String[] ALGEBRAIC_NOTATION = initializeAlgebraicNotation();
    private static final Map<String, Integer> POSITION_TO_COORDINATE = initializePositionToCoordinateMap();

    public static final int NUM_TILES = 64;
    public static final boolean[] FIRST_COLUMN = initColumn(0);
    public static final boolean[] SECOND_COLUMN = initColumn(1);
    public static final boolean[] THIRD_COLUMN = initColumn(2);
    public static final boolean[] FOURTH_COLUMN = initColumn(3);
    public static final boolean[] FIFTH_COLUMN = initColumn(4);
    public static final boolean[] SIXTH_COLUMN = initColumn(5);
    public static final boolean[] SEVENTH_COLUMN = initColumn(6);
    public static final boolean[] EIGHTH_COLUMN = initColumn(7);
    public static final boolean[] FIRST_ROW = initRow(0);
    public static final boolean[] SECOND_ROW = initRow(8);
    public static final boolean[] THIRD_ROW = initRow(16);
    public static final boolean[] FOURTH_ROW = initRow(24);
    public static final boolean[] FIFTH_ROW = initRow(32);
    public static final boolean[] SIXTH_ROW = initRow(40);
    public static final boolean[] SEVENTH_ROW = initRow(48);
    public static final boolean[] EIGHTH_ROW = initRow(56);

    public Board(final Builder boardBuilder) {
        this.gameBoard = createGameBoard(boardBuilder);
        this.whitePieces = calculateActivePieces(Alliance.WHITE);
        this.blackPieces = calculateActivePieces(Alliance.BLACK);
        this.enPassantPawn = boardBuilder.enPassantPawn;

        final List<Move> whiteStandardMoves = calculateLegalMoves(this.whitePieces);
        final List<Move> blackStandardMoves = calculateLegalMoves(this.blackPieces);

        this.whitePlayer = new WhitePlayer(this, whiteStandardMoves, blackStandardMoves);
        this.blackPlayer = new BlackPlayer(this, whiteStandardMoves, blackStandardMoves);
        this.currentPlayer = boardBuilder.nextMoveMaker == Alliance.WHITE ? this.whitePlayer : this.blackPlayer;
    }

    @Override
    public String toString() {
        final StringBuilder s = new StringBuilder();
        for (int i = 0; i < NUM_TILES; i++) {
            s.append(String.format("%2s", this.gameBoard.get(i)));
            if ((i + 1) % 8 == 0) {
                s.append("\n");
            }
        }
        return s.toString();
    }

    public List<Piece> getBlackPieces() {
        return this.blackPieces;
    }

    public List<Piece> getWhitePieces() {
        return this.whitePieces;
    }

    public Iterable<Piece> getAllPieces() {
        return Iterables.concat(this.whitePieces, this.blackPieces);
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

    public Tile getTile(final int coordinate) {
        return this.gameBoard.get(coordinate);
    }

    public List<Tile> getGameBoard() {
        return this.gameBoard;
    }

    public MoveTransition makeMove(final Move move) {
        if(!this.currentPlayer.isMoveLegal(move)) {
            return new MoveTransition(this, MoveStatus.ILLEGAL_NOT_IN_MOVES_LIST);
        }
        final Board transitionedBoard = move.execute();
        final List<Move> kingAttacks = transitionedBoard.currentPlayer().calculateAttacksOnTile(
                transitionedBoard.currentPlayer().getOpponent().getPlayerKing().getPiecePosition());
        if (!kingAttacks.isEmpty()) {
            return new MoveTransition(this, MoveStatus.ILLEGAL_LEAVES_PLAYER_IN_CHECK);
        }
        return new MoveTransition(transitionedBoard, MoveStatus.DONE);
    }

    public MoveTransition unMakeMove(final Move move) {
        return new MoveTransition(move.undo(), MoveStatus.DONE);
    }

    public static Board createStandardBoard() {
        final Builder builder = new Builder();
        // Black Layout
        builder.setPiece(0, new Rook(Alliance.BLACK, 0));
        builder.setPiece(1, new Knight(Alliance.BLACK, 1));
        builder.setPiece(2, new Bishop(Alliance.BLACK, 2));
        builder.setPiece(3, new Queen(Alliance.BLACK, 3));
        builder.setPiece(4, new King(Alliance.BLACK, 4));
        builder.setPiece(5, new Bishop(Alliance.BLACK, 5));
        builder.setPiece(6, new Knight(Alliance.BLACK, 6));
        builder.setPiece(7, new Rook(Alliance.BLACK, 7));
        builder.setPiece(8, new Pawn(Alliance.BLACK, 8));
        builder.setPiece(9, new Pawn(Alliance.BLACK, 9));
        builder.setPiece(10, new Pawn(Alliance.BLACK, 10));
        builder.setPiece(11, new Pawn(Alliance.BLACK, 11));
        builder.setPiece(12, new Pawn(Alliance.BLACK, 12));
        builder.setPiece(13, new Pawn(Alliance.BLACK, 13));
        builder.setPiece(14, new Pawn(Alliance.BLACK, 14));
        builder.setPiece(15, new Pawn(Alliance.BLACK, 15));
        // White Layout
        builder.setPiece(48, new Pawn(Alliance.WHITE, 48));
        builder.setPiece(49, new Pawn(Alliance.WHITE, 49));
        builder.setPiece(50, new Pawn(Alliance.WHITE, 50));
        builder.setPiece(51, new Pawn(Alliance.WHITE, 51));
        builder.setPiece(52, new Pawn(Alliance.WHITE, 52));
        builder.setPiece(53, new Pawn(Alliance.WHITE, 53));
        builder.setPiece(54, new Pawn(Alliance.WHITE, 54));
        builder.setPiece(55, new Pawn(Alliance.WHITE, 55));
        builder.setPiece(56, new Rook(Alliance.WHITE, 56));
        builder.setPiece(57, new Knight(Alliance.WHITE, 57));
        builder.setPiece(58, new Bishop(Alliance.WHITE, 58));
        builder.setPiece(59, new Queen(Alliance.WHITE, 59));
        builder.setPiece(60, new King(Alliance.WHITE, 60));
        builder.setPiece(61, new Bishop(Alliance.WHITE, 61));
        builder.setPiece(62, new Knight(Alliance.WHITE, 62));
        builder.setPiece(63, new Rook(Alliance.WHITE, 63));
        //white to move
        builder.setMoveMaker(Alliance.WHITE);

        return builder.build();
    }

    private static List<Tile> createGameBoard(final Builder boardBuilder) {
        final ImmutableList.Builder<Tile> boardTiles = ImmutableList.builder();
        for (int i = 0; i < NUM_TILES; i++) {
            boardTiles.add(Tile.createTile(i, boardBuilder.boardConfig.get(i)));
        }
        return boardTiles.build();
    }

    private List<Move> calculateLegalMoves(final List<Piece> pieces) {
        final ImmutableList.Builder<Move> legalMoves = ImmutableList.builder();
        for(final Piece p : pieces) {
            legalMoves.addAll(p.calculateLegalMoves(this));
        }
        return legalMoves.build();
    }

    private List<Piece> calculateActivePieces(final Alliance alliance) {
        final ImmutableList.Builder<Piece> activePieces = ImmutableList.builder();
        for (final Tile t : this.gameBoard) {
            if (t.isTileOccupied()) {
                final Piece p = t.getPiece();
                if (p.getPieceAllegiance().equals(alliance)) {
                    activePieces.add(p);
                }
            }
        }
        return activePieces.build();
    }

    private static Map<String, Integer> initializePositionToCoordinateMap() {
        final Map<String, Integer> posToCoord = new HashMap<>();
        for (int i = 0; i < NUM_TILES; i++) {
            posToCoord.put(ALGEBRAIC_NOTATION[i], i);
        }
        return Collections.unmodifiableMap(posToCoord);
    }

    private static String[] initializeAlgebraicNotation() {
        return new String[] {
                "a8", "b8", "c8", "d8", "e8", "f8", "g8", "h8",
                "a7", "b7", "c7", "d7", "e7", "f7", "g7", "h7",
                "a6", "b6", "c6", "d6", "e6", "f6", "g6", "h6",
                "a5", "b5", "c5", "d5", "e5", "f5", "g5", "h5",
                "a4", "b4", "c4", "d4", "e4", "f4", "g4", "h4",
                "a3", "b3", "c3", "d3", "e3", "f3", "g3", "h3",
                "a2", "b2", "c2", "d2", "e2", "f2", "g2", "h2",
                "a1", "b1", "c1", "d1", "e1", "f1", "g1", "h1"
        };
    }

    private static boolean [] initColumn(int columnNumber) {
        final boolean[] column = new boolean[64];
        do {
            column[columnNumber] = true;
            columnNumber += 8;
        } while(columnNumber < 64);
        return column;
    }

    private static boolean [] initRow(int rowNumber) {
        final boolean[] row = new boolean[64];
        do {
            row[rowNumber] = true;
            rowNumber++;
        } while(rowNumber % 8 != 0);
        return row;
    }

    public static boolean isValidTileCoordinate(final int coordinate) {
        return coordinate >= 0 && coordinate < Board.NUM_TILES;
    }

    public static int getCoordinateAtPosition(final String position) {
        return POSITION_TO_COORDINATE.get(position);
    }

    public static String getPositionAtCoordinate(final int coordinate) {
        return ALGEBRAIC_NOTATION[coordinate];
    }

    public enum MoveStatus {
        DONE,
        ILLEGAL_NOT_IN_MOVES_LIST,
        ILLEGAL_LEAVES_PLAYER_IN_CHECK,
    }

    public static class Builder {

        Map<Integer, Piece> boardConfig;
        Alliance nextMoveMaker;
        Piece enPassantPawn;

        public Builder() {
            this.boardConfig = new HashMap<>();
        }

        public Builder setPiece(final int location, final Piece piece) {
            this.boardConfig.put(location, piece);
            return this;
        }

        public Builder setMoveMaker(final Alliance nextMoveMaker) {
            this.nextMoveMaker = nextMoveMaker;
            return this;
        }

        public Builder setEnPassantPawn(final Piece enPassantPawn) {
            this.enPassantPawn = enPassantPawn;
            return this;
        }

        public Board build() {
            return new Board(this);
        }

    }

}
