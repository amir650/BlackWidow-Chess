package com.chess.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import com.chess.engine.classic.board.Move;
import com.chess.engine.classic.pieces.Piece;

class TakenPiecesPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private final JPanel northPanel;
    private final JPanel southPanel;
    private static final Color PANEL_COLOR = Color.decode("0xFDF5E6");
    private static final Dimension TAKEN_PIECES_PANEL_DIMENSION = new Dimension(40, 80);
    private static final EtchedBorder PANEL_BORDER = new EtchedBorder(EtchedBorder.RAISED);

    public TakenPiecesPanel() {
        super(new BorderLayout());
        setBackground(Color.decode("0xFDF5E6"));
        setBorder(PANEL_BORDER);
        this.northPanel = new JPanel(new GridLayout(8, 2));
        this.southPanel = new JPanel(new GridLayout(8, 2));
        this.northPanel.setBackground(PANEL_COLOR);
        this.southPanel.setBackground(PANEL_COLOR);
        add(this.northPanel, BorderLayout.NORTH);
        add(this.southPanel, BorderLayout.SOUTH);
        setPreferredSize(TAKEN_PIECES_PANEL_DIMENSION);
    }

    public void redo(final List<Move> moves) {
        southPanel.removeAll();
        northPanel.removeAll();
        for(final Move move : moves) {
            if(move.isAttack()) {
                try {
                    final Piece takenPiece = move.getAttackedPiece();
                    final BufferedImage image = ImageIO.read(new File(
                            "art/holywarriors/" + takenPiece.getPieceAllegiance().toString().substring(0, 1) + "" +
                                    takenPiece.toString() + ".gif"));
                    final ImageIcon ic = new ImageIcon(image);
                    final JLabel imageLabel = new JLabel(new ImageIcon(ic.getImage().getScaledInstance(ic.getIconWidth() - 15, ic.getIconWidth() - 15, Image.SCALE_SMOOTH)));
                    if (takenPiece.getPieceAllegiance().isWhite()) {
                        this.southPanel.add(imageLabel);
                    } else if (takenPiece.getPieceAllegiance().isBlack()) {
                        this.northPanel.add(imageLabel);
                    }
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }
        }
        validate();
    }
}