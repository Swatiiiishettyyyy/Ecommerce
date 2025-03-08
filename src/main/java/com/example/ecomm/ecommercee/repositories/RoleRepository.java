package com.example.ecomm.ecommercee.repositories;

import com.example.ecomm.ecommercee.model.AppRole;
import com.example.ecomm.ecommercee.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(AppRole appRole);
}
