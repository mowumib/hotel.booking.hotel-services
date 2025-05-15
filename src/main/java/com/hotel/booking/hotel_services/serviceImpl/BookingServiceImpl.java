package com.hotel.booking.hotel_services.serviceImpl;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.hotel.booking.hotel_services.dto.BookingDto;
import com.hotel.booking.hotel_services.dto.ResponseModel;
import com.hotel.booking.hotel_services.entity.Booking;
import com.hotel.booking.hotel_services.entity.Hotel;
import com.hotel.booking.hotel_services.entity.Room;
import com.hotel.booking.hotel_services.entity.User;
import com.hotel.booking.hotel_services.enums.BookingStatus;
import com.hotel.booking.hotel_services.enums.PaymentStatus;
import com.hotel.booking.hotel_services.enums.Status;
import com.hotel.booking.hotel_services.exception.GlobalRequestException;
import com.hotel.booking.hotel_services.exception.Message;
import com.hotel.booking.hotel_services.paystack.dto.PaystackPaymentDto;
import com.hotel.booking.hotel_services.paystack.service.PaystackService;
import com.hotel.booking.hotel_services.repository.BookingRepository;
import com.hotel.booking.hotel_services.repository.HotelRepository;
import com.hotel.booking.hotel_services.repository.RoomRepository;
import com.hotel.booking.hotel_services.repository.UserRepository;
import com.hotel.booking.hotel_services.service.BookingService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final UserRepository userRepository;

    private final BookingRepository bookingRepository;

    private final RoomRepository roomRepository;

    private final HotelRepository hotelRepository;

    private static final Logger logger = LoggerFactory.getLogger(BookingServiceImpl.class);

    private final PaystackService paystackService;
    @Override
    public ResponseModel bookRoom(String hotelCode, String userCode, BookingDto dto) {

        Hotel hotel = hotelRepository.findByHotelCode(hotelCode).orElseThrow( () -> new GlobalRequestException(String.format(Message.NOT_FOUND, "Hotel"), HttpStatus.NOT_FOUND));

        User user = userRepository.findByUserCode(userCode).orElseThrow( () -> new GlobalRequestException(String.format(Message.NOT_FOUND, "User"), HttpStatus.NOT_FOUND));
        // Filter available rooms
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

        if (availableRooms.isEmpty()) {
            throw new GlobalRequestException("No available rooms in this hotel.", HttpStatus.BAD_REQUEST);
        }

        Room selectedRoom = availableRooms.get(0);    
        logger.info("Room: {}",selectedRoom);

        Booking booking = new Booking();
        booking.setHotelCode(hotel.getHotelCode());
        booking.setRoom(selectedRoom);
        booking.setBookingCode("BOOKING-" + UUID.randomUUID().toString().replace("-", "").substring(0, 8));
        booking.setAmount(selectedRoom.getRoomPrice());
        booking.setPaymentStatus(PaymentStatus.UNPAID);
        booking.setBookingDate(LocalDate.now());
        booking.setCheckOutDate(LocalDate.now().plusDays(1));
        booking.setBookingStatus(BookingStatus.BOOKED);
        booking.setUserCode(userCode);
        booking.setHotel(hotel);
        booking.setUser(user);
        selectedRoom.setStatus(Status.BOOKED);

        bookingRepository.save(booking);
        roomRepository.save(selectedRoom);
        return new ResponseModel(HttpStatus.OK.value(), String.format(Message.SUCCESS_BOOKED, "Room"), booking);

    }

    @Override
    public ResponseModel cancelBooking(String bookingCode) {
        Booking booking = bookingRepository.findByBookingCode(bookingCode)
            .orElseThrow(() -> new GlobalRequestException(String.format(Message.NOT_FOUND, "Booking"), HttpStatus.NOT_FOUND));

        Room room = roomRepository.findByRoomCode(booking.getRoom().getRoomCode())
            .orElseThrow(() -> new GlobalRequestException(String.format(Message.NOT_FOUND, "Room"), HttpStatus.NOT_FOUND));

        if (room.getStatus().equals(Status.AVAILABLE)) {
            return new ResponseModel(HttpStatus.OK.value(), String.format(Message.ALREADY_AVAILABLE, "Room"));
        }

        room.setStatus(Status.AVAILABLE);
        booking.setBookingStatus(BookingStatus.CANCELLED);

        roomRepository.save(room);
        bookingRepository.delete(booking);

        return new ResponseModel(HttpStatus.OK.value(), String.format(Message.SUCCESS_CANCELLED, "Booking"), booking);

    }
    
    @Override
    public ResponseModel getAllBookingsByUserCode(String userCode) {
    User user = userRepository.findByUserCode(userCode).orElseThrow(
        () -> new GlobalRequestException(String.format(Message.NOT_FOUND, "User"), HttpStatus.NOT_FOUND));;
    
        List<Booking> bookings = bookingRepository.findByUserCode(user.getUserCode());
    
        return new ResponseModel(HttpStatus.OK.value(), 
            String.format(Message.SUCCESS_GET, "Bookings"), bookings);
    }

    @Override
    public ResponseModel getBookingByCode(String bookingCode) {
        Booking booking = bookingRepository.findByBookingCode(bookingCode).orElseThrow(
            () -> new GlobalRequestException(String.format(Message.NOT_FOUND, "Booking"), HttpStatus.NOT_FOUND));

            return new ResponseModel(HttpStatus.OK.value(), 
            String.format(Message.SUCCESS_GET, "Booking"), booking);
    }

    @Override
    public ResponseModel getAllBookingsByHotelCode(String hotelCode) {
        List<Booking> bookings = bookingRepository.findByHotelCode(hotelCode);
        if (bookings.isEmpty()) {
            return new ResponseModel(HttpStatus.NOT_FOUND.value(), String.format(Message.NOT_FOUND, "Hotel"), null);
        }
        return new ResponseModel(HttpStatus.OK.value(), String.format(Message.SUCCESS_GET, "Bookings"), bookings);
    }

    @Override
    public ResponseModel getAllBooking() {

        List<Booking> bookings = bookingRepository.findAll();
        List<String> roomCodes = new ArrayList<>();
        for(Booking booking: bookings){
            roomCodes.add(booking.getRoom().getRoomCode());
        }
        return new ResponseModel(HttpStatus.OK.value(), String.format(Message.SUCCESS_GET, "Booking"), bookings);
    }

    @Override
    public ResponseModel completeBooking(String bookingCode) {
        Booking booking = bookingRepository.findByBookingCode(bookingCode).orElseThrow(
            () -> new GlobalRequestException(String.format(Message.NOT_FOUND, "Booking"), HttpStatus.NOT_FOUND));

        String userCode = booking.getUser().getUserCode();

        User user = userRepository.findByUserCode(userCode).orElseThrow(
            () -> new GlobalRequestException(String.format(Message.NOT_FOUND, "User"), HttpStatus.NOT_FOUND));

        PaystackPaymentDto paystackPaymentDto = new PaystackPaymentDto();
        paystackPaymentDto.setAmount(booking.getAmount());
        paystackPaymentDto.setEmail(user.getEmail());
        paystackPaymentDto.setBookingCode(booking.getBookingCode());
        paystackPaymentDto.setUserCode(user.getUserCode());
        

        ResponseModel response = paystackService.initializeTransaction(paystackPaymentDto);

        if (response.getStatusCode() >= 500) {
            return new ResponseModel(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getMessage(), null);
        } else if (response.getStatusCode() < 200 || response.getStatusCode() >= 300) {
            return new ResponseModel(HttpStatus.BAD_REQUEST.value(), response.getMessage(), response.getData());
        }

        // PaystackPaymentResponse paystackPaymentResponse = (PaystackPaymentResponse) response.getData();
        // booking.setPaymentReference(paystackPaymentResponse.getData().getReference());
        // booking.setPaymentStatus(PaymentStatus.PAID);
        // bookingRepository.save(booking);
        return new ResponseModel(HttpStatus.OK.value(), response.getMessage(), response.getData());
    }

}
