package com.shiopping.ShoppingApp.usershoppinglist;

import com.shiopping.ShoppingApp.shoppinglist.ShoppingList;
import com.shiopping.ShoppingApp.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_shopping_list")
public class UserShoppingList {
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "shopping_list_id")
    private ShoppingList shoppingList;

    @Enumerated(EnumType.STRING)
    private ListRole role;
}
