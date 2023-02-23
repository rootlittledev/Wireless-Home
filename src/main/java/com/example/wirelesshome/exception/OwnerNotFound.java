package com.example.wirelesshome.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Owner not Found")
public class OwnerNotFound extends RuntimeException {

    public OwnerNotFound() {
        super();
    }

    public OwnerNotFound(String message) {
        super("Owner with name " + message + ", not found");
    }

    public OwnerNotFound(String message, Throwable cause) {
        super(message, cause);
    }

}
