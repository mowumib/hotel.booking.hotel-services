package com.hotel.booking.hotel_services.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hotel.booking.hotel_services.entity.Hotel;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, String> {
    public List<Hotel> findByLocation(String location);
    public Optional<Hotel> findByName(String name);
    public Optional<Hotel> findByHotelCode(String hotelCode);
}
