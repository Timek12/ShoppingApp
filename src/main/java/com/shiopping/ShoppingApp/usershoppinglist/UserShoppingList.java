package com.shiopping.ShoppingApp.usershoppinglist;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.shiopping.ShoppingApp.shoppinglist.ShoppingList;
import com.shiopping.ShoppingApp.user.User;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "user_shopping_list")
public class UserShoppingList {
    @EmbeddedId
    private UserShoppingListKey id;

    @MapsId("userId")
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonManagedReference
    private User user;

    @MapsId("shoppingListId")
    @ManyToOne
    @JoinColumn(name = "shopping_list_id")
    @JsonManagedReference
    private ShoppingList shoppingList;

    @Enumerated(EnumType.STRING)
    private ListRole role;
}
