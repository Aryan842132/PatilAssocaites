package com.barAndRestaurants.service;

import com.barAndRestaurants.model.Order;
import com.barAndRestaurants.model.RestaurantTable;
import com.barAndRestaurants.repository.AppUserRepository;
import com.barAndRestaurants.repository.OrderRepository;
import com.barAndRestaurants.repository.RestaurantTableRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final AppUserRepository appUserRepository;
    private final RestaurantTableRepository tableRepository;

    public OrderService(OrderRepository orderRepository, AppUserRepository appUserRepository, RestaurantTableRepository tableRepository) {
        this.orderRepository = orderRepository;
        this.appUserRepository = appUserRepository;
        this.tableRepository = tableRepository;
    }

    public Order createOrder(String appUserId, List<Order.OrderItem> items) {
        appUserRepository.findById(appUserId)
            .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = new Order();
        order.setUserId(appUserId);
        
        // Find the table booked by this user and set tableId
        List<RestaurantTable> userTables = tableRepository.findByCurrentUserIdAndIsOccupied(appUserId, true);
        if (!userTables.isEmpty()) {
            order.setTableId(userTables.get(0).getId());
        }

        order.setItems(items);
        order.setOrderTime(LocalDateTime.now());
        order.setStatus("PENDING");

        double total = items.stream().mapToDouble(i -> i.getPrice() * i.getQuantity()).sum();
        order.setTotalAmount(total);

        return orderRepository.save(order);
    }

    public List<Order> getOrdersByAppUser(String appUserId) {
        return orderRepository.findByUserId(appUserId);
    }
}
