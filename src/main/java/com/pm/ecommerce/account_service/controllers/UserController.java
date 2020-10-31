package com.pm.ecommerce.account_service.controllers;

import com.pm.ecommerce.account_service.models.*;
import com.pm.ecommerce.account_service.services.UserService;
import com.pm.ecommerce.entities.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("")
    public ResponseEntity<ApiResponse<PagedResponse<UserResponse>>> getAllUsers(
            @RequestParam(name = "itemsPerPage", defaultValue = "20") int itemsPerPage,
            @RequestParam(name = "currentPage", defaultValue = "1") int currentPage

    ) {
        ApiResponse<PagedResponse<UserResponse>> response = new ApiResponse<>();
        try {
            PagedResponse<UserResponse> users = userService.getAllUsers(itemsPerPage, currentPage);
            response.setMessage("User fetched successfully");
            response.setData(users);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setStatus(500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/guests")
    public ResponseEntity<ApiResponse<PagedResponse<UserResponse>>> getAllGuestUsers(
            @RequestParam(name = "itemsPerPage", defaultValue = "20") int itemsPerPage,
            @RequestParam(name = "currentPage", defaultValue = "1") int currentPage

    ) {
        ApiResponse<PagedResponse<UserResponse>> response = new ApiResponse<>();
        try {
            PagedResponse<UserResponse> users = userService.getAllGuestUsers(itemsPerPage, currentPage);
            response.setMessage("User fetched successfully");
            response.setData(users);
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

    @PostMapping("/guests")
    public ResponseEntity<ApiResponse<UserResponse>> createGuestUser(@RequestBody UserRequest user) {
        ApiResponse<UserResponse> response = new ApiResponse<>();
        try {
            UserResponse user1 = userService.createGuestUser(user);
            response.setMessage("Guest Created Successfully");
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
            UserResponse user1 = userService.updateUser(user, id);
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
    public ResponseEntity<ApiResponse<UserResponse>> deleteUser(@PathVariable int id) {
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

    @PostMapping("/login")
    private ResponseEntity<ApiResponse<LoginResponse>> loginUser(@RequestBody LoginRequest loginRequest) {
        ApiResponse<LoginResponse> response = new ApiResponse<>();
        try {
            LoginResponse response1 = userService.login(loginRequest);
            response.setMessage("User Logged In Successfully. ");
            response.setData(response1);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            response.setMessage(e.getMessage());
            response.setStatus(500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
