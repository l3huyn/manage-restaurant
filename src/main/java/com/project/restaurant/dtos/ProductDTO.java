package com.project.restaurant.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data //Là hàm toString()
@Getter //Hàm getter
@Setter //Hàm Setter
@AllArgsConstructor //Hàm khởi tạo có đối số
@NoArgsConstructor //Hàm khởi tạo không đối số
@Builder
public class ProductDTO {
    @NotBlank(message = "Title is required") //Annotation để kiểm tra xe chuỗi có rỗng hay không của trường name
    @Size(min = 3, max = 200, message = "Title must between 3 and 200 characters") //Annotation kiểm tra kích thước của trường name
    private String name;

    private String thumbnail;

    @Min(value = 0, message = "Price must be greater than or equal to 0") //Annotation min của giá
    @Max(value = 10000000, message = "Price must be less than or equal to 10000000") //Annotation max của giá
    private Float price;

    @JsonProperty("quantity_sold")
    private int quantitySold;

    private String status;

    @JsonProperty("category_id")
    private Long categoryId;
}
