package com.pm.ecommerce.account_service.models;

import com.pm.ecommerce.entities.Role;
import lombok.Data;

@Data
public class RoleRequest {
    protected String name;

    public Role toRole() {
        Role role = new Role();
        role.setName(getName());
        return role;
    }
}
