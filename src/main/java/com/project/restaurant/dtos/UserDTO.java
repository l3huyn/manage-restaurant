package com.project.restaurant.dtos;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.sql.Date;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
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

    @NotNull(message = "Role ID is required")
    @JsonProperty("role_id")
    private Long roleId;

}
