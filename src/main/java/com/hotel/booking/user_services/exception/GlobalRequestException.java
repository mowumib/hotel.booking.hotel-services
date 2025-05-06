package com.hotel.booking.user_services.exception;

import org.springframework.http.HttpStatus;

public class GlobalRequestException extends RuntimeException{
	
	private final HttpStatus httpStatus;
	public GlobalRequestException(String message, HttpStatus httpStatus){
		super(message) ;
		this.httpStatus = httpStatus;
	}

	public HttpStatus getHttpStatus() {
        return httpStatus;
    }

}
