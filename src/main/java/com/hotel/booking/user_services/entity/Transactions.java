/* package com.hotel.booking.user_services.entity;

import java.time.LocalDateTime;

import com.hotel.booking.user_services.enums.TransactionType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Transactions {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer transactionId;

//    @JsonFormat(pattern="yyyy-MM-dd-HH-mm-ss")
    private LocalDateTime transactionDate;

    @DecimalMin("0.0")
    private Float amount;


    private Float CurrentBalance;

    @Enumerated(EnumType.STRING)
    private TransactionType type;
}
 */