package com.shiopping.ShoppingApp.config;

import com.shiopping.ShoppingApp.category.Category;
import com.shiopping.ShoppingApp.category.CategoryRepository;
import com.shiopping.ShoppingApp.product.Product;
import com.shiopping.ShoppingApp.product.ProductRepository;
import com.shiopping.ShoppingApp.product.QuantityType;
import com.shiopping.ShoppingApp.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {
    private final UserRepository userRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return username ->
                userRepository.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
    @Bean
    CommandLineRunner initDatabase(ProductRepository productRepository, CategoryRepository categoryRepository) {
        return args -> {
            if (productRepository.count() == 0) {
                Category automotive = new Category("Automotive");
                Category food = new Category("Food");
                Category electronics = new Category("Electronics");
                Category clothing = new Category("Clothing");
                Category household = new Category("Household");

                categoryRepository.save(automotive);
                categoryRepository.save(food);
                categoryRepository.save(electronics);
                categoryRepository.save(clothing);
                categoryRepository.save(household);

                String[] automotiveProducts = {"Car", "Motorcycle", "Breaks", "Engine", "Turbo", "Tire"};
                QuantityType[] automotiveQuantities = {QuantityType.INTEGER, QuantityType.INTEGER, QuantityType.INTEGER, QuantityType.INTEGER, QuantityType.INTEGER, QuantityType.INTEGER};
                String[] automotiveUnits = {"pcs", "pcs", "pcs", "pcs", "pcs", "pcs"};

                String[] foodProducts = {"Apple", "Bread", "Cheese", "Milk", "Eggs", "Butter"};
                QuantityType[] foodQuantities = {QuantityType.INTEGER, QuantityType.INTEGER, QuantityType.INTEGER, QuantityType.INTEGER, QuantityType.INTEGER, QuantityType.INTEGER};
                String[] foodUnits = {"pcs", "pcs", "pcs", "liters", "pcs", "grams"};

                String[] electronicsProducts = {"Computer", "Laptop", "iPhone", "Headphones", "Camera", "Printer"};
                QuantityType[] electronicsQuantities = {QuantityType.INTEGER, QuantityType.INTEGER, QuantityType.INTEGER, QuantityType.INTEGER, QuantityType.INTEGER, QuantityType.INTEGER};
                String[] electronicsUnits = {"pcs", "pcs", "pcs", "pcs", "pcs", "pcs"};

                String[] clothingProducts = {"T-Shirt", "Jeans", "Jacket", "Sneakers", "Hat", "Dress"};
                QuantityType[] clothingQuantities = {QuantityType.INTEGER, QuantityType.INTEGER, QuantityType.INTEGER, QuantityType.INTEGER, QuantityType.INTEGER, QuantityType.INTEGER};
                String[] clothingUnits = {"pcs", "pcs", "pcs", "pcs", "pcs", "pcs"};

                String[] householdProducts = {"Table", "Chair", "Bed", "Couch", "Lamp", "Desk"};
                QuantityType[] householdQuantities = {QuantityType.INTEGER, QuantityType.INTEGER, QuantityType.INTEGER, QuantityType.INTEGER, QuantityType.INTEGER, QuantityType.INTEGER};
                String[] householdUnits = {"pcs", "pcs", "pcs", "pcs", "pcs", "pcs"};

                saveProducts(productRepository, automotive, automotiveProducts, automotiveQuantities, automotiveUnits);
                saveProducts(productRepository, food, foodProducts, foodQuantities, foodUnits);
                saveProducts(productRepository, electronics, electronicsProducts, electronicsQuantities, electronicsUnits);
                saveProducts(productRepository, clothing, clothingProducts, clothingQuantities, clothingUnits);
                saveProducts(productRepository, household, householdProducts, householdQuantities, householdUnits);
            }
        };
    }

    private void saveProducts(ProductRepository productRepository, Category category, String[] productNames, QuantityType[] quantityTypes, String[] units) {
        for (int i = 0; i < productNames.length; i++) {
            Product newProduct = new Product();
            newProduct.setProductName(productNames[i]);
            newProduct.setCategory(category);
            newProduct.setUnitOfMeasure(units[i]);
            newProduct.setQuantityType(quantityTypes[i]);
            productRepository.save(newProduct);
        }
    }
}
