package com.project.restaurant.dtos;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.sql.Date;
import java.time.LocalDateTime;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDTO {
    @JsonProperty("fullname")
    @NotBlank(message = "Fullname is required")
    private String fullName;

    private String position;

    @NotBlank(message = "Phone number is required")
    @JsonProperty("phone_number")
    private String phoneNumber;

    private String email;

    private String address;

    @JsonProperty("hire_date")
    private Date hireDate;

    private Float salary;

}
