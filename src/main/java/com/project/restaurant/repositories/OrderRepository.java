package com.project.restaurant.repositories;

import com.project.restaurant.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    //Tìm đơn hàng của một customer nào đó
    List<Order> findByCustomerId(Long customerId);
}
