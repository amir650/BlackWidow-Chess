package com.chess.gui;

import static javax.swing.JFrame.setDefaultLookAndFeelDecorated;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import com.chess.com.chess.pgn.Book;
import com.chess.com.chess.pgn.PGNUtilities;
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
    private static ArrayList<Move> moveLog;
    private static Tile sourceTile;
    private static Tile destinationTile;
    private static Piece movedPiece;
    private static Color lightTileColor = Color.decode("#FFFACD");
    private static Color darkTileColor = Color.decode("#593E1A");

    private static final Dimension OUTER_FRAME_DIMENSION = new Dimension(600, 600);
    private static final Dimension BOARD_PANEL_DIMENSION = new Dimension(400, 350);
    private static final Dimension TILE_PANEL_DIMENSION = new Dimension(10, 10);

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
        final ChatPanel chatPanel = new ChatPanel();
        takenPiecesPanel = new TakenPiecesPanel();
        boardPanel = new BoardPanel(chessBoard);
        moveLog = new ArrayList<>();
        gameFrame.add(takenPiecesPanel, BorderLayout.WEST);
        gameFrame.add(boardPanel, BorderLayout.CENTER);
        gameFrame.add(gameHistoryPanel, BorderLayout.EAST);
        gameFrame.add(chatPanel, BorderLayout.SOUTH);
        // Make sure we have nice window decorations.
        setDefaultLookAndFeelDecorated(true);
        gameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        gameFrame.setSize(OUTER_FRAME_DIMENSION);
        gameFrame.setVisible(true);
    }

    private static void populateMenuBar(final JMenuBar tableMenuBar) {
        tableMenuBar.add(createFileMenu());
        tableMenuBar.add(createPreferencesMenu());
        tableMenuBar.add(createOptionsMenu());
    }

    private static JMenu createFileMenu() {
        final JMenu file_menu = new JMenu("File");
        file_menu.setMnemonic(KeyEvent.VK_F);

        final JMenuItem openPGN = new JMenuItem("Open PGN File", KeyEvent.VK_O);
        openPGN.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            int option = chooser.showOpenDialog(gameFrame);
            if (option == JFileChooser.APPROVE_OPTION) {
                openPGNFile(chooser.getSelectedFile());
            }
            else {
                System.out.println("You canceled, bro.");
            }
        });
        file_menu.add(openPGN);

        final JMenuItem exitMenuItem = new JMenuItem("Exit", KeyEvent.VK_X);
        exitMenuItem.addActionListener(e -> {
            gameFrame.dispose();
            System.exit(0);
        });
        file_menu.add(exitMenuItem);

        return file_menu;
    }

    private static JMenu createOptionsMenu() {

        final JMenu options_menu = new JMenu("Options");
        options_menu.setMnemonic(KeyEvent.VK_O);
        final JMenuItem resetMenuItem = new JMenuItem("New ValidGame", KeyEvent.VK_P);
        resetMenuItem.addActionListener(e -> {
            undoAllMoves();
        });
        options_menu.add(resetMenuItem);

        final JMenuItem evaluateBoardMenuItem = new JMenuItem("Evaluate Board", KeyEvent.VK_E);
        evaluateBoardMenuItem.addActionListener(e -> System.out.println(new SimpleBoardEvaluator().evaluate(chessBoard)));
        options_menu.add(evaluateBoardMenuItem);


        final JMenuItem legalMovesMenuItem = new JMenuItem("Current State", KeyEvent.VK_L);
        legalMovesMenuItem.addActionListener(e -> {
            System.out.println(chessBoard.getWhitePieces());
            System.out.println(chessBoard.getBlackPieces());
            System.out.println(chessBoard.currentPlayer().playerInfo());
            System.out.println(chessBoard.currentPlayer().getOpponent().playerInfo());
        });
        options_menu.add(legalMovesMenuItem);

        final JMenuItem makeMoveMenuItem = new JMenuItem("Make a smart move", KeyEvent.VK_M);
        makeMoveMenuItem.addActionListener(e -> {
            final Thread t = new Thread("Think Tank") {
                @Override
                public void run() {
                    chessBoard.currentPlayer().setMoveStrategy(new AlphaBeta());
                    final Move bestMove = chessBoard.currentPlayer().getMoveStrategy().execute(chessBoard, 8);
                    chessBoard = chessBoard.makeMove(bestMove).getTransitionBoard();
                    moveLog.add(bestMove);
                    gameHistoryPanel.redo(moveLog);
                    takenPiecesPanel.redo(moveLog);
                    boardPanel.drawBoard(chessBoard);
                }
            };
            t.start();
        });
        options_menu.add(makeMoveMenuItem);

        final JMenuItem undoMoveMenuItem = new JMenuItem("Undo last move", KeyEvent.VK_M);
        undoMoveMenuItem.addActionListener(e -> {
            if(moveLog.size() > 0) {
                undoLastMove();
            }
        });
        options_menu.add(undoMoveMenuItem);

        final JMenuItem setupGameMenuItem = new JMenuItem("Setup ValidGame", KeyEvent.VK_S);
        setupGameMenuItem.addActionListener(e -> {
            System.err.println("Opening dialog.");
            SetupDialog myDialog = new SetupDialog(gameFrame, true, "Do you like Java?");
            System.err.println("After opening dialog.");
            if (myDialog.getAnswer()) {
                System.err.println("The answer stored in CustomDialog is 'true' (i.e. user clicked yes button.)");
            }
            else {
                System.err.println("The answer stored in CustomDialog is 'false' (i.e. user clicked no button.)");
            }
        });
        options_menu.add(setupGameMenuItem);

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

        chooseDarkMenuItem.addActionListener(e -> {
            final Color colorChoice = JColorChooser.showDialog(gameFrame,
                    "Choose Dark Tile Color", gameFrame.getBackground());
            if (colorChoice != null) {
                boardPanel.setTileDarkColor(chessBoard, colorChoice);
            }
        });

        chooseLightMenuItem.addActionListener(e -> {
            final Color colorChoice = JColorChooser.showDialog(gameFrame,
                    "Choose Light Tile Color", gameFrame.getBackground());
            if (colorChoice != null) {
                boardPanel.setTileLightColor(chessBoard, colorChoice);
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

        woodMenMenuItem.addActionListener(e -> {
            System.out.println("implement me");
            gameFrame.repaint();
        });

        holyWarriorsMenuItem.addActionListener(e -> {
            System.out.println("implement me");
            gameFrame.repaint();
        });

        rockMenMenuItem.addActionListener(e -> {
            System.out.println("implement me");
            gameFrame.repaint();
        });

        abstractMenMenuItem.addActionListener(e -> {
            System.out.println("implement me");
            gameFrame.repaint();
        });

        pref_menu.add(chessMenChoiceSubMenu);

        chooseLegalHighlightMenuItem.addActionListener(e -> {
            System.out.println("implement me");
            gameFrame.repaint();
        });

        final JMenuItem flipBoardMenuItem = new JMenuItem("Flip board");

        flipBoardMenuItem.addActionListener(e -> {
            gameFrame.repaint();
        });

        pref_menu.add(flipBoardMenuItem);
        pref_menu.addSeparator();


        final JCheckBoxMenuItem cbLegalMoveHighlighter = new JCheckBoxMenuItem(
                "Highlight Legal Moves", true);

        cbLegalMoveHighlighter.addActionListener(e -> {
            //highlightLegalMoves = cbLegalMoveHighlighter.isSelected();
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

    private static void openPGNFile(File pgnFile) {
        try {
            Book games = PGNUtilities.parsePGNFile(pgnFile);
            System.out.println("num games consumed = " +games);
            //games.get(games.size() - 1).play(chessBoard);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
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
            for (final TilePanel boardTile : boardTiles) {
                boardTile.drawTile(board);
            }
            validate();
            repaint();
        }

        public void setTileDarkColor(final Board board,
                                     final Color darkColor) {
            for (final TilePanel boardTile : boardTiles) {
                boardTile.setDarkTileColor(darkColor);
            }

            drawBoard(board);
        }

        public void setTileLightColor(final Board board,
                                      final Color lightColor) {
            for (final TilePanel boardTile : boardTiles) {
                boardTile.setLightTileColor(lightColor);
            }

            drawBoard(board);        }
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

