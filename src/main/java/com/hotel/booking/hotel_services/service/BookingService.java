package com.hotel.booking.hotel_services.service;

import com.hotel.booking.hotel_services.dto.BookingDto;
import com.hotel.booking.hotel_services.dto.ResponseModel;

public interface BookingService {
    public ResponseModel bookRoom(String hotelCode, String userCode, BookingDto dto);

    public ResponseModel getBookingByCode(String bookingCode);

    public ResponseModel getAllBooking();

    public ResponseModel cancelBooking(String bookingCode);

    public ResponseModel completeBooking(String bookingCode);

}
