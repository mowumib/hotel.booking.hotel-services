package com.hotel.booking.hotel_services.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hotel.booking.hotel_services.entity.Room;



public interface RoomRepository extends JpaRepository<Room, String> {

    Optional<Room> findByRoomCode(String roomCode);

    // Optional<Room> findByHotelCode(String hotelCode);
}
