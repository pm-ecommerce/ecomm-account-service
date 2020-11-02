package com.pm.ecommerce.account_service.models;

import com.pm.ecommerce.entities.Employee;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class EmployeeResponse {

    protected int id;
    protected String name;
    protected String email;
    protected Timestamp createdDate;
    protected Timestamp updatedDate;
    protected RoleResponse role;

    public EmployeeResponse(Employee employee) {
        setName(employee.getName());
        setEmail(employee.getEmail());
        setId(employee.getId());
        createdDate = employee.getCreatedDate();
        updatedDate = employee.getUpdatedDate();
        if (employee.getRole() != null) {
            setRole(new RoleResponse(employee.getRole()));
        }

    }
}
