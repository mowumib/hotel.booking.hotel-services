package com.hotel.booking.user_services.entity;

import java.util.List;

import com.hotel.booking.user_services.utils.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
@Table(name = "hotels")
public class Hotel extends BaseEntity{

    @Column(name = "hotel_code", unique = true, nullable = false)
    private String hotelCode;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "location", nullable = false)
    private String location;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "hotel_code", referencedColumnName = "hotel_code", insertable = false, updatable = false)
    private List<Room> rooms;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    private List<Booking> bookings;

}
