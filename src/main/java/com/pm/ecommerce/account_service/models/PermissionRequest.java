package com.pm.ecommerce.account_service.models;

import com.pm.ecommerce.entities.Permission;
import lombok.Data;

@Data
public class PermissionRequest {
    protected int id;
    protected String name;
    protected String path;
    protected String action;

    public Permission toPermission(){
        Permission permission = new Permission();
        permission.setId(getId());
        permission.setAction(getAction());
        permission.setName(getName());
        permission.setPath(getPath());
        return permission;
    }
}
