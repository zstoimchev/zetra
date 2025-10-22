package dev.network;

import dev.utils.Logger;
import lombok.Getter;
import lombok.Setter;

import java.net.Socket;

@Getter
@Setter
public class NetworkManager {

    public NetworkManager() {
    }

    public void start() {
        Logger.info("Starting network manager");
    }

    public Peer createOutboundPeer(Socket socket) {
        return new Peer(socket);
    }

    public Peer createInboundPeer(Socket socket) {
        return new Peer(socket);
    }
}
