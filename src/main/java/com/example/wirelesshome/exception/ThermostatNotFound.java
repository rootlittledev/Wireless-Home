package com.example.wirelesshome.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="Thermostat not Found")
public class ThermostatNotFound extends RuntimeException {

    public ThermostatNotFound() {
        super();
    }

    public ThermostatNotFound(String message) {
        super("Thermostat with name " + message + ", not found");
    }

    public ThermostatNotFound(String message, Throwable cause) {
        super(message, cause);
    }
}
