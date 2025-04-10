package com.hotel.booking.user_services.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotel.booking.user_services.dto.BaseResponseDto;
import com.hotel.booking.user_services.dto.CreateUserDto;
import com.hotel.booking.user_services.dto.ResponseModel;
import com.hotel.booking.user_services.dto.SignInDto;
import com.hotel.booking.user_services.service.UserService;

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

}
