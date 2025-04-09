package com.hotel.booking.user_services.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hotel.booking.user_services.enums.BookingStatus;
import com.hotel.booking.user_services.enums.PaymentStatus;
import com.hotel.booking.user_services.utils.BaseEntityAudit;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Booking extends BaseEntityAudit {

    @Column(name = "booking_code", unique = true, nullable = false)
    private String bookingCode;

    @Column(name = "hotel_code", unique = true, nullable = false)
    private String hotelCode;

    @Column(name = "room_code", unique = true, nullable = false)
    private String roomCode;

    @Column(name = "booking_date")
    private LocalDate bookingDate;

    @Column(name = "check_out_date")
    private LocalDate checkOutDate;

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "payment_status")
    private PaymentStatus paymentStatus;

    @Column(name = "booking_status")
    private BookingStatus bookingStatus;
    
    @ManyToOne
    @JsonIgnore
    private User user;

}
