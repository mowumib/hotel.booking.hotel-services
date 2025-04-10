package com.hotel.booking.hotel_services.controller;

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
import com.hotel.booking.hotel_services.service.BookingService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/hotels/booking/")
@RequiredArgsConstructor
@Validated
@Tag(name = "BOOKING CONTROLLER REST APIS IN HOTEL SERVICE", description = "REST API FOR BOOKING HOTEL IN HOTEL SERVICE")
public class BookingController {


    private final BookingService bookingService;

    @Operation(
            summary = "Book a room by hotel and user code",
            description = "REST API for booking a room"
    )
    @PostMapping("/addBooking")
    public ResponseEntity<ResponseModel> addBooking(@RequestParam String hotelCode, @RequestParam String userCode, @Valid @RequestBody BookingDto dto) {
        ResponseModel responseModel = bookingService.bookRoom(hotelCode, userCode, dto);
        return ResponseEntity.status(responseModel.getStatusCode()).body(responseModel);
    }


    @Operation(
            summary = "Get booking",
            description = "REST API for getting a booking"
    )
    @GetMapping("/get-booking")
    public ResponseEntity<ResponseModel> getBookingByCode(@RequestParam String bookingCode) {
        ResponseModel responseModel = bookingService.getBookingByCode(bookingCode);
        return ResponseEntity.status(responseModel.getStatusCode()).body(responseModel);
    }

    @Operation(
            summary = "Get all booking",
            description = "REST API for getting all booking"
    )
    @GetMapping("/get-all-booking")
    public ResponseEntity<ResponseModel> getAllBooking() {
        ResponseModel responseModel = bookingService.getAllBooking();
        return ResponseEntity.status(responseModel.getStatusCode()).body(responseModel);
    }


    @Operation(
            summary = "Cancel booking",
            description = "REST API for canceling a booking"
    )
    @GetMapping("/cancel-booking")
    public ResponseEntity<ResponseModel> cancelBooking(@RequestParam String bookingCode) {
        ResponseModel responseModel = bookingService.cancelBooking(bookingCode);
        return ResponseEntity.status(responseModel.getStatusCode()).body(responseModel);
    }

    @Operation(
            summary = "Complete booking",
            description = "REST API for completing a booking"
    )
    @GetMapping("/complete-booking")
    public ResponseEntity<ResponseModel> completeBooking(@RequestParam String bookingCode) {
        ResponseModel responseModel = bookingService.completeBooking(bookingCode);
        return ResponseEntity.status(responseModel.getStatusCode()).body(responseModel);
    }
}
