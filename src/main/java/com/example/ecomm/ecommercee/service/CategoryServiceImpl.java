package com.example.ecomm.ecommercee.service;

import com.example.ecomm.ecommercee.exceptions.APIException;
import com.example.ecomm.ecommercee.exceptions.ResourceNotFoundException;
import com.example.ecomm.ecommercee.model.Category;
import com.example.ecomm.ecommercee.payload.CategoryDTO;
import com.example.ecomm.ecommercee.payload.CategoryResponse;
import com.example.ecomm.ecommercee.repositories.CategoryRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{


    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;



    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = modelMapper.map(categoryDTO, Category.class);
        Category categoryFromDb = categoryRepository.findByCategoryName(category.getCategoryName());
        if(categoryFromDb!=null){
            throw new APIException("category with the name:" + category.getCategoryName()+ "already exists!!");
        }
        Category savedCategory =categoryRepository.save(category);
        CategoryDTO savedCategoryDTO = modelMapper.map(savedCategory,CategoryDTO.class);
        return  savedCategoryDTO;
    }

    @Override
    public CategoryDTO deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException("category", "categoryId", categoryId));
        categoryRepository.delete(category);
        return modelMapper.map(category, CategoryDTO.class);
    }





    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId) {
       Category savedCategory = categoryRepository.findById(categoryId)
               .orElseThrow(()->new ResourceNotFoundException("category", "categoryId", categoryId));
       Category category=modelMapper.map(categoryDTO,Category.class);
       category.setCategoryId(categoryId);
       savedCategory=categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryDTO.class);
    }


    @Override
    public CategoryResponse getAllCategories() {
        List<Category> categories= categoryRepository.findAll();
        if(categories.isEmpty())
            throw new APIException("No category created till now");
        List<CategoryDTO> categoryDTOS= categories.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .toList();
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(categoryDTOS);
        return categoryResponse;
    }
}
