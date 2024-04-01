package com.project.restaurant.responses;

import com.project.restaurant.models.DiningTable;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DiningTableResponse {
    private String tableNumber;

    private int capacity;

    private String status;

    public static DiningTableResponse fromDiningTable(DiningTable diningTable){
        DiningTableResponse diningTableResponse = DiningTableResponse.builder()
                .tableNumber(diningTable.getTableNumber())
                .capacity(diningTable.getCapacity())
                .status(diningTable.getStatus())
                .build();

        return diningTableResponse;
    }
}
