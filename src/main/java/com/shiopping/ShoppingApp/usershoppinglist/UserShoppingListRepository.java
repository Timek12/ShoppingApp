package com.shiopping.ShoppingApp.usershoppinglist;

import com.shiopping.ShoppingApp.shoppinglist.ShoppingList;
import com.shiopping.ShoppingApp.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserShoppingListRepository extends JpaRepository<UserShoppingList, Integer> {
    List<UserShoppingList> findByUser(User user);
}
