package com.project.restaurant.services;

import com.project.restaurant.dtos.OrderDTO;
import com.project.restaurant.exceptions.DataNotFoundException;
import com.project.restaurant.models.Order;
import org.hibernate.query.Page;
import org.springframework.data.domain.PageRequest;

import java.awt.print.Pageable;
import java.util.List;

public interface IOrderService {

    //Hàm tạo đơn hàng
    Order createOrder(OrderDTO orderDTO) throws Exception;

    //Hàm lấy danh sách đơn hàng theo ID khách hàng (customerId)
    List<Order> findByCustomerId(Long customerId);

    //Lấy danh sách các đơn hàng -> Thu ngân & Chủ nhà hàng
    List<Order> getAllOrders(PageRequest pageRequest);

    //Cập nhật đơn hàng
    Order updateOrder(Long id, OrderDTO orderDTO);

    //Xóa đơn hàng -> xóa mềm
    void deleteOrder(Long id);
}
