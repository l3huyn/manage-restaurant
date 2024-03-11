package com.project.restaurant.services;

import com.project.restaurant.dtos.CustomerDTO;
import com.project.restaurant.exceptions.DataNotFoundException;
import com.project.restaurant.exceptions.DuplicateDataException;
import com.project.restaurant.models.Customer;
import com.project.restaurant.models.CustomerClass;
import com.project.restaurant.models.Product;
import com.project.restaurant.repositories.CustomerRepository;
import com.project.restaurant.responses.CustomerResponse;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService implements ICustomerService{

    //DI
    private final CustomerRepository customerRepository;

    @Override
    public Customer createCustomer(CustomerDTO customerDTO) throws DuplicateDataException {
        String phoneNumber = customerDTO.getPhoneNumber();

        if(customerRepository.existsByPhoneNumber(phoneNumber)) {
            throw new DuplicateDataException("Phone number already exists");
        }

        String email = customerDTO.getEmail();

        if(customerRepository.existsByEmail(email)) {
            throw new DuplicateDataException("Email already exists");
        }

        Customer newCustomer = Customer.builder()
                .fullName(customerDTO.getFullName())
                .phoneNumber(customerDTO.getPhoneNumber())
                .email(customerDTO.getEmail())
                .address(customerDTO.getAddress())
                .point(customerDTO.getPoint())
                .classOfCustomer(CustomerClass.SILVER)
                .build();

        //Save DB
        return customerRepository.save(newCustomer);
    }

    @Override
    public Page<CustomerResponse> getAllCustomers(PageRequest pageRequest) {
        return customerRepository.findAll(pageRequest)
                .map(CustomerResponse::fromCustomer);
    }

    @Override
    public Customer getCustomerById(long id) throws Exception {
        return customerRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Cannot find customer with ID: " + id));

    }

    @Override
    public Customer updateCustomer(long id, CustomerDTO customerDTO) throws Exception {
        //Tìm xem khách hàng có tồn tại hay không
        Customer existingCustomer = getCustomerById(id);

        if(existingCustomer != null) {
            existingCustomer.setFullName(customerDTO.getFullName());
            existingCustomer.setPhoneNumber(customerDTO.getPhoneNumber());
            existingCustomer.setEmail(customerDTO.getEmail());
            existingCustomer.setAddress(customerDTO.getAddress());
            existingCustomer.setPoint(customerDTO.getPoint());

        }
        return customerRepository.save(existingCustomer);
    }

    @Override
    public void deleteCustomer(long id) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);

        //Nếu optionalProduct có tồn tại => Xóa
        optionalCustomer.ifPresent(customerRepository::delete);
    }

}
