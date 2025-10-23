package dev.network;

import dev.utils.Config;
import dev.utils.Logger;
import lombok.Getter;
import lombok.Setter;

import java.net.Socket;

//@Getter
//@Setter
public class NetworkManager {
    private final Config config;
    private final PeerPool peerPool;

    public NetworkManager(Config config) {
        this.config = config;
        this.peerPool = new PeerPool();
    }

    public void start() {
        Logger.info("Starting network manager");
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
}
