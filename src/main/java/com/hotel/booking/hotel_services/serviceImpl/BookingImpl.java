package com.hotel.booking.hotel_services.serviceImpl;


import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.hotel.booking.hotel_services.dto.BookingDto;
import com.hotel.booking.hotel_services.dto.ResponseModel;
import com.hotel.booking.hotel_services.entity.Booking;
import com.hotel.booking.hotel_services.entity.Hotel;
import com.hotel.booking.hotel_services.entity.Room;
import com.hotel.booking.hotel_services.enums.Status;
import com.hotel.booking.hotel_services.exception.HotelRequestException;
import com.hotel.booking.hotel_services.exception.Message;
import com.hotel.booking.hotel_services.repository.BookingRepository;
import com.hotel.booking.hotel_services.repository.HotelRepository;
import com.hotel.booking.hotel_services.repository.RoomRepository;
import com.hotel.booking.hotel_services.service.BookingService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookingImpl implements BookingService {

    private final HotelRepository hotelRepository;

    private final BookingRepository bookingRepository;


    private final RoomRepository roomRepository;

    @Override
    public ResponseModel bookRoom(String hotelCode, String userCode, BookingDto dto) {

        Hotel hotel = hotelRepository.findByHotelCode(hotelCode).orElseThrow( () -> new HotelRequestException(String.format(Message.NOT_FOUND, "Hotel"), HttpStatus.NOT_FOUND));

        Room room = roomRepository.findByRoomCode(dto.getRoomCode()).orElseThrow( () -> new HotelRequestException(String.format(Message.NOT_FOUND, "Room"), HttpStatus.NOT_FOUND));

        if(room.getStatus().equals(Status.BOOKED)) {
            return  new ResponseModel(HttpStatus.BAD_REQUEST.value(), String.format(Message.ALREADY_BOOKED, "Room"));
        }

        Booking booking = new Booking();
        booking.setHotel(hotel);
        booking.setRoomCode(dto.getRoomCode());
        booking.setBookingCode("BOOK-" + UUID.randomUUID().toString().replace("-", "").substring(0, 8));
        booking.setAmount(dto.getAmount());
        booking.setPaymentStatus(dto.getPaymentStatus());
        booking.setBookingDate(dto.getBookingDate());
        booking.setCheckOutDate(dto.getCheckOutDate());
        booking.setUserCode(userCode);

        room.setStatus(Status.BOOKED);
        bookingRepository.save(booking);
        return new ResponseModel(HttpStatus.OK.value(), String.format(Message.SUCCESS_BOOKED, "Room"), booking);

    }

    @Override
    public ResponseModel getBookingByCode(String bookingCode) {
        Booking booking = bookingRepository.findByBookingCode(bookingCode).orElseThrow( () -> new HotelRequestException(String.format(Message.NOT_FOUND, "Booking"), HttpStatus.NOT_FOUND));
        return new ResponseModel(HttpStatus.OK.value(), String.format(Message.SUCCESS_GET, "Booking"), booking);
    }

    @Override
    public ResponseModel getAllBooking() {

        List<Booking> booking = bookingRepository.findAll();
        return new ResponseModel(HttpStatus.OK.value(), String.format(Message.SUCCESS_GET, "Booking"), booking);
    }

    @Override
    public ResponseModel cancelBooking(String bookingCode) {
        Booking booking = bookingRepository.findByBookingCode(bookingCode).orElseThrow( () -> new HotelRequestException(String.format(Message.NOT_FOUND, "Booking"), HttpStatus.NOT_FOUND));

        Room room = roomRepository.findByRoomCode(booking.getRoomCode()).orElseThrow( () -> new HotelRequestException(String.format(Message.NOT_FOUND, "Room"), HttpStatus.NOT_FOUND));

        if (room.getStatus().equals(Status.AVAILABLE)) {
            return new ResponseModel(HttpStatus.OK.value(), String.format(Message.ALREADY_AVAILABLE, "Room"));
        }
        room.setStatus(Status.AVAILABLE);
        roomRepository.save(room);
        return new ResponseModel(HttpStatus.OK.value(), String.format(Message.SUCCESS_CANCELLED, "Booking"), booking);
    }

    @Override
    public ResponseModel completeBooking(String bookingCode) {
        Booking booking = bookingRepository.findById(bookingCode).orElseThrow( () -> new HotelRequestException(String.format(Message.NOT_FOUND, "Booking"), HttpStatus.NOT_FOUND));

        Room room = roomRepository.findById(booking.getRoomCode()).orElseThrow( () -> new HotelRequestException(String.format(Message.NOT_FOUND, "Room"), HttpStatus.NOT_FOUND));

        room.setStatus(Status.AVAILABLE);
        roomRepository.save(room);
        return new ResponseModel(HttpStatus.OK.value(), String.format(Message.SUCCESS_BOOKING, "Room"), room);
    }


}
