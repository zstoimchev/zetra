package dev.message;

public class MessageBody<T> {
    public T payload;

    @Override
    public String toString() {
        return "MessageBody{payload=" + payload + "}";
    }
}
