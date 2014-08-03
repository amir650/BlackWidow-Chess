package com.chess.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.board.Move;
import com.chess.engine.classic.board.Tile;
import com.chess.engine.classic.pieces.Piece;

class TilePanel extends JPanel {

    private final Board chessBoard;
    private final int tileId;
    private static Tile sourceTile;
    private static Tile destinationTile;
    private static Piece movedPiece;
    private static Color lightTileColor = Color.decode("#FFFACD");
    private static Color darkTileColor = Color.decode("#593E1A");
    private static final Dimension TILE_PANEL_DIMENSION = new Dimension(10, 10);


    TilePanel(final BoardPanel boardPanel,
              final Board chessBoard,
              final int tileId) {
        this.chessBoard = chessBoard;
        this.tileId = tileId;
        setPreferredSize(TILE_PANEL_DIMENSION);
        assignTileColor();
        assignTilePieceIcon();
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(final MouseEvent event) {
//                if(sourceTile == null) {
//                    sourceTile = chessBoard.getTile(tileId);
//                    movedPiece = sourceTile.getPiece();
//                } else {
//                    destinationTile = chessBoard.getTile(tileId);
//                    Table.requestMove(chessBoard, MoveFactory.createMove(chessBoard,
//                            sourceTile.getTileCoordinate(), destinationTile.getTileCoordinate()), true);
//                    sourceTile = null;
//                    destinationTile = null;
//                    movedPiece = null;
//                }
//                boardPanel.drawBoard();
                System.out.println("bleep tile id = " +tileId);
            }

            @Override
            public void mouseExited(final MouseEvent e) {
            }

            @Override
            public void mouseEntered(final MouseEvent e) {
            }

            @Override
            public void mouseReleased(final MouseEvent e) {
            }

            @Override
            public void mousePressed(final MouseEvent e) {
            }
        });
        setBorder(BorderFactory.createRaisedBevelBorder());
        validate();
    }

    public void drawTile() {
        removeAll();
        setBorder(BorderFactory.createRaisedSoftBevelBorder());
        assignTileColor();
        assignTilePieceIcon();
        highlightMovedPiece();
        highlightLegals();
        validate();
        repaint();
    }

    public static void setLightTileColor(final Color color) {
        lightTileColor = color;
    }

    public static void setDarkTileColor(final Color color) {
        darkTileColor = color;
    }

    private void highlightMovedPiece() {
        if(movedPiece != null && movedPiece.getPieceAllegiance() == chessBoard.currentPlayer().getAlliance() &&
                movedPiece.getPiecePosition() == this.tileId) {
            setBorder(BorderFactory.createLineBorder(Color.cyan));
        }
    }

    private void highlightLegals() {
        for (final Move move : pieceLegalMoves()) {
            if (move.getDestinationCoordinate() == this.tileId) {
                try {
                    add(new JLabel(new ImageIcon(ImageIO.read(new File("art/misc/green_dot.png")))));
                }
                catch (final IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private List<Move> pieceLegalMoves() {
        if(movedPiece != null && movedPiece.getPieceAllegiance() == chessBoard.currentPlayer().getAlliance()) {
            return movedPiece.calculateLegalMoves(this.chessBoard);
        }
        return Collections.emptyList();
    }

    private void assignTilePieceIcon() {
        this.removeAll();
        if(chessBoard.getTile(this.tileId).isTileOccupied()) {
            final Piece p = chessBoard.getTile(this.tileId).getPiece();
            try{
                final BufferedImage image = ImageIO.read(new File(
                        "art/holywarriors/" + p.getPieceAllegiance().toString().substring(0, 1) + "" + p.toString() +
                                ".gif"));
                JLabel imageLabel = new JLabel(new ImageIcon(image));
                add(imageLabel);
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void assignTileColor() {
        if (this.tileId >= 0 && this.tileId < 8 ||
                this.tileId >= 16 && this.tileId < 24 ||
                this.tileId >= 32 && this.tileId < 40 ||
                this.tileId >= 48 && this.tileId < 56) {
            if (this.tileId % 2 == 0) {
                setBackground(lightTileColor);
            } else {
                setBackground(darkTileColor);
            }
        } else if(this.tileId >= 8 && this.tileId < 16 ||
                this.tileId >= 24 && this.tileId < 32 ||
                this.tileId >= 40 && this.tileId < 48 ||
                this.tileId >= 56 && this.tileId < 64) {
            if (!(this.tileId % 2 == 0)) {
                setBackground(lightTileColor);
            } else {
                setBackground(darkTileColor);
            }
        }
    }

}