package com.pm.ecommerce.account_service.models;

import com.pm.ecommerce.entities.Employee;
import com.pm.ecommerce.entities.User;
import lombok.Data;

@Data
public class EmployeeResponse {

    protected int id;
    protected String name;
    protected String email;
    protected String role;

    public EmployeeResponse(Employee employee) {
        setName(employee.getName());
        setEmail(employee.getEmail());
        setId(employee.getId());
        if (employee.getRole() != null) {
            setRole(employee.getRole().getName());
        }

    }
}
