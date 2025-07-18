package com.chess.engine.board;

import com.chess.engine.Alliance;
import com.chess.engine.board.Move.MoveFactory;
import com.chess.engine.pieces.King;
import com.chess.engine.pieces.Pawn;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.PieceUtils;
import com.chess.engine.player.BlackPlayer;
import com.chess.engine.player.Player;
import com.chess.engine.player.WhitePlayer;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Board {

    private final Piece[] boardPieces;
    private final int[] whitePieceCoordinates;
    private final int[] blackPieceCoordinates;
    private final WhitePlayer whitePlayer;
    private final BlackPlayer blackPlayer;
    private final Player currentPlayer;
    private final Pawn enPassantPawn;
    private final Move transitionMove;

    private static final Board STANDARD_BOARD = createStandardBoardImpl();

    private Board(final Builder builder) {
        this.boardPieces = builder.boardPieces;
        this.whitePieceCoordinates = calculateActiveIndexes(builder.boardPieces, Alliance.WHITE);
        this.blackPieceCoordinates = calculateActiveIndexes(builder.boardPieces, Alliance.BLACK);
        this.enPassantPawn = builder.enPassantPawn;
        final Collection<Move> whiteStandardMoves = calculateLegalMoves(builder.boardPieces, this.whitePieceCoordinates);
        final Collection<Move> blackStandardMoves = calculateLegalMoves(builder.boardPieces, this.blackPieceCoordinates);
        this.whitePlayer = new WhitePlayer(this, establishKing(this.whitePieceCoordinates, this.boardPieces), whiteStandardMoves, blackStandardMoves);
        this.blackPlayer = new BlackPlayer(this, establishKing(this.blackPieceCoordinates, this.boardPieces), whiteStandardMoves, blackStandardMoves);
        this.currentPlayer = builder.nextMoveMaker.choosePlayerByAlliance(this.whitePlayer, this.blackPlayer);
        this.transitionMove = builder.transitionMove != null ? builder.transitionMove : MoveFactory.getNullMove();
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
            final String tileText = prettyPrint(this.boardPieces[i]);
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

    public Piece[] getBoardCopy() {
        return this.boardPieces.clone();
    }

    public int[] getBlackPieceCoordinates() {
        return this.blackPieceCoordinates;
    }

    public int[] getWhitePieceCoordinates() {
        return this.whitePieceCoordinates;
    }

    public Collection<Piece> getAllPieces() {
        final List<Piece> allPieces = new ArrayList<>(this.whitePieceCoordinates.length + this.blackPieceCoordinates.length);
        for (final int index : this.whitePieceCoordinates) {
            allPieces.add(this.boardPieces[index]);
        }
        for (final int index : this.blackPieceCoordinates) {
            allPieces.add(this.boardPieces[index]);
        }
        return Collections.unmodifiableList(allPieces);
    }

    public Collection<Move> getAllLegalMoves() {
        return Stream.concat(this.whitePlayer.getLegalMoves().stream(),
                             this.blackPlayer.getLegalMoves().stream()).collect(Collectors.toList());
    }

    private static King establishKing(final int[] activeIndexes,
                                      final Piece[] boardConfig) {
        for (final int index : activeIndexes) {
            final Piece piece = boardConfig[index];
            if (piece.getPieceType() == Piece.PieceType.KING) {
                return (King) piece;
            }
        }
        throw new RuntimeException("No king found for player!");
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
        return this.boardPieces[coordinate];
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

    private static Board createStandardBoardImpl() {
        final Builder builder = new Builder();
        // Black Layout
        builder.setPiece(PieceUtils.INSTANCE.getRook(Alliance.BLACK, 0, false));
        builder.setPiece(PieceUtils.INSTANCE.getKnight(Alliance.BLACK, 1, false));
        builder.setPiece(PieceUtils.INSTANCE.getBishop(Alliance.BLACK, 2, false));
        builder.setPiece(PieceUtils.INSTANCE.getQueen(Alliance.BLACK, 3, false));
        builder.setPiece(new King(Alliance.BLACK, 4, true, true));
        builder.setPiece(PieceUtils.INSTANCE.getBishop(Alliance.BLACK, 5, false));
        builder.setPiece(PieceUtils.INSTANCE.getKnight(Alliance.BLACK, 6, false));
        builder.setPiece(PieceUtils.INSTANCE.getRook(Alliance.BLACK, 7, false));
        builder.setPiece(PieceUtils.INSTANCE.getPawn(Alliance.BLACK, 8, false));
        builder.setPiece(PieceUtils.INSTANCE.getPawn(Alliance.BLACK, 9, false));
        builder.setPiece(PieceUtils.INSTANCE.getPawn(Alliance.BLACK, 10, false));
        builder.setPiece(PieceUtils.INSTANCE.getPawn(Alliance.BLACK, 11, false));
        builder.setPiece(PieceUtils.INSTANCE.getPawn(Alliance.BLACK, 12, false));
        builder.setPiece(PieceUtils.INSTANCE.getPawn(Alliance.BLACK, 13, false));
        builder.setPiece(PieceUtils.INSTANCE.getPawn(Alliance.BLACK, 14, false));
        builder.setPiece(PieceUtils.INSTANCE.getPawn(Alliance.BLACK, 15, false));

        // White Layout
        builder.setPiece(PieceUtils.INSTANCE.getPawn(Alliance.WHITE, 48, false));
        builder.setPiece(PieceUtils.INSTANCE.getPawn(Alliance.WHITE, 49, false));
        builder.setPiece(PieceUtils.INSTANCE.getPawn(Alliance.WHITE, 50, false));
        builder.setPiece(PieceUtils.INSTANCE.getPawn(Alliance.WHITE, 51, false));
        builder.setPiece(PieceUtils.INSTANCE.getPawn(Alliance.WHITE, 52, false));
        builder.setPiece(PieceUtils.INSTANCE.getPawn(Alliance.WHITE, 53, false));
        builder.setPiece(PieceUtils.INSTANCE.getPawn(Alliance.WHITE, 54, false));
        builder.setPiece(PieceUtils.INSTANCE.getPawn(Alliance.WHITE, 55, false));
        builder.setPiece(PieceUtils.INSTANCE.getRook(Alliance.WHITE, 56, false));
        builder.setPiece(PieceUtils.INSTANCE.getKnight(Alliance.WHITE, 57, false));
        builder.setPiece(PieceUtils.INSTANCE.getBishop(Alliance.WHITE, 58, false));
        builder.setPiece(PieceUtils.INSTANCE.getQueen(Alliance.WHITE, 59, false));
        builder.setPiece(new King(Alliance.WHITE, 60, true, true));
        builder.setPiece(PieceUtils.INSTANCE.getBishop(Alliance.WHITE, 61, false));
        builder.setPiece(PieceUtils.INSTANCE.getKnight(Alliance.WHITE, 62, false));
        builder.setPiece(PieceUtils.INSTANCE.getRook(Alliance.WHITE, 63, false));
        //white to move
        builder.setMoveMaker(Alliance.WHITE);

        return builder.build();
    }

    private Collection<Move> calculateLegalMoves(final Piece[] boardConfig,
                                                 final int[] pieces) {
        final Collection<Move> legalsMoves = new ArrayList<>();
        for (final int piece_index : pieces) {
            legalsMoves.addAll(boardConfig[piece_index].calculateLegalMoves(this));
        }
        return legalsMoves;
    }

    private static int[] calculateActiveIndexes(final Piece[] boardConfig,
                                                final Alliance alliance) {
        final int[] temp = new int[boardConfig.length];
        int count = 0;
        for (int idx = 0; idx < boardConfig.length; idx++) {
            final Piece piece = boardConfig[idx];
            if (piece != null && piece.getPieceAllegiance() == alliance) {
                temp[count++] = idx;
            }
        }
        return Arrays.copyOf(temp, count);
    }

    public static class Builder {

        Piece[] boardPieces;
        Alliance nextMoveMaker;
        Pawn enPassantPawn;
        Move transitionMove;

        public Builder() {
            this.boardPieces = new Piece[BoardUtils.NUM_TILES];
        }

        public Builder setBoardConfiguration(final Piece[] boardConfig) {
            this.boardPieces = boardConfig;
            return this;
        }

        public Builder setPiece(final Piece piece) {
            this.boardPieces[piece.getPiecePosition()] = piece;
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
