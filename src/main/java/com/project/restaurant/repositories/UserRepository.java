package com.project.restaurant.repositories;

import com.project.restaurant.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    //Hàm kiểm tra SĐT có tồn tại trên DB
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByEmail(String email);
}
