package com.project.restaurant.responses;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.restaurant.models.Product;
import lombok.*;

@Getter //Hàm getter
@Setter //Hàm Setter
@AllArgsConstructor //Hàm khởi tạo có đối số
@NoArgsConstructor //Hàm khởi tạo không đối số
@Builder //Annotation tự động sinh code cho việc xây dựng các phương thức khởi tạo (constructor) cho một lớp


//-- Thư mục Response dùng để lưu trữ thông tin, định nghĩa đối tượng trả về.
//-- Tùy biến Response để hiển thị cho người dùng (Không dùng Model và DTO để hiển thị ra tất cả)
//-- Cách viết khá giống DTO. Vì nó là giá trị trả về nên không cần Validate
public class ProductResponse extends BaseResponse{
    private String name;

    private String thumbnail;

    private Float price;

    @JsonProperty("quantity_sold")
    private int quantitySold;

    private String status;

    @JsonProperty("category_id")
    private Long categoryId;

    public static ProductResponse fromProduct(Product product) {
        ProductResponse productResponse = ProductResponse.builder()
                .name(product.getName())
                .thumbnail(product.getThumbnail())
                .price(product.getPrice())
                .quantitySold(product.getQuantitySold())
                .status(product.getStatus())
                .categoryId(product.getCategory().getId())
                .build();
        productResponse.setCreatedAt(product.getCreatedAt());
        productResponse.setUpdatedAt(product.getUpdatedAt());

        return productResponse;
    }
}
