package com.project.restaurant.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.restaurant.dtos.OrderDetailDTO;
import com.project.restaurant.models.OrderDetail;
import jakarta.validation.constraints.Min;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailResponse {
    private Long id;

    private Long orderId;

    private Long productId;

    private String productName;

    private Float price;

    private int numberOfProducts;

    private Float totalMoney;

    public static OrderDetailResponse fromOrderDetail(OrderDetail orderDetail) {
        return OrderDetailResponse.builder()
                .id(orderDetail.getId())
                .orderId(orderDetail.getOrder().getId())
                .productId(orderDetail.getProduct().getId())
                .productName(orderDetail.getProduct().getName())
                .price(orderDetail.getPrice())
                .numberOfProducts(orderDetail.getNumberOfProducts())
                .totalMoney(orderDetail.getTotalMoney())
                .build();
    }

}
