package com.project.restaurant.services;

import com.project.restaurant.dtos.OrderDTO;
import com.project.restaurant.exceptions.DataNotFoundException;
import com.project.restaurant.models.Order;
import com.project.restaurant.responses.OrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.awt.print.Pageable;
import java.util.List;

public interface IOrderService {

    //Hàm tạo đơn hàng
    Order createOrder(OrderDTO orderDTO) throws Exception;


    //Lấy danh sách các đơn hàng -> Thu ngân & Chủ nhà hàng
    Page<OrderResponse> getAllOrders(PageRequest pageRequest);

    //Lấy 1 đơn hàng bằng id
    Order getOrderById(Long id) throws Exception;


    //Cập nhật đơn hàng
    Order updateOrder(Long id, OrderDTO orderDTO) throws Exception;

    //Xóa đơn hàng -> xóa mềm
    void deleteOrder(Long id);

    Float getRevenueForToday();

    List<Order> gettUnpaidOrders();
}
