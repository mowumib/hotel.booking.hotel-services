package com.hotel.booking.user_services.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hotel.booking.user_services.entity.Role;
import com.hotel.booking.user_services.entity.UserRole;

public interface RoleRepository extends JpaRepository<Role, Long>{
    
    Boolean existsByRoleName(String email);
    
    @Query(value = "SELECT rl.role_name,ur.role_id as role_id from user_role_assignments ur INNER JOIN role rl ON rl.id = ur.role_id where ur.user_id = ?1", nativeQuery = true)
    List<UserRole> getUserRoles(Long userId);

    @Query("SELECT r FROM Role r WHERE r.id = :roleId")
    Role getRoleNameById(@Param("roleId") Long roleId);
    
}
