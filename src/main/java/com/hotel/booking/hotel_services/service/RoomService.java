package com.hotel.booking.hotel_services.service;

import com.hotel.booking.hotel_services.dto.ResponseModel;
import com.hotel.booking.hotel_services.dto.RoomDto;

public interface RoomService {

    public ResponseModel addRoom(String hotelCode, RoomDto dto);

    public ResponseModel getRoomByRoomCode(String roomCode);

    public ResponseModel getAllAvailableRoom(String hotelCode);

    public ResponseModel getAllBookedRoom(String hotelCode);

    public ResponseModel getAllRooms(String hotelCode);

    public ResponseModel deleteRoomByRoomCode(String roomCode);
}
