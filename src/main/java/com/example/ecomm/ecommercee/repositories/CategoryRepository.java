package com.example.ecomm.ecommercee.repositories;

import com.example.ecomm.ecommercee.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Long> {

}
