package com.hotel.booking.user_services.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hotel.booking.user_services.entity.User;

public interface UserRepository extends JpaRepository<User, String> {
    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);

    Optional<User> findByUserCode(String userCode);
}
