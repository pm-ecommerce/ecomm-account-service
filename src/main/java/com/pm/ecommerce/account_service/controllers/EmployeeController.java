package com.pm.ecommerce.account_service.controllers;

import com.pm.ecommerce.account_service.services.EmployeeService;
import com.pm.ecommerce.entities.ApiResponse;
import com.pm.ecommerce.entities.Employee;
import org.apache.coyote.Response;
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
}
