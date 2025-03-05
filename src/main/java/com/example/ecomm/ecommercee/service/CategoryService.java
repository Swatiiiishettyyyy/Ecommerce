package com.example.ecomm.ecommercee.service;

import com.example.ecomm.ecommercee.model.Category;
import com.example.ecomm.ecommercee.payload.CategoryDTO;
import com.example.ecomm.ecommercee.payload.CategoryResponse;

import java.util.List;

public interface CategoryService {
   // List<Category> getAllCategories();
   CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize);

    //void createCategory(Category category);
    CategoryDTO createCategory(CategoryDTO categoryDTO);

    //String deleteCategory(Long categoryId);
     CategoryDTO deleteCategory(Long categoryId);

//    Category updateCategory(Category category, Long categoryId);
      CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId);


}