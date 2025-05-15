package com.hotel.booking.hotel_services.paystack.service;

public interface PaystackWebhookService {
    boolean processWebhookEvent(String payload);
}
