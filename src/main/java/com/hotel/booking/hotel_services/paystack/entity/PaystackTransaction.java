package com.hotel.booking.hotel_services.paystack.entity;

import com.hotel.booking.hotel_services.utils.BaseEntity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PaystackTransaction extends BaseEntity{
    private String reference;
    private int amount;
    private String status;
}

