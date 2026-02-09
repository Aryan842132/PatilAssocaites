package com.barAndRestaurants.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "app_users")
public class AppUser {
    @Id
    private String id;
    private String username;
    private String email;
    private String password;
    private Role role;
}
