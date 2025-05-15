package com.hotel.booking.hotel_services.paystack.enums;

public enum PaystackPaymentStatus {

    SUCCESS("success"),
    FAILED("failed"),
    PENDING("pending");

    private final String status;

    PaystackPaymentStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
