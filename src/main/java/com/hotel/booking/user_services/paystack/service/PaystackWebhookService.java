package com.hotel.booking.user_services.paystack.service;

public interface PaystackWebhookService {
    boolean processWebhookEvent(String payload);
}
