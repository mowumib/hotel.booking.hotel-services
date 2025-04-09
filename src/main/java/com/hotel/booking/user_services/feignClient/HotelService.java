package com.hotel.booking.user_services.feignClient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.hotel.booking.user_services.entity.Booking;
import com.hotel.booking.user_services.entity.Hotel;

@FeignClient(name = "hotel-services")
public interface HotelService {

    @GetMapping("/hotels/get-hotel-by-code")
    public Hotel getHotelByHotelCode(@RequestParam String hotelCode);


    @PostMapping("/hotels/booking/addBooking")
    public Boolean addBookingFromHotel(@RequestBody Booking booking);

    @GetMapping("/hotels/get-all-hotels")
    public List<Hotel> getAllHotels();
}
