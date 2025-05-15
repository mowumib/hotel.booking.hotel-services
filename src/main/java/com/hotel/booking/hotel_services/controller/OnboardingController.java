package com.hotel.booking.hotel_services.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hotel.booking.hotel_services.dto.BaseResponseDto;
import com.hotel.booking.hotel_services.dto.CreateUserDto;
import com.hotel.booking.hotel_services.dto.ResponseModel;
import com.hotel.booking.hotel_services.dto.SignInDto;
import com.hotel.booking.hotel_services.dto.otp.OtpTokenValidatorDto;
import com.hotel.booking.hotel_services.dto.password.UpdatePasswordDto;
import com.hotel.booking.hotel_services.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/hotel")
@RequiredArgsConstructor
@Validated
@Tag(name = "ONBOARDING REST APIS FOR HOTEL MANAGEMENT IN USER SERVICE", description = "REST APIS FOR ONBOARDING USERS")
public class OnboardingController {

    private final UserService service;


    @Operation(
            summary = "Client User Onboarding REST API",
            description = "REST API for client users to signup"
    )
    @PostMapping("/user/signup")
    public ResponseEntity<ResponseModel> createClientUser(@Valid @RequestBody CreateUserDto dto) {
        ResponseModel responseModel = service.createClientUser(dto);
        return ResponseEntity.status(responseModel.getStatusCode()).body(responseModel);
    }
    

    @Operation(
            summary = "Admin User Onboarding REST API",
            description = "REST API for admin users to signup"
    )
    @PostMapping("/admin-user/signup")
    public ResponseEntity<ResponseModel> createAdminUser(@Valid @RequestBody CreateUserDto dto) {
        ResponseModel responseModel = service.createAdminUser(dto);
        return ResponseEntity.status(responseModel.getStatusCode()).body(responseModel);
    }

    @Operation(
            summary = "User Signin REST API",
            description = "REST API for users to signin"
    )
    @PostMapping("/user/signin")
    public ResponseEntity<BaseResponseDto> signIn(@Valid @RequestBody SignInDto dto) {
        BaseResponseDto responseModel = service.signIn(dto);
        return ResponseEntity.status(responseModel.getStatus()).body(responseModel);
    }


    @Operation(
            summary = "Verify OTP Code REST API",
            description = "REST API to verify OTP Code"
    )
    @PostMapping("/user/verify-otp-code")
    public ResponseEntity<BaseResponseDto> verifyOtpCode(@RequestBody OtpTokenValidatorDto dto) {
        BaseResponseDto responseModel = service.verifyOtpCode(dto);
        return ResponseEntity.status(responseModel.getStatus()).body(responseModel);
    }

    @Operation(
            summary = "Resend OTP Code REST API",
            description = "REST API to resend OTP Code"
    )
    @PostMapping("/user/resend-otp-code")
    public ResponseEntity<BaseResponseDto> resendOtpCode(@RequestParam String email) {
        BaseResponseDto responseModel = service.resendOtpCode(email);
        return ResponseEntity.status(responseModel.getStatus()).body(responseModel);
    }

    @Operation(
            summary = "Resend OTP Code REST API",
            description = "REST API to resend OTP Code"
    )
    @PostMapping("/user/update-password")
    public ResponseEntity<BaseResponseDto> updatePassword(@RequestBody UpdatePasswordDto dto) {
        BaseResponseDto responseModel = service.updatePassword(dto);
        return ResponseEntity.status(responseModel.getStatus()).body(responseModel);
    }
}
