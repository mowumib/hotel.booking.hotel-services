package com.hotel.booking.hotel_services.service;

import com.hotel.booking.hotel_services.dto.HotelDto;
import com.hotel.booking.hotel_services.dto.ResponseModel;
import com.hotel.booking.hotel_services.dto.RoomDto;

public interface HotelService {
    public ResponseModel addHotel(HotelDto dto);

    public ResponseModel getHotelByHotelCode(String hotelCode);

    public ResponseModel getAllHotels();

    public ResponseModel deleteHotelByHotelCode(String hotelCode);

    public ResponseModel addRoom(String hotelCode, RoomDto dto);

    public ResponseModel getRoomByRoomCode(String roomCode);

    public ResponseModel getAllAvailableRoom(String hotelCode);

    public ResponseModel getAllBookedRoom(String hotelCode);

    public ResponseModel getAllRooms(String hotelCode);

    public ResponseModel deleteRoomByRoomCode(String roomCode);

    public ResponseModel getHotelByLocation(String location);
    
    public ResponseModel getHotelByName(String name);

}
