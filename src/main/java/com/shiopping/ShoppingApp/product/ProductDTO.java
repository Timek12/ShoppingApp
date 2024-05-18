package com.shiopping.ShoppingApp.product;

import com.shiopping.ShoppingApp.category.CategoryDTO;

public class ProductDTO {
    private Integer id;
    private String productName;
    private CategoryDTO category;
    private String unitOfMeasure;
    private QuantityType quantityType;
}
