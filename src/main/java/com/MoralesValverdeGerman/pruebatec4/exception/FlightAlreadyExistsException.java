package com.MoralesValverdeGerman.pruebatec4.exception;

import org.springframework.http.HttpStatus;

public class FlightAlreadyExistsException extends RuntimeException {
    public FlightAlreadyExistsException(String message) {
        super(message);
    }

}
