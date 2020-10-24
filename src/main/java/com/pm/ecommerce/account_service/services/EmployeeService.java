package com.pm.ecommerce.account_service.services;

import com.pm.ecommerce.account_service.models.LoginRequest;
import com.pm.ecommerce.account_service.models.LoginResponse;
import com.pm.ecommerce.account_service.repositories.EmployeeRepository;
import com.pm.ecommerce.account_service.utils.JwtTokenUtil;
import com.pm.ecommerce.entities.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class EmployeeService {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee createEmployee(Employee employee) throws Exception {
        if (employee == null) {
            throw new Exception("Data expected with this request");
        }
        if (employee.getEmail() == null || employee.getEmail().length() == 0) {
            throw new Exception("Email field is empty");
        }
        if (employee.getName() == null || employee.getName().length() == 0) {
            throw new Exception("Please fill the name");
        }
        if (!validateEmail(employee.getEmail())) {
            throw new Exception("Email is invalid. Please provide a valid email");
        }

        Employee employee1 = getEmployeeByEmail(employee.getEmail());
        if (employee1 != null) {
            throw new Exception("Email is already registered");
        }
        return employeeRepository.save(employee);
    }

    public boolean validateEmail(String emailStr) {
        Pattern VALID_EMAIL_ADDRESS_REGEX =
                Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    public LoginResponse login(LoginRequest request) throws Exception {

        Employee employee1 = getEmployeeByEmail(request.getEmail());
        System.out.println(employee1.getPassword());
        if (!employee1.getPassword().equals(request.getPassword())) {
            throw new Exception("Password did not match");
        }


        final String token = jwtTokenUtil.generateToken(employee1, "employee");
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(token);
        loginResponse.setName(employee1.getName());

        return loginResponse;

    }

    public Employee getEmployeeByEmail(String email) {
        return employeeRepository.findByEmail(email);
    }


}
