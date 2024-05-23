package com.shiopping.ShoppingApp.shoppinglist;

import com.shiopping.ShoppingApp.user.UserDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/shopping-lists")
@CrossOrigin(origins = "http://localhost:8080")
public class ShoppingListController {
    ShoppingListService shoppingListService;

    public ShoppingListController(ShoppingListService shoppingListService) {
        this.shoppingListService = shoppingListService;
    }

    @GetMapping("/users/{userEmail}")
    public List<ShoppingList> getAllShoppingLists(@PathVariable String userEmail) {
        return shoppingListService.getAllShoppingLists(userEmail);
    }

    @GetMapping("/{shoppingListId}")
    public ShoppingList getShoppingList(@PathVariable Integer shoppingListId) {
        return shoppingListService.getShoppingListById(shoppingListId);
    }

    @GetMapping("/{shoppingListId}/users")
    public List<UserDTO> getUsersFromShoppingList(@PathVariable Integer shoppingListId) {
        return shoppingListService.getUsersFromShoppingList(shoppingListId);
    }

    @PostMapping("/")
    public ShoppingList createShoppingList(@RequestBody CreateShoppingListDTO createShoppingListDTO) {
        return shoppingListService.createShoppingList(createShoppingListDTO);
    }

    @PostMapping("/{shoppingListId}/users/{userEmail}")
    public ShoppingList addUserToShoppingList(@PathVariable String userEmail, @PathVariable Integer shoppingListId) {
        return shoppingListService.addUserToShoppingList(userEmail, shoppingListId);
    }

    @PostMapping("/{shoppingListId}/products/{productId}")
    public ShoppingList addProductToShoppingList(@PathVariable Integer shoppingListId, @PathVariable Integer productId) {
        return shoppingListService.addProductToShoppingList(shoppingListId, productId);
    }

    @DeleteMapping("/{shoppingListId}")
    public void deleteShoppingList(@PathVariable Integer shoppingListId) {
        shoppingListService.RemoveShoppingList(shoppingListId);
    }
}
