package com.pm.ecommerce.account_service.repositories;

import com.pm.ecommerce.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Integer> {
    Employee findByEmail(String email);
}
