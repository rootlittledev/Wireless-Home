package com.example.wirelesshome.exception;

public class CommandNotFound extends RuntimeException {

    public CommandNotFound() {
        super();
    }

    public CommandNotFound(String message) {
        super(message);
    }

    public CommandNotFound(String message, Throwable cause) {
        super(message, cause);
    }
}
