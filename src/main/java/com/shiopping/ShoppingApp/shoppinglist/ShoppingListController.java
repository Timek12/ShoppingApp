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
        try {
            List<ShoppingListDTO> shoppingLists = shoppingListService.getAllShoppingLists(userEmail);
            return new ResponseEntity<>(shoppingLists, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while fetching shopping lists: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{shoppingListId}")
    public ResponseEntity<?> getShoppingList(@PathVariable Integer shoppingListId) {
        try {
            ShoppingListDTO shoppingList = shoppingListService.getShoppingListById(shoppingListId);
            return new ResponseEntity<>(shoppingList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while fetching the shopping list: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{shoppingListId}/users")
    public ResponseEntity<?> getUsersFromShoppingList(@PathVariable Integer shoppingListId) {
        try {
            List<UserDTO> users = shoppingListService.getUsersFromShoppingList(shoppingListId);
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while fetching users from the shopping list: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> createShoppingList(@RequestBody CreateShoppingListDTO createShoppingListDTO) {
        try {
            ShoppingListDTO shoppingList = shoppingListService.createShoppingList(createShoppingListDTO);
            return new ResponseEntity<>(shoppingList, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while creating the shopping list: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{shoppingListId}/users/{userEmail}")
    public ResponseEntity<?> addUserToShoppingList(@PathVariable Integer shoppingListId, @PathVariable String userEmail) {
        try {
            ShoppingListDTO shoppingList = shoppingListService.addUserToShoppingList(userEmail, shoppingListId);
            return new ResponseEntity<>(shoppingList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while adding user to the shopping list: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{shoppingListId}/products/{productId}")
    public ResponseEntity<?> addProductToShoppingList(@PathVariable Integer shoppingListId, @PathVariable Integer productId) {
        try {
            ShoppingListDTO shoppingList = shoppingListService.addProductToShoppingList(shoppingListId, productId);
            return new ResponseEntity<>(shoppingList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while adding product to the shopping list: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{shoppingListId}")
    public ResponseEntity<?> deleteShoppingList(@PathVariable Integer shoppingListId) {
        try {
            shoppingListService.deleteShoppingList(shoppingListId);
            return new ResponseEntity<>("Shopping list has been deleted successfully.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while deleting the shopping list: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{shoppingListId}/products/{productId}")
    public ResponseEntity<?> deleteProductFromShoppingList(@PathVariable Integer shoppingListId, @PathVariable Integer productId) {
        try {
            shoppingListService.deleteProductFromShoppingList(shoppingListId, productId);
            return new ResponseEntity<>("Product has been removed from the shopping list successfully.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while removing product from the shopping list: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{shoppingListId}/users/{userId}")
    public ResponseEntity<?> deleteUserFromShoppingList(@PathVariable Integer shoppingListId, @PathVariable Integer userId) {
        try {
            shoppingListService.deleteUserFromShoppingList(shoppingListId, userId);
            return new ResponseEntity<>("User has been removed from the shopping list successfully.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while removing user from the shopping list: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}