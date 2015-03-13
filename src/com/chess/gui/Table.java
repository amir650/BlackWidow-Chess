package com.chess.gui;

import static javax.swing.JFrame.setDefaultLookAndFeelDecorated;
import static javax.swing.SwingUtilities.invokeLater;
import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
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
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileFilter;

import com.chess.com.chess.pgn.MySqlGamePersistence;
import com.chess.com.chess.pgn.PGNUtilities;
import com.chess.engine.classic.board.Board;
import com.chess.engine.classic.board.Board.MoveStatus;
import com.chess.engine.classic.board.BoardUtils;
import com.chess.engine.classic.board.Move;
import com.chess.engine.classic.board.Move.MoveFactory;
import com.chess.engine.classic.board.MoveTransition;
import com.chess.engine.classic.board.Tile;
import com.chess.engine.classic.pieces.Piece;
import com.chess.engine.classic.player.ai.AlphaBeta;
import com.chess.engine.classic.player.ai.SimpleBoardEvaluator;
import com.google.common.collect.Lists;

public final class Table extends Observable {

    private final JFrame gameFrame;
    private final GameHistoryPanel gameHistoryPanel;
    private final TakenPiecesPanel takenPiecesPanel;
    private final BoardPanel boardPanel;
    private final MoveLog moveLog;
    private final TableGameAIWatcher gameWatcher;
    private final GameSetup gameSetup;
    private Board chessBoard;
    private Tile sourceTile;
    private Tile destinationTile;
    private Piece movedPiece;
    private BoardDirection boardDirection;
    private String pieceIconPath;
    private boolean highlightLegalMoves;
    private boolean useBook;
    private Color lightTileColor = Color.decode("#FFFACD");
    private Color darkTileColor = Color.decode("#593E1A");

    private static final Dimension OUTER_FRAME_DIMENSION = new Dimension(600, 600);
    private static final Dimension BOARD_PANEL_DIMENSION = new Dimension(400, 350);
    private static final Dimension TILE_PANEL_DIMENSION = new Dimension(10, 10);

    private static final Table INSTANCE = new Table();

    private Table() {
        this.gameFrame = new JFrame("BlackWidow");
        final JMenuBar tableMenuBar = new JMenuBar();
        populateMenuBar(tableMenuBar);
        this.gameFrame.setJMenuBar(tableMenuBar);
        this.gameFrame.setLayout(new BorderLayout());
        this.chessBoard = Board.createStandardBoard();
        this.boardDirection = BoardDirection.NORMAL;
        this.highlightLegalMoves = false;
        this.useBook = true;
        this.pieceIconPath = "art/holywarriors/";
        this.gameHistoryPanel = new GameHistoryPanel();
        final ChatPanel chatPanel = new ChatPanel();
        this.takenPiecesPanel = new TakenPiecesPanel();
        this.boardPanel = new BoardPanel();
        this.moveLog = new MoveLog();
        this.gameWatcher = new TableGameAIWatcher();
        this.addObserver(this.gameWatcher);
        this.gameSetup = new GameSetup(this.gameFrame, true);
        this.gameFrame.add(this.takenPiecesPanel, BorderLayout.WEST);
        this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);
        this.gameFrame.add(this.gameHistoryPanel, BorderLayout.EAST);
        this.gameFrame.add(chatPanel, BorderLayout.SOUTH);
        setDefaultLookAndFeelDecorated(true);
        this.gameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.gameFrame.setSize(OUTER_FRAME_DIMENSION);
        center(this.gameFrame);
        this.gameFrame.setVisible(true);
    }

    public static Table get() {
        return INSTANCE;
    }

    private JFrame getGameFrame() {
        return this.gameFrame;
    }

    private Board getGameBoard() {
        return this.chessBoard;
    }

    private MoveLog getMoveLog() {
        return this.moveLog;
    }

    private BoardPanel getBoardPanel() {
        return this.boardPanel;
    }

    private GameHistoryPanel getGameHistoryPanel() {
        return this.gameHistoryPanel;
    }

    private TakenPiecesPanel getTakenPiecesPanel() {
        return this.takenPiecesPanel;
    }

    private GameSetup getGameSetup() {
        return this.gameSetup;
    }

    private boolean getHighlightLegalMoves() {
        return this.highlightLegalMoves;
    }

    private boolean getUseBook() {
        return this.useBook;
    }

    private void populateMenuBar(final JMenuBar tableMenuBar) {
        tableMenuBar.add(createFileMenu());
        tableMenuBar.add(createPreferencesMenu());
        tableMenuBar.add(createOptionsMenu());
    }

    private static void center(JFrame frame) {
        final Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        final int w = frame.getSize().width;
        final int h = frame.getSize().height;
        final int x = (dim.width - w) / 2;
        final int y = (dim.height - h) / 2;
        frame.setLocation(x, y);
    }

    private JMenu createFileMenu() {
        final JMenu filesMenu = new JMenu("File");
        filesMenu.setMnemonic(KeyEvent.VK_F);

        final JMenuItem openPGN = new JMenuItem("Load PGN File", KeyEvent.VK_O);
        openPGN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                int option = chooser.showOpenDialog(Table.get().getGameFrame());
                if (option == JFileChooser.APPROVE_OPTION) {
                    loadPGNFile(chooser.getSelectedFile());
                }
            }
        });
        filesMenu.add(openPGN);

        final JMenuItem saveToPGN = new JMenuItem("Save Game", KeyEvent.VK_S);
        saveToPGN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final JFileChooser chooser = new JFileChooser();
                chooser.setFileFilter(new FileFilter() {
                    @Override
                    public String getDescription() {
                        return ".pgn";
                    }
                    @Override
                    public boolean accept(final File file) {
                        return file.isDirectory() || file.getName().toLowerCase().endsWith("pgn");
                    }
                });
                final int option = chooser.showSaveDialog(Table.get().getGameFrame());
                if (option == JFileChooser.APPROVE_OPTION) {
                    savePGNFile(chooser.getSelectedFile());
                }
            }
        });
        filesMenu.add(saveToPGN);

        final JMenuItem exitMenuItem = new JMenuItem("Exit", KeyEvent.VK_X);
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                Table.get().getGameFrame().dispose();
                System.exit(0);
            }
        });
        filesMenu.add(exitMenuItem);

        return filesMenu;
    }

    private JMenu createOptionsMenu() {

        final JMenu optionsMenu = new JMenu("Options");
        optionsMenu.setMnemonic(KeyEvent.VK_O);

        final JMenuItem resetMenuItem = new JMenuItem("New Game", KeyEvent.VK_P);
        resetMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                undoAllMoves();
            }

        });
        optionsMenu.add(resetMenuItem);

        final JMenuItem evaluateBoardMenuItem = new JMenuItem("Evaluate Board", KeyEvent.VK_E);
        evaluateBoardMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                System.out.println(new SimpleBoardEvaluator().evaluate(Table.get().getGameBoard(), gameSetup.getSearchDepth()));

            }
        });
        optionsMenu.add(evaluateBoardMenuItem);

        final JMenuItem legalMovesMenuItem = new JMenuItem("Current State", KeyEvent.VK_L);
        legalMovesMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                System.out.println(Table.get().getGameBoard().getWhitePieces());
                System.out.println(Table.get().getGameBoard().getBlackPieces());
                System.out.println(Table.get().getGameBoard().currentPlayer().playerInfo());
                System.out.println(Table.get().getGameBoard().currentPlayer().getOpponent().playerInfo());
            }
        });
        optionsMenu.add(legalMovesMenuItem);

        final JMenuItem undoMoveMenuItem = new JMenuItem("Undo last move", KeyEvent.VK_M);
        undoMoveMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if(Table.get().getMoveLog().size() > 0) {
                    undoLastMove();
                }
            }
        });
        optionsMenu.add(undoMoveMenuItem);

        final JMenuItem setupGameMenuItem = new JMenuItem("Setup Game", KeyEvent.VK_S);
        setupGameMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                Table.get().getGameSetup().promptUser();
                Table.get().setupUpdate(Table.get().getGameSetup());
            }
        });
        optionsMenu.add(setupGameMenuItem);

        return optionsMenu;
    }

    private JMenu createPreferencesMenu() {

        final JMenu preferencesMenu = new JMenu("Preferences");

        final JMenu colorChooserSubMenu = new JMenu("Choose Colors");
        colorChooserSubMenu.setMnemonic(KeyEvent.VK_S);

        final JMenuItem chooseDarkMenuItem = new JMenuItem("Choose Dark Tile Color");
        colorChooserSubMenu.add(chooseDarkMenuItem);

        final JMenuItem chooseLightMenuItem = new JMenuItem("Choose Light Tile Color");
        colorChooserSubMenu.add(chooseLightMenuItem);

        final JMenuItem chooseLegalHighlightMenuItem = new JMenuItem(
                "Choose Legal Move Highlight Color");
        colorChooserSubMenu.add(chooseLegalHighlightMenuItem);

        preferencesMenu.add(colorChooserSubMenu);

        chooseDarkMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final Color colorChoice = JColorChooser.showDialog(Table.get().getGameFrame(), "Choose Dark Tile Color",
                        Table.get().getGameFrame().getBackground());
                if (colorChoice != null) {
                    Table.get().getBoardPanel().setTileDarkColor(Table.get().getGameBoard(), colorChoice);
                }
            }
        });

        chooseLightMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final Color colorChoice = JColorChooser.showDialog(Table.get().getGameFrame(), "Choose Light Tile Color",
                        Table.get().getGameFrame().getBackground());
                if (colorChoice != null) {
                    Table.get().getBoardPanel().setTileLightColor(Table.get().getGameBoard(), colorChoice);
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
                Table.get().getGameFrame().repaint();
            }
        });

        holyWarriorsMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                pieceIconPath = "art/holywarriors/";
                Table.get().getBoardPanel().drawBoard(Table.get().getGameBoard());
            }
        });

        rockMenMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent e) {

            }
        });

        abstractMenMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                pieceIconPath = "art/simple/";
                Table.get().getBoardPanel().drawBoard(Table.get().getGameBoard());
            }
        });

        preferencesMenu.add(chessMenChoiceSubMenu);

        chooseLegalHighlightMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                System.out.println("implement me");
                Table.get().getGameFrame().repaint();
            }
        });

        final JMenuItem flipBoardMenuItem = new JMenuItem("Flip board");

        flipBoardMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                boardDirection = boardDirection == BoardDirection.NORMAL ? BoardDirection.FLIPPED : BoardDirection.NORMAL;
                boardPanel.drawBoard(chessBoard);
            }
        });

        preferencesMenu.add(flipBoardMenuItem);
        preferencesMenu.addSeparator();


        final JCheckBoxMenuItem cbLegalMoveHighlighter = new JCheckBoxMenuItem(
                "Highlight Legal Moves", false);

        cbLegalMoveHighlighter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                highlightLegalMoves = cbLegalMoveHighlighter.isSelected();
            }
        });

        preferencesMenu.add(cbLegalMoveHighlighter);

        final JCheckBoxMenuItem cbUseBookMoves = new JCheckBoxMenuItem(
                "Use Book Moves", true);

        cbUseBookMoves.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                useBook = cbUseBookMoves.isSelected();
            }
        });

        preferencesMenu.add(cbUseBookMoves);

        return preferencesMenu;

    }

    private void undoAllMoves() {
        for(int i = Table.get().getMoveLog().size() - 1; i >= 0; i--) {
            final Move lastMove = Table.get().getMoveLog().removeMove(Table.get().getMoveLog().size() - 1);
            this.chessBoard = this.chessBoard.unMakeMove(lastMove).getTransitionBoard();
        }
        Table.get().getMoveLog().clear();
        Table.get().getGameHistoryPanel().redo(Table.get().getMoveLog());
        Table.get().getTakenPiecesPanel().redo(Table.get().getMoveLog());
        Table.get().getBoardPanel().drawBoard(Table.get().getGameBoard());
    }

    private static void loadPGNFile(final File pgnFile) {
        try {
            PGNUtilities.persistPGNFile(pgnFile);
        }
        catch (final IOException e) {
            e.printStackTrace();
        }
    }

    private static void savePGNFile(final File pgnFile) {
        try {
            PGNUtilities.writeGameToPGNFile(pgnFile, Table.get().getMoveLog());
        }
        catch (final IOException e) {
            e.printStackTrace();
        }
    }

    private void undoLastMove() {
        final Move lastMove = Table.get().getMoveLog().removeMove(Table.get().getMoveLog().size() - 1);
        this.chessBoard = this.chessBoard.unMakeMove(lastMove).getTransitionBoard();
        Table.get().getMoveLog().removeMove(lastMove);
        Table.get().getGameHistoryPanel().redo(Table.get().getMoveLog());
        Table.get().getTakenPiecesPanel().redo(Table.get().getMoveLog());
        Table.get().getBoardPanel().drawBoard(Table.get().getGameBoard());
    }

    public void moveMadeUpdate(final PlayerType playerType) {
        setChanged();
        notifyObservers(playerType);
    }

    public void setupUpdate(final GameSetup gameSetup) {
        setChanged();
        notifyObservers(gameSetup);
    }

    class TableGameAIWatcher
            implements Observer {

        final ExecutorService pool = Executors.newFixedThreadPool(1);

        public void update(final Observable o, final Object arg) {
            if (gameSetup.isAIPlayer(chessBoard.currentPlayer())) {
                System.out.println(chessBoard.currentPlayer() + " is set to AI, thinking....");
                final Callable<Move> moveSearch = new Callable<Move>() {
                    @Override
                    public Move call() throws Exception {
                        return Table.get()
                                .getGameBoard()
                                .currentPlayer()
                                .getMoveStrategy()
                                .execute(Table.get().getGameBoard(), gameSetup.getSearchDepth());
                    }
                };

                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        try {
                            Move bestMove;
                            final Move bookMove = MySqlGamePersistence.get()
                                    .getNextBestMove(Table.get().getGameBoard(),
                                            Table.get().getGameBoard().currentPlayer(),
                                            moveLog.getMoves().toString().replaceAll("\\[", "").replaceAll("\\]", ""));
                            if (Table.get().getUseBook() && bookMove != Move.NULL_MOVE) {
                                bestMove = bookMove;
                            } else {
                                Table.get().getGameBoard().currentPlayer().setMoveStrategy(new AlphaBeta());
                                bestMove = pool.submit(moveSearch).get();
                            }
                            chessBoard = chessBoard.makeMove(bestMove).getTransitionBoard();
                            Table.get().getMoveLog().addMove(bestMove);
                            Table.get().getGameHistoryPanel().redo(Table.get().getMoveLog());
                            Table.get().getTakenPiecesPanel().redo(Table.get().getMoveLog());
                            Table.get().getBoardPanel().drawBoard(Table.get().getGameBoard());
                            Table.get().moveMadeUpdate(PlayerType.COMPUTER);
                        }
                        catch (final Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }
    }

    public static enum PlayerType {
        HUMAN,
        COMPUTER
    }

    class BoardPanel extends JPanel {

        final List<TilePanel> boardTiles;

        BoardPanel() {
            super(new GridLayout(8,8));
            this.boardTiles = new ArrayList<>();
            for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
                final TilePanel tilePanel = new TilePanel(this, i);
                this.boardTiles.add(tilePanel);
                add(tilePanel);
            }
            setPreferredSize(BOARD_PANEL_DIMENSION);
            setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            setBackground(Color.decode("#8B4726"));
        }

        public void drawBoard(final Board board) {
            removeAll();
            for (final TilePanel boardTile : boardDirection.traverse(boardTiles)) {
                boardTile.drawTile(board);
                add(boardTile);
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
            drawBoard(board);
        }

    }

    public enum BoardDirection {
        NORMAL {
            @Override
            List<TilePanel> traverse(final List<TilePanel> boardTiles) {
                return boardTiles;
            }
        },
        FLIPPED {
            @Override
            List<TilePanel> traverse(final List<TilePanel> boardTiles) {
                return Lists.reverse(boardTiles);
            }
        };

        abstract List<TilePanel> traverse(final List<TilePanel> boardTiles);

    }

    public static class MoveLog {

        private final ArrayList<Move> moves;

        MoveLog() {
            this.moves = new ArrayList<>();
        }

        public List<Move> getMoves() {
            return this.moves;
        }

        public void addMove(final Move move) {
            this.moves.add(move);
        }

        public int size() {
            return this.moves.size();
        }

        public void clear() {
            this.moves.clear();
        }

        public Move removeMove(int index) {
            return this.moves.remove(index);
        }

        public boolean removeMove(final Move move) {
            return this.moves.remove(move);
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
            highlightTileBorder(chessBoard);
            addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(final MouseEvent event) {
                    if(isRightMouseButton(event)) {
                        sourceTile = null;
                        destinationTile = null;
                        movedPiece = null;
                    } else if(isLeftMouseButton(event)) {
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
                                moveLog.addMove(move);
                            }
                            sourceTile = null;
                            destinationTile = null;
                            movedPiece = null;
                        }
                    }
                    invokeLater(new Runnable() {
                        public void run() {
                            gameHistoryPanel.redo(moveLog);
                            takenPiecesPanel.redo(moveLog);
                            if(gameSetup.isAIPlayer(chessBoard.currentPlayer())) {
                                Table.get().moveMadeUpdate(PlayerType.HUMAN);
                            }
                            boardPanel.drawBoard(chessBoard);
                        }
                    });
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
            validate();
        }

        public void drawTile(final Board board) {
            assignTileColor();
            assignTilePieceIcon(board);
            highlightTileBorder(board);
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

        private void highlightTileBorder(final Board board) {
            if(movedPiece != null && movedPiece.getPieceAllegiance() == board.currentPlayer().getAlliance() &&
                    movedPiece.getPiecePosition() == this.tileId) {
                setBorder(BorderFactory.createLineBorder(Color.cyan));
            } else {
                setBorder(BorderFactory.createLineBorder(Color.GRAY));
            }
        }

        private void highlightLegals(final Board board) {
            if (Table.get().getHighlightLegalMoves()) {
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
                try{
                    final BufferedImage image = ImageIO.read(new File(pieceIconPath +
                            board.getTile(this.tileId).getPiece().getPieceAllegiance().toString().substring(0, 1) + "" +
                            board.getTile(this.tileId).getPiece().toString() +
                            ".gif"));
                    add(new JLabel(new ImageIcon(image)));
                } catch(final IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void assignTileColor() {
            if (BoardUtils.FIRST_ROW[this.tileId] ||
                    BoardUtils.THIRD_ROW[this.tileId] ||
                    BoardUtils.FIFTH_ROW[this.tileId] ||
                    BoardUtils.SEVENTH_ROW[this.tileId]) {
                setBackground(this.tileId % 2 == 0 ? lightTileColor : darkTileColor);
            } else if(BoardUtils.SECOND_ROW[this.tileId] ||
                    BoardUtils.FOURTH_ROW[this.tileId] ||
                    BoardUtils.SIXTH_ROW[this.tileId]  ||
                    BoardUtils.EIGHTH_ROW[this.tileId]) {
                setBackground(this.tileId % 2 != 0 ? lightTileColor : darkTileColor);
            }
        }
    }
}

