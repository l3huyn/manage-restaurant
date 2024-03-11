package com.project.restaurant.services;

import com.project.restaurant.dtos.DiningTableDTO;
import com.project.restaurant.exceptions.DataNotFoundException;
import com.project.restaurant.models.DiningTable;

import java.util.List;

public interface IDiningTableService {

    //HÀM TẠO BÀN ĂN
    DiningTable createDiningTable(DiningTableDTO diningTableDTO);

    //HÀM LẤY BÀN ĂN BẰNG ID
    DiningTable getDiningTableById(long id) throws Exception;

    //HÀM LẤY DANH SÁCH BÀN ĂN
    List<DiningTable> getAllDiningTables();

    //HÀM CẬP NHẬT BÀN ĂN BẰNG ID
    DiningTable updateDiningTable(long id, DiningTableDTO diningTableDTO) throws Exception;

    //HÀM XÓA MỘT BÀN ĂN BẰNG ID
    void deleteDiningTable(long id);


}
