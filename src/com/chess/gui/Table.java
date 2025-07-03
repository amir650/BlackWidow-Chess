package com.chess.gui;

import com.chess.engine.board.*;
import com.chess.engine.board.Move.MoveFactory;
import com.chess.engine.pieces.Piece;
import com.chess.engine.player.Player;
import com.chess.engine.player.ai.AIProgressListener;
import com.chess.engine.player.ai.StandardBoardEvaluator;
import com.chess.engine.player.ai.StockAlphaBeta;
import com.chess.pgn.FenUtilities;
import com.chess.pgn.MySqlGamePersistence;
import com.chess.pgn.PGNUtilities;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

import static javax.swing.JFrame.setDefaultLookAndFeelDecorated;
import static javax.swing.SwingUtilities.*;

public final class Table {

    private final JFrame gameFrame;
    private final GameHistoryPanel gameHistoryPanel;
    private final TakenPiecesPanel takenPiecesPanel;
    private final DebugPanel debugPanel;
    private final BoardPanel boardPanel;
    private final MoveLog moveLog;
    private final GameSetup gameSetup;
    private final GameEventManager eventManager;
    private Board chessBoard;
    private Move computerMove;
    private Piece sourceTile;
    private Piece humanMovedPiece;
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
        this.eventManager = new GameEventManager();
        this.gameFrame = new JFrame("BlackWidow");
        final JMenuBar tableMenuBar = new JMenuBar();
        populateMenuBar(tableMenuBar);
        this.gameFrame.setJMenuBar(tableMenuBar);
        this.gameFrame.setLayout(new BorderLayout());
        this.chessBoard = Board.createStandardBoard();
        this.boardDirection = BoardDirection.NORMAL;
        this.highlightLegalMoves = false;
        this.useBook = false;
        this.pieceIconPath = "art/holywarriors/";
        this.gameHistoryPanel = new GameHistoryPanel();
        this.debugPanel = new DebugPanel();
        this.takenPiecesPanel = new TakenPiecesPanel();
        this.boardPanel = new BoardPanel();
        this.moveLog = new MoveLog();
        this.eventManager.addGameEventListener(this::handleGameEvent);
        this.eventManager.addAIProgressListener(this.debugPanel::updateProgress);
        this.gameSetup = new GameSetup(this.gameFrame, true);
        this.gameFrame.add(this.takenPiecesPanel, BorderLayout.WEST);
        this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);
        this.gameFrame.add(this.gameHistoryPanel, BorderLayout.EAST);
        this.gameFrame.add(debugPanel, BorderLayout.SOUTH);
        setDefaultLookAndFeelDecorated(true);
        this.gameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.gameFrame.setSize(OUTER_FRAME_DIMENSION);
        center(this.gameFrame);
        this.gameFrame.setVisible(true);
        this.gameFrame.setResizable(false);
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

    private DebugPanel getDebugPanel() {
        return this.debugPanel;
    }

    public GameSetup getGameSetup() {
        return this.gameSetup;
    }

    public boolean getHighlightLegalMoves() {
        return this.highlightLegalMoves;
    }

    private boolean getUseBook() {
        return this.useBook;
    }

    public void show() {
        Table.get().getMoveLog().clear();
        Table.get().getGameHistoryPanel().redo(this.chessBoard, Table.get().getMoveLog());
        Table.get().getTakenPiecesPanel().redo(Table.get().getMoveLog());
        Table.get().getBoardPanel().drawBoard(Table.get().getGameBoard());
        Table.get().getDebugPanel().redo();
    }

    // Event handling method
    private void handleGameEvent(GameEvent event) {
        event.processEvent(this, event);
    }

    void handleMoveMade() {
        if (getGameSetup().isAIPlayer(getGameBoard().currentPlayer()) &&
                !getGameBoard().currentPlayer().isInCheckMate() &&
                !getGameBoard().currentPlayer().isInStaleMate()) {
            System.out.println(getGameBoard().currentPlayer() + " is set to AI, thinking....");
            this.eventManager.publishGameEvent(new GameEvent(GameEvent.Type.AI_THINKING, null));
            AIThinkTank thinkTank = new AIThinkTank(new MySqlGamePersistence(), this.eventManager);
            thinkTank.execute();
        }

        if (getGameBoard().currentPlayer().isInCheckMate()) {
            String message = "Player " + getGameBoard().currentPlayer() + " is in checkmate!";
            JOptionPane.showMessageDialog(getBoardPanel(), "Game Over: " + message, "Game Over",
                    JOptionPane.INFORMATION_MESSAGE);
            this.eventManager.publishGameEvent(new GameEvent(GameEvent.Type.GAME_OVER, message));
        }

        if (getGameBoard().currentPlayer().isInStaleMate()) {
            String message = "Player " + getGameBoard().currentPlayer() + " is in stalemate!";
            JOptionPane.showMessageDialog(getBoardPanel(), "Game Over: " + message, "Game Over",
                    JOptionPane.INFORMATION_MESSAGE);
            this.eventManager.publishGameEvent(new GameEvent(GameEvent.Type.GAME_OVER, message));
        }
    }

    void handleGameSetupChanged(GameSetup gameSetup) {
        System.out.println("Game setup changed: " + gameSetup);
        // Check if current player is now AI and should start thinking
        if (gameSetup.isAIPlayer(getGameBoard().currentPlayer()) &&
                !getGameBoard().currentPlayer().isInCheckMate() &&
                !getGameBoard().currentPlayer().isInStaleMate()) {
            System.out.println(getGameBoard().currentPlayer() + " is set to AI, thinking....");
            this.eventManager.publishGameEvent(new GameEvent(GameEvent.Type.AI_THINKING, null));
            AIThinkTank thinkTank = new AIThinkTank(new MySqlGamePersistence(), this.eventManager);
            thinkTank.execute();
        }
    }

    private void populateMenuBar(final JMenuBar tableMenuBar) {
        tableMenuBar.add(createFileMenu());
        tableMenuBar.add(createPreferencesMenu());
        tableMenuBar.add(createOptionsMenu());
    }

    private static void center(final JFrame frame) {
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
        openPGN.addActionListener(_ -> {
            JFileChooser chooser = new JFileChooser();
            int option = chooser.showOpenDialog(Table.get().getGameFrame());
            if (option == JFileChooser.APPROVE_OPTION) {
                loadPGNFile(chooser.getSelectedFile());
            }
        });
        filesMenu.add(openPGN);

        final JMenuItem openFEN = new JMenuItem("Load FEN File", KeyEvent.VK_F);
        openFEN.addActionListener(_ -> {
            String fenString = JOptionPane.showInputDialog("Input FEN");
            if(fenString != null) {
                undoAllMoves();
                this.chessBoard = FenUtilities.createGameFromFEN(fenString);
                Table.get().getBoardPanel().drawBoard(this.chessBoard);
            }
        });
        filesMenu.add(openFEN);

        final JMenuItem saveToPGN = new JMenuItem("Save Game", KeyEvent.VK_S);
        saveToPGN.addActionListener(_ -> {
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
        });
        filesMenu.add(saveToPGN);

        final JMenuItem exitMenuItem = new JMenuItem("Exit", KeyEvent.VK_X);
        exitMenuItem.addActionListener(_ -> {
            Table.get().getGameFrame().dispose();
            System.exit(0);
        });
        filesMenu.add(exitMenuItem);

        return filesMenu;
    }

    private JMenu createOptionsMenu() {

        final JMenu optionsMenu = new JMenu("Options");
        optionsMenu.setMnemonic(KeyEvent.VK_O);

        final JMenuItem resetMenuItem = new JMenuItem("New Game", KeyEvent.VK_P);
        resetMenuItem.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                undoAllMoves();
                Table.get().getGameFrame().repaint();
            }
        });
        optionsMenu.add(resetMenuItem);

        final JMenuItem evaluateBoardMenuItem = new JMenuItem("Evaluate Board", KeyEvent.VK_E);
        evaluateBoardMenuItem.addActionListener(_ -> {
            System.out.println(StandardBoardEvaluator.get().evaluationDetails(this.chessBoard, this.gameSetup.getSearchDepth()));
            System.out.println(FenUtilities.createFENFromGame(this.chessBoard));
        });
        optionsMenu.add(evaluateBoardMenuItem);

        final JMenuItem escapeAnalysis = new JMenuItem("Escape Analysis Score", KeyEvent.VK_S);
        escapeAnalysis.addActionListener(_ -> {
            final Move lastMove = this.moveLog.getMoves().get(this.moveLog.size() - 1);
            if(lastMove != null) {
                System.out.println(MoveUtils.exchangeScore(lastMove));
            }

        });
        optionsMenu.add(escapeAnalysis);

        final JMenuItem legalMovesMenuItem = new JMenuItem("Current State", KeyEvent.VK_L);
        legalMovesMenuItem.addActionListener(_ -> {
            System.out.println(FenUtilities.createFENFromGame(this.chessBoard));
            System.out.println(Arrays.toString(this.chessBoard.getWhitePieceCoordinates()));
            System.out.println(Arrays.toString(this.chessBoard.getBlackPieceCoordinates()));
            System.out.println(playerInfo(this.chessBoard.currentPlayer()));
            System.out.println(playerInfo(this.chessBoard.currentPlayer().getOpponent()));
        });
        optionsMenu.add(legalMovesMenuItem);

        final JMenuItem undoMoveMenuItem = new JMenuItem("Undo last move", KeyEvent.VK_M);
        undoMoveMenuItem.addActionListener(_ -> {
            if(Table.get().getMoveLog().size() > 0) {
                undoLastMove();
            }
        });
        optionsMenu.add(undoMoveMenuItem);

        final JMenuItem setupGameMenuItem = new JMenuItem("Setup Game", KeyEvent.VK_S);
        setupGameMenuItem.addActionListener(_ -> {
            Table.get().getGameSetup().promptUser();
            // REMOVED: Table.get().setupUpdate(Table.get().getGameSetup());
            // setupUpdate() is now only called from GameSetup OK button
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

        chooseDarkMenuItem.addActionListener(_ -> {
            final Color colorChoice = JColorChooser.showDialog(Table.get().getGameFrame(), "Choose Dark Tile Color",
                    Table.get().getGameFrame().getBackground());
            if (colorChoice != null) {
                Table.get().getBoardPanel().setTileDarkColor(this.chessBoard, colorChoice);
            }
        });

        chooseLightMenuItem.addActionListener(_ -> {
            final Color colorChoice = JColorChooser.showDialog(Table.get().getGameFrame(), "Choose Light Tile Color",
                    Table.get().getGameFrame().getBackground());
            if (colorChoice != null) {
                Table.get().getBoardPanel().setTileLightColor(this.chessBoard, colorChoice);
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

        final JMenuItem fancyMenMenuItem = new JMenuItem("Fancy Men");
        chessMenChoiceSubMenu.add(fancyMenMenuItem);

        final JMenuItem fancyMenMenuItem2 = new JMenuItem("Fancy Men 2");
        chessMenChoiceSubMenu.add(fancyMenMenuItem2);

        woodMenMenuItem.addActionListener(_ -> {
            System.out.println("implement me");
            Table.get().getGameFrame().repaint();
        });

        holyWarriorsMenuItem.addActionListener(_ -> {
            this.pieceIconPath = "art/holywarriors/";
            Table.get().getBoardPanel().drawBoard(this.chessBoard);
        });

        rockMenMenuItem.addActionListener(_ -> {
        });

        abstractMenMenuItem.addActionListener(_ -> {
            this.pieceIconPath = "art/simple/";
            Table.get().getBoardPanel().drawBoard(this.chessBoard);
        });

        fancyMenMenuItem2.addActionListener(_ -> {
            this.pieceIconPath = "art/fancy2/";
            Table.get().getBoardPanel().drawBoard(this.chessBoard);
        });

        fancyMenMenuItem.addActionListener(_ -> {
            this.pieceIconPath = "art/fancy/";
            Table.get().getBoardPanel().drawBoard(this.chessBoard);
        });

        preferencesMenu.add(chessMenChoiceSubMenu);

        chooseLegalHighlightMenuItem.addActionListener(_ -> {
            System.out.println("implement me");
            Table.get().getGameFrame().repaint();
        });

        final JMenuItem flipBoardMenuItem = new JMenuItem("Flip board");

        flipBoardMenuItem.addActionListener(_ -> {
            this.boardDirection = this.boardDirection.opposite();
            this.boardPanel.drawBoard(this.chessBoard);
        });

        preferencesMenu.add(flipBoardMenuItem);
        preferencesMenu.addSeparator();


        final JCheckBoxMenuItem cbLegalMoveHighlighter = new JCheckBoxMenuItem(
                "Highlight Legal Moves", false);

        cbLegalMoveHighlighter.addActionListener(_ -> highlightLegalMoves = cbLegalMoveHighlighter.isSelected());

        preferencesMenu.add(cbLegalMoveHighlighter);

        final JCheckBoxMenuItem cbUseBookMoves = new JCheckBoxMenuItem(
                "Use Book Moves", false);

        cbUseBookMoves.addActionListener(_ -> useBook = cbUseBookMoves.isSelected());

        preferencesMenu.add(cbUseBookMoves);

        return preferencesMenu;

    }

    private static String playerInfo(final Player player) {
        return ("Player is: " +player.getAlliance() + "\nlegal moves (" +player.getLegalMoves().size()+ ") = " +player.getLegalMoves() + "\ninCheck = " +
                player.isInCheck() + "\nisInCheckMate = " +player.isInCheckMate() +
                "\nisCastled = " +player.isCastled())+ "\n";
    }

    private void updateGameBoard(final Board board) {
        this.chessBoard = board;
    }

    private void updateComputerMove(final Move move) {
        this.computerMove = move;
    }

    private void undoAllMoves() {
        for(int i = Table.get().getMoveLog().size() - 1; i >= 0; i--) {
            final Move lastMove = Table.get().getMoveLog().removeMove(Table.get().getMoveLog().size() - 1);
            this.chessBoard = this.chessBoard.currentPlayer().unMakeMove(lastMove).getToBoard();
        }
        this.computerMove = null;
        Table.get().getMoveLog().clear();
        Table.get().getGameHistoryPanel().redo(chessBoard, Table.get().getMoveLog());
        Table.get().getTakenPiecesPanel().redo(Table.get().getMoveLog());
        Table.get().getBoardPanel().drawBoard(chessBoard);
        Table.get().getDebugPanel().redo();
    }

    private static void loadPGNFile(final File pgnFile) {
        try {
            MySqlGamePersistence persistence = new MySqlGamePersistence();
            PGNUtilities.persistPGNFile(pgnFile, persistence);
        } catch (final Exception e) {
            JOptionPane.showMessageDialog(
                    Table.get().getGameFrame(),
                    "Error importing PGN file: " + e.getMessage(),
                    "Import Error",
                    JOptionPane.ERROR_MESSAGE
            );
            throw new RuntimeException(e);
        }
    }

    private static void savePGNFile(final File pgnFile) {
        //try {
        //    writeGameToPGNFile(pgnFile, Table.get().getMoveLog());
        //}
        //catch (final IOException e) {
        //    e.printStackTrace();
        //}
    }

    private void undoLastMove() {
        final Move lastMove = Table.get().getMoveLog().removeMove(Table.get().getMoveLog().size() - 1);
        this.chessBoard = this.chessBoard.currentPlayer().unMakeMove(lastMove).getToBoard();
        this.computerMove = null;
        final boolean removed = Table.get().getMoveLog().removeMove(lastMove);
        if(removed) {
            Table.get().getGameHistoryPanel().redo(this.chessBoard, Table.get().getMoveLog());
            Table.get().getTakenPiecesPanel().redo(Table.get().getMoveLog());
            Table.get().getBoardPanel().drawBoard(this.chessBoard);
            Table.get().getDebugPanel().redo();
        }
    }

    // Updated methods using functional interface event system
    public void moveMadeUpdate(final PlayerType playerType) {
        this.eventManager.publishGameEvent(new GameEvent(GameEvent.Type.MOVE_MADE, playerType));
    }

    public void setupUpdate(final GameSetup gameSetup) {
        this.eventManager.publishGameEvent(new GameEvent(GameEvent.Type.GAME_SETUP_CHANGED, gameSetup));
    }

    public enum PlayerType {
        HUMAN,
        COMPUTER
    }

    private static class AIThinkTank extends SwingWorker<Move, String> {

        private final MySqlGamePersistence persistence;
        private final GameEventManager eventManager;

        private AIThinkTank(MySqlGamePersistence persistence, GameEventManager eventManager) {
            this.persistence = persistence;
            this.eventManager = eventManager;
        }

        @Override
        protected Move doInBackground() {
            final Move bestMove;
            final Move bookMove = Table.get().getUseBook()
                    ? this.persistence.getNextBestMove(
                    Table.get().getGameBoard(),
                    Table.get().getGameBoard().currentPlayer())
                    : MoveFactory.getNullMove();
            if (Table.get().getUseBook() && bookMove != MoveFactory.getNullMove()) {
                bestMove = bookMove;
            }
            else {
                final StockAlphaBeta strategy = new StockAlphaBeta(Table.get().getGameSetup().getSearchDepth());
                // Add AI progress listener to the strategy
                strategy.addAIProgressListener(eventManager::publishAIProgress);
                bestMove = strategy.execute(Table.get().getGameBoard());
            }
            return bestMove;
        }

        @Override
        public void done() {
            try {
                final Move bestMove = get();
                Table.get().updateComputerMove(bestMove);
                Table.get().updateGameBoard(Table.get().getGameBoard().currentPlayer().makeMove(bestMove).getToBoard());
                Table.get().getMoveLog().addMove(bestMove);
                Table.get().getGameHistoryPanel().redo(Table.get().getGameBoard(), Table.get().getMoveLog());
                Table.get().getTakenPiecesPanel().redo(Table.get().getMoveLog());
                Table.get().getBoardPanel().drawBoard(Table.get().getGameBoard());
                Table.get().getDebugPanel().redo();
                Table.get().moveMadeUpdate(PlayerType.COMPUTER);
            } catch (final Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private class BoardPanel extends JPanel {

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
            validate();
        }

        void drawBoard(final Board board) {
            removeAll();
            for (final TilePanel boardTile : boardDirection.traverse(boardTiles)) {
                boardTile.drawTile(board);
                add(boardTile);
            }
            validate();
            repaint();
        }

        void setTileDarkColor(final Board board,
                              final Color darkColor) {
            for (final TilePanel boardTile : this.boardTiles) {
                boardTile.setDarkTileColor(darkColor);
            }
            drawBoard(board);
        }

        void setTileLightColor(final Board board,
                               final Color lightColor) {
            for (final TilePanel boardTile : this.boardTiles) {
                boardTile.setLightTileColor(lightColor);
            }
            drawBoard(board);
        }

    }

    enum BoardDirection {
        NORMAL {
            @Override
            List<TilePanel> traverse(final List<TilePanel> boardTiles) {
                return boardTiles;
            }

            @Override
            BoardDirection opposite() {
                return FLIPPED;
            }
        },
        FLIPPED {
            @Override
            List<TilePanel> traverse(final List<TilePanel> boardTiles) {
                final List<TilePanel> reversed = new ArrayList<>(boardTiles);
                Collections.reverse(reversed);
                return reversed;
            }

            @Override
            BoardDirection opposite() {
                return NORMAL;
            }
        };

        abstract List<TilePanel> traverse(final List<TilePanel> boardTiles);
        abstract BoardDirection opposite();

    }

    public static class MoveLog {

        private final List<Move> moves;

        MoveLog() {
            this.moves = new ArrayList<>();
        }

        public List<Move> getMoves() {
            return this.moves;
        }

        void addMove(final Move move) {
            this.moves.add(move);
        }

        public int size() {
            return this.moves.size();
        }

        void clear() {
            this.moves.clear();
        }

        Move removeMove(final int index) {
            return this.moves.remove(index);
        }

        boolean removeMove(final Move move) {
            return this.moves.remove(move);
        }

    }

    private class TilePanel extends JPanel {

        private final int tileId;

        TilePanel(final BoardPanel boardPanel,
                  final int tileId) {
            super(new GridBagLayout());
            this.tileId = tileId;
            setPreferredSize(TILE_PANEL_DIMENSION);
            assignTileColor();
            assignTilePieceIcon(chessBoard);
            highlightTileBorder(chessBoard);
            addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(final MouseEvent event) {

                    if(Table.get().getGameSetup().isAIPlayer(Table.get().getGameBoard().currentPlayer()) ||
                            BoardUtils.isEndGame(Table.get().getGameBoard())) {
                        return;
                    }

                    if (isRightMouseButton(event)) {
                        sourceTile = null;
                        humanMovedPiece = null;
                    } else if (isLeftMouseButton(event)) {
                        if (sourceTile == null) {
                            sourceTile = chessBoard.getPiece(tileId);
                            humanMovedPiece = sourceTile;
                        } else {
                            final Move move = MoveFactory.createMove(chessBoard, sourceTile.getPiecePosition(),
                                    tileId);
                            final MoveTransition transition = chessBoard.currentPlayer().makeMove(move);
                            if (transition.getMoveStatus().isDone()) {
                                chessBoard = transition.getToBoard();
                                moveLog.addMove(move);
                            }
                            sourceTile = null;
                            humanMovedPiece = null;
                        }
                    }
                    invokeLater(() -> {
                        gameHistoryPanel.redo(chessBoard, moveLog);
                        takenPiecesPanel.redo(moveLog);
                        Table.get().moveMadeUpdate(PlayerType.HUMAN);
                        boardPanel.drawBoard(chessBoard);
                        debugPanel.redo();
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

        void drawTile(final Board board) {
            assignTileColor();
            assignTilePieceIcon(board);
            highlightTileBorder(board);
            highlightLegals(board);
            highlightAIMove();
            validate();
            repaint();
        }

        void setLightTileColor(final Color color) {
            lightTileColor = color;
        }

        void setDarkTileColor(final Color color) {
            darkTileColor = color;
        }

        private void highlightTileBorder(final Board board) {
            if(humanMovedPiece != null &&
                    humanMovedPiece.getPieceAllegiance() == board.currentPlayer().getAlliance() &&
                    humanMovedPiece.getPiecePosition() == this.tileId) {
                setBorder(BorderFactory.createLineBorder(Color.cyan));
            } else {
                setBorder(BorderFactory.createLineBorder(Color.GRAY));
            }
        }

        private void highlightAIMove() {
            if(computerMove != null) {
                if(this.tileId == computerMove.getCurrentCoordinate()) {
                    setBackground(Color.pink);
                } else if(this.tileId == computerMove.getDestinationCoordinate()) {
                    setBackground(Color.red);
                }
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
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }

        private Collection<Move> pieceLegalMoves(final Board board) {
            if(humanMovedPiece != null && humanMovedPiece.getPieceAllegiance() == board.currentPlayer().getAlliance()) {
                return humanMovedPiece.calculateLegalMoves(board);
            }
            return Collections.emptyList();
        }

        private void assignTilePieceIcon(final Board board) {
            this.removeAll();
            if(board.getPiece(this.tileId) != null) {
                try {
                    final BufferedImage image = ImageIO.read(new File(pieceIconPath +
                            board.getPiece(this.tileId).getPieceAllegiance().toString().charAt(0) +
                            board.getPiece(this.tileId).toString() +
                            ".gif"));
                    add(new JLabel(new ImageIcon(image)));
                } catch(final IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        private void assignTileColor() {
            if (BoardUtils.FIRST_ROW.get(this.tileId) ||
                    BoardUtils.THIRD_ROW.get(this.tileId) ||
                    BoardUtils.FIFTH_ROW.get(this.tileId) ||
                    BoardUtils.SEVENTH_ROW.get(this.tileId)) {
                setBackground(this.tileId % 2 == 0 ? lightTileColor : darkTileColor);
            } else if(BoardUtils.SECOND_ROW.get(this.tileId) ||
                    BoardUtils.FOURTH_ROW.get(this.tileId) ||
                    BoardUtils.SIXTH_ROW.get(this.tileId)  ||
                    BoardUtils.EIGHTH_ROW.get(this.tileId)) {
                setBackground(this.tileId % 2 != 0 ? lightTileColor : darkTileColor);
            }
        }
    }

    private static class GameEventManager {

        @FunctionalInterface
        interface GameEventListener {
            void onGameEvent(GameEvent event);
        }

        private final List<GameEventListener> gameListeners;
        private final List<AIProgressListener> aiProgressListeners;

        GameEventManager() {
            this.gameListeners = new ArrayList<>();
            this.aiProgressListeners = new ArrayList<>();
        }

        public void addGameEventListener(final GameEventListener listener) {
            this.gameListeners.add(listener);
        }

        public void addAIProgressListener(final AIProgressListener listener) {
            this.aiProgressListeners.add(listener);
        }

        public void publishGameEvent(final GameEvent event) {
            this.gameListeners.forEach(listener -> listener.onGameEvent(event));
        }

        public void publishAIProgress(final String progress) {
            this.aiProgressListeners.forEach(listener -> listener.onAIProgress(progress));
        }

    }

    private static class GameEvent {

        public enum Type {
            MOVE_MADE {
                @Override
                void handleGameEvent(final Table table,
                                     final GameEvent event) {
                    //Table.PlayerType playerType = (Table.PlayerType) event.getData();
                    table.handleMoveMade();
                }
            },
            GAME_SETUP_CHANGED {
                @Override
                void handleGameEvent(final Table table,
                                     final GameEvent event) {
                    GameSetup gameSetup = (GameSetup) event.getData();
                    table.handleGameSetupChanged(gameSetup);
                }
            },
            AI_THINKING {
                @Override
                void handleGameEvent(final Table table,
                                     final GameEvent event) {
                    System.out.println("AI is thinking...");
                }
            },
            GAME_OVER {
                @Override
                void handleGameEvent(final Table table,
                                     final GameEvent event) {
                    String gameOverMessage = (String) event.getData();
                    System.out.println("Game Over: " + gameOverMessage);
                }
            };

            abstract void handleGameEvent(Table table, GameEvent event);

        }

        private final Type type;
        private final Object data;

        public GameEvent(final Type type,
                         final Object data) {
            this.type = type;
            this.data = data;
        }

        public Object getData() {
            return data;
        }

        public void processEvent(final Table table,
                                 final GameEvent event) {
            this.type.handleGameEvent(table, event);
        }

    }

}