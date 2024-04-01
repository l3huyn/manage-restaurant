package com.project.restaurant.repositories;

import com.project.restaurant.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    //lấy doanh thu từ 0h0' đến hiện tại
    @Query("SELECT SUM(o.totalMoney) FROM Order o WHERE o.orderDate BETWEEN :startDate AND :endDate")
    Float getTotalRevenue(LocalDateTime startDate, LocalDateTime endDate);

    //lấy order chưa thanh toán
    List<Order> findByStatus(String status);
}
