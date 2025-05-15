package com.hotel.booking.user_services.paystack.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hotel.booking.user_services.paystack.entity.Transaction;
public interface TransactionRepository extends JpaRepository<Transaction, String> {
    Optional<Transaction> findByReference(String reference);
    List<Transaction> findByEmail(String email);
}
