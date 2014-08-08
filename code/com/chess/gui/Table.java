package com.chess.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.prefs.Preferences;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.board.Board.MoveStatus;
import com.chess.engine.classic.board.Move;
import com.chess.engine.classic.board.Move.MoveFactory;
import com.chess.engine.classic.board.MoveTransition;
import com.chess.engine.classic.board.Tile;
import com.chess.engine.classic.pieces.Piece;
import com.chess.engine.classic.player.ai.AlphaBeta;
import com.chess.engine.classic.player.ai.SimpleBoardEvaluator;

public final class Table {

    private static JFrame gameFrame;
    private static Board chessBoard;
    private static GameHistoryPanel gameHistoryPanel;
    private static TakenPiecesPanel takenPiecesPanel;
    private static BoardPanel boardPanel;
    private static ChatPanel chatPanel;
    private static ArrayList<Move> moveLog;
    private static Tile sourceTile;
    private static Tile destinationTile;
    private static Piece movedPiece;
    private static Color lightTileColor = Color.decode("#FFFACD");
    private static Color darkTileColor = Color.decode("#593E1A");

    private static final Dimension OUTER_FRAME_DIMENSION = new Dimension(600, 600);
    private static final Dimension BOARD_PANEL_DIMENSION = new Dimension(400, 350);
    private static final Dimension TILE_PANEL_DIMENSION = new Dimension(10, 10);
    private static final Color defaultLegalMoveHighlightColor = Color.PINK;
    private static Preferences userPreferencesRoot = Preferences.userRoot();

    public Table() {
        init();
    }

    private void init() {
        gameFrame = new JFrame("BlackWidow");
        final JMenuBar tableMenuBar = new JMenuBar();
        populateMenuBar(tableMenuBar);
        gameFrame.setJMenuBar(tableMenuBar);
        gameFrame.setLayout(new BorderLayout());
        chessBoard = Board.createStandardBoard();
        gameHistoryPanel = new GameHistoryPanel();
        chatPanel = new ChatPanel();
        takenPiecesPanel = new TakenPiecesPanel();
        boardPanel = new BoardPanel(chessBoard);
        moveLog = new ArrayList<>();
        gameFrame.add(takenPiecesPanel, BorderLayout.WEST);
        gameFrame.add(boardPanel, BorderLayout.CENTER);
        gameFrame.add(gameHistoryPanel, BorderLayout.EAST);
        gameFrame.add(chatPanel, BorderLayout.SOUTH);
        // Make sure we have nice window decorations.
        gameFrame.setDefaultLookAndFeelDecorated(true);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setSize(OUTER_FRAME_DIMENSION);
        gameFrame.setVisible(true);
    }

    private static void populateMenuBar(final JMenuBar tableMenuBar) {
        final JMenu fileMenu = createFileMenu();
        final JMenu preferencesMenu = createPreferencesMenu();
        final JMenu optionsMenu = createOptionsMenu();
        tableMenuBar.add(fileMenu);
        tableMenuBar.add(preferencesMenu);
        tableMenuBar.add(optionsMenu);
    }

    private static JMenu createFileMenu() {
        final JMenu file_menu = new JMenu("File");
        file_menu.setMnemonic(KeyEvent.VK_F);
        final JMenuItem exitMenuItem = new JMenuItem("Exit", KeyEvent.VK_X);
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                gameFrame.dispose();
                System.exit(0);
            }
        });
        file_menu.add(exitMenuItem);
        return file_menu;
    }

    private static JMenu createOptionsMenu() {

        final JMenu options_menu = new JMenu("Options");
        options_menu.setMnemonic(KeyEvent.VK_O);
        final JMenuItem resetMenuItem = new JMenuItem("New Game", KeyEvent.VK_P);
        resetMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                undoAllMoves();
            }
        });
        options_menu.add(resetMenuItem);

        final JMenuItem evaluateBoardMenuItem = new JMenuItem("Evaluate Board", KeyEvent.VK_E);
        evaluateBoardMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                System.out.println(new SimpleBoardEvaluator().evaluate(chessBoard));
            }
        });
        options_menu.add(evaluateBoardMenuItem);


        final JMenuItem legalMovesMenuItem = new JMenuItem("Current State", KeyEvent.VK_L);
        legalMovesMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                System.out.println(chessBoard.getWhitePieces());
                System.out.println(chessBoard.getBlackPieces());
                System.out.println(chessBoard.currentPlayer().playerInfo());
                System.out.println(chessBoard.currentPlayer().getOpponent().playerInfo());
            }
        });
        options_menu.add(legalMovesMenuItem);

        final JMenuItem makeMoveMenuItem = new JMenuItem("Make a smart move", KeyEvent.VK_M);
        makeMoveMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                final Thread t = new Thread("Think Tank") {
                    @Override
                    public void run() {
                        chessBoard.currentPlayer().setMoveStrategy(new AlphaBeta());
                        final Move bestMove = chessBoard.currentPlayer().getMoveStrategy().execute(chessBoard, 6);
                        chessBoard = chessBoard.makeMove(bestMove).getTransitionBoard();
                        moveLog.add(bestMove);
                        gameHistoryPanel.redo(moveLog);
                        takenPiecesPanel.redo(moveLog);
                        boardPanel.drawBoard(chessBoard);
                    }
                };
                t.start();
            }
        });
        options_menu.add(makeMoveMenuItem);

        final JMenuItem undoMoveMenuItem = new JMenuItem("Undo last move", KeyEvent.VK_M);
        undoMoveMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                if(moveLog.size() > 0) {
                    undoLastMove();
                }
            }
        });
        options_menu.add(undoMoveMenuItem);

        final JMenuItem setupGameMenuItem = new JMenuItem("Setup Game", KeyEvent.VK_S);
        setupGameMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                System.err.println("Opening dialog.");
                SetupDialog myDialog = new SetupDialog(gameFrame, true, "Do you like Java?");
                System.err.println("After opening dialog.");
                if(myDialog.getAnswer()) {
                    System.err.println("The answer stored in CustomDialog is 'true' (i.e. user clicked yes button.)");
                }
                else {
                    System.err.println("The answer stored in CustomDialog is 'false' (i.e. user clicked no button.)");
                }            }
        });
        options_menu.add(setupGameMenuItem);

        final JMenu networkSubMenu = new JMenu("Network");
        networkSubMenu.setMnemonic(KeyEvent.VK_N);
        final JMenuItem hostGameMenuItem = new JMenuItem("Host Game", KeyEvent.VK_H);
        hostGameMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                final String listen_port = JOptionPane.showInputDialog(gameFrame,
                        "Specify listen port (valid port range:1001-9999)");
                final int port;
                if (listen_port != null) {
                    try {
                        port = Integer.parseInt(listen_port);
                    } catch (NumberFormatException nfe) {
                        JOptionPane
                                .showMessageDialog(
                                        gameFrame,
                                        "No port specified, request ignored.  Next time choose \n a valid port (valid port range:1001-9999)",
                                        "Notification",
                                        JOptionPane.ERROR_MESSAGE);
                        return;

                    }
                    if (port < 1001 || port > 9999) {
                        JOptionPane
                                .showMessageDialog(
                                        gameFrame,
                                        "Invalid port"
                                                + port
                                                + " ignored. Valid port range:1001-9999",
                                        "Notification",
                                        JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                }
//                networkActor = new Server(Integer.parseInt(listen_port));
//                networkActor.start();
                hostGameMenuItem.setEnabled(false);
//                connectGameMenuItem.setEnabled(false);
//                disconnectGameMenuItem.setEnabled(true);

            }

        });

        networkSubMenu.add(hostGameMenuItem);
        final JMenuItem connectGameMenuItem = new JMenuItem("Connect to Game", KeyEvent.VK_C);
        connectGameMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                final String host = JOptionPane.showInputDialog(gameFrame,
                        "Please specify the host name");
                final String port = JOptionPane.showInputDialog(gameFrame,
                        "Please specify the port");

//                networkActor = new Client(host, Integer.parseInt(port));
//                networkActor.start();

                hostGameMenuItem.setEnabled(false);
                connectGameMenuItem.setEnabled(false);
                //disconnectGameMenuItem.setEnabled(true);
            }
        });

        networkSubMenu.add(connectGameMenuItem);
        final JMenuItem disconnectGameMenuItem = new JMenuItem("Disconnect", KeyEvent.VK_D);
        disconnectGameMenuItem.setEnabled(false);
        disconnectGameMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
//                networkActor.closeConnection();
//                chatPanel.writeToDisplay(networkActor.getName()
//                        + " disconnected from the network");
//                networkActor = null;
                //connectionEstablished = false;
                hostGameMenuItem.setEnabled(true);
                connectGameMenuItem.setEnabled(true);
                disconnectGameMenuItem.setEnabled(false);
            }
        });

        networkSubMenu.add(disconnectGameMenuItem);
        options_menu.add(networkSubMenu);

        return options_menu;
    }

    private static JMenu createPreferencesMenu() {

        final JMenu pref_menu = new JMenu("Preferences");

        final JMenu colorChooserSubMenu = new JMenu("Choose Colors");
        colorChooserSubMenu.setMnemonic(KeyEvent.VK_S);

        final JMenuItem chooseDarkMenuItem = new JMenuItem("Choose Dark Tile Color");
        chooseDarkMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1,
                ActionEvent.ALT_MASK));
        colorChooserSubMenu.add(chooseDarkMenuItem);

        final JMenuItem chooseLightMenuItem = new JMenuItem("Choose Light Tile Color");
        chooseLightMenuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_2, ActionEvent.ALT_MASK));
        colorChooserSubMenu.add(chooseLightMenuItem);

        final JMenuItem chooseLegalHighlightMenuItem = new JMenuItem(
                "Choose Legal Move Highlight Color");
        chooseLegalHighlightMenuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_3, ActionEvent.ALT_MASK));
        colorChooserSubMenu.add(chooseLegalHighlightMenuItem);

        pref_menu.add(colorChooserSubMenu);

        chooseDarkMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                final Color colorChoice = JColorChooser.showDialog(gameFrame,
                        "Choose Dark Tile Color", gameFrame.getBackground());
                if (colorChoice != null) {
                    //TilePanel.setDarkTileColor(colorChoice);
                    boardPanel.drawBoard(chessBoard);
                }
            }
        });

        chooseLightMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                final Color colorChoice = JColorChooser.showDialog(gameFrame,
                        "Choose Light Tile Color", gameFrame.getBackground());
                if (colorChoice != null) {
                    //TilePanel.setLightTileColor(colorChoice);
                }
            }
        });

        final JMenu chessMenChoiceSubMenu = new JMenu("Choose Chess Men Image Set");

        final JMenuItem holyWarriorsMenuItem = new JMenuItem("Holy Warriors");
        chessMenChoiceSubMenu.add(holyWarriorsMenuItem);

        final JMenuItem rockMenMenuItem = new JMenuItem("Rock Men");
        chessMenChoiceSubMenu.add(rockMenMenuItem);

        final JMenuItem abstractMenMenuItem = new JMenuItem("Abstract Men");
        chessMenChoiceSubMenu.add(abstractMenMenuItem);

        final JMenuItem woodMenMenuItem = new JMenuItem("Wood Men");
        chessMenChoiceSubMenu.add(woodMenMenuItem);

        woodMenMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                System.out.println("implement me");
                gameFrame.repaint();
            }
        });

        holyWarriorsMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                System.out.println("implement me");
                gameFrame.repaint();
            }
        });

        rockMenMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                System.out.println("implement me");
                gameFrame.repaint();
            }
        });

        abstractMenMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                System.out.println("implement me");
                gameFrame.repaint();
            }
        });

        pref_menu.add(chessMenChoiceSubMenu);

        chooseLegalHighlightMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                System.out.println("implement me");
                gameFrame.repaint();
            }
        });

        final JMenuItem flipBoardMenuItem = new JMenuItem("Flip board");

        flipBoardMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                gameFrame.repaint();
            }
        });

        pref_menu.add(flipBoardMenuItem);
        pref_menu.addSeparator();


        final JCheckBoxMenuItem cbLegalMoveHighlighter = new JCheckBoxMenuItem(
                "Highlight Legal Moves", true);

        cbLegalMoveHighlighter.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                //highlightLegalMoves = cbLegalMoveHighlighter.isSelected();
            }
        });

        pref_menu.add(cbLegalMoveHighlighter);

        return pref_menu;

    }

    public static MoveTransition humanMoveRequest(final Board board,
                                                  final Move move,
                                                  final boolean showPopUp) {
        final MoveTransition moveTransition = board.makeMove(move);
        if(moveTransition.getMoveStatus() == MoveStatus.DONE) {
            moveLog.add(move);
            gameHistoryPanel.redo(moveLog);
            takenPiecesPanel.redo(moveLog);
        } else {
            try {
                final BufferedImage image = ImageIO.read(new File("art/misc/illegal.png"));
                if (showPopUp) {
                    JOptionPane.showMessageDialog(boardPanel, "Move " +move.getMovedPiece()+ ": " +move+ " is illegal",
                            "Notification", JOptionPane.WARNING_MESSAGE,
                            new ImageIcon(image));
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        boardPanel.drawBoard(board);
        return moveTransition;
    }

    private static void undoAllMoves() {
        for(int i = moveLog.size() - 1; i >= 0; i--) {
            final Move lastMove = moveLog.remove(moveLog.size() - 1);
            chessBoard = chessBoard.unMakeMove(lastMove).getTransitionBoard();
        }
        moveLog.clear();
        gameHistoryPanel.redo(moveLog);
        takenPiecesPanel.redo(moveLog);
        boardPanel.drawBoard(chessBoard);
    }

    private static void undoLastMove() {
        final Move lastMove = moveLog.remove(moveLog.size() - 1);
        chessBoard = chessBoard.unMakeMove(lastMove).getTransitionBoard();
        moveLog.remove(lastMove);
        gameHistoryPanel.redo(moveLog);
        takenPiecesPanel.redo(moveLog);
        boardPanel.drawBoard(chessBoard);
    }

    class BoardPanel extends JPanel {

        final TilePanel[] boardTiles;

        BoardPanel(Board board) {
            super(new GridLayout(8,8));
            this.boardTiles = new TilePanel[64];
            for (int i = 0; i < boardTiles.length; i++) {
                this.boardTiles[i] = new TilePanel(this, i);
                add(this.boardTiles[i]);
            }
            drawBoard(board);
            setPreferredSize(BOARD_PANEL_DIMENSION);
            setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            setBackground(Color.decode("#8B4726"));
        }

        public void drawBoard(Board board) {
            for (int i = 0; i < boardTiles.length; i++) {
                this.boardTiles[i].drawTile(board);
            }
            validate();
            repaint();
        }

        public void setTileDarkColor(final Board board,
                                     final Color darkColor) {
            drawBoard(board);
        }

        public void setTileLightColor(final Board board,
                                      final Color lightColor) {
            drawBoard(board);
        }
    }

    class TilePanel extends JPanel {

        private final int tileId;

        TilePanel(final BoardPanel boardPanel,
                  final int tileId) {
            this.tileId = tileId;
            setPreferredSize(TILE_PANEL_DIMENSION);
            assignTileColor();
            assignTilePieceIcon(chessBoard);
            addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(final MouseEvent event) {
                    if(SwingUtilities.isRightMouseButton(event)) {
                        sourceTile = null;
                        destinationTile = null;
                        movedPiece = null;
                    } else if(SwingUtilities.isLeftMouseButton(event)) {
                        if (sourceTile == null) {
                            sourceTile = chessBoard.getTile(tileId);
                            movedPiece = sourceTile.getPiece();
                            if(movedPiece == null) {
                                sourceTile = null;
                            }
                        }
                        else {
                            destinationTile = chessBoard.getTile(tileId);
                            final Move move =  MoveFactory.createMove(chessBoard, sourceTile.getTileCoordinate(),
                                    destinationTile.getTileCoordinate());
                            final MoveTransition transition = chessBoard.makeMove(move);
                            if(transition.getMoveStatus() == MoveStatus.DONE) {
                                chessBoard = chessBoard.makeMove(move).getTransitionBoard();
                                moveLog.add(move);
                                gameHistoryPanel.redo(moveLog);
                                takenPiecesPanel.redo(moveLog);
                                boardPanel.drawBoard(chessBoard);
                            }
                            sourceTile = null;
                            destinationTile = null;
                            movedPiece = null;
                        }
                    }
                    boardPanel.drawBoard(chessBoard);
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

        public void drawTile(final Board board) {
            setBorder(BorderFactory.createRaisedSoftBevelBorder());
            assignTileColor();
            assignTilePieceIcon(board);
            highlightMovedPiece(board);
            highlightLegals(board);
            validate();
            repaint();
        }

        public void setLightTileColor(final Color color) {
            lightTileColor = color;
        }

        public void setDarkTileColor(final Color color) {
            darkTileColor = color;
        }

        private void highlightMovedPiece(final Board board) {
            if(movedPiece != null && movedPiece.getPieceAllegiance() == board.currentPlayer().getAlliance() &&
                    movedPiece.getPiecePosition() == this.tileId) {
                setBorder(BorderFactory.createLineBorder(Color.cyan));
            }
        }

        private void highlightLegals(final Board board) {
            for (final Move move : pieceLegalMoves(board)) {
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

        private List<Move> pieceLegalMoves(final Board board) {
            if(movedPiece != null && movedPiece.getPieceAllegiance() == board.currentPlayer().getAlliance()) {
                return movedPiece.calculateLegalMoves(board);
            }
            return Collections.emptyList();
        }

        private void assignTilePieceIcon(final Board board) {
            this.removeAll();
            if(board.getTile(this.tileId).isTileOccupied()) {
                final Piece p = board.getTile(this.tileId).getPiece();
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
            if (Board.FIRST_ROW[this.tileId] ||
                Board.THIRD_ROW[this.tileId] ||
                Board.FIFTH_ROW[this.tileId] ||
                Board.SEVENTH_ROW[this.tileId]) {
                setBackground(this.tileId % 2 == 0 ? lightTileColor : darkTileColor);
            } else if(Board.SECOND_ROW[this.tileId] ||
                      Board.FOURTH_ROW[this.tileId] ||
                      Board.SIXTH_ROW[this.tileId]  ||
                      Board.EIGHTH_ROW[this.tileId]) {
                setBackground(this.tileId % 2 != 0 ? lightTileColor : darkTileColor);
            }
        }
    }
}

