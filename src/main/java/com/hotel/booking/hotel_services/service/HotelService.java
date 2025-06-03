package com.hotel.booking.hotel_services.service;

import com.hotel.booking.hotel_services.dto.HotelDto;
import com.hotel.booking.hotel_services.dto.ResponseModel;

public interface HotelService {
    public ResponseModel addHotel(HotelDto dto);

    public ResponseModel getHotelByHotelCode(String hotelCode);

    public ResponseModel getAllHotels();

    public ResponseModel deleteHotelByHotelCode(String hotelCode);

    public ResponseModel getHotelByLocation(String location);
    
    public ResponseModel getHotelByName(String name);

}
