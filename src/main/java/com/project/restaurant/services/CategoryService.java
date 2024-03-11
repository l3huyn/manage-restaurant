package com.project.restaurant.services;

import com.project.restaurant.dtos.CategoryDTO;
import com.project.restaurant.models.Category;
import com.project.restaurant.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService{

    //DI
    private final CategoryRepository categoryRepository;


    //HÀM TẠO MỘT CATEGORY
    @Override
    public Category createCategory(CategoryDTO categoryDTO) {
        //Cách convert CategoryDTO sang Category
        //Tạo ra đối tượng Category sau đó gán getName() của categoryDTO sang cho newCategory
        Category newCategory = Category.builder()
                .name(categoryDTO.getName())
                .build();

        //Lưu vào DB
        return categoryRepository.save(newCategory);
    }


    //HÀM LẤY CATEGORY BẰNG ID
    @Override
    public Category getCategoryById(long id) {
        return categoryRepository.findById(id) //Tìm bằng id
                //Tìm kh thấy trả về ngoại lệ
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }


    //HÀM LẤY DANH SÁCH CÁC CATEGORIES
    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }



    //HÀM CẬP NHẬT CATEGORY
    @Override
    public Category updateCategory(long categoryId, CategoryDTO categoryDTO) {
        //Tìm Category với categoryId này
        Category existingCategory = getCategoryById(categoryId);

        //Cập nhật category => vì category chỉ có trường name nên chỉ setName()
        existingCategory.setName(categoryDTO.getName());

        //Lưu lại
        categoryRepository.save(existingCategory);

        //Trả về existingCategory đã sửa đổi
        return existingCategory;
    }



    //HÀM XÓA CATEGORY
    @Override
    public void deleteCategory(long id) {
        //Xóa cứng
        categoryRepository.deleteById(id);
    }
}
