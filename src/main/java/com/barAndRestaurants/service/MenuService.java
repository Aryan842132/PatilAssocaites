package com.barAndRestaurants.service;

import com.barAndRestaurants.model.MenuItem;
import com.barAndRestaurants.repository.MenuItemRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MenuService {
    private final MenuItemRepository menuItemRepository;

    public MenuService(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    public List<MenuItem> getAllItems() {
        return menuItemRepository.findAll();
    }

    public MenuItem addItem(MenuItem item) {
        return menuItemRepository.save(item);
    }
}
