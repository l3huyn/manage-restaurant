package com.project.restaurant.repositories;

import com.project.restaurant.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    //Hàm kiểm tra SĐT có tồn tại trên DB
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByEmail(String email);
}
