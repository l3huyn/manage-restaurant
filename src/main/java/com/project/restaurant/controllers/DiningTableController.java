package com.project.restaurant.controllers;


import com.project.restaurant.dtos.DiningTableDTO;
import com.project.restaurant.exceptions.DataNotFoundException;
import com.project.restaurant.models.DiningTable;
import com.project.restaurant.responses.DiningTableListResponse;
import com.project.restaurant.responses.DiningTableResponse;
import com.project.restaurant.services.DiningTableService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("api/v1/dining_tables")
@RequiredArgsConstructor
public class DiningTableController {

    //DI
    private final DiningTableService diningTableService;

    //HÀM TẠO BÀN MỚI
    @PostMapping("")
    public ResponseEntity<?> createDiningTable(
            @Valid @RequestBody DiningTableDTO diningTableDTO,
            BindingResult result
    ) {
        //Hiển thị lỗi, lỗi được lưu ở biến result
        if(result.hasErrors()) {
            //Trả về danh sách lỗi errorMessage kiểu String
            List<String> errorMessage =  result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(errorMessage);
        }

        //Tạo dining table
        diningTableService.createDiningTable(diningTableDTO);

        return ResponseEntity.ok("Create dining table successfully");
    }


    //HÀM LẤY TẤT CẢ CÁC BÀN
    @GetMapping("") //http://localhost:8088/api/v1/dining_tables?page=1&limit=10
    public ResponseEntity<DiningTableListResponse> getAllDiningTables(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ) {
        PageRequest pageRequest = PageRequest.of(
                page, limit,
                //Sắp xếp theo thứ tự createdAt giảm dần
                Sort.by("id").ascending());


        Page<DiningTableResponse> diningTablePage = diningTableService.getAllDiningTables(pageRequest);

        int totalPages = diningTablePage.getTotalPages();

        List<DiningTableResponse> diningTables = diningTablePage.getContent();

        return ResponseEntity.ok(DiningTableListResponse
                .builder()
                        .diningTables(diningTables)
                        .totalPages(totalPages)
                .build());
    }

    //HÀM LẤY BÀN BẰNG ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getDiningTableById(@PathVariable("id") Long id) {
        try {
            DiningTable existingDiningTable = diningTableService.getDiningTableById(id);
            return ResponseEntity.ok(existingDiningTable);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //HÀM CẬP NHẬT 1 BÀN BẰNG ID
    @PutMapping("/{id}")
    public ResponseEntity<?> updateDiningTable(@PathVariable("id") Long id,  @RequestBody DiningTableDTO diningTableDTO) {
        try {
            DiningTable updateDiningTable = diningTableService.updateDiningTable(id, diningTableDTO);
            return ResponseEntity.ok(updateDiningTable);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //HÀM XÓA MỘT BÀN
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDiningTable(@PathVariable("id") Long id) {
        try {
            DiningTable existingDiningTable = diningTableService.getDiningTableById(id);
            if(existingDiningTable != null) {
                diningTableService.deleteDiningTable(id);
            }
            return ResponseEntity.ok("Deleted dining table with id: " + id + " successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
