package com.shiopping.ShoppingApp.shoppinglist;

import com.shiopping.ShoppingApp.category.CategoryDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProductDTO {
    private Integer id;
    private String productName;
    private CategoryDTO category;
    private String unitOfMeasure;
    private String quantityType;
    private Integer quantity;
}
