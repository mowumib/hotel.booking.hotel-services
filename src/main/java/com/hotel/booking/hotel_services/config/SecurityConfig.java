// package com.hotel.booking.hotel_services.config;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.oauth2.jwt.JwtDecoder;
// import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
// import org.springframework.security.web.SecurityFilterChain;


// @Configuration
// @EnableWebSecurity
// public class SecurityConfig {

//     @Bean
//     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//         http
//             .authorizeHttpRequests(auth -> auth
//                 .requestMatchers("/api-docs/**", "/swagger-ui/**").permitAll()
//                 .anyRequest().authenticated()
//             )
//             .oauth2ResourceServer(oauth2 -> 
//                 oauth2.jwt(jwtConfigurer -> 
//                     jwtConfigurer.decoder(jwtDecoder()) // Explicitly set decoder
//                 )
//             );

//         return http.build();
//     }
//     @Bean
//     public JwtDecoder jwtDecoder() {
//         return NimbusJwtDecoder.withJwkSetUri("http://localhost:8091/user-services/oauth2/jwks").build();
//     }
// }
