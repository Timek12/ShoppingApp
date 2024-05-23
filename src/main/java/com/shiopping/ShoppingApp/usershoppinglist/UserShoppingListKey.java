package com.shiopping.ShoppingApp.usershoppinglist;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class UserShoppingListKey implements Serializable {
    @Column(name = "user_id")
    private Integer userId;
    
    @Column(name = "shopping_list_id")
    private Integer shoppingListId;
}
