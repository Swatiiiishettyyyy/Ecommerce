package com.example.ecomm.ecommercee.service;

import com.example.ecomm.ecommercee.model.Category;
import com.example.ecomm.ecommercee.payload.CategoryDTO;
import com.example.ecomm.ecommercee.payload.CategoryResponse;

import java.util.List;

public interface CategoryService {
   // List<Category> getAllCategories();

    //void createCategory(Category category);
    CategoryDTO createCategory(CategoryDTO categoryDTO);

    String deleteCategory(Long categoryId);

    Category updateCategory(Category category, Long categoryId);

    CategoryResponse getAllCategories();
}