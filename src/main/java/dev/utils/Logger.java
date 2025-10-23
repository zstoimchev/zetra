package dev.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    private static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String PURPLE = "\u001B[35m";
    private static final String CYAN = "\u001B[36m";
    private static final String RESET = "\u001B[0m";

    public enum LogLevel {
        DEBUG, INFO, NOTICE, WARNING, ERROR, CRITICAL, ALERT, EMERGENCY
    }

    private static void log(Throwable t, String message, LogLevel level) {
        String date = dateFormat.format(LocalDateTime.now());
        String threadName = Thread.currentThread().getName();

        String messagePrefix = "[" + date + "][Thread: " + threadName + "] " + level + ": ";

        switch (level) {
            case DEBUG -> messagePrefix = CYAN + messagePrefix + RESET;
            case INFO -> messagePrefix = GREEN + messagePrefix + RESET;
            case NOTICE -> messagePrefix = BLUE + messagePrefix + RESET;
            case WARNING -> messagePrefix = YELLOW + messagePrefix + RESET;
            case ERROR -> messagePrefix = RED + messagePrefix + RESET;
            case CRITICAL -> messagePrefix = PURPLE + messagePrefix + RESET;
            case ALERT, EMERGENCY -> messagePrefix = RED + PURPLE + messagePrefix + RESET;
        }

        System.out.println(messagePrefix + message);
        if (t != null) System.err.println(t.toString());
    }

    public static void info(String message) {
        log(null, message, LogLevel.INFO);
    }

    public static void debug(String message) {
        log(null, message, LogLevel.DEBUG);
    }

    public static void notice(String message) {
        log(null, message, LogLevel.NOTICE);
    }

    public static void warn(String message) {
        log(null, message, LogLevel.WARNING);
    }

    public static void warn(Throwable t, String message) {
        log(t, message, LogLevel.WARNING);
    }

    public static void error(String message) {
        log(null, message, LogLevel.ERROR);
    }

    public static void error(Throwable t, String message) {
        log(t, message, LogLevel.ERROR);
    }

    public static void critical(Throwable t, String message) {
        log(t, message, LogLevel.CRITICAL);
    }

    public static void alert(Throwable t, String message) {
        log(t, message, LogLevel.ALERT);
    }

    public static void emergency(Throwable t, String message) {
        log(t, message, LogLevel.EMERGENCY);
    }
}