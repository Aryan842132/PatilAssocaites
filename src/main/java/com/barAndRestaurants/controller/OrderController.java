package com.barAndRestaurants.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.barAndRestaurants.model.Order;
import com.barAndRestaurants.service.OrderService;

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
        return orderService.createOrder(request.getAppUsersId(), request.getItems());
    }

    @GetMapping("/{appUsersId}")
    public List<Order> getOrders(@PathVariable("appUsersId") String appUsersId) {
        return orderService.getOrdersByAppUser(appUsersId);
    }

    // DTO for Order Request
    public static class OrderRequest {
        private String appUsersId;
        private List<Order.OrderItem> items;

        public String getAppUsersId() {
            return appUsersId;
        }

        public void setAppUsersId(String appUsersId) {
            this.appUsersId = appUsersId;
        }

        public List<Order.OrderItem> getItems() {
            return items;
        }

        public void setItems(List<Order.OrderItem> items) {
            this.items = items;
        }
    }
}
