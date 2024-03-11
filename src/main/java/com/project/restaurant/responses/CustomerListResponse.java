package com.project.restaurant.responses;

import com.project.restaurant.models.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data //Là hàm toString()
@AllArgsConstructor //Hàm khởi tạo có đối số
@NoArgsConstructor //Hàm khởi tạo không đối số
@Builder //Annotation tự động sinh code cho việc xây dựng các phương thức khởi tạo (constructor) cho một lớp

public class CustomerListResponse {
    private List<CustomerResponse> customers;

    private int totalPages;
}
