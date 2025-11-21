package dev;

import dev.message.MessageQueue;
import dev.network.NetworkManager;
import dev.network.Server;
import dev.utils.Config;
import dev.utils.Logger;

public class Main {
    private final Logger logger;
    private final Config config;
    private final Server server;
    private final InputHandler inputHandler;

    // DI and registering all the configuration
    public Main(String[] args) {
        this.logger = Logger.getLogger(Main.class);
        this.config = Config.load(args[0]);
        NetworkManager networkManager = new NetworkManager(config);
        MessageQueue queue = new MessageQueue();
        this.server = new Server(config, queue, networkManager);
        this.inputHandler = new InputHandler();
    }

    public static void main(String[] args) {
        // TODO: validate args & add a shutdown hook to gracefully stop the network
        new Main(args).startNetwork();
    }

    private void startNetwork() {
        logger.info("Starting network on port: {}...", config.getNodePort());
        this.server.start();
        this.inputHandler.start();
    }
}