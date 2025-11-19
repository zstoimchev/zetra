package dev.protocol;

import dev.message.Message;
import dev.network.NetworkManager;
import dev.network.Peer;
import dev.utils.CustomException;
import dev.utils.Logger;

import java.util.HashMap;
import java.util.Map;

public class PeerDiscoveryProtocol implements Protocol {
    private final Logger logger;
    private final NetworkManager networkManager;
    private final Map<String, PeerInfo> knownPeers;

    public PeerDiscoveryProtocol(NetworkManager networkManager) {
        this.logger = Logger.getLogger(this.getClass());
        this.networkManager = networkManager;
        this.knownPeers = new HashMap<>();
    }

    @Override
    public void digest(Peer peer, Message message) {
        switch (message.getType()) {
            case PEER_REQUEST:
                handlePeerRequest(peer, message);
                break;
            case PEER_RESPONSE:
                handlePeerResponse(peer, message);
                break;
            default:
                logger.warn("PeerDiscoveryProtocol received unexpected message type: {}", message.getType());
        }
    }

    private void handlePeerRequest(Peer peer, Message message) {
        throw new CustomException("Method not implemented yet . . . ", null);
    }

    private void handlePeerResponse(Peer peer, Message message) {
        throw new CustomException("Method not implemented yet . . . ", null);
    }

    public static class PeerInfo {
        public String publicKey;
        public String host;
        public int port;

        public PeerInfo(String publicKey, String host, int port) {
            this.publicKey = publicKey;
            this.host = host;
            this.port = port;
        }
    }
}
