package dev.network;

import lombok.Getter;

import java.util.concurrent.ConcurrentHashMap;

@Getter
public class PeerPool {
    private final ConcurrentHashMap<String, Peer> activePeers;
    private final ConcurrentHashMap<String, Peer> knownPeers;

    public PeerPool() {
        this.activePeers = new ConcurrentHashMap<>();
        this.knownPeers = new ConcurrentHashMap<>();
    }

    public void addNewActivePeer(String peerId, Peer peer) {
        // TODO: check if there is still space for new outbound peers
        activePeers.put(peerId, peer);
    }

    public void removeActivePeer(String peerId) {
        knownPeers.put(peerId, activePeers.remove(peerId));
    }

    public void removeKnownPeer(String peerId) {
        activePeers.remove(peerId);
        knownPeers.remove(peerId);
    }

    public int getInboundPeerCount() {
        return 0;
    }

    public int getOutboundPeerCount() {
        return 0;
    }
}
