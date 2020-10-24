package com.pm.ecommerce.account_service.controllers;

import com.pm.ecommerce.account_service.models.LoginRequest;
import com.pm.ecommerce.account_service.models.LoginResponse;
import com.pm.ecommerce.account_service.services.EmployeeService;
import com.pm.ecommerce.entities.ApiResponse;
import com.pm.ecommerce.entities.Employee;
import com.pm.ecommerce.account_service.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;


    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @PostMapping("")
    private ResponseEntity<ApiResponse<Employee>> createEmployee(@RequestBody Employee employee) {
        ApiResponse<Employee> response = new ApiResponse<>();
        try {
            Employee employee1 = employeeService.createEmployee(employee);
            response.setMessage("Employee Registered Successfully");
            response.setData(employee1);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
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
