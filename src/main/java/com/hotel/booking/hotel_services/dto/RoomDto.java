package com.hotel.booking.hotel_services.dto;

import com.hotel.booking.hotel_services.enums.RoomType;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class RoomDto {
    @NotEmpty(message = "Room type cannot be empty")
    private RoomType roomType = RoomType.STANDARD;

    @NotEmpty(message = "Room number cannot be empty")
    private String roomNumber;

    private Integer roomPrice;

    @NotEmpty(message = "Room description cannot be empty")
    private String roomDescription;

}
