package com.hotel.booking.user_services.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hotel.booking.user_services.utils.BaseEntityAudit;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity@EqualsAndHashCode(callSuper = true)
@Table(name = "hotels")
public class Hotel extends BaseEntityAudit{

    @Column(name = "hotel_code", unique = true, nullable = false)
    private String hotelCode;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "location", nullable = false)
    private String location;

    private List<Room> rooms;

    @JsonIgnore
    private List<Booking> bookings;

}
