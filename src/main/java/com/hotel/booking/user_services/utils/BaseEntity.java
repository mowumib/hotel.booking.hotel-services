package com.hotel.booking.user_services.utils;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.TableGenerator;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity implements Serializable {
    @Id
    @GeneratedValue(generator = "UUID")
    @TableGenerator(
            name = "UUID",
            table = "hibernate_unique_key",
            pkColumnValue = "uuid"
    )
    @Setter(AccessLevel.NONE)
    @Column(name = "id", updatable = false, unique = true)
    private String id;

    @Column(name = "is_deleted")
    private boolean isDeleted;
}
