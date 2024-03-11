package com.project.restaurant.models;

import jakarta.persistence.*;
import lombok.*;

@Entity //Annotation đánh dấu một class là Entity
@Table(name = "categories") //Annotation dùng để chỉ định tên bảng trong CSDL mà Entity tương tác
@Data //Là hàm toString()
@Builder //Annotation tự động sinh code cho việc xây dựng các phương thức khởi tạo (constructor) cho một lớp
@Getter //Hàm getter
@Setter //Hàm Setter
@AllArgsConstructor //Hàm khởi tạo có đối số
@NoArgsConstructor //Hàm khởi tạo không đối số
public class Category {
    @Id //Annotation đánh dấu một trường là khóa chính
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Annotation giúp giá trị của trường khóa chính
    // sẽ được sinh tự động dựa trên tính năng tự động tăng (auto-increment) của cơ sở dữ liệu.
    private Long id;

    @Column(name = "name", nullable = false) //Annotation đánh dấu trường name trong class Category là
    // một cột trong cơ sở dữ liệu với tên là "name".
    // nullable = false: Chỉ định rằng giá trị của cột không được phép là null.
    private String name;
}
