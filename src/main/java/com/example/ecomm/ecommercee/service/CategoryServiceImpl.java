package com.example.ecomm.ecommercee.service;

import com.example.ecomm.ecommercee.model.Category;
import com.example.ecomm.ecommercee.repositories.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    //private List<Category> categories = new ArrayList<>();
   private Long nextId = 1L;
    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public List<Category> getAllCategories() {

        return categoryRepository.findAll();
    }

    @Override
    @Transactional
    public void createCategory(Category category) {
       category.setCategoryId(nextId++);
        //categories.add(category);
        categoryRepository.save(category);
    }

    @Override
    @Transactional
    public String deleteCategory(Long categoryId) {
        List<Category> categories =categoryRepository.findAll();
        Category category = categories.stream()// converting collections to strams , so we can apply filtering etc to it
                .filter(c -> c.getCategoryId().equals(categoryId))
                .findFirst()

                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND , "Resource Not Found"));


        //.orElse(null);
//        if (category == null)
//            return "category not found";

       // categories.remove(category);
        categoryRepository.delete(category);
        return "category with categoryId:" + categoryId +"deleted successfully";
    }

    @Override
    @Transactional
    public Category updateCategory(Category category, Long categoryId) {
        // way 1
//        List<Category> categories =categoryRepository.findAll();
//        Optional<Category> optionalCategory = categories.stream()// converting collections to strams , so we can apply filtering etc to it
//                .filter(c -> c.getCategoryId().equals(categoryId))
//                .findFirst();
//        if(optionalCategory.isPresent()){
//            Category existingCategory = optionalCategory.get();
//            existingCategory.setCategoryName(category.getCategoryName());
//            Category savedCategory = categoryRepository.save(existingCategory);
//            return savedCategory;
//        }
//         else{
//             throw new ResponseStatusException(HttpStatus.NOT_FOUND , "category Not Found");
//        }

        //way 2
        Category existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));

        existingCategory.setCategoryName(category.getCategoryName());
        return categoryRepository.save(existingCategory);

    }
}
