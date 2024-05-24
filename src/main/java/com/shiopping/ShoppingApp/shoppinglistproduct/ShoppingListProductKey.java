package com.shiopping.ShoppingApp.shoppinglistproduct;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class ShoppingListProductKey implements Serializable {
    private Integer shoppingListId;
    private Integer productId;
}
