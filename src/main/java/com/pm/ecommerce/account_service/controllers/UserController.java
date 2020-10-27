package com.pm.ecommerce.account_service.controllers;

import com.pm.ecommerce.account_service.Models.UserRequest;
import com.pm.ecommerce.account_service.Models.UserResponse;
import com.pm.ecommerce.account_service.services.UserService;
import com.pm.ecommerce.entities.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers() {
        ApiResponse<List<UserResponse>> response = new ApiResponse<>();
        try {
            List<UserResponse> user1 = userService.getAllUsers();
            response.setMessage("Users Fetched Successfully");
            response.setData(user1);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setStatus(500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable int id) {
        ApiResponse<UserResponse> response = new ApiResponse<>();
        try {
            UserResponse user1 = userService.getUserById(id);
            response.setMessage("Users Fetched Successfully");
            response.setData(user1);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setStatus(500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse<UserResponse>> createUser(@RequestBody UserRequest user) {
        ApiResponse<UserResponse> response = new ApiResponse<>();
        try {
            UserResponse user1 = userService.createUser(user);
            response.setMessage("User Created Successfully");
            response.setData(user1);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setStatus(500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(@RequestBody UserRequest user, @PathVariable int id) {
        ApiResponse<UserResponse> response = new ApiResponse<>();
        try {
            UserResponse user1 = userService.updateUser(user,id);
            response.setMessage("User Updated Successfully");
            response.setData(user1);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setStatus(500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> deleteUser( @PathVariable int id) {
        ApiResponse<UserResponse> response = new ApiResponse<>();
        try {
            UserResponse user1 = userService.deleteUser(id);
            response.setMessage("User Deleted Successfully");
            response.setData(user1);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setStatus(500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
