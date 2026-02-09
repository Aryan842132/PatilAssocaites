package com.barAndRestaurants.service;

import com.barAndRestaurants.model.RestaurantTable;
import com.barAndRestaurants.repository.RestaurantTableRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {
    private final RestaurantTableRepository tableRepository;

    public DataLoader(RestaurantTableRepository tableRepository) {
        this.tableRepository = tableRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (tableRepository.count() == 0) {
            tableRepository.saveAll(List.of(
                    createTable(1, 2),
                    createTable(2, 4),
                    createTable(3, 6)
            ));
        }
    }

    private RestaurantTable createTable(int number, int capacity) {
        RestaurantTable t = new RestaurantTable();
        t.setTableNumber(number);
        t.setCapacity(capacity);
        t.setOccupied(false);
        t.setCurrentUserId(null);
        return t;
    }
}
