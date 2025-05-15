package com.hotel.booking.hotel_services.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hotel.booking.hotel_services.enums.BookingStatus;
import com.hotel.booking.hotel_services.enums.PaymentStatus;
import com.hotel.booking.hotel_services.utils.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Booking extends BaseEntity {

    @Column(name = "booking_code", unique = true, nullable = false)
    private String bookingCode;

    @Column(name = "hotel_code", nullable = false)
    private String hotelCode;

    // @Column(name = "room_code", nullable = false)
    // private String roomCode;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "room_code", referencedColumnName = "room_code")
    private Room room;

    @Column(name = "booking_date")
    private LocalDate bookingDate;

    @Column(name = "check_out_date")
    private LocalDate checkOutDate;

    @Column(name = "amount")
    private int amount;

    @Column(name = "payment_status")
    private PaymentStatus paymentStatus;

    @Column(name = "booking_status")
    private BookingStatus bookingStatus;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "hotel_code", referencedColumnName = "hotel_code", insertable = false, updatable = false)
    private Hotel hotel;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_code", referencedColumnName = "user_code", insertable = false, updatable = false)
    private User user;
    
    @Column(name = "user_code", nullable = false)
    private String userCode;

}
