package com.hotel.booking.hotel_services.email.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailMessageDto {
    private String to;
    private String subject;
    private String body;
}
