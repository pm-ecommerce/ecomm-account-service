package com.pm.ecommerce.account_service.controllers;

import com.pm.ecommerce.account_service.models.*;
import com.pm.ecommerce.account_service.services.EmployeeService;
import com.pm.ecommerce.account_service.utils.JwtTokenUtil;
import com.pm.ecommerce.entities.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;


    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @GetMapping("")
    public ResponseEntity<ApiResponse<PagedResponse<EmployeeResponse>>> getAllEmployees(
            @RequestParam(name = "itemsPerPage", defaultValue = "20") int itemsPerPage,
            @RequestParam(name = "currentPage", defaultValue = "1") int currentPage

    ) {
        ApiResponse<PagedResponse<EmployeeResponse>> response = new ApiResponse<>();
        try {
            PagedResponse<EmployeeResponse> employees = employeeService.getAllEmployees(itemsPerPage, currentPage);
            response.setMessage("Employee fetched successfully");
            response.setData(employees);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setStatus(500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EmployeeResponse>> getEmployeeById(@PathVariable int id) {
        ApiResponse<EmployeeResponse> response = new ApiResponse<>();
        try {
            EmployeeResponse employee1 = employeeService.getEmployeeById(id);
            response.setMessage("Employee Fetched Successfully");
            response.setData(employee1);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setStatus(500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("")
    private ResponseEntity<ApiResponse<EmployeeResponse>> createEmployee(@RequestBody EmployeeRequest employee) {
        ApiResponse<EmployeeResponse> response = new ApiResponse<>();
        try {
            EmployeeResponse employee1 = employeeService.createEmployee(employee);
            response.setMessage("Employee Registered Successfully");
            response.setData(employee1);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            response.setMessage(e.getMessage());
            response.setStatus(500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PatchMapping("/{id}")
    private ResponseEntity<ApiResponse<EmployeeResponse>> updateEmployee(@RequestBody EmployeeRequest employee, @PathVariable  int id) {
        ApiResponse<EmployeeResponse> response = new ApiResponse<>();
        try {
            EmployeeResponse employee1 = employeeService.updateEmployee(employee, id);
            response.setMessage("Employee Updated Successfully");
            response.setData(employee1);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            response.setMessage(e.getMessage());
            response.setStatus(500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/login")
    private ResponseEntity<ApiResponse<LoginResponse>> loginEmployee(@RequestBody LoginRequest loginRequest) {
        ApiResponse<LoginResponse> response = new ApiResponse<>();
        try {
            LoginResponse response1 = employeeService.login(loginRequest);
            response.setMessage("Employee Logged In Successfully. ");
            response.setData(response1);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setStatus(500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


}
