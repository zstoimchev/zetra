package dev.message;

import dev.network.Peer;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Event {
    public final Peer sender;
    public final Message message;
}
