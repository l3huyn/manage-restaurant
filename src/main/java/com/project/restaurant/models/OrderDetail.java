package com.project.restaurant.models;


import jakarta.persistence.*;
import lombok.*;

@Entity //Annotation đánh dấu một class là Entity
@Table(name = "order_details") //Annotation dùng để chỉ định tên bảng trong CSDL mà Entity tương tác
@Builder //Annotation tự động sinh code cho việc xây dựng các phương thức khởi tạo (constructor) cho một lớp
@Getter //Hàm getter
@Setter //Hàm Setter
@AllArgsConstructor //Hàm khởi tạo có đối số
@NoArgsConstructor //Hàm khởi tạo không đối số
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "price", nullable = false)
    private Float price;

    @Column(name = "number_of_products", nullable = false)
    private int numberOfProducts;

    @Column(name = "total_money", nullable = false)
    private Float totalMoney;
}
