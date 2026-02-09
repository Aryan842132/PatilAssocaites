package com.barAndRestaurants.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "restaurant_tables")
public class RestaurantTable {
    @Id
    private String id;
    private int tableNumber;
    private int capacity;
    private boolean isOccupied;
    private String currentUserId;
}
