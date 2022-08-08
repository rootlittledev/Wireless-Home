package com.example.wirelesshome.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Device state missing")
public class DeviceStateMissing extends RuntimeException {

    public DeviceStateMissing() {
        super();
    }

    public DeviceStateMissing(String message) {
        super(message);
    }

    public DeviceStateMissing(String message, Throwable cause) {
        super(message, cause);
    }
}
