package com.chess.gui;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class DebugPanel extends JPanel {

    private final JTextArea debugTextArea;
    private final JScrollPane scrollPane;
    private final List<String> debugMessages;
    private static final int MAX_DEBUG_MESSAGES = 100; // Limit to prevent memory issues

    public DebugPanel() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(600, 150));
        setBorder(BorderFactory.createTitledBorder("Debug Information"));
        this.debugMessages = new ArrayList<>();
        this.debugTextArea = new JTextArea();
        this.debugTextArea.setEditable(false);
        this.debugTextArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        this.debugTextArea.setBackground(Color.WHITE);
        this.debugTextArea.setForeground(Color.BLACK);
        this.scrollPane = new JScrollPane(debugTextArea);
        this.scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(this.scrollPane, BorderLayout.CENTER);
        // Add control buttons
        final JPanel buttonPanel = new JPanel(new FlowLayout());
        final JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(e -> clearDebugInfo());
        final JButton saveButton = new JButton("Save Log");
        saveButton.addActionListener(e -> saveDebugLog());
        buttonPanel.add(clearButton);
        buttonPanel.add(saveButton);
        add(buttonPanel, BorderLayout.SOUTH);
        // Initialize with welcome message
        updateProgress("Debug panel initialized - ready for AI progress updates");
    }

    // Method called by the functional interface system to update AI progress
    public void updateProgress(String progress) {
        SwingUtilities.invokeLater(() -> {
            // Add timestamp to the message
            final String message = progress + "\n";
            // Add to our message list
            debugMessages.add(message);
            // Limit the number of stored messages to prevent memory issues
            if (debugMessages.size() > MAX_DEBUG_MESSAGES) {
                debugMessages.removeFirst();
            }
            // Update the text area
            debugTextArea.append(message);
            // Auto-scroll to bottom
            debugTextArea.setCaretPosition(debugTextArea.getDocument().getLength());
        });
    }

    // Method for general debug information updates
    public void updateDebugInfo(String info) {
        SwingUtilities.invokeLater(() -> {
            String timestampedMessage = String.format("[%tT] DEBUG: %s%n",
                    System.currentTimeMillis(), info);

            debugMessages.add(timestampedMessage);

            if (debugMessages.size() > MAX_DEBUG_MESSAGES) {
                debugMessages.removeFirst();
            }

            debugTextArea.append(timestampedMessage);
            debugTextArea.setCaretPosition(debugTextArea.getDocument().getLength());
        });
    }

    // Method called when game state changes (replaces the old Observer update)
    public void redo() {
        SwingUtilities.invokeLater(() -> {
            // Add a separator or status update when the game state changes
            String gameStateMessage = String.format("[%tT] --- Game state updated ---%n",
                    System.currentTimeMillis());

            debugMessages.add(gameStateMessage);

            if (debugMessages.size() > MAX_DEBUG_MESSAGES) {
                debugMessages.removeFirst();
            }

            debugTextArea.append(gameStateMessage);
            debugTextArea.setCaretPosition(debugTextArea.getDocument().getLength());
        });
    }

    // Clear all debug information
    private void clearDebugInfo() {
        SwingUtilities.invokeLater(() -> {
            debugMessages.clear();
            debugTextArea.setText("");
            updateProgress("Debug panel cleared");
        });
    }

    // Save debug log to file
    private void saveDebugLog() {
        SwingUtilities.invokeLater(() -> {
            final JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save Debug Log");
            fileChooser.setSelectedFile(new File("chess_debug_log.txt"));
            final int userSelection = fileChooser.showSaveDialog(this);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                final File fileToSave = fileChooser.getSelectedFile();
                try (final PrintWriter writer = new PrintWriter(fileToSave)) {
                    for (final String message : debugMessages) {
                        writer.print(message);
                    }
                    updateProgress("Debug log saved to: " + fileToSave.getAbsolutePath());
                } catch (final IOException e) {
                    updateProgress("Error saving debug log: " + e.getMessage());
                }
            }
        });
    }

}