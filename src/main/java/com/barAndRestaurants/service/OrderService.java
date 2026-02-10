package com.barAndRestaurants.service;

import com.barAndRestaurants.model.AppUser;
import com.barAndRestaurants.model.Order;
import com.barAndRestaurants.repository.AppUserRepository;
import com.barAndRestaurants.repository.OrderRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final AppUserRepository appUserRepository;

    public OrderService(OrderRepository orderRepository, AppUserRepository appUserRepository) {
        this.orderRepository = orderRepository;
        this.appUserRepository = appUserRepository;
    }

    public Order createOrder(String appUserId, List<Order.OrderItem> items) {
        AppUser appUser = appUserRepository.findById(appUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = new Order();
        order.setUserId(appUserId);
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
