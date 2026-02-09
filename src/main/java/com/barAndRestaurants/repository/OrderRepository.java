package com.barAndRestaurants.repository;

import com.barAndRestaurants.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface OrderRepository extends MongoRepository<Order, String> {
    List<Order> findByUserId(String userId);

    List<Order> findByTableId(String tableId);
}
