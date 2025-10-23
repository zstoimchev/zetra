package dev.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {
    private final Properties properties;

    public Config(Properties properties) {
        this.properties = properties;
    }

    public static Config load(String filename) {
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream(filename)) {
            properties.load(fis);
        } catch (IOException e) {
            // TODO: log error
            throw new RuntimeException(e);
            // TODO: make custom exception
        }
        return new Config(properties);
    }

    public String getNodeHost() {
        return properties.getProperty("node.host");
    }

    public int getNodePort() {
        return Integer.parseInt(properties.getProperty("node.port"));
    }

    public boolean isBootstrapNode() {
        return Boolean.parseBoolean(properties.getProperty("node.bootstrap"));
    }

    public int getMaxConnections() {
        return Integer.parseInt(properties.getProperty("node.connections.max"));
    }

    public int getInboundConnectionLimit() {
        return Integer.parseInt(properties.getProperty("node.connections.inbound.max"));
    }

    public int getOutboundConnectionLimit() {
        return Integer.parseInt(properties.getProperty("node.connections.outbound.max"));
    }

    public String getBootstrapNodeHost() {
        return properties.getProperty("bootstrap.host");
    }

    public int getBootstrapNodePort() {
        return Integer.parseInt(properties.getProperty("bootstrap.port"));
    }

}