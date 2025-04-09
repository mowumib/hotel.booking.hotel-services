package com.hotel.booking.user_services.service;

import org.springframework.web.bind.annotation.RequestBody;

import com.hotel.booking.user_services.dto.BaseResponseDto;
import com.hotel.booking.user_services.dto.CreateUserDto;
import com.hotel.booking.user_services.dto.ResponseModel;
import com.hotel.booking.user_services.dto.SignInDto;


public interface UserService {
    public ResponseModel createClientUser(@RequestBody CreateUserDto dto);

    public ResponseModel createAdminUser(@RequestBody CreateUserDto dto);

    public BaseResponseDto signIn(@RequestBody SignInDto dto);
}
