package com.barAndRestaurants.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "app_users")
public class AppUser {
    @Id
    private String usersId;
    private String username;
    private String email;
    private String password;
    private Role role;
}
