package com.chess.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

import com.chess.engine.classic.board.Board;

class BoardPanel extends JPanel {

    final TilePanel[] boardTiles;
    final Board chessBoard;
    private static final Dimension BOARD_PANEL_DIMENSION = new Dimension(400, 350);

    BoardPanel(final Board chessBoard) {
        super(new GridLayout(8,8));
        this.chessBoard = chessBoard;
        this.boardTiles = new TilePanel[64];
        for (int i = 0; i < boardTiles.length; i++) {
            this.boardTiles[i] = new TilePanel(this, chessBoard, i);
            add(this.boardTiles[i]);
        }
        setPreferredSize(BOARD_PANEL_DIMENSION);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(Color.decode("#8B4726"));
    }

    public void drawBoard() {
        for(final TilePanel t : this.boardTiles) {
            t.drawTile();
        }
        validate();
        repaint();
    }

    public void setTileDarkColor(final Color darkColor) {
        drawBoard();
    }

    public void setTileLightColor(final Color lightColor) {
        drawBoard();
    }
}