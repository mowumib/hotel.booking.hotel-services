package com.hotel.booking.hotel_services.service;

import com.hotel.booking.hotel_services.dto.otp.OtpValidationResult;

public interface OtpService {

    public String generateOtp();
    public void saveOtp(String userId, String otpCode);
    public OtpValidationResult validateOtp(String userId, String otpCode);
    public void deleteOtpByUserId(String userId);
    public void resendOtp(String userId);

}
