package com.hotel.booking.user_services.email.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hotel.booking.user_services.email.dto.EmailMessageDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class EmailService {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.email.queue}")
    private String emailQueue;

    public void sendEmailAsync(String to, String subject, String body) {
        EmailMessageDto message = new EmailMessageDto(to, subject, body);
        rabbitTemplate.convertAndSend(emailQueue, message);
    }
}
