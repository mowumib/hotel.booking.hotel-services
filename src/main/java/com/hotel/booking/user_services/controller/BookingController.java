package com.hotel.booking.user_services.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hotel.booking.user_services.dto.ResponseModel;
import com.hotel.booking.user_services.service.BookingService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
@Validated
@RequestMapping("/hotel/bookings")
public class BookingController {

    private final BookingService bookingService;

    @PostMapping("/addBooking")
    public ResponseEntity<ResponseModel> bookRoom(@RequestParam String hotelCode, @RequestParam String userCode){
        ResponseModel responseModel = bookingService.bookRoom(userCode, hotelCode);
        return ResponseEntity.status(responseModel.getStatusCode()).body(responseModel);
    }

    @GetMapping("/getBooking")
    public ResponseEntity<ResponseModel> getBookingByCode(@RequestParam String bookingCode){
        ResponseModel responseModel = bookingService.getBookingByCode(bookingCode);
        return ResponseEntity.status(responseModel.getStatusCode()).body(responseModel);

    }


    // @PostMapping("/completeBooking/{bookingCode}")
    // public ResponseEntity<Booking> completeBookingHandler(@RequestParam String bookingCode){
    //     return ResponseEntity.ok(bookingService.completeBooking(bookingCode));
    // }


    @DeleteMapping("/cancelBooking")
    public ResponseEntity<ResponseModel> cancelBooking(@RequestParam String bookingCode){
        ResponseModel responseModel = bookingService.cancelBooking(bookingCode);
        return ResponseEntity.status(responseModel.getStatusCode()).body(responseModel);
    }

    @GetMapping("/getAllBookings")
    public ResponseEntity<ResponseModel> getAllBookings(@RequestParam String userCode){
        ResponseModel responseModel = bookingService.getAllBookings(userCode);
        return ResponseEntity.status(responseModel.getStatusCode()).body(responseModel);
    }

    @GetMapping("/getAllBookingsByHotelCode")
    public ResponseEntity<ResponseModel> getAllBookingsByHotelCode(@RequestParam String hotelCode){
        ResponseModel responseModel = bookingService.getAllBookingsByHotelCode(hotelCode);
        return ResponseEntity.status(responseModel.getStatusCode()).body(responseModel);
    }

    @GetMapping("/getHotelByLocation")
    public ResponseEntity<ResponseModel> getHotelByLocation(@RequestParam String location){
        ResponseModel responseModel = bookingService.getHotelByLocation(location);
        return ResponseEntity.status(responseModel.getStatusCode()).body(responseModel);
    }

    @GetMapping("/getHotelByName")
    public ResponseEntity<ResponseModel> getHotelByName(@RequestParam String name){
        ResponseModel responseModel = bookingService.getHotelByName(name);
        return ResponseEntity.status(responseModel.getStatusCode()).body(responseModel);
    }

    @GetMapping("/getAllHotels")
    public ResponseEntity<ResponseModel> getAllHotels(){
        ResponseModel responseModel = bookingService.getAllHotels();
        return ResponseEntity.status(responseModel.getStatusCode()).body(responseModel);
    }
}
