package com.barAndRestaurants.repository;

import com.barAndRestaurants.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {
    List<User> findByTableIdAndActive(String tableId, boolean active);
}
