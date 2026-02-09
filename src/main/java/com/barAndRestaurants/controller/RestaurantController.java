package com.barAndRestaurants.controller;

import com.barAndRestaurants.model.RestaurantTable;
import com.barAndRestaurants.model.User;
import com.barAndRestaurants.service.RestaurantService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tables")
@CrossOrigin("*")
public class RestaurantController {
    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping
    public List<RestaurantTable> getAllTables() {
        return restaurantService.getAllTables();
    }

    @PostMapping("/book")
    public User bookTable(@RequestBody Map<String, Integer> payload) {
        int capacity = payload.getOrDefault("capacity", 2);
        return restaurantService.bookTable(capacity);
    }

    @PostMapping("/release")
    public void releaseTable(@RequestBody Map<String, String> payload) {
        String userId = payload.get("userId");
        restaurantService.releaseTable(userId);
    }
}
