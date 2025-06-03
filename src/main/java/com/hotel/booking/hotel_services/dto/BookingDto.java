package com.hotel.booking.hotel_services.dto;

import java.time.LocalDate;

import com.hotel.booking.hotel_services.enums.RoomType;

import lombok.Data;

@Data
public class BookingDto {
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private RoomType roomType;
}
