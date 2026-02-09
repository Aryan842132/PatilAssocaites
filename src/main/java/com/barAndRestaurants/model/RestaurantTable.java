package com.barAndRestaurants.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "restaurant_tables")
public class RestaurantTable {
    @Id
    private String id;
    private int tableNumber;
    private int capacity;
    private boolean isOccupied;
    private String currentUserId; // ID of the user currently occupying the table
}
