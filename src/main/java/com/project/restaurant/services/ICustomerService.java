package com.project.restaurant.services;

import com.project.restaurant.dtos.CustomerDTO;
import com.project.restaurant.exceptions.DataNotFoundException;
import com.project.restaurant.exceptions.DuplicateDataException;
import com.project.restaurant.models.Customer;
import com.project.restaurant.responses.CustomerResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface ICustomerService {
    Customer createCustomer(CustomerDTO customerDTO) throws DuplicateDataException;

    Page<CustomerResponse> getAllCustomers(PageRequest pageRequest);

    Customer getCustomerById(long id) throws Exception;

    Customer updateCustomer(long id, CustomerDTO customerDTO) throws Exception;

    void deleteCustomer(long id);


}
