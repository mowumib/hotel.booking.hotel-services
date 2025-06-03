package com.hotel.booking.hotel_services.serviceImpl;


import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.hotel.booking.hotel_services.dto.HotelDto;
import com.hotel.booking.hotel_services.dto.ResponseModel;
import com.hotel.booking.hotel_services.entity.Hotel;
import com.hotel.booking.hotel_services.exception.GlobalRequestException;
import com.hotel.booking.hotel_services.exception.Message;
import com.hotel.booking.hotel_services.repository.HotelRepository;
import com.hotel.booking.hotel_services.service.HotelService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;

    private static final Logger logger = LoggerFactory.getLogger(HotelServiceImpl.class);
        
    @Override
    public ResponseModel addHotel(HotelDto dto) {
        boolean hotelExists = hotelRepository.existsByNameIgnoreCase(dto.getName());
        if (hotelExists) {
            throw new GlobalRequestException(
                String.format(Message.ALREADY_EXISTS, "Hotel"),
                HttpStatus.CONFLICT
            );
        }

        ModelMapper modelMapper = new ModelMapper();
        Hotel newHotel = modelMapper.map(dto, Hotel.class);
            // modelMapper.getConfiguration().setSkipNullEnabled(true);modelMapper.getConfiguration().setSkipNullEnabled(true);
            newHotel.setHotelCode("HOTEL-" + UUID.randomUUID().toString().replace("-", "").substring(0, 8));
        hotelRepository.save(newHotel);

        return new ResponseModel(HttpStatus.OK.value(), String.format(Message.SUCCESS_CREATE, "Hotel"), newHotel);
    }

    @Override
    public ResponseModel getHotelByHotelCode(String hotelCode) {
        Hotel hotel =hotelRepository.findByHotelCode(hotelCode).orElseThrow( () -> new GlobalRequestException(String.format(Message.NOT_FOUND, "Hotel"), HttpStatus.NOT_FOUND));
        return new ResponseModel(HttpStatus.OK.value(), String.format(Message.SUCCESS_GET, "Hotel"), hotel);
    }

    
    @Override
    public ResponseModel getAllHotels() {
        List<Hotel> hotels = hotelRepository.findAll();
        return new ResponseModel(HttpStatus.OK.value(), String.format(Message.SUCCESS_GET, "Hotels"), hotels);  
    }

    @Override
    public ResponseModel deleteHotelByHotelCode(String hotelCode) {
        Hotel hotel = hotelRepository.findByHotelCode(hotelCode).orElseThrow( 
            () -> new GlobalRequestException(String.format(Message.NOT_FOUND, "Hotel"), HttpStatus.NOT_FOUND));

        hotelRepository.delete(hotel);
        return new ResponseModel(HttpStatus.OK.value(), String.format(Message.SUCCESS_DELETE, "Hotel"), null);
    }
    
    @Override
    public ResponseModel getHotelByLocation(String location) {
        List<Hotel> hotels = hotelRepository.findByLocation(location);
        if (hotels.isEmpty()) {
            throw new GlobalRequestException(String.format(Message.NOT_FOUND, "Hotels"), HttpStatus.NOT_FOUND);
        }
        return new ResponseModel(HttpStatus.OK.value(), String.format(Message.SUCCESS_GET, "Hotels"), hotels);
    }

    
    @Override
    public ResponseModel getHotelByName(String name) {
        Hotel hotel = hotelRepository.findByName(name).orElseThrow(() ->
        new GlobalRequestException(String.format(Message.NOT_FOUND, "Hotel"), HttpStatus.NOT_FOUND));
        logger.info("Fetched hotel: {}", hotel);
        return new ResponseModel(HttpStatus.OK.value(), String.format(Message.SUCCESS_GET, "Hotel"), hotel);
    }
}










