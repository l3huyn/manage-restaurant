package com.project.restaurant.services;

import com.project.restaurant.dtos.CategoryDTO;
import com.project.restaurant.dtos.ProductImageDTO;
import com.project.restaurant.exceptions.DataNotFoundException;
import com.project.restaurant.models.Category;
import com.project.restaurant.models.ProductImage;

import java.util.List;

public interface ICategoryService {
    Category createCategory(CategoryDTO categoryDTO);

    Category getCategoryById(long id);

    List<Category> getAllCategories();

    Category updateCategory(long categoryId, CategoryDTO category);

    void deleteCategory(long id) throws Exception;


}
