package com.example.wirelesshome.exception;

public class CommandFailed extends RuntimeException {

    public CommandFailed() {
        super();
    }

    public CommandFailed(String message) {
        super(message);
    }

    public CommandFailed(String message, Throwable cause) {
        super(message, cause);
    }
}
