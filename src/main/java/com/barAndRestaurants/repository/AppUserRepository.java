package com.barAndRestaurants.repository;

import com.barAndRestaurants.model.AppUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface AppUserRepository extends MongoRepository<AppUser, String> {
    Optional<AppUser> findByUsername(String username);

    Boolean existsByUsername(String username);

    Optional<AppUser> findByEmail(String email);

    Boolean existsByEmail(String email);
}
