package com.shiopping.ShoppingApp.product;

import lombok.Data;

@Data
public class UpdateProductDTO {
    private Integer id;
    private Integer categoryId;
    private String productName;
    private String unitOfMeasure;
    private String quantityType;
}
