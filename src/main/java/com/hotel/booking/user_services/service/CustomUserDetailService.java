package com.hotel.booking.user_services.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hotel.booking.user_services.entity.User;
import com.hotel.booking.user_services.repository.UserRepository;
import com.hotel.booking.user_services.serviceImpl.CustomUserDetailImpl;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    UserRepository repo;
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return CustomUserDetailImpl.build(user);
    }
}
