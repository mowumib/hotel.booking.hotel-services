package com.hotel.booking.hotel_services.serviceImpl;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hotel.booking.hotel_services.entity.User;

public class CustomUserDetailImpl implements UserDetails{

    private Collection<? extends GrantedAuthority> authorities;

    private String id;

    private String email;

    @JsonIgnore
    private String password;

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    public CustomUserDetailImpl(String id, String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }
    public static CustomUserDetailImpl build(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream()
                // .map(role -> new SimpleGrantedAuthority(role.getRoleName().name()))
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRoleName().name()))
                .collect(Collectors.toList());
        return new CustomUserDetailImpl(
            user.getId(),
            user.getEmail(),
            user.getPassword(),
            authorities);
      }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
}
