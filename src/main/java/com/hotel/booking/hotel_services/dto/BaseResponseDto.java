package com.hotel.booking.hotel_services.dto;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponseDto {
  private HttpStatus status;
  private String message;
  private Object data;
}
