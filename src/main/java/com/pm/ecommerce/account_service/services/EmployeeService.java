package com.pm.ecommerce.account_service.services;

import com.pm.ecommerce.account_service.models.*;
import com.pm.ecommerce.account_service.repositories.EmployeeRepository;
import com.pm.ecommerce.account_service.utils.JwtTokenUtil;
import com.pm.ecommerce.entities.Employee;
import com.pm.ecommerce.entities.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private RoleService roleService;

    //Employee Registration
    public EmployeeResponse createEmployee(EmployeeRequest employee) throws Exception {
        if (employee == null) {
            throw new Exception("Data expected with this request");
        }
        if (employee.getPassword() == null || employee.getPassword().length() == 0) {
            throw new Exception("Please provide a password");
        }

        if (employee.getPasswordConfirmation() == null || employee.getPasswordConfirmation().length() == 0) {
            throw new Exception("Please provide a confirmation password");
        }

        if (!employee.getPassword().equals(employee.getPasswordConfirmation())) {
            throw new Exception("Password doesn't match!");
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

        Employee employee2 = employee.toEmployee();

        if (employee.getRole() != null) {
            Role role = roleService.getById(employee.getRole().getId());
            if (role == null) {
                throw new Exception("Role not found");
            }
            employee2.setRole(role);
        }

        employee2.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        employeeRepository.save(employee2);

        return new EmployeeResponse(employee2);
    }

    //update Employee
    public EmployeeResponse updateEmployee(EmployeeRequest employee, int id) throws Exception {
        Employee employee1 = getById(id);
        if (employee1 == null) {
            throw new Exception("Employee not found!");
        }
        if (employee.getEmail() == null || employee.getEmail().length() == 0) {
            throw new Exception("Email is empty");
        }

        if (employee.getName() == null || employee.getName().length() == 0) {
            throw new Exception("Name is empty");
        }

        if (!validateEmail(employee.getEmail())) {
            throw new Exception("Email is invalid. Please provide a valid email");
        }

        Employee employee2 = getEmployeeByEmail(employee.getEmail());
        if (employee2 != null && employee2.getId() != employee1.getId()) {
            throw new Exception("Employee is already being used by another account");
        }
        Role role = roleService.getById(employee.getRole().getId());
        if (role == null) {
            throw new Exception("Role not found");
        }

        employee1.setName(employee.getName());
        employee1.setEmail(employee.getEmail());
        employee1.setRole(role);
        employee1.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
        employeeRepository.save(employee1);
        return new EmployeeResponse(employee1);

    }

    public boolean validateEmail(String emailStr) {
        Pattern VALID_EMAIL_ADDRESS_REGEX =
                Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    public LoginResponse login(LoginRequest request) throws Exception {
        if (request.getEmail() == null || request.getEmail().length() == 0) {
            throw new Exception("Email field is empty");
        }

        if (request.getPassword() == null || request.getPassword().length() == 0) {
            throw new Exception("Password is empty");
        }

        if (!validateEmail(request.getEmail())) {
            throw new Exception("Email is invalid. Please provide a valid email");
        }

        Employee employee1 = getEmployeeByEmail(request.getEmail());
        if (employee1 == null) {
            throw new Exception("Employee not found");
        }

        if (!employee1.verify(request.getPassword())) {
            throw new Exception("Password did not match");
        }

        final String token = jwtTokenUtil.generateToken(employee1, "employee");
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(token);
        loginResponse.setId(employee1.getId());
        loginResponse.setEmail(employee1.getEmail());
        loginResponse.setName(employee1.getName());

        return loginResponse;

    }

    public Employee getEmployeeByEmail(String email) {
        return employeeRepository.findByEmail(email);
    }

    //get user by Id
    public Employee getById(int id) {
        return employeeRepository.findById(id).orElse(null);
    }

    public EmployeeResponse getEmployeeById(int id) {
        return new EmployeeResponse(getById(id));
    }

    //get all employees
    public PagedResponse<EmployeeResponse> getAllEmployees(int itemsPerPage, int currentPage) {
        Pageable paging = PageRequest.of(currentPage - 1, itemsPerPage);
        Page<Employee> pagedResult = employeeRepository.findAll(paging);

        int totalPages = pagedResult.getTotalPages();
        List<EmployeeResponse> response = pagedResult.toList().stream().map(EmployeeResponse::new).collect(Collectors.toList());

        return new PagedResponse<>(totalPages, currentPage, itemsPerPage, response);
    }

    //delete an employee
    public EmployeeResponse deleteAnEmployee(int id) throws Exception {
        Employee employee = getById(id);
        if (employee == null) {
            throw new Exception("Employee isn't registered");
        }
        employeeRepository.delete(employee);
        return new EmployeeResponse(employee);
    }

    public EmployeeResponse updatePassword(EmployeeRequest request, int id) throws Exception {
        if (request == null) {
            throw new Exception("Data expected with this request.");
        }

        if (request.getPassword() == null) {
            throw new Exception("Password is missing");
        }

        if (request.getPasswordConfirmation() == null) {
            throw new Exception("Password confirmation is missing");
        }

        if (!request.getPasswordConfirmation().equals(request.getPassword())) {
            throw new Exception("Passwords do not match");
        }

        Employee employee = getById(id);
        if (employee == null) {
            throw new Exception("Employee not found");
        }

        employee.setPassword(request.getPassword());
        employeeRepository.save(employee);

        return new EmployeeResponse(employee);
    }


}
