package com.hotel.booking.hotel_services.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hotel.booking.hotel_services.entity.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, String> {

    Optional<Booking> findByBookingCode(String bookingCode);

}
