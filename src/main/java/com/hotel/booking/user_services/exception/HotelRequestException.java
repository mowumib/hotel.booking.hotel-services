package com.hotel.booking.user_services.exception;

import org.springframework.http.HttpStatus;

public class HotelRequestException extends RuntimeException{
	
	private final HttpStatus httpStatus;
	public HotelRequestException(String message, HttpStatus httpStatus){
		super(message) ;
		this.httpStatus = httpStatus;
	}

	public HttpStatus getHttpStatus() {
        return httpStatus;
    }

}
