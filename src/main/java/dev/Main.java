package dev;

import dev.network.NetworkManager;
import dev.utils.Logger;

public class Main {

    public Main() {
        NetworkManager networkManager = new NetworkManager();
    }

    public static void main(String[] args) {

        Logger logger = new Logger(Main.class);
        logger.info("Application started.");
    }
}