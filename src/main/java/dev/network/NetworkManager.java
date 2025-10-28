package dev.network;

import dev.message.Message;
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

    // TODO: public/private keypair for this node

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

    public void handleMessage() {
        // TODO: need a class, probably protocol implemented,
        // that handles incoming messages and dispatches them to appropriate handlers

    }

    public Peer createOutboundPeer(Socket socket) {
        return new Peer(socket, this);
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
        return new Peer(socket, this);
    }

    public boolean isRunning() {
        return isRunning.get();
    }

    public void sendMessage(Peer peer, Message message) {
        // TODO: first sign the message
        // then, serialize the message
        // and only after that, send the message
        peer.send(message);
    }

    public void broadcastMessage(Message message) {
        peerPool.getActivePeers().values().forEach(peer -> sendMessage(peer, message));
        for (Peer peer : peerPool.getActivePeers().values()) {
            sendMessage(peer, message);
        }
    }
}
