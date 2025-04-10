package com.hotel.booking.hotel_services.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hotel.booking.hotel_services.enums.Status;
import com.hotel.booking.hotel_services.utils.BaseEntityAudit;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "room")
public class Room extends BaseEntityAudit{

    @Column(name = "room_code", unique = true,nullable = false)
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
    
    @ManyToOne
    @JsonIgnore
    private Hotel hotel;

    @OneToMany(mappedBy = "room")
    private List<Booking> bookings;

}
