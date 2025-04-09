package com.hotel.booking.hotel_services.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class RoomDto {
    @NotEmpty(message = "Room type cannot be empty")
    private String roomType;

    @NotEmpty(message = "Room number cannot be empty")
    private String roomNumber;

    @NotEmpty(message = "Room price cannot be empty")
    private Integer roomPrice;

    @NotEmpty(message = "Room description cannot be empty")
    private String roomDescription;

}
