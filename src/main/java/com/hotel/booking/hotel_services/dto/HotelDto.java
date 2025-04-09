package com.hotel.booking.hotel_services.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class HotelDto {
    @NotEmpty(message = "Hotel name cannot be empty")
    private String name;

    @NotEmpty(message = "Hotel location cannot be empty")
    private String location;
}
