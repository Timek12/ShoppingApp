package com.shiopping.ShoppingApp.shoppinglist;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShoppingListDTO {
    private Integer id;
    private String name;
    private String description;
    private Integer productCount;
    private Integer userCount;

}
