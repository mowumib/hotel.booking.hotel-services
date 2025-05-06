package com.hotel.booking.user_services.serviceImpl;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.hotel.booking.user_services.entity.otp.OTP;
import com.hotel.booking.user_services.repository.OtpRepository;
import com.hotel.booking.user_services.service.OtpService;

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

    @Override
    public boolean validateOtp(String userId, String otpCode) {
        LocalDateTime thresholdTime = LocalDateTime.now().minus(5, ChronoUnit.MINUTES);
        // Check for expired OTPs and delete them
       otpRepository.deleteByExpirationTimeBefore(thresholdTime);

        OTP otp = otpRepository.findByUserIdAndUsedFalse(userId);
        if (otp != null && otp.getOtpCode().equals(otpCode)) {
            otp.setUsed(true);
            otpRepository.save(otp);
            return true;
        }
        return false;
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
