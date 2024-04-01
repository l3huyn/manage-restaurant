package com.project.restaurant.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity //Annotation đánh dấu một class là Entity
@Table(name = "orders") //Annotation dùng để chỉ định tên bảng trong CSDL mà Entity tương tác
@Builder //Annotation tự động sinh code cho việc xây dựng các phương thức khởi tạo (constructor) cho một lớp
@Getter //Hàm getter
@Setter //Hàm Setter
@AllArgsConstructor //Hàm khởi tạo có đối số
@NoArgsConstructor //Hàm khởi tạo không đối số
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "table_id")
    private DiningTable diningTable;

    @Column(name = "fullname", length = 100)
    private String fullName;

    @Column(name = "phone_number", nullable = false, length = 100)
    private String phoneNumber;

    @Column(name = "note", length = 100)
    private String note;

    @Column(name="order_date")
    private Date orderDate;

    @Column(name = "status")
    private String status;

    @Column(name = "total_money")
    private Float totalMoney;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "discount")
    private Float discount;

    @Column(name = "the_discounted_price")
    private Float theDiscountedPrice;

    @Column(name = "shipping_address")
    private String shippingAddress;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private User employee;

    @Column(name = "active")
    private Boolean active; //thuộc về admin

}
