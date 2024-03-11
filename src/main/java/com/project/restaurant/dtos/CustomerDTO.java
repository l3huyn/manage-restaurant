package com.project.restaurant.dtos;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CustomerDTO {
    @JsonProperty("fullname")
    private String fullName;

    @NotBlank(message = "Phone number is required")
    @JsonProperty("phone_number")
    private String phoneNumber;

    private String email;

    private String address;

    private Float point;

    @JsonProperty("class")
    private String classOfCustomer;

}
