package com.project.restaurant.controllers;

import com.project.restaurant.dtos.EmployeeDTO;
import com.project.restaurant.models.Employee;
import com.project.restaurant.services.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping("")
    public ResponseEntity<?> createEmployee(
            @Valid @RequestBody EmployeeDTO employeeDTO,
            BindingResult result
    ) {

        try {
            if(result.hasErrors()) {
                //Trả về mảng errorMessage kiểu String
                List<String> errorMessage =  result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessage);
            }

            employeeService.createEmployee(employeeDTO);

            return ResponseEntity.ok("Created employee successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable("id") Long id) {
        try {
            Employee existingEmployee = employeeService.getEmployeeById(id);
            return ResponseEntity.ok(existingEmployee);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("")
    public ResponseEntity<?> getEmployees(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ) {
        PageRequest pageRequest = PageRequest.of(
                page, limit,
                //Sắp xếp theo thứ tự createdAt giảm dần
                Sort.by("hireDate").descending());

        Page<Employee> employeePage = employeeService.getAllEmployees(pageRequest);

        int totalPages = employeePage.getTotalPages();

        List<Employee> employees = employeePage.getContent();

        return ResponseEntity.ok(employees);
    }



    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable("id") Long id, @RequestBody EmployeeDTO employeeDTO) {
        try {
            Employee updateEmployee = employeeService.updateEmployee(id, employeeDTO);
            return ResponseEntity.ok(updateEmployee);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable("id") Long id) {
        try {
            Employee existingEmployee = employeeService.getEmployeeById(id);
            if(existingEmployee != null) {
                employeeService.deleteEmployee(id);
            }
            return ResponseEntity.ok("Deleted employee successfully with ID: " + id);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
