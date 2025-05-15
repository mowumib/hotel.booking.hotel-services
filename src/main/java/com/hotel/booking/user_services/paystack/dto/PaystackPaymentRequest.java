package com.hotel.booking.user_services.paystack.dto;

import lombok.Data;

@Data
public class PaystackPaymentRequest {
    private String email; 
    private int amount;
    private String currency = "NGN";
}
