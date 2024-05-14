package com.shiopping.ShoppingApp.product;

import com.shiopping.ShoppingApp.category.Category;
import com.shiopping.ShoppingApp.shoppinglist.ShoppingList;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue
    private Integer id;
    private String productName;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    private String unitOfMeasure;

    @Enumerated(EnumType.STRING)
    private QuantityType quantityType;

    // tells that Product class is not the owner of 'products' field thus cannot save changes
    @ManyToMany(mappedBy = "products")
    private List<ShoppingList> shoppingLists = new ArrayList<>();
}
