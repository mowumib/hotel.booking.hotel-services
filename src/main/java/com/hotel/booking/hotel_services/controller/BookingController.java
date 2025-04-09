package com.hotel.booking.hotel_services.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hotel.booking.hotel_services.dto.BookingDto;
import com.hotel.booking.hotel_services.dto.ResponseModel;
import com.hotel.booking.hotel_services.entity.Booking;
import com.hotel.booking.hotel_services.service.BookingService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/hotels/booking/")
@RequiredArgsConstructor
@Validated
@Tag(name = "BOOKING CONTROLLER REST APIS FOR HOTEL SERVICE ", description = "REST API FOR BOOKING HOTEL IN HOTEL SERVICE")
public class BookingController {


    private final BookingService bookingService;

    @PostMapping("/addBooking")
    public ResponseEntity<ResponseModel> addBooking(@RequestParam String hotelCode, @RequestParam String userCode, @RequestBody BookingDto dto) {
        ResponseModel responseModel = bookingService.bookRoom(hotelCode, userCode, dto);
        return ResponseEntity.status(responseModel.getStatusCode()).body(responseModel);
    }

    @GetMapping("/get-booking")
    public ResponseEntity<ResponseModel> getBookingByCode(@RequestParam String bookingCode) {
        ResponseModel responseModel = bookingService.getBookingByCode(bookingCode);
        return ResponseEntity.status(responseModel.getStatusCode()).body(responseModel);
    }

    @GetMapping("/get-all-booking")
    public ResponseEntity<ResponseModel> getAllBooking() {
        ResponseModel responseModel = bookingService.getAllBooking();
        return ResponseEntity.status(responseModel.getStatusCode()).body(responseModel);
    }

    @GetMapping("/cancel-booking")
    public ResponseEntity<ResponseModel> cancelBooking(@RequestParam String bookingCode) {
        ResponseModel responseModel = bookingService.cancelBooking(bookingCode);
        return ResponseEntity.status(responseModel.getStatusCode()).body(responseModel);
    }

    @GetMapping("/complete-booking")
    public ResponseEntity<ResponseModel> completeBooking(@RequestParam String bookingCode) {
        ResponseModel responseModel = bookingService.completeBooking(bookingCode);
        return ResponseEntity.status(responseModel.getStatusCode()).body(responseModel);
    }
}
