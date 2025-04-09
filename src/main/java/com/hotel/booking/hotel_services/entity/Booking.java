package com.hotel.booking.hotel_services.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hotel.booking.hotel_services.enums.PaymentStatus;
import com.hotel.booking.hotel_services.utils.BaseEntityAudit;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "booking")
public class Booking extends BaseEntityAudit {

    @Column(name = "booking_code", unique = true, nullable = false)
    private String bookingCode;

    @Column(name = "user_code", unique = true, nullable = false)
    private String userCode;

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

    @ManyToOne
    @JsonIgnore
    private Hotel hotel;
    
    @Transient
    @JsonIgnore
    private User user;

}
