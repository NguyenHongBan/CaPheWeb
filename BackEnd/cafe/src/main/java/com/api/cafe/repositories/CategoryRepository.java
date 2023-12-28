package com.api.cafe.repositories;

import com.api.cafe.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
    boolean existsByCategoryName(String categoryName);

    List<Category> findByCategoryNameContainingIgnoreCase(String categoryName);
}
