package com.hotel.booking.hotel_services.paystack.dto;

import lombok.Data;

@Data
public class PaystackPaymentDto {
    private String bookingCode;
    private String userCode;
    private int amount;
    private String email;

}
