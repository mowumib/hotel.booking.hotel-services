package com.hotel.booking.hotel_services.paystack.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hotel.booking.hotel_services.paystack.entity.PaystackTransaction;

import java.util.Optional;

public interface PaystackTransactionRepository extends JpaRepository<PaystackTransaction, String> {
    Optional<PaystackTransaction> findByReference(String reference);
}
