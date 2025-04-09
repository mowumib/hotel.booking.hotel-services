package com.hotel.booking.user_services.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hotel.booking.user_services.entity.Booking;
import com.hotel.booking.user_services.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, String> {
    // public List<Booking> findByHotelId(String hotelId);
    public List<Booking> findByUser(User user);
    public List<Booking> findByHotelCode(String hotelCode);
    public Optional<Booking> findByBookingCode(String bookingCode);

}
