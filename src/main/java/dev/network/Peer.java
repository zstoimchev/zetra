package dev.network;

import dev.utils.Logger;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

public class Peer implements Runnable {
    private final Socket socket;
    private final AtomicBoolean isRunning = new AtomicBoolean(false);
    private final BufferedReader in;
    private final BufferedWriter out;

    public Peer(Socket socket) {
        this.socket = socket;
        try {
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            Logger.error(e, "Could not create input/output stream for peer.");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        isRunning.set(true);
        Logger.info("Peer connected: " + socket.getRemoteSocketAddress());

        try {
            String message;
            while (isRunning.get() && (message = in.readLine()) != null) {
                Logger.info("Received message from " + socket.getRemoteSocketAddress() + ": " + message);
                // Handle the received message here
                // TODO: Implement message handling logic
//                networkManager.processIncomingMessage(this, line);
                // maybe create a protocol that handles the messages aka dispatches them?
            }
            Logger.info("Peer disconnected: " + socket.getRemoteSocketAddress());
        } catch (IOException e) {
            Logger.error(e, "Connection error with peer: " + socket.getRemoteSocketAddress());
        } finally {
            closeConnection();
        }
    }

    private void closeConnection() {
        isRunning.set(false);
        try {
            socket.close();
            in.close();
            out.close();
            Logger.info("Closed connection with peer: " + socket.getRemoteSocketAddress());
        } catch (IOException e) {
            Logger.warn(e, "Error closing connection with peer: " + socket.getRemoteSocketAddress());
        }
    }
}
