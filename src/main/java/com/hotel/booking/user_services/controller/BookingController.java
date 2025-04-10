package com.hotel.booking.user_services.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hotel.booking.user_services.dto.ResponseModel;
import com.hotel.booking.user_services.service.BookingService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
@Validated
@RequestMapping("/hotel/bookings")
@Tag(name = "Booking Controller REST API IN USER SERVICE", description = "Booking APIs")
public class BookingController {

    private final BookingService bookingService;

    @Operation(
        summary = "Add booking",
        description = "REST API for adding booking"
    )
    @PostMapping("/addBooking")
    public ResponseEntity<ResponseModel> bookRoom(@RequestParam String hotelCode, @RequestParam String userCode){
        ResponseModel responseModel = bookingService.bookRoom(userCode, hotelCode);
        return ResponseEntity.status(responseModel.getStatusCode()).body(responseModel);
    }


    @Operation(
        summary = "Get booking by booking code",
        description = "REST API for getting booking by code"
    )
    @GetMapping("/getBooking")
    public ResponseEntity<ResponseModel> getBookingByCode(@RequestParam String bookingCode){
        ResponseModel responseModel = bookingService.getBookingByCode(bookingCode);
        return ResponseEntity.status(responseModel.getStatusCode()).body(responseModel);

    }


    // @PostMapping("/completeBooking/{bookingCode}")
    // public ResponseEntity<Booking> completeBookingHandler(@RequestParam String bookingCode){
    //     return ResponseEntity.ok(bookingService.completeBooking(bookingCode));
    // }


    @Operation(
        summary = "Cancel Booking",
        description = "REST API for canceling booking"
    )
    @DeleteMapping("/cancelBooking")
    public ResponseEntity<ResponseModel> cancelBooking(@RequestParam String bookingCode){
        ResponseModel responseModel = bookingService.cancelBooking(bookingCode);
        return ResponseEntity.status(responseModel.getStatusCode()).body(responseModel);
    }

    @Operation(
        summary = "Get all bookings",
        description = "REST API for getting all bookings"
    )
    @GetMapping("/getAllBookings")
    public ResponseEntity<ResponseModel> getAllBookings(@RequestParam String userCode){
        ResponseModel responseModel = bookingService.getAllBookings(userCode);
        return ResponseEntity.status(responseModel.getStatusCode()).body(responseModel);
    }

    @Operation(
        summary = "Get all bookings by hotel code",
        description = "REST API for getting all bookings by hotel code"
    )
    @GetMapping("/getAllBookingsByHotelCode")
    public ResponseEntity<ResponseModel> getAllBookingsByHotelCode(@RequestParam String hotelCode){
        ResponseModel responseModel = bookingService.getAllBookingsByHotelCode(hotelCode);
        return ResponseEntity.status(responseModel.getStatusCode()).body(responseModel);
    }

    @Operation(
        summary = "Get hotel by location",
        description = "REST API for getting all hotels by location"
    )
    @GetMapping("/getHotelByLocation")
    public ResponseEntity<ResponseModel> getHotelByLocation(@RequestParam String location){
        ResponseModel responseModel = bookingService.getHotelByLocation(location);
        return ResponseEntity.status(responseModel.getStatusCode()).body(responseModel);
    }

    
    @Operation(
        summary = "Get hotel by name",
        description = "REST API for getting hotel by name"
    )
    @GetMapping("/getHotelByName/{name}")
    public ResponseEntity<ResponseModel> getHotelByName(@PathVariable String name){
        ResponseModel responseModel = bookingService.getHotelByName(name);
        return ResponseEntity.status(responseModel.getStatusCode()).body(responseModel);
    }


    @Operation(
        summary = "Get all hotels",
        description = "REST API for getting all hotels"
    )
    @GetMapping("/getAllHotels")
    public ResponseEntity<ResponseModel> getAllHotels(){
        ResponseModel responseModel = bookingService.getAllHotels();
        return ResponseEntity.status(responseModel.getStatusCode()).body(responseModel);
    }
}
