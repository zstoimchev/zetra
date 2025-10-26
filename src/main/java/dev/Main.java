package dev;

import dev.network.NetworkManager;
import dev.network.Peer;
import dev.network.PeerPool;
import dev.utils.Config;
import dev.utils.CustomException;
import dev.utils.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main {
    private final Logger logger;
    private final Config config;
    private final NetworkManager networkManager;
    private final ExecutorService executorService;
    private final PeerPool peerPool;

    public Main(String[] args) {
        // TODO: check if args[0] exists and is a valid config file
        this.config = Config.load(args[0]);
        this.networkManager = new NetworkManager(this.config);
        this.executorService = Executors.newVirtualThreadPerTaskExecutor();
        this.peerPool = new PeerPool();
        this.logger = Logger.getLogger(Main.class);
    }

    public static void main(String[] args) {
        // TODO: validate args
        // TODO: handle exceptions from startNetwork
        // TODO: add shutdown hook to gracefully stop the network
        new Main(args).startNetwork();
    }

    private void startNetwork() {
        executorService.submit(this::startServerSocket);
        if (!config.isBootstrapNode()) executorService.submit(this::connectToBootstrapNode);

        logger.info("Network is ready and booted up.");
        networkManager.start();

        // TODO: replace with proper wait/notify mechanism
        // keep main thread alive while network is running
        // TODO: handle graceful shutdown
        while (networkManager.isRunning()) {
            try {
                Thread.sleep(Long.MAX_VALUE);
            } catch (InterruptedException e) {
                logger.error("Main thread interrupted. Exiting.", e);
                throw new CustomException("Main thread interrupted.", e);
            }
        }
        networkManager.setIsRunning(new AtomicBoolean(false));
    }

    private void startServerSocket() {
        try (ServerSocket serverSocket = new ServerSocket(config.getNodePort())) {
            logger.info("Server socket started on port " + config.getNodePort());

            while (!Thread.currentThread().isInterrupted()) {
                Socket clientSocket = serverSocket.accept();
                Peer newPeer = networkManager.createInboundPeer(clientSocket);
                executorService.submit(newPeer);

            }
        } catch (IOException e) {
            logger.error("Could not start Bootstrap Node. Exiting.", e);
            throw new CustomException("Could not start Bootstrap Node.", e);
        }
    }

    private void connectToBootstrapNode() {
        logger.info("Connecting to bootstrap node at " + config.getBootstrapNodeHost() + ":" + config.getBootstrapNodePort());

        try {
            Socket socket = new Socket(config.getBootstrapNodeHost(), config.getBootstrapNodePort());
            logger.info("Connected to bootstrap node: " + socket.getRemoteSocketAddress());
            Peer peer = networkManager.createOutboundPeer(socket);
            executorService.submit(peer);
        } catch (IOException e) {
            logger.error("Could not connect to Bootstrap Node. Exiting.", e);
            throw new CustomException("Could not connect to Bootstrap Node.", e);
        }
    }
}