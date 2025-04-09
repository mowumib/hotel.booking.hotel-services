package com.hotel.booking.user_services.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.hotel.booking.user_services.enums.RoleEnum;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
@Table(name = "role")
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @Column(name = "roleName")
    // private String roleName;
    
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private RoleEnum roleName;

    public Role(){

    }

    public Role(RoleEnum name){
        this.roleName = name;
    }
    /* @Column(name = "description")
    private String description; */

    /* @ManyToMany(mappedBy = "roles", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonBackReference
    private List<User> users; */

}
