package com.barAndRestaurants.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "users")
public class User {
    @Id
    private String userId;
    private String tableId;
    private boolean active;
    private LocalDateTime connectedAt;
}
