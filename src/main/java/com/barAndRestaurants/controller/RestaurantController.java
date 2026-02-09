package com.barAndRestaurants.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.barAndRestaurants.model.RestaurantTable;
import com.barAndRestaurants.model.User;
import com.barAndRestaurants.service.RestaurantService;

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
    public ResponseEntity<?> bookTable(@RequestBody Map<String, Integer> payload) {
        int capacity = payload.getOrDefault("capacity", 2);
        User user = restaurantService.bookTable(capacity);
        return ResponseEntity.ok(Map.of(
                "userId", user.getUserId(),
                "tableId", user.getTableId(),
                "active", user.isActive(),
                "connectedAt", user.getConnectedAt()
        ));
    }

    @PostMapping("/release")
    public void releaseTable(@RequestBody Map<String, String> payload) {
        String userId = payload.get("userId");
        restaurantService.releaseTable(userId);
    }
}
