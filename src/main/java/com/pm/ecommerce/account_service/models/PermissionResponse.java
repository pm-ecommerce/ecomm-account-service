package com.pm.ecommerce.account_service.models;

import com.pm.ecommerce.entities.Permission;
import lombok.Data;

@Data
public class PermissionResponse {
    private int id;
    private String action;
    private String path;
    private String name;

    public PermissionResponse(Permission permission) {
        setId(permission.getId());
        setAction(permission.getAction());
        setName(permission.getName());
        setPath(permission.getPath());
    }
}
