package com.project.restaurant.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data //Là hàm toString()
@AllArgsConstructor //Hàm khởi tạo có đối số
@NoArgsConstructor //Hàm khởi tạo không đối số
@Builder //Annotation tự động sinh code cho việc xây dựng các phương thức khởi tạo (constructor) cho một lớp

//--Response dưới dạng danh sách. Trong TH này: Hiển thị Response danh sách sản phẩm
public class ProductListResponse {
    //Danh sách các ProductResponse
    private List<ProductResponse> products;

    //Tổng trang
    private int totalPages;
}
