package com.shiopping.ShoppingApp.shoppinglist;

import com.shiopping.ShoppingApp.product.ProductDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ShoppingListDTO {
    private Integer id;
    private String name;
    private String description;
    private Integer productCount;
    private Integer userCount;
    private List<UserProductDTO> products;
    private Integer version;
}
