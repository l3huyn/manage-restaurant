package com.project.restaurant.repositories;


import com.project.restaurant.models.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    //Hàm tìm danh sách các ProductImage theo Id
    List<ProductImage> findByProductId(Long productId);
}
