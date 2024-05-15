package com.shiopping.ShoppingApp.product;

import com.shiopping.ShoppingApp.category.Category;
import com.shiopping.ShoppingApp.category.CategoryRepository;
import com.shiopping.ShoppingApp.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(int id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    @Transactional
    public Product createProduct(CreateProductDTO productDTO) {
        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        QuantityType quantityType = QuantityType.valueOf(productDTO.getQuantityType());

        Product newProduct = Product.builder()
                .productName(productDTO.getProductName())
                .category(category)
                .unitOfMeasure(productDTO.getUnitOfMeasure())
                .quantityType(quantityType)
                .build();

        return productRepository.save(newProduct);
    }

    @Transactional
    public Product updateProduct(Integer id, UpdateProductDTO productDTO) {
        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        return productRepository.findById(id)
                .map(product -> {
                    product.setProductName(productDTO.getProductName());
                    product.setQuantityType(QuantityType.valueOf(productDTO.getQuantityType()));
                    product.setUnitOfMeasure(productDTO.getUnitOfMeasure());
                    product.setCategory(category);
                    return productRepository.save(product);
                }).orElseThrow(() -> new ResourceNotFoundException("Product with given id does not exists"));
    }

    @Transactional
    public void deleteProduct(Integer id) {
        if(!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product with given id does not exists");
        }

        productRepository.deleteById(id);
    }
}
