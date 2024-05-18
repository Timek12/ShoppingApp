package com.shiopping.ShoppingApp.shoppinglist;

import com.shiopping.ShoppingApp.product.Product;
import com.shiopping.ShoppingApp.user.User;
import com.shiopping.ShoppingApp.usershoppinglist.UserShoppingList;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "shopping_lists")
public class ShoppingList {
    @Id
    @GeneratedValue
    private Integer id;

    @OneToMany(mappedBy = "shoppingList")
    private List<UserShoppingList> userShoppingLists = new ArrayList<>();

    @ManyToMany // declare link table with 2 foreign keys (composite key)
    @JoinTable (
            name = "shopping_list_product",
            joinColumns = @JoinColumn(name = "shopping_list_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products = new ArrayList<>();

    @Version
    private Integer version;
}
