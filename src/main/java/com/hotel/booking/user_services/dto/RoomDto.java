package com.hotel.booking.user_services.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class RoomDto {
    @NotEmpty(message = "Room type cannot be empty")
    private String roomType;

    @NotEmpty(message = "Room number cannot be empty")
    private String roomNumber;

    private Integer roomPrice;

    @NotEmpty(message = "Room description cannot be empty")
    private String roomDescription;

}
