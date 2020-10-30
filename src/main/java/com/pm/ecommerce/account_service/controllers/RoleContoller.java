package com.pm.ecommerce.account_service.controllers;

import com.pm.ecommerce.account_service.models.PagedResponse;
import com.pm.ecommerce.account_service.models.RoleRequest;
import com.pm.ecommerce.account_service.models.RoleResponse;
import com.pm.ecommerce.account_service.services.RoleService;
import com.pm.ecommerce.entities.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/roles")
public class RoleContoller {

    @Autowired
    private RoleService roleService;

    @PostMapping("")
    private ResponseEntity<ApiResponse<RoleResponse>> createRole(@RequestBody RoleRequest role) {
        ApiResponse<RoleResponse> response = new ApiResponse<>();
        try {
            RoleResponse response1 = roleService.createRole(role);
            response.setMessage("Role is created successfully");
            response.setData(response1);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setStatus(500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PatchMapping("/{id}")
    private ResponseEntity<ApiResponse<RoleResponse>> updateRole(@RequestBody RoleRequest role, @PathVariable int id) {
        ApiResponse<RoleResponse> response = new ApiResponse<>();
        try {
            RoleResponse response1 = roleService.updateRole(role, id);
            response.setMessage("Role is updated successfully");
            response.setData(response1);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setStatus(500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<ApiResponse<RoleResponse>> deleteRole(@PathVariable int id) {
        ApiResponse<RoleResponse> response = new ApiResponse<>();
        try {
            RoleResponse response1 = roleService.deleteRoles(id);
            response.setMessage("Role is deleted successfully");
            response.setData(response1);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setStatus(500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<PagedResponse<RoleResponse>>> getAllUsers(
            @RequestParam(name = "itemsPerPage", defaultValue = "20") int itemsPerPage,
            @RequestParam(name = "currentPage", defaultValue = "1") int currentPage

    ) {
        ApiResponse<PagedResponse<RoleResponse>> response = new ApiResponse<>();
        try {
            PagedResponse<RoleResponse> users = roleService.getAllRoles(itemsPerPage, currentPage);
            response.setMessage("Role fetched successfully");
            response.setData(users);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setStatus(500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RoleResponse>> getRoleById(@PathVariable int id) {
        ApiResponse<RoleResponse> response = new ApiResponse<>();
        try {
            RoleResponse role = roleService.getRoleById(id);
            response.setMessage("Roles Fetched Successfully");
            response.setData(role);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setStatus(500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
