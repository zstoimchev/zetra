package dev;

import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class InputHandler extends Thread {
    private final AtomicBoolean running = new AtomicBoolean(false);

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);

        while (running.get()) {
            String input = scanner.nextLine();

            // TODO: Process the input as needed
            // maybe add commands for the network manager, etc.
            // to request peers, show status, etc.
            // most probably use different colors for the console output

            // add a webhook to close the input handler
            if (input.equalsIgnoreCase("exit")) {
                running.set(false);
                // TODO: call network manager or whatever to shutdown
            }
        }
    }
}
