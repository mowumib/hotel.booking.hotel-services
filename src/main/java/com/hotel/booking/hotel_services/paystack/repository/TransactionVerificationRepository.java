package com.hotel.booking.hotel_services.paystack.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hotel.booking.hotel_services.paystack.entity.TransactionVerification;


public interface TransactionVerificationRepository extends JpaRepository<TransactionVerification, String> {
    Optional<TransactionVerification> findByTransactionReference(String reference);
}
