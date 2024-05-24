package com.shiopping.ShoppingApp.shoppinglist;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    private String name;
    private String description;
    private Integer productCount = 0;
    private Integer userCount = 1;

    @OneToMany(mappedBy = "shoppingList")
    @JsonBackReference
    private List<UserShoppingList> userShoppingLists = new ArrayList<>();

    @Version
    private Integer version;
}
