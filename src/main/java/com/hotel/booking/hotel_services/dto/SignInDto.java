package com.hotel.booking.hotel_services.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignInDto {

    @NotEmpty(message = "Email address can not be a null or empty")
    @Email(message = "Email address should be a valid value")
    private String email;

    @Size(min = 5, max = 10, message = "The length of the password should be between 5 and 10")
    private String password;
}
