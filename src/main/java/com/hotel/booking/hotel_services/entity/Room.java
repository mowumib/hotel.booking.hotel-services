package com.hotel.booking.hotel_services.entity;

import java.util.List;

import com.hotel.booking.hotel_services.enums.Status;
import com.hotel.booking.hotel_services.utils.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "room")
public class Room extends BaseEntity{

    @Column(name = "room_code", unique = true, nullable = false)
    private String roomCode;

    @Column(name = "room_type", nullable = false)   
    private String roomType;

    @Column(name = "room_number", nullable = false)
    private String roomNumber;

    @Column(name = "room_price", nullable = false)
    private Integer roomPrice;
    
    @Column(name = "status", nullable = false)
    private Status status;

    @Column(name = "room_description", nullable = false)
    private String roomDescription;
    
    @Column(name = "hotel_code")
    private String hotelCode;

    @OneToMany(mappedBy = "room")
    private List<Booking> bookings;

}
