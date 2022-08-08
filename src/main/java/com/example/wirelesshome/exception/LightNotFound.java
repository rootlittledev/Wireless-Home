package com.example.wirelesshome.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="Light not Found")
public class LightNotFound extends RuntimeException {

    public LightNotFound() {
        super();
    }

    public LightNotFound(String message) {
        super("Light with name " + message + ", not found");
    }

    public LightNotFound(String message, Throwable cause) {
        super(message, cause);
    }
}
