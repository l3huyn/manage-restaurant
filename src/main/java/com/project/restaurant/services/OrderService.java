package com.project.restaurant.services;

import com.project.restaurant.dtos.OrderDTO;
import com.project.restaurant.exceptions.DataNotFoundException;
import com.project.restaurant.models.*;
import com.project.restaurant.repositories.DiningTableRepository;
import com.project.restaurant.repositories.UserRepository;
import com.project.restaurant.repositories.OrderRepository;
import com.project.restaurant.responses.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService{

    //DI
    private final OrderRepository orderRepository;

    private final DiningTableRepository diningTableRepository;

    private final UserRepository userRepository;

    @Override
    public Order createOrder(OrderDTO orderDTO) throws Exception {
        //Tìm xem có tồn tại bàn đó hay không?
        DiningTable existingDiningTable = diningTableRepository.findById(orderDTO.getDiningTableId())
                .orElseThrow(() ->
                        new DataNotFoundException("Cannot find dining table with ID: "
                                + orderDTO.getDiningTableId()));


        //Tìm xem nhân viên có tồn tại hay không?
        User existingUser = userRepository.findById(orderDTO.getEmployeeId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find employee with ID: "
                        + orderDTO.getEmployeeId()));


        //Tính tiền sau giảm giá
        Float totalMoney = orderDTO.getTotalMoney();

        //Lấy discount
        Float discount = orderDTO.getDiscount();

        //Kiểm tra nếu không có discount -> gán = 0
        if(discount == null) {
            discount = (float) 0;
        }

        //Tổng tiền sau giảm giá
        Float theDiscountedPrice = totalMoney - discount;


        Order newOrder = Order.builder()
                .diningTable(existingDiningTable)
                .fullName(orderDTO.getFullName())
                .phoneNumber(orderDTO.getPhoneNumber())
                .note(orderDTO.getNote())
                .orderDate(new Date())
                .status(OrderStatus.ORDERED)
                .totalMoney(totalMoney)
                .paymentMethod(orderDTO.getPaymentMethod())
                .discount(orderDTO.getDiscount())
                .theDiscountedPrice(theDiscountedPrice)
                .shippingAddress(orderDTO.getShippingAddress())
                .employee(existingUser)
                .active(true)
                .build();

        //Set trạng thái bàn thành "Đang sử dụng"
        existingDiningTable.setStatus(DiningTableStatus.INUSE);

        //Lưu vào DB
        orderRepository.save(newOrder);

        return newOrder;
    }

    @Override
    public Page<OrderResponse> getAllOrders(PageRequest pageRequest) {
        return orderRepository.findAll(pageRequest)
                .map(OrderResponse::fromOrder);
    }

    @Override
    public Order getOrderById(Long id) throws Exception {
        return orderRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Cannot find order with ID: " + id));
    }

    @Override
    public Order updateOrder(Long id, OrderDTO orderDTO) throws Exception {
        //Lấy đơn hàng bằng id
        Order existingOrder = getOrderById(id);

        //tìm bàn ăn
       DiningTable existingDiningTable = diningTableRepository
               .findById(orderDTO.getDiningTableId())
               .orElseThrow(() -> new DataNotFoundException("Cannot find dining table"));

        //Tìm xem nhân viên có tồn tại hay không?
        User existingUser = userRepository.findById(orderDTO.getEmployeeId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find employee with ID: "
                        + orderDTO.getEmployeeId()));

        //Tính tiền sau giảm giá
        Float totalMoney = orderDTO.getTotalMoney();

        //Lấy discount
        Float discount = orderDTO.getDiscount();

        //Kiểm tra nếu không có discount -> gán = 0
        if(discount == null) {
            discount = (float) 0;
        }

        //Tổng tiền sau giảm giá
        Float theDiscountedPrice = totalMoney - discount;

        //lấy tình trạng đơn , nếu "đã thanh toán" -> set bàn ăn về empty
        String status = orderDTO.getStatus();

        if(status.equals("Đã thanh toán")) {
            existingDiningTable.setStatus(DiningTableStatus.EMPTY);
            diningTableRepository.save(existingDiningTable);
        }

        if(existingOrder != null) {
            existingOrder.setDiningTable(existingDiningTable);
            existingOrder.setFullName(orderDTO.getFullName());
            existingOrder.setPhoneNumber(orderDTO.getPhoneNumber());
            existingOrder.setNote(orderDTO.getNote());
            existingOrder.setStatus(status);
            existingOrder.setTotalMoney(totalMoney);
            existingOrder.setPaymentMethod(orderDTO.getPaymentMethod());
            existingOrder.setDiscount(discount);
            existingOrder.setTheDiscountedPrice(theDiscountedPrice);
            existingOrder.setShippingAddress(orderDTO.getShippingAddress());
            existingOrder.setEmployee(existingUser);
            return orderRepository.save(existingOrder);
        }
       return existingOrder;
    }

    @Override
    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id).orElse(null);
        //Xóa mềm
        if(order != null) {
            order.setActive(false);
            orderRepository.save(order);
        }
    }

    @Override
    public Float getRevenueForToday() {
        LocalDateTime startTime = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endTime = LocalDateTime.now();
        return orderRepository.getTotalRevenue(startTime, endTime);
    }

    @Override
    public List<Order> gettUnpaidOrders() {
        return orderRepository.findByStatus(OrderStatus.ORDERED);
    }
}
