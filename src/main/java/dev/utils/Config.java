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
            Logger.error(e, "Could not load config file: " + filename);
            throw new CustomException("Could not load file: " + filename, e);
        }
        return new Config(properties);
    }

    public String getNodeHost() {
        return properties.getProperty("node.host", "localhost");
    }

    public int getNodePort() {
        return Integer.parseInt(properties.getProperty("node.port", "12137"));
    }

    public boolean isBootstrapNode() {
        return Boolean.parseBoolean(properties.getProperty("node.bootstrap", "true"));
    }

    public int getMaxConnections() {
        return getOutboundConnectionLimit() + getInboundConnectionLimit();
    }

    public int getInboundConnectionLimit() {
        return Integer.parseInt(properties.getProperty("node.connections.inbound.max", "3"));
    }

    public int getOutboundConnectionLimit() {
        return Integer.parseInt(properties.getProperty("node.connections.outbound.max", "3"));
    }

    public String getBootstrapNodeHost() {
        return properties.getProperty("bootstrap.host", "localhost");
    }

    public int getBootstrapNodePort() {
        return Integer.parseInt(properties.getProperty("bootstrap.port", "12137"));
    }

    // TODO: method for verifying config values (integers specifically)
}