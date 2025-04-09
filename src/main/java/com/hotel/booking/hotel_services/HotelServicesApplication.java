package com.hotel.booking.hotel_services;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.hotel.booking.hotel_services.config.ServerProp;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@SpringBootApplication
public class HotelServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelServicesApplication.class, args);
	}

	@Bean
	OpenAPI myAPI() {
		return new OpenAPI()
			.components(new Components()
				.addSecuritySchemes("bearer-key",
					new SecurityScheme()
						.type(SecurityScheme.Type.HTTP)
						.scheme("bearer")
						.bearerFormat("JWT")))
			.addSecurityItem(
				new SecurityRequirement()
					.addList("bearerAuth"))
					/* .addList("bearer-jwt", Arrays.asList("read", "write"))
					.addList("bearer-key", Collections.emptyList())) */
			.servers(Arrays.asList(
				new ServerProp().url("http://localhost:8091/hotel-services")
					.description("Local host URL")));
	}

}
