package com.hotel.booking.hotel_services.seeder;

import java.util.Set;
import java.util.UUID;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.hotel.booking.hotel_services.entity.Role;
import com.hotel.booking.hotel_services.entity.User;
import com.hotel.booking.hotel_services.enums.RoleEnum;
import com.hotel.booking.hotel_services.repository.RoleRepository;
import com.hotel.booking.hotel_services.repository.UserRepository;

@Component
@Order(2)
public class AdminUserSeeder implements ApplicationListener<ContextRefreshedEvent> {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminUserSeeder(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onApplicationEvent(@NonNull ContextRefreshedEvent event) {
        seedAdminUser();
    }

    private void seedAdminUser() {
        String adminEmail = "admin@example.com";

        if (!userRepository.existsByEmail(adminEmail)) {
            Role adminRole = roleRepository.findByRoleName(RoleEnum.ADMIN)
                    .orElseThrow(() -> new RuntimeException("ADMIN role not found. Make sure RoleSeeder runs first."));

            User adminUser = new User();
            adminUser.setUserCode("USER-" + UUID.randomUUID().toString().replace("-", "").substring(0, 8));
            adminUser.setName("admin");
            adminUser.setEmail(adminEmail);
            adminUser.setPassword(passwordEncoder.encode("admin123"));
            adminUser.setRoles(Set.of(adminRole));

            userRepository.save(adminUser);
            System.out.println("Admin user seeded: " + adminEmail);
        }
    }
}

