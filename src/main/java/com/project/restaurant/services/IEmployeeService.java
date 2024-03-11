package com.project.restaurant.services;

import com.project.restaurant.dtos.EmployeeDTO;
import com.project.restaurant.exceptions.DataNotFoundException;
import com.project.restaurant.exceptions.DuplicateDataException;
import com.project.restaurant.models.Employee;
import com.project.restaurant.responses.CustomerResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface IEmployeeService {
    Employee createEmployee(EmployeeDTO employeeDTO) throws DuplicateDataException;

    Employee getEmployeeById(long id) throws Exception;

    Page<Employee> getAllEmployees(PageRequest pageRequest);

    Employee updateEmployee(long id, EmployeeDTO employeeDTO) throws Exception;

    void deleteEmployee(long id);

}
