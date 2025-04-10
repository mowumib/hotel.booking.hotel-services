package com.hotel.booking.user_services.serviceImpl;


import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hotel.booking.user_services.dto.GenericResponseModel;
import com.hotel.booking.user_services.dto.ResponseModel;
import com.hotel.booking.user_services.entity.Booking;
import com.hotel.booking.user_services.entity.Hotel;
import com.hotel.booking.user_services.entity.Room;
import com.hotel.booking.user_services.entity.User;
import com.hotel.booking.user_services.enums.BookingStatus;
import com.hotel.booking.user_services.enums.PaymentStatus;
import com.hotel.booking.user_services.enums.Status;
import com.hotel.booking.user_services.exception.HotelRequestException;
import com.hotel.booking.user_services.exception.Message;
import com.hotel.booking.user_services.feignClient.HotelService;
import com.hotel.booking.user_services.repository.BookingRepository;
import com.hotel.booking.user_services.repository.UserRepository;
import com.hotel.booking.user_services.service.BookingService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final UserRepository userRepository;

    private final RestTemplate restTemplate;

    private final HotelService hotelService;

    private final BookingRepository bookingRepository;

    private static final Logger logger = LoggerFactory.getLogger(BookingServiceImpl.class);


    @Override
    public ResponseModel bookRoom(String userCode, String hotelCode) {
        User user = userRepository.findByUserCode(userCode).orElseThrow(() -> new HotelRequestException(String.format(Message.NOT_FOUND, "User"), HttpStatus.NOT_FOUND));

        Hotel hotels = hotelService.getHotelByHotelCode(hotelCode);
        if(hotels == null){
            throw new HotelRequestException(String.format(Message.NOT_FOUND, "Hotel"), HttpStatus.NOT_FOUND);
        }

        List<Room> rooms = hotels.getRooms();
        Room room = rooms.stream().filter(r -> r.getStatus().equals(Status.AVAILABLE)).findFirst().orElseThrow(() -> new HotelRequestException("Room not found", HttpStatus.NOT_FOUND));
        log.info("Room: {}",room);

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setHotelCode(hotelCode);
        booking.setRoomCode(room.getRoomCode());
        booking.setBookingCode("BOOKING-" + System.currentTimeMillis());
        booking.setAmount(room.getRoomPrice());
        booking.setPaymentStatus(PaymentStatus.UNPAID);
        booking.setBookingDate(LocalDate.now());
        booking.setCheckOutDate(LocalDate.now().plusDays(1));
        booking.setBookingStatus(BookingStatus.BOOKED);
        room.setStatus(Status.BOOKED);



        log.info("Booking: line 66 {} ",booking);
        String isBookingAdded = restTemplate.postForObject("http://HOTELS-SERVICE/hotels/addBooking/"+hotelCode+"/"+userCode,booking,String.class);

        log.info("Boolean isBookingAdded {} ", isBookingAdded);

        if(isBookingAdded.equals("BOOKED")){
            log.info("Booking added");
        }else{
            throw new HotelRequestException("Booking not added", HttpStatus.BAD_REQUEST);

        }
        bookingRepository.save(booking);


        return new ResponseModel(HttpStatus.OK.value(), String.format(Message.SUCCESS_GET, "Booking"), booking);

    }

    @Override
    public ResponseModel cancelBooking(String bookingCode) {
        Booking booking = bookingRepository.findById(bookingCode).orElseThrow( () -> new HotelRequestException(String.format(Message.NOT_FOUND, "Booking"), HttpStatus.NOT_FOUND));
        log.info("Booking: {}",booking);


        String cancelBooking = restTemplate.getForObject("http://HOTELS-SERVICE/hotels/cancelBooking/"+bookingCode,String.class);
        log.info("Boolean 99 cancelBooking {} ", cancelBooking);
        if(cancelBooking.equals("CANCELLED")){
            booking.setBookingStatus(BookingStatus.CANCELLED);
            log.info("Booking cancelled");
        }else {
            throw new HotelRequestException(String.format(Message.NOT_CANCELLED,"Booking"), HttpStatus.BAD_REQUEST);
        }
        bookingRepository.save(booking);
        return new ResponseModel(HttpStatus.OK.value(), String.format(Message.SUCCESS_CANCELLED, "Booking"), null);
    }
    
    @Override
    public ResponseModel getAllBookings(String userCode) {
    User user = userRepository.findByUserCode(userCode).orElseThrow(
        () -> new HotelRequestException(String.format(Message.NOT_FOUND, "User"), HttpStatus.NOT_FOUND));;
    
        List<Booking> bookings = bookingRepository.findByUser(user);
    
        return new ResponseModel(HttpStatus.OK.value(), 
            String.format(Message.SUCCESS_GET, "Bookings"), bookings);
    }

    @Override
    public ResponseModel getBookingByCode(String bookingCode) {
        Booking booking = bookingRepository.findByBookingCode(bookingCode).orElseThrow(
            () -> new HotelRequestException(String.format(Message.NOT_FOUND, "Booking"), HttpStatus.NOT_FOUND));

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
    public ResponseModel getHotelByLocation(String location) {
        GenericResponseModel<List<Hotel>>  hotels = hotelService.getHotelByLocation(location);
        // List<Hotel> hotels = restTemplate.getForObject("http://hotel-services/hotels/get-hotel-by-location/"+location,List.class);
        if (hotels == null) {
            throw new HotelRequestException(String.format(Message.NOT_FOUND, "Hotels"), HttpStatus.NOT_FOUND);
        }
        return new ResponseModel(HttpStatus.OK.value(), String.format(Message.SUCCESS_GET, "Hotels"), hotels);
    }

    @Override
    public ResponseModel getHotelByName(String name) {
        GenericResponseModel<Hotel>  response = hotelService.getHotelByName(name);
        // restTemplate.getForObject("http://hotel-services/hotels/get-hotel-by-name/" + name, HotelResponseModel.class);

        // logger.info("Hotels: {}", response);
        Hotel hotel = response.getData();

        if (hotel == null) {
           throw new HotelRequestException(String.format(Message.NOT_FOUND, "Hotel"), HttpStatus.NOT_FOUND);
        }
        return new ResponseModel(HttpStatus.OK.value(), String.format(Message.SUCCESS_GET, "Hotel"), hotel);
    }

    @Override
    public ResponseModel getAllHotels() {
        GenericResponseModel<List<Hotel>> hotels = hotelService.getAllHotels();
        // restTemplate.getForObject("http://hotel-services/hotels/get-all-hotels",List.class); 


        logger.info("Hotels: {}",hotels);

        if (hotels == null) {
            throw new HotelRequestException(String.format(Message.NOT_FOUND, "Hotels"), HttpStatus.NOT_FOUND);
        }
        return new ResponseModel(HttpStatus.OK.value(), String.format(Message.SUCCESS_GET, "Hotels"), hotels);
    }


}
