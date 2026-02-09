package com.barAndRestaurants.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.barAndRestaurants.model.RestaurantTable;
import com.barAndRestaurants.model.User;
import com.barAndRestaurants.repository.RestaurantTableRepository;
import com.barAndRestaurants.repository.UserRepository;

@Service
public class RestaurantService {
    private final RestaurantTableRepository tableRepository;
    private final UserRepository userRepository;

    public RestaurantService(RestaurantTableRepository tableRepository, UserRepository userRepository) {
        this.tableRepository = tableRepository;
        this.userRepository = userRepository;
    }

    public List<RestaurantTable> getAllTables() {
        return tableRepository.findAll();
    }

    @Transactional
    public User bookTable(int requiredCapacity) {
        List<RestaurantTable> freeTables = tableRepository.findByIsOccupied(false);
        RestaurantTable table = freeTables.stream()
                .filter(t -> t.getCapacity() >= requiredCapacity)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No available table found for capacity: " + requiredCapacity));

        
        User user = new User();
        user.setTableId(table.getId());
        user.setActive(true);
        user.setConnectedAt(LocalDateTime.now());
        user = userRepository.save(user);

       
        table.setOccupied(true);
        table.setCurrentUserId(user.getUserId());
        tableRepository.save(table);

        return user;
    }

    @Transactional
    public void releaseTable(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.isActive()) {
            user.setActive(false);
            userRepository.save(user);

            RestaurantTable table = tableRepository.findById(user.getTableId())
                    .orElseThrow(() -> new RuntimeException("Table not found"));
            table.setOccupied(false);
            table.setCurrentUserId(null);
            tableRepository.save(table);
        }
    }
}
