package com.shiopping.ShoppingApp.shoppinglist;

import com.shiopping.ShoppingApp.exception.ResourceNotFoundException;
import com.shiopping.ShoppingApp.product.Product;
import com.shiopping.ShoppingApp.product.ProductRepository;
import com.shiopping.ShoppingApp.user.User;
import com.shiopping.ShoppingApp.user.UserDTO;
import com.shiopping.ShoppingApp.user.UserRepository;
import com.shiopping.ShoppingApp.usershoppinglist.ListRole;
import com.shiopping.ShoppingApp.usershoppinglist.UserShoppingList;
import com.shiopping.ShoppingApp.usershoppinglist.UserShoppingListKey;
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

    public List<ShoppingList> getAllShoppingLists(String userEmail) {
        Optional<User> userOptional = userRepository.findByEmail(userEmail);

        if(userOptional.isEmpty()) {
            throw new ResourceNotFoundException("User with given email does not exist");
        }

        User user = userOptional.get();

        List<UserShoppingList> userShoppingLists = userShoppingListRepository.findByUser(user);

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

        Optional<User> userOptional = userRepository.findByEmail(createShoppingListDTO.getEmail());
        if(userOptional.isEmpty()) {
            throw new ResourceNotFoundException("User with given id does not exist");
        }

        User user = userOptional.get();

        // Save the ShoppingList entity before setting it to the UserShoppingList entity
        shoppingList = shoppingListRepository.save(shoppingList);

        UserShoppingList userShoppingList = new UserShoppingList();

        UserShoppingListKey userShoppingListKey = new UserShoppingListKey();
        userShoppingListKey.setUserId(user.getId());
        userShoppingListKey.setShoppingListId(shoppingList.getId());

        userShoppingList.setId(userShoppingListKey);
        userShoppingList.setUser(user);
        userShoppingList.setShoppingList(shoppingList);
        userShoppingList.setRole(ListRole.OWNER);

        userShoppingListRepository.save(userShoppingList);

        return shoppingList;
    }

    public ShoppingList addUserToShoppingList(String userEmail, Integer shoppingListId) {
        Optional<User> userOptional = userRepository.findByEmail(userEmail);

        if(userOptional.isEmpty()) {
            throw new ResourceNotFoundException("User with given id does not exist");
        }

        Optional<ShoppingList> shoppingListOptional = shoppingListRepository.findById(shoppingListId);

        if(shoppingListOptional.isEmpty()) {
            throw new ResourceNotFoundException("Shopping list with given id does not exist");
        }

        User user = userOptional.get();
        ShoppingList shoppingList = shoppingListOptional.get();

        // add 1 to user count attribute
        shoppingList.setUserCount(shoppingList.getUserCount() + 1);
        shoppingList = shoppingListRepository.save(shoppingList);

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
        shoppingList.setProductCount(shoppingList.getProductCount() + 1);

        shoppingListRepository.save(shoppingList);

        return shoppingList;
    }

    public void RemoveShoppingList(Integer shoppingListId) {
        Optional<ShoppingList> shoppingListOptional = shoppingListRepository.findById(shoppingListId);

        if(shoppingListOptional.isEmpty()) {
            throw new ResourceNotFoundException("Shopping list with given id does not exists");
        }

        ShoppingList shoppingList = shoppingListOptional.get();

        userShoppingListRepository.deleteAll(shoppingList.getUserShoppingLists());

        shoppingListRepository.deleteById(shoppingListId);
    }

    public List<UserDTO> getUsersFromShoppingList(Integer shoppingListId) {
        Optional<ShoppingList> shoppingListOptional = shoppingListRepository.findById(shoppingListId);

        if(shoppingListOptional.isEmpty()) {
            throw new ResourceNotFoundException("Shopping list with given id does not exists");
        }

        ShoppingList shoppingList = shoppingListOptional.get();

        List<UserShoppingList> userShoppingLists = userShoppingListRepository.findByShoppingList(shoppingList);

        List<UserDTO> userList = new ArrayList<>();
        for(UserShoppingList userShoppingList : userShoppingLists) {
            Integer id = userShoppingList.getUser().getId();
            String firstName = userShoppingList.getUser().getFirstName();
            String lastName = userShoppingList.getUser().getLastName();
            String email = userShoppingList.getUser().getEmail();
            String role = userShoppingList.getRole().name();

            userList.add(new UserDTO(id, firstName, lastName, email, role));
        }

        return userList;
    }
}
