package com.hotel.booking.hotel_services.dto;

import com.hotel.booking.hotel_services.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoResponseDto {
  private String jwt;
  private User user;
}
