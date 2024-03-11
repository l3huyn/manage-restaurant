package com.project.restaurant.models;

import jakarta.persistence.*;
import lombok.*;

@Entity //Annotation đánh dấu một class là Entity
@Table(name = "product_images") //Annotation dùng để chỉ định tên bảng trong CSDL mà Entity tương tác
@Builder //Annotation tự động sinh code cho việc xây dựng các phương thức khởi tạo (constructor) cho một lớp
@Getter //Hàm getter
@Setter //Hàm Setter
@AllArgsConstructor //Hàm khởi tạo có đối số
@NoArgsConstructor //Hàm khởi tạo không đối số
public class ProductImage {

    //Khai báo thuộc tính MAXIMUM_IMAGES_PER_PRODUCT (5 ảnh trên 1 sản phẩm)
    public static final int MAXIMUM_IMAGES_PER_PRODUCT = 5;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;


    @Column(name = "image_url", length = 380)
    private String imageUrl;
}
