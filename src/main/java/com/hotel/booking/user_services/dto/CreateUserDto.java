package com.hotel.booking.user_services.dto;

import lombok.Data;

@Data
public class CreateUserDto {
    private String name;
    private String email;
    private String password;
}
