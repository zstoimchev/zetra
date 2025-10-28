package dev.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    public MessageHeader header;
    public MessageBody body;

    @Override // TODO: construct proper skeleton for JSON formatting?
    public String toString() {
        return "Message{header=" + header + ", body=" + body + "}";
    }
}

