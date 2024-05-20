package com.shiopping.ShoppingApp.product;

import com.shiopping.ShoppingApp.category.CategoryDTO;
import lombok.Data;

@Data
public class ProductDTO {
    private Integer id;
    private String productName;
    private CategoryDTO category;
    private String unitOfMeasure;
    private QuantityType quantityType;
}
