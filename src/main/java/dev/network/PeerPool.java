package dev.network;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class PeerPool {
    private final ConcurrentHashMap<String, Peer> inboundPeers;
    private final ConcurrentHashMap<String, Peer> outboundPeers;
    private final ConcurrentHashMap<String, Peer> knownPeers;

    public PeerPool() {
        this.inboundPeers = new ConcurrentHashMap<>();
        this.outboundPeers = new ConcurrentHashMap<>();
        this.knownPeers = new ConcurrentHashMap<>();
    }

    public void addNewOutboundPeer(String peerId, Peer peer) {
        // TODO: check if there is still space for new outbound peers
        outboundPeers.put(peerId, peer);
    }

    public void removeOutboundPeer(String peerId) {
        knownPeers.put(peerId, outboundPeers.remove(peerId));
    }

    public void addNewInboundPeer(String peerId, Peer peer) {
        // TODO: check if there is still space for new inbound peers
        inboundPeers.put(peerId, peer);
    }

    public void removeInboundPeer(String peerId) {
        knownPeers.put(peerId, inboundPeers.remove(peerId));
    }

    public int getInboundPeerCount() {
        return inboundPeers.size();
    }

    public int getOutboundPeerCount() {
        return outboundPeers.size();
    }
}
