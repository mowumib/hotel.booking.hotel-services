package com.hotel.booking.user_services.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
@Slf4j
public class HotelExceptionHandler{

    @ExceptionHandler(value = {HotelRequestException.class})
    public ResponseEntity<Object> handleApiRequestException(HotelRequestException e) {
        // HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        HttpStatus status = e.getHttpStatus();
        log.error("An error occurred: {}", e.getMessage(), e);
        HotelException hotelException = new HotelException(e.getMessage(), status, ZonedDateTime.now(ZoneId.of("Z")));
        return new ResponseEntity<>(hotelException, status);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleException(Exception e) {
        HttpStatus serverError = HttpStatus.INTERNAL_SERVER_ERROR;
        log.error("An error occurred: {}", e.getMessage(), e);
        HotelException hotelException = new HotelException(e.getMessage(), serverError, ZonedDateTime.now(ZoneId.of("Z")));
        return new ResponseEntity<>(hotelException, serverError);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Object> handleAuthenticationException(AuthenticationException e) {
        log.warn("Authentication failed: {}", e.getMessage());

        HotelException apiException = new HotelException(
            "Invalid username or password", // Customizable message
            HttpStatus.UNAUTHORIZED,
            ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(apiException, HttpStatus.UNAUTHORIZED);
    }
}
