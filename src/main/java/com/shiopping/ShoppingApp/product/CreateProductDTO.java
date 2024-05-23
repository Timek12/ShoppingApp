package com.shiopping.ShoppingApp.product;

import lombok.Data;

@Data
public class CreateProductDTO {
    private String productName;
    private String categoryName;
    private String unitOfMeasure;
    private String quantityType;
}
