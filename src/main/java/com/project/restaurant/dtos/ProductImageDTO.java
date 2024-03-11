package com.project.restaurant.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data //Là hàm toString()
@Getter //Hàm getter
@Setter //Hàm Setter
@AllArgsConstructor //Hàm khởi tạo có đối số
@NoArgsConstructor //Hàm khởi tạo không đối số
@Builder
public class ProductImageDTO {
    @JsonProperty("product_id")
    @Min(value = 1, message = "Product's ID must be > 1")
    private Long productId;

    @Size(min = 5, max = 200, message = "Image's name must between 5 and 200 characters")
    @JsonProperty("image_url")
    private String imageUrl;
}
