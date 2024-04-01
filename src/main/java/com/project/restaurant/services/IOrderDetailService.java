package com.project.restaurant.services;

import com.project.restaurant.dtos.OrderDetailDTO;
import com.project.restaurant.exceptions.DataNotFoundException;
import com.project.restaurant.models.OrderDetail;

import java.util.List;

public interface IOrderDetailService {
    OrderDetail createOrderDetail(OrderDetailDTO orderDetailDTO) throws Exception;

    OrderDetail getOrderDetail(Long id) throws DataNotFoundException;

    OrderDetail updateOrderDetail(Long id, OrderDetailDTO orderDetailDTO) throws DataNotFoundException;

    void deleteById(Long id);

    List<OrderDetail> findByOrderId(Long orderId);
}
