package com.hotel.booking.user_services.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hotel.booking.user_services.dto.HotelDto;
import com.hotel.booking.user_services.dto.ResponseModel;
import com.hotel.booking.user_services.dto.RoomDto;
import com.hotel.booking.user_services.service.HotelService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/hotels")
@RequiredArgsConstructor
@Validated
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "HOTEL CONTROLLER REST APIS IN HOTEL SERVICE", description = "REST APIS IN HOTEL SERVICE")
public class HotelController {

    private final HotelService hotelService;

    
    @Operation(
        summary = "Add hotel",
        description = "Hotel APIs"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add-hotel")
    public ResponseEntity<ResponseModel> addHotel(@Valid @RequestBody HotelDto dto) {
        ResponseModel responseModel = hotelService.addHotel(dto);
        return ResponseEntity.status(responseModel.getStatusCode()).body(responseModel);
    }

    @PreAuthorize("hasRole('ADMIN' ) or hasRole('CLIENT')")
    @Operation(
        summary = "Get hotel by code",
        description = "REST API for getting a hotel"
    )
    @GetMapping("/get-hotel-by-code")
    public ResponseEntity<ResponseModel> getHotelByHotelCode(@RequestParam String hotelCode) {
        ResponseModel responseModel = hotelService.getHotelByHotelCode(hotelCode);
        return ResponseEntity.status(responseModel.getStatusCode()).body(responseModel);
    }

    @PreAuthorize("hasRole('ADMIN' ) or hasRole('CLIENT')")
    @Operation(
        summary = "Get all hotels",
        description = "REST API for getting all hotels"
    )
    @GetMapping("/get-all-hotels")
    public ResponseEntity<ResponseModel> getAllHotels() {
        return ResponseEntity.ok(hotelService.getAllHotels());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
        summary = "Delete hotel by code",
        description = "REST API for deleting a hotel"
    )
    @DeleteMapping("/delete-hotel-by-hotel-code")
    public ResponseEntity<ResponseModel> deleteHotelByHotelCode(@RequestParam String hotelCode) {
        ResponseModel responseModel = hotelService.deleteHotelByHotelCode(hotelCode);
        return ResponseEntity.status(responseModel.getStatusCode()).body(responseModel);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
        summary = "Add room by hotel code",
        description = "REST API for adding a room"
    )
    @PostMapping("/room/add-room")
    public ResponseEntity<ResponseModel> addRoom(@RequestParam String hotelCode, @Valid @RequestBody RoomDto dto) {
        ResponseModel responseModel = hotelService.addRoom(hotelCode, dto);
        return ResponseEntity.status(responseModel.getStatusCode()).body(responseModel);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('CLIENT')")
    @Operation(
        summary = "Get room by code",
        description = "REST API for getting a room"
    )
    @GetMapping("/room/get-room")
    public ResponseEntity<ResponseModel> getRoomByRoomCode(@RequestParam String roomCode) {
        ResponseModel responseModel = hotelService.getRoomByRoomCode(roomCode);
        return ResponseEntity.status(responseModel.getStatusCode()).body(responseModel);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
        summary = "Get all available rooms by hotel code",
        description = "REST API for getting all available rooms"
    )
    @GetMapping("/room/get-all-available-rooms")
    public ResponseEntity<ResponseModel> getAllAvailableRoom(@RequestParam String hotelCode) {
        ResponseModel responseModel = hotelService.getAllAvailableRoom(hotelCode);
        return ResponseEntity.status(responseModel.getStatusCode()).body(responseModel);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
        summary = "Get all booked rooms by hotel code",
        description = "REST API for getting all booked room"
    )
    @GetMapping("/room/get-all-booked-rooms")
    public ResponseEntity<ResponseModel> getAllBookedRoom(@RequestParam String hotelCode) {
        ResponseModel responseModel = hotelService.getAllBookedRoom(hotelCode);
        return ResponseEntity.status(responseModel.getStatusCode()).body(responseModel);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
        summary = "Get all rooms",
        description = "REST API for getting all rooms"
    )
    @GetMapping("/room/get-all-rooms by hotel code")
    public ResponseEntity<ResponseModel> getAllRooms(@RequestParam String hotelCode) {
        ResponseModel responseModel = hotelService.getAllRooms(hotelCode);
        return ResponseEntity.status(responseModel.getStatusCode()).body(responseModel);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
        summary = "Delete room by room code",
        description = "REST API for deleting room"
    )
    @DeleteMapping("/room/delete-room")
    public ResponseEntity<ResponseModel> deleteRoomByRoomCode(@RequestParam String roomCode) {
        ResponseModel responseModel = hotelService.deleteRoomByRoomCode(roomCode);
        return ResponseEntity.status(responseModel.getStatusCode()).body(responseModel);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('CLIENT')")
    @Operation(
        summary = "Get hotel by location",
        description = "REST API for getting all hotels by location"
    )
    @GetMapping("/get-hotel-by-location")
    public ResponseEntity<ResponseModel> getHotelByLocation(@RequestParam String location) {
        ResponseModel responseModel = hotelService.getHotelByLocation(location);
        return ResponseEntity.status(responseModel.getStatusCode()).body(responseModel);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('CLIENT')")
    @Operation(
        summary = "Get hotel by name",
        description = "REST API for getting hotel by name"
    )
    @GetMapping("/get-hotel-by-name")
    public ResponseEntity<ResponseModel> getHotelByName(@RequestParam String name) {
        ResponseModel responseModel = hotelService.getHotelByName(name);
        return ResponseEntity.status(responseModel.getStatusCode()).body(responseModel);
    }
}
