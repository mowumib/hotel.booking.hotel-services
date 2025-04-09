package com.hotel.booking.hotel_services.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

import com.hotel.booking.hotel_services.utils.BaseEntityAudit;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "hotels")
public class Hotel extends BaseEntityAudit{

    @Column(name = "hotel_code", unique = true, nullable = false)
    private String hotelCode;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "location", nullable = false)
    private String location;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    private List<Room> rooms;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    private List<Booking> bookings;

    
}
