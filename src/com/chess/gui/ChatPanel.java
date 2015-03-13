package com.chess.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

class ChatPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTextField enterField;
    private JTextArea displayArea;
    private static final Dimension CHAT_PANEL_DIMENSION = new Dimension(600, 150);

    public ChatPanel() {
        super(new BorderLayout());
        enterField = new JTextField();
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setBackground(Color.LIGHT_GRAY);
        displayArea.setFont(new Font("Monospaced", Font.BOLD, 12));

        enterField.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent event) {
//                if (connectionEstablished) {
//                    assert (networkActor != null) : "connection established, but networkActor == null!";
//                    networkActor.sendData(networkActor.getName() + ">>> "
//                            + event.getActionCommand());
//                    writeToDisplay(networkActor.getName() + ">>> "
//                            + event.getActionCommand());
//                } else {
//                    writeToDisplay(">>> " + event.getActionCommand());
//                }
                enterField.setText("");
            }
        });

        add(enterField, BorderLayout.NORTH);
        add(new JScrollPane(displayArea), BorderLayout.CENTER);
        setPreferredSize(CHAT_PANEL_DIMENSION);
        validate();

    }

    public void writeToDisplay(final String message) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                displayArea.append(message + "\n");
            }
        });
    }

}