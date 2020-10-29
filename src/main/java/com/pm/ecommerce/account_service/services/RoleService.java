package com.pm.ecommerce.account_service.services;

import com.pm.ecommerce.account_service.models.PagedResponse;
import com.pm.ecommerce.account_service.models.PermissionRequest;
import com.pm.ecommerce.account_service.models.RoleRequest;
import com.pm.ecommerce.account_service.models.RoleResponse;
import com.pm.ecommerce.account_service.repositories.RoleRepository;
import com.pm.ecommerce.entities.Permission;
import com.pm.ecommerce.entities.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionService permissionService;

    //role creation
    public RoleResponse createRole(RoleRequest role) throws Exception {
        if (role == null) {
            throw new Exception("Data expected with this request.");
        }

        if (role.getName() == null) {
            throw new Exception("role should not be null");
        }

        Role role1 = roleRepository.findByName(role.getName());
        if (role1 != null) {
            throw new Exception("Role already exist");
        }
        List<Permission> permissions = new ArrayList<>();
        for (PermissionRequest request : role.getPermissions()) {
            Permission permission = permissionService.getById(request.getId());
            if (permission != null) {
                permissions.add(permission);
            }
        }
        Role role2 = role.toRole();
        role2.setPermissions(permissions);
        roleRepository.save(role2);
        return new RoleResponse(role2);
    }

    //update role
    public RoleResponse updateRole(RoleRequest role, int id) throws Exception {
        Role role1 = getById(id);
        if (role1 == null) {
            throw new Exception("role not found");
        }
        if (role.getName() == null || role.getName().length() == 0) {
            throw new Exception("Name is Empty");
        }

        Role role2 = roleRepository.findByName(role.getName());
        if (role2 != null && role2.getId() != role1.getId()) {
            throw new Exception("Role already exists");
        }
        List<Permission> permissions = new ArrayList<>();
        for (PermissionRequest request : role.getPermissions()) {
            Permission permission = permissionService.getById(request.getId());
            if (permission != null) {
                permissions.add(permission);
            }
        }
        role1.setName(role.getName());
        role1.setPermissions(permissions);
        roleRepository.save(role1);
        return new RoleResponse(role1);
    }

    //get all roles
    public PagedResponse<RoleResponse> getAllRoles(int itemsPerPage, int currentPage) {
        Pageable paging = PageRequest.of(currentPage - 1, itemsPerPage);
        Page<Role> pagedResult = roleRepository.findAll(paging);

        int totalPages = pagedResult.getTotalPages();
        List<RoleResponse> response = pagedResult.toList().stream().map(RoleResponse::new).collect(Collectors.toList());

        return new PagedResponse<>(totalPages, currentPage, itemsPerPage, response);
    }

    public Role getById(int id) {
        return roleRepository.findById(id).orElse(null);
    }

    public RoleResponse getRoleById(int id) {
        return new RoleResponse(getById(id));
    }

    //delete role
    public RoleResponse deleteRoles(int id) throws Exception {
        Role role = getById(id);
        if (role == null) {
            throw new Exception("Role does not exists");
        }

        roleRepository.delete(role);
        return new RoleResponse(role);
    }
}




