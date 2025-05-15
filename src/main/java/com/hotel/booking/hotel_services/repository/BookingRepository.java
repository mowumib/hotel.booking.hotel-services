package com.hotel.booking.hotel_services.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hotel.booking.hotel_services.entity.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, String> {
    public List<Booking> findByUserCode(String userCode);
    public List<Booking> findByHotelCode(String hotelCode);
    public Optional<Booking> findByBookingCode(String bookingCode);

}
