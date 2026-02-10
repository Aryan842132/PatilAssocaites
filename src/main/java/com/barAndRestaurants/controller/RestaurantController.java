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
import com.barAndRestaurants.repository.AppUserRepository;

@RestController
@RequestMapping("/api/tables")
@CrossOrigin("*")
public class RestaurantController {
    private final RestaurantService restaurantService;

    private final AppUserRepository appUserRepository;

    public RestaurantController(RestaurantService restaurantService, AppUserRepository appUserRepository) {
        this.restaurantService = restaurantService;
        this.appUserRepository = appUserRepository;
    }

    @GetMapping
    public List<RestaurantTable> getAllTables() {
        return restaurantService.getAllTables();
    }

    @GetMapping("/available")
    public ResponseEntity<?> getAvailableTables() {
        List<RestaurantTable> tables = restaurantService.getAllTables();
        long available = tables.stream().filter(t -> !t.isOccupied()).count();
        return ResponseEntity.ok(Map.of(
                "totalTables", tables.size(),
                "availableTables", available,
                "tables", tables
        ));
    }

    @PostMapping("/book")
    public ResponseEntity<?> bookTable(@RequestBody Map<String, Object> payload) {
        int capacity = ((Number) payload.getOrDefault("capacity", 2)).intValue();
        String appUserId = (String) payload.get("appUserId");
        String email = (String) payload.get("email");

        if ((appUserId == null || appUserId.isBlank())) {
            if (email != null && !email.isBlank()) {
                appUserId = appUserRepository.findByEmail(email)
                        .map(AppUser::getUsersId)
                        .orElse(null);
            }
        }

        if (appUserId == null || appUserId.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("message", "appUserId or email is required and must match an existing AppUser"));
        }

        try {
            AppUser appUser = restaurantService.bookTable(appUserId, capacity);
            return ResponseEntity.ok(Map.of(
                    "usersId", appUser.getUsersId(),
                    "username", appUser.getUsername(),
                    "tableBooked", true
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @PostMapping("/release")
    public ResponseEntity<?> releaseTable(@RequestBody Map<String, String> payload) {
        String appUserId = payload.get("appUserId");
        if (appUserId == null || appUserId.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("message", "appUserId is required"));
        }

        try {
            restaurantService.releaseTable(appUserId);
            return ResponseEntity.ok(Map.of("message", "Table(s) released", "appUserId", appUserId));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }
}
