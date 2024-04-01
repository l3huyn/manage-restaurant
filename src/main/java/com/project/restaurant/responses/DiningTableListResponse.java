package com.project.restaurant.responses;

import com.project.restaurant.models.DiningTable;
import com.project.restaurant.models.Product;
import lombok.*;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DiningTableListResponse {
    private List<DiningTableResponse> diningTables;

    //Tổng trang
    private int totalPages;
}
