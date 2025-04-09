package com.hotel.booking.user_services.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hotel.booking.user_services.enums.Status;
import com.hotel.booking.user_services.utils.BaseEntityAudit;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Room extends BaseEntityAudit{

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
    
    private Hotel hotel;

}
