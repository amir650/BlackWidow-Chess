package network;

import java.io.IOException;
import java.net.ServerSocket;

public class Server extends NetworkEntity {

    private ServerSocket server;

    private final int listenPort;

    public Server(final int listen_port) {
        super("SERVER");
        listenPort = listen_port;
    }

    public void run() {

        try {
            server = new ServerSocket(listenPort, 1);
            //chatPanel.writeToDisplay("Listening on port " + listenPort);
            try {
                waitForConnection();
                getStreams();
                processIncomingData();
            }
            catch (IOException ioe) {
                ioe.printStackTrace();
            } finally {
                closeConnection();
            }
        } catch (IOException e) {
        //    JOptionPane.showMessageDialog(gameFrame, "Network Error: " + e, "Notification",
        //            JOptionPane.ERROR_MESSAGE);
        }

    }

    private void waitForConnection() throws IOException {
        connectionHandle = server.accept();
        //connectionEstablished = true;
        //chatPanel.writeToDisplay("Connection received from:"
        //        + connectionHandle.getInetAddress().getHostName());
    }

    public void closeConnection() {
        super.closeConnection();
        try {
            server.close();
        } catch (IOException e) {
        //    chatPanel.writeToDisplay(getName()
        //            + "failed to disconnect from the network");
        }
    }
}