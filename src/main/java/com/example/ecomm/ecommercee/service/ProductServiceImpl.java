package com.example.ecomm.ecommercee.service;

import com.example.ecomm.ecommercee.exceptions.ResourceNotFoundException;
import com.example.ecomm.ecommercee.model.Category;
import com.example.ecomm.ecommercee.model.Product;
import com.example.ecomm.ecommercee.payload.ProductDTO;
import com.example.ecomm.ecommercee.payload.ProductResponse;
import com.example.ecomm.ecommercee.repositories.CategoryRepository;
import com.example.ecomm.ecommercee.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private FileService fileService;

    @Autowired
    private ModelMapper modelMapper;

    @Value("${project.image}")
    private String path;

    @Override
    public ProductDTO addProduct(Long categoryId, ProductDTO productDTO) {
        Category category= categoryRepository.findById(categoryId)
                .orElseThrow(()->
                        new ResourceNotFoundException("category","categoryId", categoryId));
        Product product = modelMapper.map(productDTO, Product.class);
        product.setImage("default.png");
        product.setCategory(category);
        double specialPrice = product.getPrice()-(product.getDiscount() * 0.01) * product.getPrice();
        product.setSpecialPrice(specialPrice);
       Product savedProduct= productRepository.save(product);
        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    @Override
    public ProductResponse getAllProducts() {
       List<Product> products= productRepository.findAll();
       List<ProductDTO> productDTOS= products.stream()
               .map(product -> modelMapper.map(product, ProductDTO.class))
               .toList();
       ProductResponse productResponse = new ProductResponse();
       productResponse.setContent(productDTOS);
        return productResponse;
    }

    @Override
    public ProductResponse searchByCategory(Long categoryId) {
        Category category= categoryRepository.findById(categoryId)
                .orElseThrow(()->
                        new ResourceNotFoundException("category","categoryId", categoryId));
        List<Product> products= productRepository.findByCategoryOrderByPriceAsc(category);
        List<ProductDTO> productDTOS= products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOS);
        return productResponse;

    }

    @Override
    public ProductResponse searchProductByKeyword(String keyword) {
        List<Product> products= productRepository.findByProductNameLikeIgnoreCase('%'+keyword+'%');
        List<ProductDTO> productDTOS= products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOS);
        return productResponse;
    }

    @Override
    public ProductDTO updateProduct(Long productId, ProductDTO productDTO) {
        //get existing product from db
        Product productFromDb = productRepository.findById(productId)
                .orElseThrow(()->
                        new ResourceNotFoundException("product","productId", productId));
        Product product = modelMapper.map(productDTO, Product.class);
        //update the product info with one in request body
        productFromDb.setProductName(product.getProductName());
        productFromDb.setDescription(product.getDescription());
        productFromDb.setQuantity(product.getQuantity());
        productFromDb.setDiscount(product.getDiscount());
        productFromDb.setPrice(product.getPrice());
        productFromDb.setSpecialPrice(product.getSpecialPrice());
        Product savedProduct = productRepository.save(productFromDb);
        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    @Override
    public ProductDTO deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(()->new ResourceNotFoundException("product", "productId" , productId));
        productRepository.delete(product);
        return modelMapper.map(product, ProductDTO.class);
    }

    @Override
    public ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException {
        //get product from db
        Product productFromDb = productRepository.findById(productId)
                .orElseThrow(()->new ResourceNotFoundException("product", "productId" , productId));

        //upload image to server
        //get the file name of uploaded image
        //String path = "images/";
        String fileName =fileService.uploadImage(path,image);

        //updating new file  name to the product
        productFromDb.setImage(fileName);

        //save the updated product
        Product updatedProduct =productRepository.save(productFromDb);

        //return DTO after mapping product to DTO
        return modelMapper.map(updatedProduct, ProductDTO.class);

    }




}
