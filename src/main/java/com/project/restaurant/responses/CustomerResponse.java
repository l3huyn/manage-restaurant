package com.project.restaurant.responses;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.restaurant.models.Customer;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter //Hàm getter
@Setter //Hàm Setter
@AllArgsConstructor //Hàm khởi tạo có đối số
@NoArgsConstructor //Hàm khởi tạo không đối số
@Builder //Annotation tự động sinh code cho việc xây dựng các phương thức khởi tạo (constructor) cho một lớp
public class CustomerResponse extends BaseResponse{
    @JsonProperty("fullname")
    private String fullName;

    @JsonProperty("phone_number")
    private String phoneNumber;

    private String email;

    private String address;

    private Float point;

    @JsonProperty("class")
    private String classOfCustomer;

    public static CustomerResponse fromCustomer(Customer customer) {
        CustomerResponse customerResponse = CustomerResponse.builder()
                .fullName(customer.getFullName())
                .phoneNumber(customer.getPhoneNumber())
                .email(customer.getEmail())
                .address(customer.getAddress())
                .point(customer.getPoint())
                .classOfCustomer(customer.getClassOfCustomer())
                .build();

        customerResponse.setCreatedAt(customer.getCreatedAt());
        customerResponse.setUpdatedAt(customer.getUpdatedAt());

        return customerResponse;
    }
}
