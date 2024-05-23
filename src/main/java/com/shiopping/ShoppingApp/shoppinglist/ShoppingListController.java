package com.shiopping.ShoppingApp.shoppinglist;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/shopping-lists")
public class ShoppingListController {
    ShoppingListService shoppingListService;

    public ShoppingListController(ShoppingListService shoppingListService) {
        this.shoppingListService = shoppingListService;
    }

    @GetMapping("/{userId}")
    public List<ShoppingList> getAllShoppingLists(@RequestParam Integer userId) {
        return shoppingListService.getAllShoppingLists(userId);
    }

    @GetMapping("/{shoppingListId}")
    public ShoppingList getShoppingList(@RequestParam Integer shoppingListId) {
        return shoppingListService.getShoppingListById(shoppingListId);
    }

    @PostMapping("/")
    public ShoppingList createShoppingList(@RequestBody CreateShoppingListDTO createShoppingListDTO) {
        return shoppingListService.createShoppingList(createShoppingListDTO);
    }

    @PostMapping("/{shoppingListId}/users/{userId}")
    public ShoppingList addUserToShoppingList(@RequestParam Integer shoppingListId, @RequestParam Integer userId) {
        return shoppingListService.addUserToShoppingList(userId, shoppingListId);
    }

    @PostMapping("/{shoppingListId}/products/{productId}")
    public ShoppingList addProductToShoppingList(@RequestParam Integer shoppingListId, @RequestParam Integer productId) {
        return shoppingListService.addProductToShoppingList(shoppingListId, productId);
    }
}
