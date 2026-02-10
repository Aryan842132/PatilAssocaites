package com.barAndRestaurants.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.barAndRestaurants.model.AppUser;
import com.barAndRestaurants.model.RestaurantTable;
import com.barAndRestaurants.repository.AppUserRepository;
import com.barAndRestaurants.repository.RestaurantTableRepository;

@Service
public class RestaurantService {
    private final RestaurantTableRepository tableRepository;
    private final AppUserRepository appUserRepository;

    public RestaurantService(RestaurantTableRepository tableRepository, AppUserRepository appUserRepository) {
        this.tableRepository = tableRepository;
        this.appUserRepository = appUserRepository;
    }

    public List<RestaurantTable> getAllTables() {
        return tableRepository.findAll();
    }

    @Transactional
    public AppUser bookTable(String appUserId, int requiredCapacity) {
        AppUser appUser = appUserRepository.findById(appUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<RestaurantTable> freeTables = tableRepository.findByIsOccupied(false);
        RestaurantTable table = freeTables.stream()
                .filter(t -> t.getCapacity() >= requiredCapacity)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No available table found for capacity: " + requiredCapacity));

        table.setOccupied(true);
        table.setCurrentUserId(appUserId);
        tableRepository.save(table);

        return appUser;
    }

    @Transactional
    public void releaseTable(String appUserId) {
        appUserRepository.findById(appUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        List<RestaurantTable> occupiedTables = tableRepository.findByCurrentUserIdAndIsOccupied(appUserId, true);
        for (RestaurantTable table : occupiedTables) {
            table.setOccupied(false);
            table.setCurrentUserId(null);
            tableRepository.save(table);
        }
    }
}
