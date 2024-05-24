package com.shiopping.ShoppingApp.product;

import com.shiopping.ShoppingApp.category.Category;
import com.shiopping.ShoppingApp.category.CategoryDTO;
import com.shiopping.ShoppingApp.category.CategoryRepository;
import com.shiopping.ShoppingApp.exception.ResourceNotFoundException;
import com.shiopping.ShoppingApp.shoppinglist.UserProductDTO;
import com.shiopping.ShoppingApp.shoppinglistproduct.ShoppingListProduct;
import com.shiopping.ShoppingApp.shoppinglistproduct.ShoppingListProductRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ShoppingListProductRepository shoppingListProductRepository;

    public ProductService(
            ProductRepository productRepository,
            CategoryRepository categoryRepository,
            ShoppingListProductRepository shoppingListProductRepository,
            ModelMapper modelMapper)
    {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.shoppingListProductRepository = shoppingListProductRepository;
    }

    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public ProductDTO getProductById(int id) {
        Optional<Product> product = productRepository.findById(id);

        if(product.isEmpty()) {
            throw new ResourceNotFoundException("Product not found");
        }

        return mapToDTO(product.get());
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

        newProduct = productRepository.save(newProduct);

        return mapToDTO(newProduct);
    }

    @Transactional
    public ProductDTO updateProduct(Integer id, UpdateProductDTO productDTO) {
        Optional<Product> productOptional = productRepository.findById(id);

        if(productOptional.isEmpty()) {
            throw new ResourceNotFoundException("Product with given id does not exist");
        }

        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        Product updatedProduct = productOptional.get();
        updatedProduct.setProductName(productDTO.getProductName());
        updatedProduct.setCategory(category);
        updatedProduct.setUnitOfMeasure(productDTO.getUnitOfMeasure());
        updatedProduct.setQuantityType(QuantityType.valueOf(productDTO.getQuantityType()));

        updatedProduct = productRepository.save(updatedProduct);

        return mapToDTO(updatedProduct);
    }

    @Transactional
    public void deleteProduct(Integer id) {
        Optional<Product> productOptional = productRepository.findById(id);

        if(productOptional.isEmpty()) {
            throw new ResourceNotFoundException("Product with given id does not exists");
        }

        Product product = productOptional.get();

        List<ShoppingListProduct> shoppingListProducts = shoppingListProductRepository.findByProduct(product);
        shoppingListProductRepository.deleteAll(shoppingListProducts);

        productRepository.deleteById(id);
    }

    private ProductDTO mapToDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setProductName(product.getProductName());
        productDTO.setCategory(new CategoryDTO(product.getCategory().getId(), product.getCategory().getCategoryName()));
        productDTO.setUnitOfMeasure(product.getUnitOfMeasure());
        productDTO.setQuantityType(product.getQuantityType());

        return productDTO;
    }
}
