package com.pm.ecommerce.account_service.models;

import com.pm.ecommerce.entities.Permission;
import lombok.Data;

@Data
public class PermissionRequest {
    protected String name;
    protected String path;
    protected String action;

    public Permission toPermission(){
        Permission permission = new Permission();
        permission.setAction(getAction());
        permission.setName(getName());
        permission.setPath(getPath());
        return permission;
    }
}
