package com.hotel.booking.user_services.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;

@EnableAsync
@Configuration
public class AppConfig {
    // @Bean
    // public ModelMapper modelMapper() {
    //     return new ModelMapper();
    // }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
