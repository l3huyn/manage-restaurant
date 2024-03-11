package com.project.restaurant.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DiningTableDTO {
    @JsonProperty("table_number")
    @NotEmpty(message = "Table number cannot be empty")
    private String tableNumber;

    private int capacity;

    private String status;
}
