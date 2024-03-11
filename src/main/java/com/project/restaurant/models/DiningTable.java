package com.project.restaurant.models;

import jakarta.persistence.*;
import lombok.*;

@Entity //Annotation đánh dấu một class là Entity
@Table(name = "dining_tables") //Annotation dùng để chỉ định tên bảng trong CSDL mà Entity tương tác
@Data //Là hàm toString()
@Builder //Annotation tự động sinh code cho việc xây dựng các phương thức khởi tạo (constructor) cho một lớp
@Getter //Hàm getter
@Setter //Hàm Setter
@AllArgsConstructor //Hàm khởi tạo có đối số
@NoArgsConstructor //Hàm khởi tạo không đối số
public class DiningTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "table_number", nullable = false, length = 350)
    private String tableNumber;

    @Column(name = "capacity")
    private int capacity;

    @Column(name = "status")
    private String status;

}
