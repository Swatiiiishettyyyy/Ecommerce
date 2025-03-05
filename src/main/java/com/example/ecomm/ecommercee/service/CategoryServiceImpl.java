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
    //private List<Category> categories = new ArrayList<>();
    //private Long nextId = 1L;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

//    @Override
//    public List<Category> getAllCategories() {
//        List<Category> categories = categoryRepository.findAll();
//        if(categories.isEmpty())
//            throw new APIException("No category created till now");
//        return categories;
//    }



//    @Override
//    public void createCategory(Category category) {
//        //category.setCategoryId(nextId++);
//        Category savedCategory = categoryRepository.findByCategoryName(category.getCategoryName());
//        if(savedCategory!=null){
//            throw new APIException("category with the name:" + category.getCategoryName()+ "already exists!!");
//        }
//        categoryRepository.save(category);
//    }

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
    public String deleteCategory(Long categoryId) {
        List<Category> categories = categoryRepository.findAll();

        Category category = categories.stream()
                .filter(c -> c.getCategoryId().equals(categoryId))
                .findFirst()
               // .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found"));
                .orElseThrow(()->new ResourceNotFoundException("category", "categoryId", categoryId));
        categoryRepository.delete(category);
        return "Category with categoryId: " + categoryId + " deleted successfully !!";
    }

    @Override
    public Category updateCategory(Category category, Long categoryId) {
//        List<Category> categories = categoryRepository.findAll();
//
//        Optional<Category> optionalCategory = categories.stream()
//                .filter(c -> c.getCategoryId().equals(categoryId))
//                .findFirst();
//
//        if(optionalCategory.isPresent()){
//            Category existingCategory = optionalCategory.get();
//            existingCategory.setCategoryName(category.getCategoryName());
//            Category savedCategory = categoryRepository.save(existingCategory);
//            return savedCategory;
//        } else {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found");
//        }
        Category savedCategory = categoryRepository.findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException("category", "categoryId", categoryId));
        category.setCategoryId(categoryId);
        savedCategory=categoryRepository.save(category);
        return savedCategory;
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
