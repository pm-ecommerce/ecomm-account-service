package com.pm.ecommerce.account_service.models;

import com.pm.ecommerce.entities.Role;
import lombok.Data;

@Data
public class RoleResponse {
    private int id;
    private String name;

    public RoleResponse(Role role) {
        setName(role.getName());
        setId(role.getId());
    }
}

