package com.example.wirelesshome.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="HeatingMat not Found")
public class HeatingMatNotFound extends RuntimeException {

    public HeatingMatNotFound(){
        super();
    }

    public HeatingMatNotFound(String message) {
        super("HeatingMat with name " + message + ", not found");
    }

    public HeatingMatNotFound(String message, Throwable cause) {
        super(message, cause);
    }

}
