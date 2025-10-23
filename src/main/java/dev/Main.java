package dev;

import dev.network.NetworkManager;
import dev.network.Peer;
import dev.utils.Config;
import dev.utils.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private static NetworkManager networkManager;
    private static ExecutorService executorService;
    private static Config config;

    public static void main(String[] args) {
        networkManager = new NetworkManager();
        executorService = Executors.newVirtualThreadPerTaskExecutor();
        config = Config.load(args[0]);

        startNetwork();
    }

    private static void startNetwork() {
        executorService.submit(Main::startServerSocket);
        if (!config.isBootstrapNode()) executorService.submit(Main::connectToBootstrapNode);

        Logger.info("Network is ready and booted up.");
        networkManager.start();

        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            Logger.critical(e, "Main thread interrupted. Exiting.");
            throw new RuntimeException(e);
        }
    }

    private static void startServerSocket() {
        try (ServerSocket serverSocket = new ServerSocket(config.getNodePort())) {
            Logger.info("Server socket started on port " + config.getNodePort());

            while (!Thread.currentThread().isInterrupted()) {
                Socket clientSocket = serverSocket.accept();
                Peer newPeer = networkManager.createInboundPeer(clientSocket);
                executorService.submit(newPeer);
            }

        } catch (IOException e) {
            Logger.emergency(e, "Could not start Bootstrap Node. Exiting.");
            throw new RuntimeException(e);
        }
    }

    private static void connectToBootstrapNode() {
        Logger.info("Connecting to bootstrap node");
        try (Socket socket = new Socket(config.getBootstrapNodeHost(), config.getBootstrapNodePort())) {
            Logger.info("Connected to bootstrap node: " + socket.getRemoteSocketAddress());
            Peer peer = networkManager.createOutboundPeer(socket);
            executorService.submit(peer);
        } catch (IOException e) {
            Logger.emergency(e, "Could not start Bootstrap Node. Exiting.");
            throw new RuntimeException(e);
        }

    }
}