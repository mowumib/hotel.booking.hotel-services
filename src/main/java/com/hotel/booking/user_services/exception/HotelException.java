package com.hotel.booking.user_services.exception;

import java.time.ZonedDateTime;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class HotelException{

    private final String message;
    private final HttpStatus httpStatus;
    private final ZonedDateTime timestamp;

    public HotelException(String message, HttpStatus httpStatus, ZonedDateTime timestamp) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.timestamp = timestamp;
    }
}
