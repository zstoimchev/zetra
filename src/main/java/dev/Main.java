package dev;

import dev.message.MessageQueue;
import dev.message.MessageType;
import dev.network.NetworkManager;
import dev.network.Server;
import dev.protocol.MessageHandler;
import dev.protocol.PeerDiscoveryProtocol;
import dev.utils.Config;
import dev.utils.CustomException;
import dev.utils.Logger;

public class Main {
    private final Logger logger;
    private final Config config;
    private final Server server;
    private final MessageHandler messageHandler;
    private final PeerDiscoveryProtocol peerDiscoveryProtocol;

    // DI and registering all the configuration
    public Main(String[] args) {
        this.logger = Logger.getLogger(Main.class);
        this.config = Config.load(args[0]);
        NetworkManager networkManager = new NetworkManager(config);
        MessageQueue queue = new MessageQueue();
        this.server = new Server(config, queue, networkManager);
        this.messageHandler = new MessageHandler(queue);
        this.peerDiscoveryProtocol = new PeerDiscoveryProtocol(networkManager);
        registerProtocols();
    }

    public static void main(String[] args) {
        // TODO: validate args & add a shutdown hook to gracefully stop the network
        if (args.length < 1) throw new CustomException("Please specify the node configuration!", null);
        new Main(args).startNetwork();
    }

    private void startNetwork() {
        logger.info("Starting network on port: {}...", config.getNodePort());
        this.server.start();
    }

    // Register protocols in Message Handler
    private void registerProtocols() {
        messageHandler.registerProtocol(MessageType.PEER_REQUEST, peerDiscoveryProtocol);
        messageHandler.registerProtocol(MessageType.PEER_RESPONSE, peerDiscoveryProtocol);
        logger.info("Registered all protocol handlers");
    }
}