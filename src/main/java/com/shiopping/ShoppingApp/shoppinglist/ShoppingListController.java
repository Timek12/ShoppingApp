package com.shiopping.ShoppingApp.shoppinglist;

import com.shiopping.ShoppingApp.user.UserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> getAllShoppingLists(@PathVariable String userEmail) {
        List<ShoppingListDTO> shoppingLists = shoppingListService.getAllShoppingLists(userEmail);
        return new ResponseEntity<>(shoppingLists, HttpStatus.OK);
    }

    @GetMapping("/{shoppingListId}")
    public ResponseEntity<?> getShoppingList(@PathVariable Integer shoppingListId) {
        ShoppingListDTO shoppingList = shoppingListService.getShoppingListById(shoppingListId);
        return new ResponseEntity<>(shoppingList, HttpStatus.OK);
    }

    @GetMapping("/{shoppingListId}/users")
    public ResponseEntity<?> getUsersFromShoppingList(@PathVariable Integer shoppingListId) {
        List<UserDTO> users = shoppingListService.getUsersFromShoppingList(shoppingListId);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<?> createShoppingList(@RequestBody CreateShoppingListDTO createShoppingListDTO) {
        ShoppingListDTO shoppingList = shoppingListService.createShoppingList(createShoppingListDTO);
        return new ResponseEntity<>(shoppingList, HttpStatus.CREATED);
    }

    @PostMapping("/{shoppingListId}/users/{userEmail}")
    public ResponseEntity<?> addUserToShoppingList(@PathVariable Integer shoppingListId, @PathVariable String userEmail) {
        ShoppingListDTO shoppingList = shoppingListService.addUserToShoppingList(userEmail, shoppingListId);
        return new ResponseEntity<>(shoppingList, HttpStatus.OK);
    }

    @PostMapping("/{shoppingListId}/products/{productId}")
    public ResponseEntity<?> addProductToShoppingList(@PathVariable Integer shoppingListId, @PathVariable Integer productId) {
        ShoppingListDTO shoppingList = shoppingListService.addProductToShoppingList(shoppingListId, productId);
        return new ResponseEntity<>(shoppingList, HttpStatus.OK);
    }

    @DeleteMapping("/{shoppingListId}")
    public ResponseEntity<?> deleteShoppingList(@PathVariable Integer shoppingListId) {
        shoppingListService.deleteShoppingList(shoppingListId);
        return new ResponseEntity<>("Shopping list has been deleted successfully.", HttpStatus.OK);
    }

    @DeleteMapping("/{shoppingListId}/products/{productId}")
    public ResponseEntity<?> deleteProductFromShoppingList(@PathVariable Integer shoppingListId, @PathVariable Integer productId) {
        shoppingListService.deleteProductFromShoppingList(shoppingListId, productId);
        return new ResponseEntity<>("Product has been removed from the shopping list successfully.", HttpStatus.OK);
    }

    @DeleteMapping("/{shoppingListId}/users/{userId}")
    public ResponseEntity<?> deleteUserFromShoppingList(@PathVariable Integer shoppingListId, @PathVariable Integer userId) {
        shoppingListService.deleteUserFromShoppingList(shoppingListId, userId);
        return new ResponseEntity<>("User has been removed from the shopping list successfully.", HttpStatus.OK);
    }
}
