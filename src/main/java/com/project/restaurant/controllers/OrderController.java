package com.project.restaurant.controllers;


import com.project.restaurant.dtos.OrderDTO;
import com.project.restaurant.models.Order;
import com.project.restaurant.responses.OrderListResponse;
import com.project.restaurant.responses.OrderResponse;
import com.project.restaurant.responses.RevenueResponse;
import com.project.restaurant.services.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    //DI
    private final OrderService orderService;

    @PostMapping("")
    public ResponseEntity<?> createOrder(@Valid @RequestBody OrderDTO orderDTO, BindingResult result) {
        try {
            //Hiển thị lỗi, lỗi được lưu ở biến result
            if(result.hasErrors()) {
                //Trả về mảng errorMessage kiểu String
                List<String> errorMessage =  result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessage);
            }
           Order newOrder = orderService.createOrder(orderDTO);
            return ResponseEntity.ok(newOrder);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("")
    public ResponseEntity<?> getOrders(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ) {
        PageRequest pageRequest = PageRequest.of(
                page, limit,
                //Sắp xếp theo thứ tự orderDate giảm dần
                Sort.by("orderDate").descending());

        Page<OrderResponse> orderPage = orderService.getAllOrders(pageRequest);

        int totalPage = orderPage.getTotalPages();

        List<OrderResponse> orders = orderPage.getContent();

        return ResponseEntity.ok(
                OrderListResponse.builder()
                        .orders(orders)
                        .totalPages(totalPage)
                        .build()
        );
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable("id") Long id) {
        try {
            Order existingOrder = orderService.getOrderById(id);
            return ResponseEntity.ok(OrderResponse.fromOrder(existingOrder));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(@PathVariable("id") Long id, @Valid @RequestBody OrderDTO orderDTO) {
        try {
            Order updateOrder = orderService.updateOrder(id, orderDTO);
            return ResponseEntity.ok(updateOrder);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable("id") Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok("Order deleted successfully.");
    }


    //thống kê doanh thu
    @GetMapping("/revenue")
    public ResponseEntity<RevenueResponse> getRevenueForToday() {
        Float revenue = orderService.getRevenueForToday();
        return ResponseEntity.ok(RevenueResponse.builder()
                        .revenue(revenue)
                .build());
    }

    //lấy những đơn hàng chưa thanh tóan (Đã đặt món)
    @GetMapping("/unpaid_orders")
    public ResponseEntity<List<Order>> getCurrentUnpaidOrders() {
        List<Order> orderList = orderService.gettUnpaidOrders();
        return ResponseEntity.ok(orderList);
    }
}
