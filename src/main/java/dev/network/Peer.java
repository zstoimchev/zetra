package dev.network;

import dev.message.Message;
import dev.utils.CustomException;
import dev.utils.Logger;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

public class Peer implements Runnable {
    private final Logger logger;
    private final Socket socket;
    private final AtomicBoolean isRunning = new AtomicBoolean(false);
    private final BufferedReader in;
    private final BufferedWriter out;
    private final NetworkManager networkManager;

    // TODO: direction (inbound/outbound), peer ID, capabilities, etc. ? ? ?

    public Peer(Socket socket, NetworkManager networkManager) {
        this.logger = Logger.getLogger(Peer.class);
        this.networkManager = networkManager;
        this.socket = socket;
        try {
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            logger.error("Could not create input/output stream for peer.", e);
            throw new CustomException("Could not create input/output stream for peer.", e);
        }
    }

    @Override
    public void run() {
        isRunning.set(true);
        logger.info("Peer connected: " + socket.getRemoteSocketAddress());

        try {
            String message;
            while (isRunning.get() && (message = in.readLine()) != null) {
                logger.info("Received message from {}, {}", socket.getRemoteSocketAddress(), message);
                // Handle the received message here
                // TODO: Implement message handling logic
//                networkManager.processIncomingMessage(this, line);
                // maybe create a protocol that handles the messages aka dispatches them?
            }
            logger.info("Peer disconnected: {}", socket.getRemoteSocketAddress());
        } catch (IOException e) {
            logger.error("Connection error with peer: " + socket.getRemoteSocketAddress(), e);
            throw new CustomException("Connection error with peer: " + socket.getRemoteSocketAddress(), e);
        } finally {
            closeConnection();
        }
    }

    private void closeConnection() {
        isRunning.set(false);
        try {
            in.close();
            out.close();
            socket.close();
            logger.info("Closed connection with peer: {}", socket.getRemoteSocketAddress());
        } catch (IOException e) {
            logger.warn("Error closing connection with peer: {}", socket.getRemoteSocketAddress(), e);
            throw new CustomException("Error closing connection with peer: " + this.socket.getRemoteSocketAddress(), e);
        }
    }

    public void send(Message message) {
        // TODO: send the message to this.out
    }
}
