package com.barAndRestaurants.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Document(collection = "orders")
public class Order {
    @Id
    private String id;
    private String userId;
    private String tableId;
    private List<OrderItem> items;
    private double totalAmount;
    private String status; // PENDING, CONFIRMED, SERVED, COMPLETED
    private LocalDateTime orderTime;

    @Data
    public static class OrderItem {
        private String menuItemId;
        private String name;
        private double price;
        private int quantity;
    }
}
