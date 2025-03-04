package com.example.ecomm.ecommercee.repositories;

import com.example.ecomm.ecommercee.model.Category;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Long> {

    Category findByCategoryName(@NotBlank String categoryName);
}
