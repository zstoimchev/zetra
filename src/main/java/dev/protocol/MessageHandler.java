package dev.protocol;

import dev.message.Message;
import dev.network.Peer;

public class MessageHandler implements Protocol {
    @Override
    public void digest(Peer peer, Message message) {
        // TODO: switch header.protocol to appropriate handler
    }
}
