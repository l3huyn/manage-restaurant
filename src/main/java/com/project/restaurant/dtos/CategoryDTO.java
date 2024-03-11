package com.project.restaurant.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data //Là hàm toString()
@Getter //Hàm getter
@Setter //Hàm Setter
@AllArgsConstructor //Hàm khởi tạo có đối số
@NoArgsConstructor //Hàm khởi tạo không đối số
public class CategoryDTO {
    //Vì trường ID có tự động nên không cần khai báo
    //Chỉ khai báo trường name là tên danh mục
    @NotEmpty(message = "Category's name cannot be empty") //Không cho để trống trường name,
    // bên trong có đối số là message dùng để hiển thị thông báo khi lỗi
    private String name;
}
