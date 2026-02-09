package com.barAndRestaurants.repository;

import com.barAndRestaurants.model.RestaurantTable;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface RestaurantTableRepository extends MongoRepository<RestaurantTable, String> {
    List<RestaurantTable> findByIsOccupied(boolean isOccupied);

    RestaurantTable findByTableNumber(int tableNumber);
}
