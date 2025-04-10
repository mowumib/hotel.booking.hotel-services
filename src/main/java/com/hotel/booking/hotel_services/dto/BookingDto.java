package com.hotel.booking.hotel_services.dto;

import java.time.LocalDate;

import com.hotel.booking.hotel_services.entity.Room;
import com.hotel.booking.hotel_services.enums.PaymentStatus;

import lombok.Data;

@Data
public class BookingDto {
    // private String bookingCode;
    private String userCode;
    private Room room;
    private LocalDate bookingDate;
    private LocalDate checkOutDate;
    private Integer amount;
    private PaymentStatus paymentStatus;
}
