package com.barAndRestaurants.controller;

import com.barAndRestaurants.model.MenuItem;
import com.barAndRestaurants.service.MenuService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/menu")
@CrossOrigin("*")
public class MenuController {
    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping
    public List<MenuItem> getMenu() {
        return menuService.getAllItems();
    }

    @PostMapping
    public MenuItem addMenuItem(@RequestBody MenuItem item) {
        return menuService.addItem(item);
    }
}
