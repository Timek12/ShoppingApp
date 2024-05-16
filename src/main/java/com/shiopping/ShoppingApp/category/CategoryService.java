package com.shiopping.ShoppingApp.category;

import com.shiopping.ShoppingApp.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Integer id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category with given id does not exist"));
    }

    public Category createCategory(CreateCategoryDTO categoryDTO) {
        Category newCategory = new Category(categoryDTO.categoryName);
        categoryRepository.save(newCategory);
        return newCategory;
    }

    public Category updateCategory(Integer id, CategoryDTO categoryDTO) {
        return categoryRepository.findById(id)
                .map(category -> {
                    category.setCategoryName(categoryDTO.getCategoryName());
                    return categoryRepository.save(category);
                }).orElseThrow(() -> new ResourceNotFoundException("Category with given id does not exist"));
    }

    public void deleteCategory(Integer id) {
        if(!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Category with given id does not exist");
        }

        categoryRepository.deleteById(id);
    }

}
