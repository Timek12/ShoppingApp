package com.shiopping.ShoppingApp.usershoppinglist;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class UserShoppingListKey implements Serializable {
    @Column(name = "user_id")
    private Integer userId;
    
    @Column(name = "shopping_list_id")
    private Integer shoppingListId;
}
