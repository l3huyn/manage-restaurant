package com.project.restaurant.services;

import com.project.restaurant.dtos.EmployeeDTO;
import com.project.restaurant.exceptions.DataNotFoundException;
import com.project.restaurant.exceptions.DuplicateDataException;
import com.project.restaurant.models.Employee;
import com.project.restaurant.repositories.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService implements IEmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public Employee createEmployee(EmployeeDTO employeeDTO) throws DuplicateDataException {

        String phoneNumber = employeeDTO.getPhoneNumber();

        if(employeeRepository.existsByPhoneNumber(phoneNumber)) {
            throw new DuplicateDataException("Phone number already exists");
        }

        String email = employeeDTO.getEmail();

        if(employeeRepository.existsByEmail(email)) {
            throw new DuplicateDataException("Email already exists");
        }

        Employee newEmployee = Employee.builder()
                .fullName(employeeDTO.getFullName())
                .position(employeeDTO.getPosition())
                .phoneNumber(employeeDTO.getPhoneNumber())
                .email(employeeDTO.getEmail())
                .address(employeeDTO.getAddress())
                .hireDate(employeeDTO.getHireDate())
                .salary(employeeDTO.getSalary())
                .build();

        //Lưu vào DB
        return employeeRepository.save(newEmployee);
    }

    @Override
    public Employee getEmployeeById(long id) throws Exception {

        return employeeRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Cannot find employee with ID: " + id));
    }

    @Override
    public Page<Employee> getAllEmployees(PageRequest pageRequest) {
        return employeeRepository.findAll(pageRequest);
    }

    @Override
    public Employee updateEmployee(long id, EmployeeDTO employeeDTO) throws Exception {
        Employee existingEmployee = getEmployeeById(id);

        if(existingEmployee != null) {
            existingEmployee.setFullName(employeeDTO.getFullName());
            existingEmployee.setPosition(employeeDTO.getPosition());
            existingEmployee.setPhoneNumber(employeeDTO.getPhoneNumber());
            existingEmployee.setEmail(employeeDTO.getEmail());
            existingEmployee.setAddress(employeeDTO.getAddress());
            existingEmployee.setHireDate(employeeDTO.getHireDate());
            existingEmployee.setSalary(employeeDTO.getSalary());
        }
        return employeeRepository.save(existingEmployee);
    }

    @Override
    public void deleteEmployee(long id) {
        Optional<Employee> deleteEmployee = employeeRepository.findById(id);

       deleteEmployee.ifPresent(employeeRepository::delete);
    }
}
