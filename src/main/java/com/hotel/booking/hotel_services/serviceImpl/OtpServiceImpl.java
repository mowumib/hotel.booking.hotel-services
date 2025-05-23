package com.hotel.booking.hotel_services.serviceImpl;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.hotel.booking.hotel_services.dto.otp.OtpValidationResult;
import com.hotel.booking.hotel_services.entity.otp.OTP;
import com.hotel.booking.hotel_services.repository.OtpRepository;
import com.hotel.booking.hotel_services.service.OtpService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OtpServiceImpl implements OtpService {
    private final OtpRepository otpRepository;

    @Override
    public String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    @Override
    public void saveOtp(String userId, String otpCode) {
        // Set expiration time to 5 minutes from now
        LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(5);

        // Check if OTP exists for user, if yes update, else create new
        OTP existingOTP = otpRepository.findByUserIdAndUsedFalse(userId);
        if (existingOTP != null) {
            existingOTP.setOtpCode(otpCode);
            existingOTP.setExpirationTime(expirationTime);
            existingOTP.setUsed(false); // Reset the used flag
        } else {
            OTP otp = new OTP();
            otp.setUserId(userId);
            otp.setOtpCode(otpCode);
            otp.setExpirationTime(expirationTime);
            otp.setUsed(false);
            otpRepository.save(otp);
        }
    }

    /* @Override
    public boolean validateOtp(String userId, String otpCode) {
        // LocalDateTime thresholdTime = LocalDateTime.now().minus(5, ChronoUnit.MINUTES);
        
        LocalDateTime now = LocalDateTime.now();
        // Check for expired OTPs and delete them
       otpRepository.deleteByExpirationTimeBefore(now);

        OTP otp = otpRepository.findByUserIdAndUsedFalse(userId);
        if (otp != null && otp.getOtpCode().equals(otpCode) && 
            otp.getExpirationTime().isAfter(now)) {
            otp.setUsed(true);
            otpRepository.save(otp);
            return true;
        }
        return false;
    } */

    @Override
    public OtpValidationResult validateOtp(String userId, String otpCode) {
        LocalDateTime now = LocalDateTime.now();

        // Optional: clean up expired OTPs
        otpRepository.deleteByExpirationTimeBefore(now);

        OTP otp = otpRepository.findByUserIdAndUsedFalse(userId);
        if (otp == null) {
            return new OtpValidationResult(false, "No active OTP found or it has already been used.");
        }

        if (!otp.getOtpCode().equals(otpCode)) {
            return new OtpValidationResult(false, "Invalid OTP code.");
        }

        if (otp.getExpirationTime().isBefore(now)) {
            return new OtpValidationResult(false, "OTP has expired. Please request a new one.");
        }

        otp.setUsed(true);
        otpRepository.save(otp);
        return new OtpValidationResult(true, "OTP verified successfully.");
    }

    @Override
    public void deleteOtpByUserId(String userId) {
        otpRepository.deleteByUserId(userId);
    }

    @Override
    public void resendOtp(String userId) {
        String newOTP = generateOtp();
        saveOtp(userId, newOTP);
    }
}
