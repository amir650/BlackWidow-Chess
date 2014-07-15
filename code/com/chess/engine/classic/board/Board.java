package com.chess.engine.classic.board;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chess.engine.classic.pieces.Pawn;
import com.chess.engine.classic.pieces.Piece;
import com.chess.engine.classic.player.BlackPlayer;
import com.chess.engine.classic.player.Player;
import com.chess.engine.classic.player.WhitePlayer;

public final class Board {

    private final Tile[] gameBoard;
    private final List<Piece> whitePieces;
    private final List<Piece> blackPieces;
    private final WhitePlayer whitePlayer;
    private final BlackPlayer blackPlayer;
    private Player currentPlayer;
    private final int boardOrientation;
    private Pawn enPassantCapture;

    private static final String[] ALGEBRAIC_NOTATION = initializeAlgebraicNotation();
    private static final Map<String, Integer> POSITION_TO_COORDINATE = initializePositionToCoordinateMap();
    public static final int NUM_TILES = 64;
    public static final boolean[] FIRST_COLUMN = initColumn(0);
    public static final boolean[] SECOND_COLUMN = initColumn(1);
    public static final boolean[] SEVENTH_COLUMN = initColumn(6);
    public static final boolean[] EIGHTH_COLUMN = initColumn(7);
    public static final boolean[] FIRST_ROW = initRow(0);
    public static final boolean[] SECOND_ROW = initRow(8);
    public static final boolean[] SEVENTH_ROW = initRow(48);
    public static final boolean[] EIGHTH_ROW = initRow(56);

    static {
        initializePositionToCoordinateMap();
    }

    public Board(final BoardConfigurator configurator) {
        this.gameBoard = new Tile[NUM_TILES];
        this.boardOrientation = 0;
        this.whitePieces = new ArrayList<>();
        this.blackPieces = new ArrayList<>();
        for (int i = 0; i < NUM_TILES; i++) {
            this.gameBoard[i] = new Tile(i);
        }
        configurator.setBoardPieces(this);
        calculateActivePieces();
        this.whitePlayer = new WhitePlayer(this);
        this.blackPlayer = new BlackPlayer(this);
        this.whitePlayer.calculateLegalMoves();
        this.blackPlayer.calculateLegalMoves();
        configurator.setCurrentPlayer(this);
    }

    private Board (final Board board) {
        this.gameBoard = new Tile[NUM_TILES];
        this.boardOrientation = board.boardOrientation;
        this.whitePieces = new ArrayList<>();
        this.blackPieces = new ArrayList<>();
        final Tile[] boardTiles = board.getGameBoard();
        for (int i = 0; i < boardTiles.length; i++) {
            this.gameBoard[i] = new Tile(boardTiles[i]);
        }
        calculateActivePieces();
        this.whitePlayer = new WhitePlayer(this);
        this.blackPlayer = new BlackPlayer(this);
        this.currentPlayer = board.currentPlayer() == board.whitePlayer() ? this.whitePlayer : this.blackPlayer;
        this.whitePlayer.calculateLegalMoves();
        this.blackPlayer.calculateLegalMoves();
    }

    public Board createCopy() {
        return new Board(this);
    }

    public List<Piece> getBlackPieces() {
        return this.blackPieces;
    }

    public List<Piece> getWhitePieces() {
        return this.whitePieces;
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

    public void clearGameBoard() {
        for (final Tile tile : this.gameBoard) {
            if (tile.isTileOccupied()) {
                tile.removePiece();
            }
        }
    }

    public Tile getTile(final int coordinate) {
        return this.gameBoard[coordinate];
    }

    public Tile[] getGameBoard() {
        return this.gameBoard;
    }

    public void setCurrentPlayer(final Player player) {
        this.currentPlayer = player;
    }

    public Pawn getEnPassantCapture() {
        return this.enPassantCapture;
    }

    void calculateActivePieces() {
        for (final Tile t : this.gameBoard) {
            if (t.isTileOccupied()) {
                final Piece p = t.getPiece();
                switch(p.getPieceAllegiance()) {
                case WHITE:
                    this.whitePieces.add(p);
                    break;
                case BLACK:
                    this.blackPieces.add(p);
                    break;
                default:
                    throw new RuntimeException("wtf");
                }
            }
        }
    }

    public void printCurrentBoardState() {
        System.out.println("--------------------------");
        for (int i = 0; i < this.gameBoard.length; i++) {
            System.out.print(this.gameBoard[i]);
            if ((i + 1) % 8 == 0) {
                System.out.print("\n");
            }
        }
        System.out.println("--------------------------");
    }

    public void setPiece(final int coordinate, final Piece piece) {
        this.gameBoard[coordinate].setPiece(piece);
    }

    public void setEnPassantCapture(final Pawn enPassantCapture) {
        this.enPassantCapture = enPassantCapture;
    }

    @Override
    public String toString() {
        final StringBuilder s = new StringBuilder();
        for (int i = 0; i < NUM_TILES; i++) {
            s.append(String.format("%2s", this.gameBoard[i]));
            if ((i + 1) % 8 == 0) {
                s.append("\n");
            }
        }
        return s.toString();
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
        boolean[] column = new boolean[64];
        do {
            column[columnNumber] = true;
            columnNumber += 8;
        } while(columnNumber < 64);
        return column;
    }

    private static boolean [] initRow(int rowNumber) {
        boolean[] row = new boolean[64];
        do {
            row[rowNumber] = true;
            rowNumber++;
        } while(rowNumber % 8 != 0);
        return row;
    }

    public static boolean isValidTileCoordinate(final int coordinate) {
        return coordinate >= 0 && coordinate < Board.NUM_TILES;
    }

    public static int getCoordinateAtPosition(final String pos) {
        return POSITION_TO_COORDINATE.get(pos);
    }

    public static String getPositionAtCoordinate(final int c) {
        return ALGEBRAIC_NOTATION[c];
    }

    public enum MoveStatus {
        DONE,
        ILLEGAL_NOT_IN_MOVES_LIST,
        ILLEGAL_LEAVES_PLAYER_IN_CHECK,
        UNDONE
    }

}
