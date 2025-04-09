package com.hotel.booking.user_services.service;

import com.hotel.booking.user_services.dto.ResponseModel;

public interface BookingService {
    public ResponseModel bookRoom(String userCode, String hotelCode);

    public ResponseModel cancelBooking(String bookingCode);

    public ResponseModel getAllBookings(String userCode);

    public ResponseModel getBookingByCode(String bookingCode);

    public ResponseModel getAllBookingsByHotelCode(String hotelCode);

    public ResponseModel getHotelByLocation(String location);

    public ResponseModel getHotelByName(String name);

    public ResponseModel getAllHotels();

    // public Booking completeBooking(String bookingCode);

}
