package com.hotel.booking.hotel_services.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hotel.booking.hotel_services.dto.HotelDto;
import com.hotel.booking.hotel_services.dto.ResponseModel;
import com.hotel.booking.hotel_services.dto.RoomDto;
import com.hotel.booking.hotel_services.service.HotelService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/hotels")
@RequiredArgsConstructor
@Validated
@Tag(name = "HOTEL CONTROLLER REST APIS FOR HOTEL SERVICE", description = "REST APIS in HOTEL SERVICE")
public class HotelController {

    private final HotelService hotelService;

    @PostMapping
    public ResponseEntity<ResponseModel> addHotel(@Valid @RequestBody HotelDto dto) {
        ResponseModel responseModel = hotelService.addHotel(dto);
        return ResponseEntity.status(responseModel.getStatusCode()).body(responseModel);
    }

    @GetMapping("/get-hotel-by-code")
    public ResponseEntity<ResponseModel> getHotelByHotelCode(@RequestParam String hotelCode) {
        ResponseModel responseModel = hotelService.getHotelByHotelCode(hotelCode);
        return ResponseEntity.status(responseModel.getStatusCode()).body(responseModel);
    }

    @GetMapping("/get-all-hotels")
    public ResponseEntity<ResponseModel> getAllHotels() {
        return ResponseEntity.ok(hotelService.getAllHotels());
    }

    @DeleteMapping("/delete-hotel-hotel-code")
    public ResponseEntity<ResponseModel> deleteHotelByHotelCode(@RequestParam String hotelCode) {
        ResponseModel responseModel = hotelService.deleteHotelByHotelCode(hotelCode);
        return ResponseEntity.status(responseModel.getStatusCode()).body(responseModel);
    }

    @PostMapping("/add-rooms")
    public ResponseEntity<ResponseModel> addRoom(@RequestParam String hotelCode, @Valid @RequestBody RoomDto dto) {
        ResponseModel responseModel = hotelService.addRoom(hotelCode, dto);
        return ResponseEntity.status(responseModel.getStatusCode()).body(responseModel);
    }

    @GetMapping("/get-rooms")
    public ResponseEntity<ResponseModel> getRoomByRoomCode(@RequestParam String roomCode) {
        ResponseModel responseModel = hotelService.getRoomByRoomCode(roomCode);
        return ResponseEntity.status(responseModel.getStatusCode()).body(responseModel);
    }

    @GetMapping("/get-all-rooms-available")
    public ResponseEntity<ResponseModel> getAllAvailableRoom(@RequestParam String hotelCode) {
        ResponseModel responseModel = hotelService.getAllAvailableRoom(hotelCode);
        return ResponseEntity.status(responseModel.getStatusCode()).body(responseModel);
    }

    @GetMapping("/get-all-rooms-booked")
    public ResponseEntity<ResponseModel> getAllBookedRoom(@RequestParam String hotelCode) {
        ResponseModel responseModel = hotelService.getAllBookedRoom(hotelCode);
        return ResponseEntity.status(responseModel.getStatusCode()).body(responseModel);
    }

    @GetMapping("/{hotelCode}/getAllRooms")
    public ResponseEntity<ResponseModel> getAllRooms(@RequestParam String hotelCode) {
        ResponseModel responseModel = hotelService.getAllRooms(hotelCode);
        return ResponseEntity.status(responseModel.getStatusCode()).body(responseModel);
    }


    @DeleteMapping("/rooms/delete-room")
    public ResponseEntity<ResponseModel> deleteRoomByRoomCode(@RequestParam String roomCode) {
        ResponseModel responseModel = hotelService.deleteRoomByRoomCode(roomCode);
        return ResponseEntity.status(responseModel.getStatusCode()).body(responseModel);
    }


    @GetMapping("/get-hotel-by-location/{location}")
    public ResponseEntity<ResponseModel> getHotelByLocation(@PathVariable String location) {
        ResponseModel responseModel = hotelService.getHotelByLocation(location);
        return ResponseEntity.status(responseModel.getStatusCode()).body(responseModel);
    }

    @GetMapping("/get-hotel-by-name/{name}")
    public ResponseEntity<ResponseModel> getHotelByName(@PathVariable String name) {
        ResponseModel responseModel = hotelService.getHotelByName(name);
        return ResponseEntity.status(responseModel.getStatusCode()).body(responseModel);
    }
}
