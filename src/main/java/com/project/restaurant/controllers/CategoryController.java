package com.project.restaurant.controllers;

import com.project.restaurant.dtos.CategoryDTO;
import com.project.restaurant.exceptions.DataNotFoundException;
import com.project.restaurant.models.Category;
import com.project.restaurant.services.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/categories") //http://localhost:8088/api/v1/categories
@RequiredArgsConstructor //Annotation được dùng cho cơ chế Dependency Injection
public class CategoryController {

    //Dependency Injection
    private final CategoryService categoryService;


    //HÀM TẠO CATEGORY
    @PostMapping("")
    public ResponseEntity<?> createCategory(
            @Valid @RequestBody CategoryDTO categoryDTO,
            BindingResult result) {
        //Hiển thị lỗi, lỗi được lưu ở biến result
        if(result.hasErrors()) {
            //Trả về danh sách lỗi errorMessage kiểu String
            List<String> errorMessage =  result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(errorMessage);
        }

        //Tạo ra menu category
        categoryService.createCategory(categoryDTO);

        return ResponseEntity.ok("create category successfully");
    }



    //HÀM LẤY TẤT CẢ CÁC CATEGORIES
    @GetMapping("") //http://localhost:8088/api/v1/menu_categories?page=1&limit=10
    public ResponseEntity<List<Category>> getAllCategories(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ) {
        //Trả về mảng các categories
       List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }



    //HÀM CẬP NHẬT CATEGORIES
    @PutMapping("/{id}")
    public ResponseEntity<String> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody CategoryDTO categoryDTO
    ){
        categoryService.updateCategory(id, categoryDTO);
        return ResponseEntity.ok("update category with id: " + id +"  successfully");
    }


    //HÀM XÓA MỘT CATEGORIES -> Xóa cứng
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id){
        try {
            categoryService.deleteCategory(id);
            return ResponseEntity.ok("Delete menu category with ID: " + id + " successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

}
