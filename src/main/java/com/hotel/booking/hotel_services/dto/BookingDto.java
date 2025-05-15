package com.hotel.booking.hotel_services.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class BookingDto {
    private LocalDate bookingDate;
    private LocalDate checkOutDate;
}
