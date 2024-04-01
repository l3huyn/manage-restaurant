package com.project.restaurant.responses;

import com.project.restaurant.models.Order;
import lombok.*;

import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {
    private Long diningTableId;

    private String tableNumber;

    private String fullName;

    private String phoneNumber;

    private Date orderDate;

    private String status;

    private Float totalMoney;

    private String paymentMethod;

    private Float discount;

    private Float theDiscountedPrice;

    private String shippingAddress;

    private String employeeName;

    public static OrderResponse fromOrder(Order order) {
        OrderResponse orderResponse = OrderResponse.builder()
                .diningTableId(order.getDiningTable().getId())
                .tableNumber(order.getDiningTable().getTableNumber())
                .fullName(order.getFullName())
                .phoneNumber(order.getPhoneNumber())
                .orderDate(order.getOrderDate())
                .status(order.getStatus())
                .totalMoney(order.getTotalMoney())
                .paymentMethod(order.getPaymentMethod())
                .discount(order.getDiscount())
                .theDiscountedPrice(order.getTheDiscountedPrice())
                .shippingAddress(order.getShippingAddress())
                .employeeName(order.getEmployee().getFullName())
                .build();

        return orderResponse;
    }
}
