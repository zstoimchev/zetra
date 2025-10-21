package dev;

import dev.network.NetworkManager;
import dev.utils.Config;

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
        // one thread starts the server socket to listen for incoming connections
        // another thread manages outgoing connections
        // for now, just start the server socket

        // if the node is bootstrap node, it doesn't need to connect to other nodes
        if (config.isBootstrapNode()) {
            // log that we are bootstrap node and start accepting connections
        }



    }
}