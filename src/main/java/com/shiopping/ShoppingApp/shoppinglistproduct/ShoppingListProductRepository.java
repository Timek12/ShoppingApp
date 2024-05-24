package com.shiopping.ShoppingApp.shoppinglistproduct;

import com.shiopping.ShoppingApp.product.Product;
import com.shiopping.ShoppingApp.shoppinglist.ShoppingList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoppingListProductRepository extends JpaRepository<ShoppingListProduct, ShoppingListProductKey> {
    List<ShoppingListProduct> findByProduct(Product product);
    List<ShoppingListProduct> findByShoppingList(ShoppingList shoppingList);
}
