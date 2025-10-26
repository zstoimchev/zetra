package dev.network;

import dev.utils.Config;
import dev.utils.Logger;
import lombok.Getter;
import lombok.Setter;

import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

@Getter
@Setter
public class NetworkManager {
    private final Logger logger;

    private AtomicBoolean isRunning = new AtomicBoolean(false);

    private final Config config;
    private final PeerPool peerPool;

    // methods to broadcast messages to peers


    public NetworkManager(Config config) {
        this.logger = Logger.getLogger(NetworkManager.class);
        this.config = config;
        this.peerPool = new PeerPool();
    }

    public void start() {
        logger.info("Starting network manager");
        isRunning.set(true);
    }

    public Peer createOutboundPeer(Socket socket) {
        return new Peer(socket);
    }

    public Peer createInboundPeer(Socket socket) {
        /*if (peerPool.getInboundPeerCount() >= config.getInboundConnectionLimit()) {
            Logger.warn("Inbound connection limit reached. Rejecting new peer: " + socket.getRemoteSocketAddress());
            return null;
        }

        // Accept the new inbound peer
        Peer peer = new Peer(socket);
        peerPool.addNewInboundPeer(peer);
        Logger.info("Accepting new inbound peer: " + socket.getRemoteSocketAddress());
        return peer;
        */
        return new Peer(socket);
    }

    public boolean isRunning() {
        return isRunning.get();
    }
}
