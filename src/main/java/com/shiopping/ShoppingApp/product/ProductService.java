package com.shiopping.ShoppingApp.product;

import com.shiopping.ShoppingApp.category.Category;
import com.shiopping.ShoppingApp.category.CategoryRepository;
import com.shiopping.ShoppingApp.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();
    }

    public ProductDTO getProductById(int id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        return modelMapper.map(product, ProductDTO.class);
    }

//    @Transactional
    public ProductDTO createProduct(CreateProductDTO productDTO) {
        Optional<Category> categoryOptional = categoryRepository.findByCategoryName(productDTO.getCategoryName());
        if(categoryOptional.isEmpty()) {
            categoryRepository.save(new Category(productDTO.getCategoryName()));
            categoryOptional = categoryRepository.findByCategoryName(productDTO.getCategoryName());
        }

        Category categoryFromDb = categoryOptional.get();

        QuantityType quantityType = QuantityType.valueOf(productDTO.getQuantityType());

        Product newProduct = Product.builder()
                .productName(productDTO.getProductName())
                .category(categoryFromDb)
                .unitOfMeasure(productDTO.getUnitOfMeasure())
                .quantityType(quantityType)
                .build();

        productRepository.save(newProduct);

        return modelMapper.map(newProduct, ProductDTO.class);
    }

    @Transactional
    public ProductDTO updateProduct(Integer id, UpdateProductDTO productDTO) {
        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        Product updatedProduct =  productRepository.findById(id)
                .map(product -> {
                    product.setProductName(productDTO.getProductName());
                    product.setQuantityType(QuantityType.valueOf(productDTO.getQuantityType()));
                    product.setUnitOfMeasure(productDTO.getUnitOfMeasure());
                    product.setCategory(category);
                    return productRepository.save(product);
                }).orElseThrow(() -> new ResourceNotFoundException("Product with given id does not exists"));

        return modelMapper.map(updatedProduct, ProductDTO.class);
    }

    @Transactional
    public void deleteProduct(Integer id) {
        if(!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product with given id does not exists");
        }

        productRepository.deleteById(id);
    }
}
