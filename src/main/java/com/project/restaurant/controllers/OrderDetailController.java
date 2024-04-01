package com.project.restaurant.controllers;


import com.project.restaurant.dtos.OrderDetailDTO;
import com.project.restaurant.exceptions.DataNotFoundException;
import com.project.restaurant.models.Order;
import com.project.restaurant.models.OrderDetail;
import com.project.restaurant.responses.OrderDetailResponse;
import com.project.restaurant.services.OrderDetailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/order_details")
@RequiredArgsConstructor
public class OrderDetailController {

    //DI
    private final OrderDetailService orderDetailService;

    @PostMapping("")
    public ResponseEntity<?> createOrderDetail(
            @Valid @RequestBody OrderDetailDTO orderDetailDTO, BindingResult result
    ) {
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

            OrderDetail orderDetail = orderDetailService.createOrderDetail(orderDetailDTO);
            return ResponseEntity.ok(OrderDetailResponse.fromOrderDetail(orderDetail));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    //Hàm lấy một order detail bằng id
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderDetail(@PathVariable("id") Long id) {
        try {
            OrderDetail orderDetail = orderDetailService.getOrderDetail(id);
//            return ResponseEntity.ok(orderDetail);
            return ResponseEntity.ok(OrderDetailResponse.fromOrderDetail(orderDetail));

        } catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    //Hàm lấy danh sách các order_details của một order nào đó
    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<OrderDetailResponse>> getOrderDetails(@Valid @PathVariable("orderId") Long orderId) {
        List<OrderDetail> orderDetails = orderDetailService.findByOrderId(orderId);

        //Convert danh sách OrderDetail sang danh sách OrderDetailResponse
        List<OrderDetailResponse> orderDetailResponses = orderDetails
                .stream().map(OrderDetailResponse::fromOrderDetail).toList();

        //Trả về danh sách OrderDetailResponse
        return ResponseEntity.ok(orderDetailResponses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrderDetail(
            @Valid @PathVariable("id") Long id,
            @RequestBody OrderDetailDTO orderDetailDTO
    ) {
        try {
            OrderDetail orderDetail = orderDetailService.updateOrderDetail(id, orderDetailDTO);
            return ResponseEntity.ok(OrderDetailResponse.fromOrderDetail(orderDetail));
        } catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrderDetail(@Valid @PathVariable("id") Long id) {
        orderDetailService.deleteById(id);
        return ResponseEntity.ok().body("Deleted order detail with id: " + id + " successfully");
    }
}
