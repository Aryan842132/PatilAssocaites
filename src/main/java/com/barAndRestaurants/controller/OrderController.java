package com.barAndRestaurants.controller;

import com.barAndRestaurants.model.Order;
import com.barAndRestaurants.service.OrderService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin("*")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public Order createOrder(@RequestBody OrderRequest request) {
        return orderService.createOrder(request.getUserId(), request.getItems());
    }

    @GetMapping("/{userId}")
    public List<Order> getOrders(@PathVariable String userId) {
        return orderService.getOrdersByAppUser(userId);
    }

    // DTO for Order Request
    public static class OrderRequest {
        private String userId;
        private List<Order.OrderItem> items;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public List<Order.OrderItem> getItems() {
            return items;
        }

        public void setItems(List<Order.OrderItem> items) {
            this.items = items;
        }
    }
}
