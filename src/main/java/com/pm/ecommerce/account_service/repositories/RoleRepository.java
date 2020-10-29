package com.pm.ecommerce.account_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pm.ecommerce.entities.Role;

public interface RoleRepository extends JpaRepository<Role,Integer> {
    Role findByName(String name);
}
