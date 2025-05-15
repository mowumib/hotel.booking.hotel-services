package com.hotel.booking.hotel_services.serviceImpl;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.hotel.booking.hotel_services.dto.HotelDto;
import com.hotel.booking.hotel_services.dto.ResponseModel;
import com.hotel.booking.hotel_services.dto.RoomDto;
import com.hotel.booking.hotel_services.entity.Hotel;
import com.hotel.booking.hotel_services.entity.Room;
import com.hotel.booking.hotel_services.enums.Status;
import com.hotel.booking.hotel_services.exception.GlobalRequestException;
import com.hotel.booking.hotel_services.exception.Message;
import com.hotel.booking.hotel_services.repository.HotelRepository;
import com.hotel.booking.hotel_services.repository.RoomRepository;
import com.hotel.booking.hotel_services.service.HotelService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;

    private final RoomRepository roomRepository;

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
    public ResponseModel addRoom(String hotelCode, RoomDto dto) {
        Hotel hotel = hotelRepository.findByHotelCode(hotelCode).orElseThrow( () -> new GlobalRequestException(String.format(Message.NOT_FOUND, "Hotel"), HttpStatus.NOT_FOUND));

        boolean roomExists = hotel.getRooms().stream()
            .anyMatch(room -> room.getRoomNumber().equalsIgnoreCase(dto.getRoomNumber()));

        if (roomExists) {
            throw new GlobalRequestException(String.format(Message.ALREADY_EXISTS, "Room"), HttpStatus.CONFLICT);
        }

        ModelMapper modelMapper = new ModelMapper();
        log.info("Hotel: " + hotel);
        Room room = modelMapper.map(dto, Room.class);

        room.setRoomCode("ROOM-" + UUID.randomUUID().toString().replace("-", "").substring(0, 8));
        // hotel.getRooms().add(room);
        room.setHotelCode(hotel.getHotelCode());
        room.setStatus(Status.AVAILABLE);
        // hotelRepository.save(hotel);
        roomRepository.save(room);
        return new ResponseModel(HttpStatus.OK.value(), String.format(Message.SUCCESS_CREATE, "Room"), room);
    }

    
    @Override
    public ResponseModel getRoomByRoomCode(String roomCode) {
        Room room = roomRepository.findByRoomCode(roomCode).orElseThrow(
            () -> new GlobalRequestException(String.format(Message.NOT_FOUND, "Room"), HttpStatus.NOT_FOUND));

        return new ResponseModel(HttpStatus.OK.value(), String.format(Message.SUCCESS_GET, "Room"), room);
    }

    @Override
    public ResponseModel getAllAvailableRoom(String hotelCode) {
        Hotel hotel = hotelRepository.findByHotelCode(hotelCode).orElseThrow(
            () -> new GlobalRequestException(String.format(Message.NOT_FOUND, "Hotel"), HttpStatus.NOT_FOUND));
        List<Room> rooms = hotel.getRooms();
        List<Room> availableRooms = new ArrayList<>();
        for(Room room: rooms) {
            if(room == null) {
                continue;
            }
            if(room.getStatus().equals(Status.AVAILABLE)) {
                availableRooms.add(room);
            }
        }
        return new ResponseModel(HttpStatus.OK.value(), String.format(Message.SUCCESS_GET, "Rooms"), availableRooms);
    }

    @Override
    public ResponseModel getAllBookedRoom(String hotelCode) {
        Hotel hotel = hotelRepository.findByHotelCode(hotelCode).orElseThrow(
            () -> new GlobalRequestException(String.format(Message.NOT_FOUND, "Hotel"), HttpStatus.NOT_FOUND));
        

        List<Room> rooms = hotel.getRooms();
        // rooms.stream().filter(r -> r.getStatus().equals(Status.BOOKED)).toList();
        List<Room> bookedRooms = new ArrayList<>();
        for(Room room: rooms) {
            if(room == null) {
                continue;
            }
            if(room.getStatus().equals(Status.BOOKED)) {
                bookedRooms.add(room);
            }
        }
        return new ResponseModel(HttpStatus.OK.value(), String.format(Message.SUCCESS_GET, "Rooms"), bookedRooms);
    }


    
    @Override
    public ResponseModel getAllRooms(String hotelCode) {
        Hotel hotel = hotelRepository.findByHotelCode(hotelCode).orElseThrow(
            () -> new GlobalRequestException(String.format(Message.NOT_FOUND, "Hotel"), HttpStatus.NOT_FOUND));

        List<Room> rooms = hotel.getRooms();
        return new ResponseModel(HttpStatus.OK.value(), String.format(Message.SUCCESS_GET, "Rooms"), rooms);
    }

    
    @Override
    public ResponseModel deleteRoomByRoomCode(String roomCode) {
        Room room = roomRepository.findByRoomCode(roomCode).orElseThrow(
            () -> new GlobalRequestException(String.format(Message.NOT_FOUND, "Room"), HttpStatus.NOT_FOUND));
        roomRepository.delete(room);
        return new ResponseModel(HttpStatus.OK.value(), String.format(Message.SUCCESS_DELETE, "Room"), null);
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










