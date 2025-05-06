package com.hotel.booking.user_services.service;

public interface OtpService {

    public String generateOtp();
    public void saveOtp(String userId, String otpCode);
    public boolean validateOtp(String userId, String otpCode);
    public void deleteOtpByUserId(String userId);
    public void resendOtp(String userId);

}
