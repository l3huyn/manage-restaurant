package com.project.restaurant.models;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.*;

import java.time.LocalDateTime;

@Data //Là hàm toString()
@Getter //Hàm getter
@Setter //Hàm Setter
@AllArgsConstructor //Hàm khởi tạo có đối số
@NoArgsConstructor //Hàm khởi tạo không đối số
@MappedSuperclass //Annotation để chỉ định rằng một class là một class cơ sở ánh xạ (mapped superclass).
// Class cơ sở ánh xạ này không được ánh xạ trực tiếp vào cơ sở dữ liệu, nhưng các class con của nó sẽ kế thừa các thuộc tính và phương thức ánh xạ từ nó.

public class BaseEntity {
    @Column(name = "created_at")
    private LocalDateTime createdAt; //Kiểu dữ liệu này dùng để lưu thờ điểm tự động


    @Column(name = "updated_at")
    private LocalDateTime updatedAt; //Kiểu dữ liệu này dùng để lưu thờ điểm tự động


    @PrePersist //Annotation để đánh dấu phương thức nào đó sẽ được thực hiện
    // trước khi đối tượng được lưu vào cơ sở dữ liệu lần đầu tiên (khi nó được persist).
    protected void onCreate() {
        //Cập nhật trường createdAt và updatedAt với thời điểm hiện tại (LocalDateTime.now()).
        //Do đó, khi tạo mới một đối tượng của class này và lưu nó vào cơ sở dữ liệu
        // createdAt và updatedAt sẽ được cập nhật với cùng một giá trị thời gian.
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate //Annotation để đánh dấu phương thức nào đó sẽ được thực hiện
    // trước khi đối tượng được cập nhật trong cơ sở dữ liệu.
    protected void onUpdate() {
        //Chỉ cập nhật trường updatedAt với thời điểm hiện tại (LocalDateTime.now()).
        //Do đó, khi cập nhật một đối tượng của class này trong cơ sở dữ liệu, chỉ trường updatedAt sẽ được cập nhật
        // trong khi createdAt giữ nguyên giá trị đã được thiết lập khi tạo mới.
        updatedAt = LocalDateTime.now();
    }
}
