package com.example.ecomm.ecommercee.service;

import com.example.ecomm.ecommercee.payload.ProductDTO;
import com.example.ecomm.ecommercee.payload.ProductResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface ProductService {
    ProductDTO addProduct(Long categoryId, ProductDTO product);

    ProductResponse getAllProducts();

    ProductResponse searchByCategory(Long categoryId);

    ProductResponse searchProductByKeyword(String keyword);

    ProductDTO updateProduct(Long productId, ProductDTO product);

    ProductDTO deleteProduct(Long productId);

    ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException;
}
