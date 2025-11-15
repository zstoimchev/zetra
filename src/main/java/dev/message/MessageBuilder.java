package dev.message;

import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class MessageBuilder {
    private String senderPublicKey;
    private UUID senderNodeId;

    public Message buildHandshakeMessage() {
        return new Message(
                MessageType.HANDSHAKE,
                senderPublicKey,
                System.currentTimeMillis(),
                UUID.randomUUID().toString(),
                null,
                null
        );
    }
}
