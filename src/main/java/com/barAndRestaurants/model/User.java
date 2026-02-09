package com.barAndRestaurants.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Data
@Document(collection = "users")
public class User {
    @Id
    private String id; // This serves as the connection ID
    private String tableId; // The table they are connected to
    private boolean active;
    private LocalDateTime connectedAt;
}
