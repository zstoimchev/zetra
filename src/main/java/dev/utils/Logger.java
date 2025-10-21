package dev.utils;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Logger {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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

    private static void log(String message, LogLevel level) {
        String date = dateFormat.format(System.currentTimeMillis());

        String threadName = Thread.currentThread().getName();
        String className = Arrays.stream(getCallerClassName().split("\\.")).skip(2).collect(Collectors.joining("."));

        String messagePrefix = "[" + date + "][Thread: " + threadName + "][" + className + ".java] " + level + ": ";

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
    }

    private static String getCallerClassName() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        String fullClassName = stackTrace[4].getClassName();
        String fullMethodName = stackTrace[4].getMethodName();
        int lineNumber = stackTrace[4].getLineNumber();
        return fullClassName + "." + fullMethodName + "():L" + lineNumber;
    }

    public static void info(String message) {
        log(message, LogLevel.INFO);
    }

    public static void debug(String message) {
        log(message, LogLevel.DEBUG);
    }

    public static void notice(String message) {
        log(message, LogLevel.NOTICE);
    }

    public static void warn(String message) {
        log(message, LogLevel.WARNING);
    }

    public static void error(String message) {
        log(message, LogLevel.ERROR);
    }

    public static void error(Throwable t, String message) {
        log(message, LogLevel.ERROR);
    }

    public static void critical(String message) {
        log(message, LogLevel.CRITICAL);
    }

    public static void alert(String message) {
        log(message, LogLevel.ALERT);
    }

    public static void emergency(String message) {
        log(message, LogLevel.EMERGENCY);
    }
}