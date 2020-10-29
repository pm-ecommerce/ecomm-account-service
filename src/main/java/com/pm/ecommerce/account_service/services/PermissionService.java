package com.pm.ecommerce.account_service.services;

import com.pm.ecommerce.account_service.models.PagedResponse;
import com.pm.ecommerce.account_service.models.PermissionRequest;
import com.pm.ecommerce.account_service.models.PermissionResponse;
import com.pm.ecommerce.account_service.repositories.PermissionRepository;
import com.pm.ecommerce.entities.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;

    //permission creation
    public PermissionResponse createPermission(PermissionRequest permission) throws Exception {
        if (permission == null) {
            throw new Exception("Data expected with this request.");
        }
        if (permission.getName() == null) {
            throw new Exception("Name should not be null");
        }

        if (permission.getAction() == null) {
            throw new Exception("Action should not be null");
        }

        if (permission.getPath() == null) {
            throw new Exception("Path should not be null");
        }
        Permission permission1 = permission.toPermission();
        permissionRepository.save(permission1);
        return new PermissionResponse(permission1);
    }

    //get permission by Id
    public Permission getById(int id) {
        return permissionRepository.findById(id).orElse(null);
    }

    public PermissionResponse getPermissionById(int id) {
        return new PermissionResponse(getById(id));
    }

    //delete Permission
    public PermissionResponse deletePermission(int id) throws Exception {
        Permission permission = getById(id);
        if (permission == null) {
            throw new Exception("Permission does not exists");
        }

        permissionRepository.delete(permission);
        return new PermissionResponse(permission);
    }

    //get all permissions
    public PagedResponse<PermissionResponse> getAllPermissions(int itemsPerPage, int currentPage) {
        Pageable paging = PageRequest.of(currentPage - 1, itemsPerPage);
        Page<Permission> pagedResult = permissionRepository.findAll(paging);

        int totalPages = pagedResult.getTotalPages();
        List<PermissionResponse> response = pagedResult.toList().stream().map(PermissionResponse::new).collect(Collectors.toList());

        return new PagedResponse<>(totalPages, currentPage, itemsPerPage, response);
    }


}
