package com.project.restaurant.models;


import jakarta.persistence.*;
import lombok.*;

@Entity //Annotation đánh dấu một class là Entity
@Table(name = "customers") //Annotation dùng để chỉ định tên bảng trong CSDL mà Entity tương tác
@Builder //Annotation tự động sinh code cho việc xây dựng các phương thức khởi tạo (constructor) cho một lớp
@Getter //Hàm getter
@Setter //Hàm Setter
@AllArgsConstructor //Hàm khởi tạo có đối số
@NoArgsConstructor //Hàm khởi tạo không đối số
public class Customer extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fullname", nullable = true, length = 100)
    private String fullName;

    @Column(name = "phone_number", nullable = false, length = 20)
    private String phoneNumber;

    @Column(name = "email", nullable = true, length = 100)
    private String email;

    @Column(name = "address", nullable = true, length = 200)
    private String address;

    @Column(name = "point")
    private Float point;

    @Column(name = "class", nullable = true, length = 50)
    private String classOfCustomer;
}
