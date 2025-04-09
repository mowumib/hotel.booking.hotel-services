// package com.hotel.booking.user_services.config;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
// import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
// import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
// import org.springframework.security.config.http.SessionCreationPolicy;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// import com.hotel.booking.user_services.filter.AuthTokenFilter;
// import com.hotel.booking.user_services.service.CustomUserDetailService;
// import com.hotel.booking.user_services.utils.AuthEntryPointJwt;

// @Configuration
// @EnableMethodSecurity
// public class WebSecurityConfig {

//     @Autowired
//     CustomUserDetailService customUserDetailService;

//     @Autowired
//     AuthEntryPointJwt unauthorizedHandler;

//     @Bean
//     AuthTokenFilter authenticationJwtTokenFilter() {
//         return new AuthTokenFilter();
//     }

//     @Bean
//     AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
//         return authConfig.getAuthenticationManager();
//     }
//     @Bean
//     PasswordEncoder passwordEncoder() {
//         return new BCryptPasswordEncoder();
//     }
//     @Bean
//     DaoAuthenticationProvider authenticationProvider() {
//         DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

//         authProvider.setUserDetailsService(customUserDetailService);
//         authProvider.setPasswordEncoder(passwordEncoder());

//         return authProvider;
//     }

//     @Bean
//     public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//         http.csrf(AbstractHttpConfigurer::disable)
//                 .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
//                 .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                 .authorizeHttpRequests(auth ->
//                         auth
//                         .requestMatchers("/api-docs/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
//                         .requestMatchers("/hotel/booking/user/signup/**", 
//                         "/hotel/booking/admin-user/signup/**", "/hotel/booking/user/signin/**").permitAll()
//                         .anyRequest().authenticated()
//                 );
//         http.authenticationProvider(authenticationProvider());

//         http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

//         return http.build();
//     }
// }
