package network;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Client extends NetworkEntity {

    private String hostName;

    private int serverPort;

    public Client(final String host, final int port) {
        super("CLIENT");
        hostName = host;
        serverPort = port;
    }

    public void run() {
        try {
            connectToServer();
            getStreams();
            processIncomingData();
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    private void connectToServer() {
        try {
            connectionHandle = new Socket(InetAddress.getByName(hostName),
                    serverPort);
            //connectionEstablished = true;
            //chatPanel.writeToDisplay("Successfully connected to "
            //        + connectionHandle.getInetAddress().getHostName());
        } catch (IOException e) {
            //chatPanel.writeToDisplay("Failed to connect to: " + hostName);
        }
    }

}