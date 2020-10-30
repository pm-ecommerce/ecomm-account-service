package com.pm.ecommerce.account_service.controllers;

import com.pm.ecommerce.account_service.models.PagedResponse;
import com.pm.ecommerce.account_service.models.PermissionRequest;
import com.pm.ecommerce.account_service.models.PermissionResponse;
import com.pm.ecommerce.account_service.services.PermissionService;
import com.pm.ecommerce.entities.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/permissions")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @PostMapping("")
    public ResponseEntity<ApiResponse<PermissionResponse>> createPermission(@RequestBody PermissionRequest permission) {
        ApiResponse<PermissionResponse> response = new ApiResponse<>();
        try {
            PermissionResponse permissionResponse = permissionService.createPermission(permission);
            response.setMessage("Permission is created successfuly");
            response.setData(permissionResponse);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setStatus(500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }


    @GetMapping("")
    public ResponseEntity<ApiResponse<PagedResponse<PermissionResponse>>> getAllPermission(
            @RequestParam(name = "itemsPerPage", defaultValue = "20") int itemsPerPage,
            @RequestParam(name = "currentPage", defaultValue = "1") int currentPage

    ) {
        ApiResponse<PagedResponse<PermissionResponse>> response = new ApiResponse<>();
        try {
            PagedResponse<PermissionResponse> permissions = permissionService.getAllPermissions(itemsPerPage, currentPage);
            response.setMessage("Permission fetched successfully");
            response.setData(permissions);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setStatus(500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PermissionResponse>> getPermissionById(@PathVariable int id) {
        ApiResponse<PermissionResponse> response = new ApiResponse<>();
        try {
            PermissionResponse permission1 = permissionService.getPermissionById(id);
            response.setMessage("Permissions Fetched Successfully");
            response.setData(permission1);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setStatus(500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<PermissionResponse>> deletePermission( @PathVariable int id) {
        ApiResponse<PermissionResponse> response = new ApiResponse<>();
        try {
            PermissionResponse permission = permissionService.deletePermission(id);
            response.setMessage("Permission Deleted Successfully");
            response.setData(permission);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setStatus(500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
