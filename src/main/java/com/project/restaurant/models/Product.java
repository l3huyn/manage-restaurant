package com.project.restaurant.models;

import jakarta.persistence.*;
import lombok.*;

@Entity //Annotation đánh dấu một class là Entity
@Table(name = "products") //Annotation dùng để chỉ định tên bảng trong CSDL mà Entity tương tác
@Builder //Annotation tự động sinh code cho việc xây dựng các phương thức khởi tạo (constructor) cho một lớp
@Getter //Hàm getter
@Setter //Hàm Setter
@AllArgsConstructor //Hàm khởi tạo có đối số
@NoArgsConstructor //Hàm khởi tạo không đối số
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 350)
    private String name;

    @Column(name= "thumbnail", nullable = true, length = 350)
    private String thumbnail;

    private Float price;

    @Column(name="quantity_sold")
    private int quantitySold;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
