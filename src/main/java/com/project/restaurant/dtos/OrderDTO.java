package com.project.restaurant.dtos;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class OrderDTO {
    @JsonProperty("table_id")
    private Long diningTableId;

    @JsonProperty("fullname")
    private String fullName;

    @JsonProperty("phone_number")
    private String phoneNumber;

    private String note;

    private String status;

    @JsonProperty("total_money")
    private Float totalMoney;

    @JsonProperty("payment_method")
    private String paymentMethod;

    private Float discount;

    @JsonProperty("the_discounted_price")
    private Float theDiscountedPrice;

    @JsonProperty("shipping_address")
    private String shippingAddress;

    @JsonProperty("employee_id")
    private Long employeeId;

}
