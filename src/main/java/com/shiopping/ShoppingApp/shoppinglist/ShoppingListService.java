package com.shiopping.ShoppingApp.shoppinglist;

import com.shiopping.ShoppingApp.exception.ResourceNotFoundException;
import com.shiopping.ShoppingApp.product.Product;
import com.shiopping.ShoppingApp.product.ProductRepository;
import com.shiopping.ShoppingApp.user.User;
import com.shiopping.ShoppingApp.user.UserRepository;
import com.shiopping.ShoppingApp.usershoppinglist.ListRole;
import com.shiopping.ShoppingApp.usershoppinglist.UserShoppingList;
import com.shiopping.ShoppingApp.usershoppinglist.UserShoppingListRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ShoppingListService {
    private final ShoppingListRepository shoppingListRepository;
    private final UserRepository userRepository;
    private final UserShoppingListRepository userShoppingListRepository;
    private final ProductRepository productRepository;
    public ShoppingListService(
            ShoppingListRepository shoppingListRepository,
            UserRepository userRepository,
            UserShoppingListRepository userShoppingListRepository,
            ProductRepository productRepository
    ) {
        this.shoppingListRepository = shoppingListRepository;
        this.userRepository = userRepository;
        this.userShoppingListRepository = userShoppingListRepository;
        this.productRepository = productRepository;
    }

    public List<ShoppingList> getAllShoppingLists(Integer userId) {
        Optional<User> userOptional = userRepository.findById(userId);

        if(userOptional.isEmpty()) {
            throw new ResourceNotFoundException("User with given id does not exist");
        }

        List<UserShoppingList> userShoppingLists = userShoppingListRepository.findAll();

        List<ShoppingList> shoppingLists = new ArrayList<>();

        for(UserShoppingList userShoppingList : userShoppingLists) {
            shoppingLists.add(userShoppingList.getShoppingList());
        }

        return shoppingLists;
    }

    public ShoppingList getShoppingListById(Integer id) {
        return shoppingListRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Shopping list with given id does not exist."));
    }

    public ShoppingList createShoppingList(CreateShoppingListDTO createShoppingListDTO) {
        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setName(createShoppingListDTO.getName());
        shoppingList.setDescription(createShoppingListDTO.getDescription());

        Optional<User> userOptional = userRepository.findById(createShoppingListDTO.getUserId());
        if(userOptional.isEmpty()) {
            throw new ResourceNotFoundException("User with given id does not exist");
        }

        User user = userOptional.get();

        // returns created shopping list with id
        shoppingList = shoppingListRepository.save(shoppingList);

        UserShoppingList userShoppingList = new UserShoppingList();
        userShoppingList.setUser(user);
        userShoppingList.setShoppingList(shoppingList);
        userShoppingList.setRole(ListRole.OWNER);

        userShoppingListRepository.save(userShoppingList);

        return shoppingList;
    }

    public ShoppingList addUserToShoppingList(Integer userId, Integer shoppingListId) {
        Optional<User> userOptional = userRepository.findById(userId);

        if(userOptional.isEmpty()) {
            throw new ResourceNotFoundException("User with given id does not exist");
        }

        Optional<ShoppingList> shoppingListOptional = shoppingListRepository.findById(shoppingListId);

        if(shoppingListOptional.isEmpty()) {
            throw new ResourceNotFoundException("Shopping list with given id does not exist");
        }

        User user = userOptional.get();
        ShoppingList shoppingList = shoppingListOptional.get();

        UserShoppingList userShoppingList = new UserShoppingList();
        userShoppingList.setUser(user);
        userShoppingList.setShoppingList(shoppingList);
        userShoppingList.setRole(ListRole.MEMBER);

        userShoppingListRepository.save(userShoppingList);

        return shoppingList;
    }

    public ShoppingList addProductToShoppingList(Integer shoppingListId, Integer productId) {
        Optional<ShoppingList> shoppingListOptional = shoppingListRepository.findById(shoppingListId);

        if(shoppingListOptional.isEmpty()) {
            throw new ResourceNotFoundException("Shopping list with given id does not exist");
        }

        ShoppingList shoppingList = shoppingListOptional.get();

        Optional<Product> productOptional = productRepository.findById(productId);

        if(productOptional.isEmpty()) {
            throw new ResourceNotFoundException("Product with given id does not exist");
        }

        Product product = productOptional.get();

        shoppingList.getProducts().add(product);

        shoppingListRepository.save(shoppingList);

        return shoppingList;
    }


}
