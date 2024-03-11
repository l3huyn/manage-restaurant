package com.project.restaurant.services;

import com.project.restaurant.dtos.OrderDTO;
import com.project.restaurant.exceptions.DataNotFoundException;
import com.project.restaurant.models.Customer;
import com.project.restaurant.models.DiningTable;
import com.project.restaurant.models.Employee;
import com.project.restaurant.models.Order;
import com.project.restaurant.repositories.CustomerRepository;
import com.project.restaurant.repositories.DiningTableRepository;
import com.project.restaurant.repositories.EmployeeRepository;
import com.project.restaurant.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService{

    //DI
    private final OrderRepository orderRepository;

    private final DiningTableRepository diningTableRepository;

    private final CustomerRepository customerRepository;

    private final EmployeeRepository employeeRepository;

    @Override
    public Order createOrder(OrderDTO orderDTO) throws Exception {
        //Tìm xem có tồn tại bàn đó hay không?
        DiningTable existingDiningTable = diningTableRepository.findById(orderDTO.getDiningTableId())
                .orElseThrow(() ->
                        new DataNotFoundException("Cannot find dining table with ID: "
                                + orderDTO.getDiningTableId()));


        //Tìm xem khách hàng có tồn tại hay không?
        Customer existingCustomer = customerRepository.findById(orderDTO.getCustomerId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find customer with ID: "
                        + orderDTO.getCustomerId()));


        //Tìm xem nhân viên có tồn tại hay không?
        Employee existingEmployee = employeeRepository.findById(orderDTO.getEmployeeId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find employee with ID: "
                        + orderDTO.getEmployeeId()));


        Order newOrder = Order.builder()
                .diningTable(existingDiningTable)
                .customer(existingCustomer)
                .fullName(orderDTO.getFullName())
                .phoneNumber(orderDTO.getPhoneNumber())
                .note(orderDTO.getNote())
                .orderDate(new Date())
                .status(orderDTO.getStatus())
                .totalMoney(orderDTO.getTotalMoney())
                .paymentMethod(orderDTO.getPaymentMethod())
                .discount(orderDTO.getDiscount())
                .theDiscountedPrice(orderDTO.getTheDiscountedPrice())
                .shippingAddress(orderDTO.getShippingAddress())
                .employee(existingEmployee)
                .active(true)
                .build();


        //Lưu vào DB
        orderRepository.save(newOrder);

        return newOrder;
    }

    @Override
    public List<Order> findByCustomerId(Long customerId) {
        return null;
    }

    @Override
    public List<Order> getAllOrders(PageRequest pageRequest) {
        return null;
    }

    @Override
    public Order updateOrder(Long id, OrderDTO orderDTO) {
        return null;
    }

    @Override
    public void deleteOrder(Long id) {

    }
}
