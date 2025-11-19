package dev.network;

import dev.message.MessageBuilder;
import dev.utils.Config;
import dev.utils.Crypto;
import dev.utils.Logger;
import lombok.Getter;
import lombok.Setter;

import java.security.PublicKey;
import java.util.Base64;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

@Getter
@Setter
public class NetworkManager {
    private final Logger logger;
    private final UUID nodeId;

    private AtomicBoolean isRunning = new AtomicBoolean(false);

    private final Config config;
    private ConcurrentHashMap<UUID, Peer> connectedPeers;
    private ConcurrentHashMap<String, Peer> pendingPeers;
    
    private final MessageBuilder messageBuilder;
    private final Crypto crypto;

    public NetworkManager(Config config) {
        this.logger = Logger.getLogger(NetworkManager.class);
        this.nodeId = UUID.randomUUID();
        this.config = config;
        this.connectedPeers = new ConcurrentHashMap<>();
        this.pendingPeers = new ConcurrentHashMap<>();
        this.crypto = new Crypto();
        messageBuilder = new MessageBuilder(getEncodedPublicKey(), nodeId);
    }

    public void start() {
        logger.info("Starting network manager");
        isRunning.set(true);
    }

    public void registerPeer(Peer peer) {
        UUID peerId = peer.getPeerId();
        connectedPeers.put(peerId, peer);
        logger.info("Registered peer: {}", peerId);
    }

    public void unregisterPeer(Peer peer) {
        connectedPeers.remove(peer.getPeerId());
        logger.info("Unregistered peer: {}", peer.getPeerId());
    }

    public PublicKey getPublicKey() {
        return crypto.getPublicKey();
    }

    public String getEncodedPublicKey() {
        return Base64.getEncoder().encodeToString(getPublicKey().getEncoded());
    }

}

