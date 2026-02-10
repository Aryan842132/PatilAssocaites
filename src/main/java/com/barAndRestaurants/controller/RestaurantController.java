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

import com.barAndRestaurants.model.AppUser;
import com.barAndRestaurants.model.RestaurantTable;
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
    public ResponseEntity<?> bookTable(@RequestBody Map<String, Object> payload) {
        int capacity = ((Number) payload.getOrDefault("capacity", 2)).intValue();
        String appUserId = (String) payload.get("appUserId");
        AppUser appUser = restaurantService.bookTable(appUserId, capacity);
        return ResponseEntity.ok(Map.of(
                "usersId", appUser.getUsersId(),
                "username", appUser.getUsername(),
                "tableBooked", true
        ));
    }

    @PostMapping("/release")
    public void releaseTable(@RequestBody Map<String, String> payload) {
        String appUserId = payload.get("appUserId");
        restaurantService.releaseTable(appUserId);
    }
}
