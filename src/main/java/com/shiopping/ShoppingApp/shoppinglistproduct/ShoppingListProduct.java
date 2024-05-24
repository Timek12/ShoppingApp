package com.shiopping.ShoppingApp.shoppinglistproduct;

import com.shiopping.ShoppingApp.product.Product;
import com.shiopping.ShoppingApp.shoppinglist.ShoppingList;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ShoppingListProduct {
    @EmbeddedId
    private ShoppingListProductKey id;

    @ManyToOne
    @MapsId("shoppingListId")
    @JoinColumn(name = "shopping_list_id")
    private ShoppingList shoppingList;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

    private Integer quantity;
}
