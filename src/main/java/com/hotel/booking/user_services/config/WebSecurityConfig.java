package com.hotel.booking.user_services.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.hotel.booking.user_services.filter.JwtAuthFilter;
import com.hotel.booking.user_services.service.CustomUserDetailService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    CustomUserDetailService customUserDetailService;

    @Bean
    JwtAuthFilter jwtAuthFilter() {
        return new JwtAuthFilter();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api-docs/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .requestMatchers("/hotel/user/signup/**", "/hotel/admin-user/signup/**", "/hotel/user/signin/**", "/error").permitAll()
                        .anyRequest().authenticated()
                );
        http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(jwtAuthFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }



    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfiguration) throws Exception {
        return authConfiguration.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(customUserDetailService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
// public class WebSecurityConfig {

//     @Autowired
//     CustomUserDetailService customUserDetailService;

//     @Autowired
//     AuthEntryPointJwt unauthorizedHandler;

//     @Autowired
//     JwtAuthFilter jwtAuthFilter;

//     @Bean
//     PasswordEncoder passwordEncoder() {
//         return new BCryptPasswordEncoder();
//     }
//     @Bean
//     AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
//         return authConfig.getAuthenticationManager();
//     }
    
//     @Bean
//     DaoAuthenticationProvider authenticationProvider() {
//         DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

//         authProvider.setUserDetailsService(customUserDetailService);
//         authProvider.setPasswordEncoder(passwordEncoder());

//         return authProvider;
//     }

//     /* @Bean
//     JwtAuthFilter authenticationJwtTokenFilter() {
//         return new JwtAuthFilter();
//     } */
    
//     @Bean
//     public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//         http.csrf(csrf -> csrf
//                 .disable())
//                 .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
//                 .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                 .authorizeHttpRequests(auth ->
//                         auth
//                                 .requestMatchers("/api-docs/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
//                                 .requestMatchers("/hotel/user/signup/**", "/hotel/admin-user/signup/**", "/hotel/user/signin/**").permitAll()
//                                 .anyRequest().authenticated()
//                 );
//         http.authenticationProvider(authenticationProvider());

//         http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
//         return http.build();
//     }
// }
