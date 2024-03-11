package com.project.restaurant.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.MappedSuperclass;
import lombok.*;

import java.time.LocalDateTime;

@Data //Là hàm toString()
@Getter //Hàm getter
@Setter //Hàm Setter
@AllArgsConstructor //Hàm khởi tạo có đối số
@NoArgsConstructor //Hàm khởi tạo không đối số
@MappedSuperclass //Annotation để chỉ định rằng một class là một class cơ sở ánh xạ (mapped superclass).

public class BaseResponse { //Chứa các thuộc tính cơ bản dùng chung (Nếu cần)
    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;
}
