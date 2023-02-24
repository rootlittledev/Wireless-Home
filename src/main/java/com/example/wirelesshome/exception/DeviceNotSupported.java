package com.example.wirelesshome.exception;

public class DeviceNotSupported extends RuntimeException {

    public DeviceNotSupported() {
        super();
    }

    public DeviceNotSupported(String message) {
        super(message);
    }

    public DeviceNotSupported(String message, Throwable cause) {
        super(message, cause);
    }
}
