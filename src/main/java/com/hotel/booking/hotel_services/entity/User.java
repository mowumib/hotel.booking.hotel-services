package com.hotel.booking.hotel_services.entity;

import com.hotel.booking.hotel_services.utils.BaseEntityAudit;

import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "users")
public class User extends BaseEntityAudit {
    private String userCode;
    private String name;
    private String email;
    private String about;
}
