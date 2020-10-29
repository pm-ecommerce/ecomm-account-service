package com.pm.ecommerce.account_service.models;

import com.pm.ecommerce.entities.Employee;
import com.pm.ecommerce.entities.Role;
import lombok.Data;

@Data
public class EmployeeRequest {
    protected  int id;
    protected String name;
    protected String email;
    protected String password;
    protected RoleRequest role;
    protected String passwordConfirmation;

    public Employee toEmployee() {
        Employee employee = new Employee();
        employee.setId(getId());
        employee.setName(getName());
        employee.setPassword(getPassword());
        employee.setEmail(getEmail());
        employee.setRole(role.toRole());
        return employee;
    }
}
