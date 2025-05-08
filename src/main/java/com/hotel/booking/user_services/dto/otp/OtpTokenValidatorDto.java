package com.hotel.booking.user_services.dto.otp;

import lombok.Data;

@Data
public class OtpTokenValidatorDto {
    private String email;
    private String otpCode;
}
