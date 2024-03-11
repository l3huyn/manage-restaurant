package com.project.restaurant.controllers;

import com.github.javafaker.Faker;
import com.project.restaurant.dtos.CustomerDTO;
import com.project.restaurant.models.Customer;
import com.project.restaurant.models.CustomerClass;
import com.project.restaurant.responses.CustomerListResponse;
import com.project.restaurant.responses.CustomerResponse;
import com.project.restaurant.services.CustomerService;
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
@RequestMapping("api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    //DI
    private final CustomerService customerService;

    //HÀM TẠO KHÁCH HÀNG MỚI
    @PostMapping("")
    public ResponseEntity<?> createCustomer(@Valid @RequestBody CustomerDTO customerDTO, BindingResult result) {
        try {
            //Hiển thị lỗi, lỗi được lưu ở biến result
            if(result.hasErrors()) {
                //Trả về mảng errorMessage kiểu String
                List<String> errorMessage =  result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessage);
            }

            Customer newCustomer = customerService.createCustomer(customerDTO);

            return ResponseEntity.ok(newCustomer);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    //HÀM LẤY TẤT CẢ CÁC KHÁCH HÀNG
    @GetMapping("")
    public ResponseEntity<CustomerListResponse> getCustomers(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ){
        //Tạo Pageable từ thông tin của page và limit
        //pageRequest được tạo ra bằng cách truyền 2 tham số page và limit
        PageRequest pageRequest = PageRequest.of(
                page, limit,
                //Sắp xếp theo thứ tự createdAt giảm dần
                Sort.by("createdAt").descending());

        //Lấy ra danh sách khách hàng
        Page<CustomerResponse> customerPage = customerService.getAllCustomers(pageRequest);

        //Lấy tổng số trang
        int totalPages = customerPage.getTotalPages();

        //Lấy ra danh sách các Customer
        List<CustomerResponse> customers = customerPage.getContent();

        return ResponseEntity.ok(CustomerListResponse.builder()
                .customers(customers)
                .totalPages(totalPages)
                .build());
    }



    //HÀM LẤY KHÁCH HÀNG BẰNG ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable("id") Long id) {
        try {
            Customer existingCustomer = customerService.getCustomerById(id);
            return ResponseEntity.ok(CustomerResponse.fromCustomer(existingCustomer));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    //HÀM CẬP NHẬT MỘT KHÁCH HÀNG
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable("id") Long id, @RequestBody CustomerDTO customerDTO) {
        try {
            Customer updateCustomer = customerService.updateCustomer(id, customerDTO);
            return ResponseEntity.ok(updateCustomer);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    //HÀM XÓA KHÁCH HÀNG
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable("id") Long id) {
        try {
            Customer existingCustomer = customerService.getCustomerById(id);
            if(existingCustomer != null) {
                customerService.deleteCustomer(id);
            }

            return ResponseEntity.ok("Deleted customer successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    /*Fake data customer*/
//    @PostMapping("/generateFakeCustomers")
    private ResponseEntity<String> generateFakeCustomers() {
        Faker faker = new Faker();


        for(int i = 0; i < 50; i++) {
            String fullName = faker.name().firstName();


            CustomerDTO customerDTO = CustomerDTO.builder()
                    .fullName(fullName)
                    .phoneNumber(faker.phoneNumber().cellPhone())
                    .email(faker.internet().emailAddress())
                    .address(faker.address().fullAddress())
                    .point((float)faker.number().numberBetween(1, 100))
                    .classOfCustomer(CustomerClass.SILVER)
                    .build();

            try {
                customerService.createCustomer(customerDTO);
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }

        return ResponseEntity.ok("Fake customer successfully");
    }
}
