package com.project.restaurant.repositories;

import com.project.restaurant.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    //Hàm kiểm tra xem với name được truyền vào thì có menu item nào với name như thế tồn tại hay không
    boolean existsByName(String name);

    //Hàm phân trang
    Page<Product> findAll(Pageable pageable);
}
