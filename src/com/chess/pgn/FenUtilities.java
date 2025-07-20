package com.chess.pgn;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.pieces.King;
import com.chess.engine.pieces.Pawn;
import com.chess.engine.pieces.PieceUtils;

import static com.chess.engine.board.Board.Builder;

public class FenUtilities {

    private FenUtilities() {
        throw new RuntimeException("Not Instantiable!");
    }

    public static Board createGameFromFEN(final String fenString) {
        return parseFEN(fenString);
    }

    public static Board createStandardBoard() {
        return Board.createStandardBoard();
    }

    public static String createFENFromGame(final Board board) {
        return calculateBoardText(board) + " " +
                calculateCurrentPlayerText(board) + " " +
                calculateCastleText(board) + " " +
                calculateEnPassantSquare(board) + " " +
                "0 1";
    }


    private static Board parseFEN(final String fenString) {
        final String[] fenPartitions = fenString.trim().split(" ");
        final Builder builder = new Builder();
        final boolean whiteKingSideCastle = whiteKingSideCastle(fenPartitions[2]);
        final boolean whiteQueenSideCastle = whiteQueenSideCastle(fenPartitions[2]);
        final boolean blackKingSideCastle = blackKingSideCastle(fenPartitions[2]);
        final boolean blackQueenSideCastle = blackQueenSideCastle(fenPartitions[2]);

        // Parse en passant square
        final String enPassantSquare = fenPartitions[3];
        Pawn enPassantPawn = null;

        final String gameConfiguration = fenPartitions[0];
        final char[] boardTiles = gameConfiguration.replaceAll("/", "")
                .replaceAll("8", "--------")
                .replaceAll("7", "-------")
                .replaceAll("6", "------")
                .replaceAll("5", "-----")
                .replaceAll("4", "----")
                .replaceAll("3", "---")
                .replaceAll("2", "--")
                .replaceAll("1", "-")
                .toCharArray();

        // First pass: place all pieces
        int i = 0;
        while (i < boardTiles.length) {
            switch (boardTiles[i]) {
                case 'r':
                    builder.setPiece(PieceUtils.INSTANCE.getRook(Alliance.BLACK, i, false));
                    break;
                case 'n':
                    builder.setPiece(PieceUtils.INSTANCE.getKnight(Alliance.BLACK, i, false));
                    break;
                case 'b':
                    builder.setPiece(PieceUtils.INSTANCE.getBishop(Alliance.BLACK, i, false));
                    break;
                case 'q':
                    builder.setPiece(PieceUtils.INSTANCE.getQueen(Alliance.BLACK, i, false));
                    break;
                case 'p':
                    builder.setPiece(PieceUtils.INSTANCE.getPawn(Alliance.BLACK, i, false));
                    break;
                case 'k':
                    builder.setPiece(new King(Alliance.BLACK, i, blackKingSideCastle, blackQueenSideCastle));
                    break;
                case 'R':
                    builder.setPiece(PieceUtils.INSTANCE.getRook(Alliance.WHITE, i, false));
                    break;
                case 'N':
                    builder.setPiece(PieceUtils.INSTANCE.getKnight(Alliance.WHITE, i, false));
                    break;
                case 'B':
                    builder.setPiece(PieceUtils.INSTANCE.getBishop(Alliance.WHITE, i, false));
                    break;
                case 'Q':
                    builder.setPiece(PieceUtils.INSTANCE.getQueen(Alliance.WHITE, i, false));
                    break;
                case 'K':
                    builder.setPiece(new King(Alliance.WHITE, i, whiteKingSideCastle, whiteQueenSideCastle));
                    break;
                case 'P':
                    builder.setPiece(PieceUtils.INSTANCE.getPawn(Alliance.WHITE, i, false));
                    break;
                case '-':
                    break; // empty square
                default:
                    throw new RuntimeException("Invalid FEN String " + gameConfiguration);
            }
            i++;
        }

        // Second pass: determine en passant pawn if applicable
        if (!enPassantSquare.equals("-")) {
            final int enPassantCoordinate = BoardUtils.INSTANCE.getCoordinateAtPosition(enPassantSquare);

            // If en passant square is on rank 3 (SIXTH_ROW in your coordinate system),
            // a white pawn just moved from rank 2 to rank 4
            if (BoardUtils.INSTANCE.SIXTH_ROW.get(enPassantCoordinate)) {
                // White pawn just moved two squares forward
                // The pawn is on rank 4, which is one rank "back" toward white from en passant square
                final int pawnPosition = enPassantCoordinate - 8; // Add 8 to go toward rank 1
                enPassantPawn = PieceUtils.INSTANCE.getPawn(Alliance.WHITE, pawnPosition, true);
                builder.setPiece(enPassantPawn); // Replace the pawn with moved version
            }
            // If en passant square is on rank 6 (THIRD_ROW in your coordinate system),
            // a black pawn just moved from rank 7 to rank 5
            else if (BoardUtils.INSTANCE.THIRD_ROW.get(enPassantCoordinate)) {
                // Black pawn just moved two squares forward
                // The pawn is on rank 5, which is one rank "back" toward black from en passant square
                final int pawnPosition = enPassantCoordinate + 8; // Subtract 8 to go toward rank 8
                enPassantPawn = PieceUtils.INSTANCE.getPawn(Alliance.BLACK, pawnPosition, true);
                builder.setPiece(enPassantPawn); // Replace the pawn with moved version
            }

            if (enPassantPawn != null) {
                builder.setEnPassantPawn(enPassantPawn);
            }
        }

        builder.setMoveMaker(moveMaker(fenPartitions[1]));
        return builder.build();
    }

    private static Alliance moveMaker(final String moveMakerString) {
        return moveMakerString.equals("w") ? Alliance.WHITE : Alliance.BLACK;
    }

    private static boolean whiteKingSideCastle(final String fenCastleString) {
        return fenCastleString.contains("K");
    }

    private static boolean whiteQueenSideCastle(final String fenCastleString) {
        return fenCastleString.contains("Q");
    }

    private static boolean blackKingSideCastle(final String fenCastleString) {
        return fenCastleString.contains("k");
    }

    private static boolean blackQueenSideCastle(final String fenCastleString) {
        return fenCastleString.contains("q");
    }

    private static String calculateCastleText(final Board board) {
        final StringBuilder builder = new StringBuilder();
        if (board.whitePlayer().isKingSideCastleCapable()) builder.append("K");
        if (board.whitePlayer().isQueenSideCastleCapable()) builder.append("Q");
        if (board.blackPlayer().isKingSideCastleCapable()) builder.append("k");
        if (board.blackPlayer().isQueenSideCastleCapable()) builder.append("q");
        return builder.isEmpty() ? "-" : builder.toString();
    }

    private static String calculateEnPassantSquare(final Board board) {
        final Pawn enPassantPawn = board.getEnPassantPawn();
        if (enPassantPawn != null) {
            return BoardUtils.INSTANCE.getPositionAtCoordinate(
                    enPassantPawn.getPiecePosition() + (8 * enPassantPawn.getPieceAllegiance().getOppositeDirection()));
        }
        return "-";
    }

    private static String calculateBoardText(final Board board) {
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
            final String tileText = board.getPiece(i) == null ? "-" :
                    board.getPiece(i).getPieceAllegiance().isWhite() ? board.getPiece(i).toString() :
                            board.getPiece(i).toString().toLowerCase();
            builder.append(tileText);
        }
        builder.insert(8, "/");
        builder.insert(17, "/");
        builder.insert(26, "/");
        builder.insert(35, "/");
        builder.insert(44, "/");
        builder.insert(53, "/");
        builder.insert(62, "/");
        return builder.toString()
                .replaceAll("--------", "8")
                .replaceAll("-------", "7")
                .replaceAll("------", "6")
                .replaceAll("-----", "5")
                .replaceAll("----", "4")
                .replaceAll("---", "3")
                .replaceAll("--", "2")
                .replaceAll("-", "1");
    }

    private static String calculateCurrentPlayerText(final Board board) {
        return board.currentPlayer().toString().substring(0, 1).toLowerCase();
    }

}
