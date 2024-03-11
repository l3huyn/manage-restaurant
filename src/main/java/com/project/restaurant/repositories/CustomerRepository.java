package com.project.restaurant.repositories;

import com.project.restaurant.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByEmail(String email);
}
