package com.chess.com.chess.pgn;


import com.chess.engine.classic.Alliance;
import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.board.BoardUtils;
import com.chess.engine.classic.board.Tile;
import com.chess.engine.classic.pieces.*;

public class FenUtilities {

    private FenUtilities() {
        throw new RuntimeException("Not Instantiable!");
    }

    public static Board createGameFromFEN(final String fenString) {

        final String[] fenPartitions = fenString.split(" ");

        final Board.Builder gameBuilder = new Board.Builder();

        setupGameBoard(gameBuilder, fenPartitions[0]);

        setupCurrentMoveMaker(gameBuilder, fenPartitions[1]);

        setupCastleCapabilities(gameBuilder, fenPartitions[2]);

        setupEnPassantTargetSquare(gameBuilder, fenPartitions[3]);

        return gameBuilder.build();
    }

    public static String createFENFromGame(final Board board) {

        //r2r1n2/pp2bk2/2p1p2p/3q4/3PN1QP/2P3R1/P4PP1/5RK1 w - - 0 1


        final StringBuilder builder = new StringBuilder();

        for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
            final String tileText = board.getTile(i).toString();
            builder.append(tileText);
        }

        builder.insert(8, "/");
        builder.insert(17, "/");
        builder.insert(26, "/");
        builder.insert(35, "/");
        builder.insert(44, "/");
        builder.insert(53, "/");
        builder.insert(62, "/");

        final String intermediate = builder.toString()
                .replaceAll("--------", "8")
                .replaceAll("-------", "7")
                .replaceAll("------", "6")
                .replaceAll("-----", "5")
                .replaceAll("----", "4")
                .replaceAll("---", "3")
                .replaceAll("--", "2")
                .replaceAll("-", "1");

        return intermediate + board.currentPlayer().toString().substring(0, 0).toLowerCase() + " w KQkq - 0 1";

    }

    private static void setupEnPassantTargetSquare(Board.Builder gameBuilder, String fenPartition) {

    }

    private static void setupCastleCapabilities(Board.Builder gameBuilder, String fenPartition) {

    }

    private static Board.Builder setupCurrentMoveMaker(final Board.Builder gameBuilder,
                                              final String moveMaker) {

        if(moveMaker.equals("w")) {
            gameBuilder.setMoveMaker(Alliance.WHITE);
        } else if(moveMaker.equals("b")) {
            gameBuilder.setMoveMaker(Alliance.BLACK);
        }

        return gameBuilder;

    }

    private static Board.Builder setupGameBoard(final Board.Builder builder, final String gameConfiguration) {

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
        int i = 0;
        while (i < boardTiles.length) {
            switch (boardTiles[i]) {
            case 'r':
                builder.setPiece(new Rook(Alliance.BLACK, i));
                i++;
                break;
            case 'n':
                builder.setPiece(new Knight(Alliance.BLACK, i));
                i++;
                break;
            case 'b':
                builder.setPiece(new Bishop(Alliance.BLACK, i));
                i++;
                break;
            case 'q':
                builder.setPiece(new Queen(Alliance.BLACK, i));
                i++;
                break;
            case 'k':
                builder.setPiece(new King(Alliance.BLACK, i));
                i++;
                break;
            case 'p':
                builder.setPiece(new Pawn(Alliance.BLACK, i));
                i++;
                break;
            case 'R':
                builder.setPiece(new Rook(Alliance.WHITE, i));
                i++;
                break;
            case 'N':
                builder.setPiece(new Knight(Alliance.WHITE, i));
                i++;
                break;
            case 'B':
                builder.setPiece(new Bishop(Alliance.WHITE, i));
                i++;
                break;
            case 'Q':
                builder.setPiece(new Queen(Alliance.WHITE, i));
                i++;
                break;
            case 'K':
                builder.setPiece(new King(Alliance.WHITE, i));
                i++;
                break;
            case 'P':
                builder.setPiece(new Pawn(Alliance.WHITE, i));
                i++;
                break;
            case '-':
                i++;
                break;
            default:
                System.out.println("fuck you ");
                throw new RuntimeException("Invalid FEN String " +gameConfiguration);
            }

        }

        return builder;
    }

}
