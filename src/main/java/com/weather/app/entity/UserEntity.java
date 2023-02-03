package com.weather.app.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class UserEntity {

    @Id
    private String id;
    private String email;
    private String firstName;
    private String lastName;
    private UserRole userRole;
    private String password;
    private Boolean isActive = true;
    private LocalDateTime createdAt = LocalDateTime.now();

    public UserEntity(String email, String firstName, String lastName, UserRole userRole, String password){
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userRole = userRole;
        this.password = password;
    }
}
