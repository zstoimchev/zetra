package dev.network;

import dev.utils.Logger;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class NetworkManager {
    private final Logger logger = new Logger(NetworkManager.class);

    public NetworkManager() { }
}
