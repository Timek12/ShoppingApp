package com.shiopping.ShoppingApp.shoppinglist;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateShoppingListDTO {
    private String email;
    private String name;
    private String description;
}
