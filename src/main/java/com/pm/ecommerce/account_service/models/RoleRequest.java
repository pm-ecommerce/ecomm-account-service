package com.pm.ecommerce.account_service.models;

import com.pm.ecommerce.entities.Role;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class RoleRequest {
    protected String name;
    protected int id;
    protected List<PermissionRequest> permissions;

    public Role toRole() {
        Role role = new Role();
        role.setName(getName());
        role.setId(getId());

        return role;
    }
}
