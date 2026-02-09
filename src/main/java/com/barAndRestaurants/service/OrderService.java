package com.barAndRestaurants.service;

import com.barAndRestaurants.model.Order;
import com.barAndRestaurants.model.User;
import com.barAndRestaurants.repository.OrderRepository;
import com.barAndRestaurants.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    public Order createOrder(String userId, List<Order.OrderItem> items) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.isActive()) {
            throw new RuntimeException("User session is not active");
        }

        Order order = new Order();
        order.setUserId(userId);
        order.setTableId(user.getTableId());
        order.setItems(items);
        order.setOrderTime(LocalDateTime.now());
        order.setStatus("PENDING");

        double total = items.stream().mapToDouble(i -> i.getPrice() * i.getQuantity()).sum();
        order.setTotalAmount(total);

        return orderRepository.save(order);
    }

    public List<Order> getOrdersByUser(String userId) {
        return orderRepository.findByUserId(userId);
    }
}
