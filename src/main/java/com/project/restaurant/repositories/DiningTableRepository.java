package com.project.restaurant.repositories;

import com.project.restaurant.models.DiningTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiningTableRepository extends JpaRepository<DiningTable, Long> {
}
