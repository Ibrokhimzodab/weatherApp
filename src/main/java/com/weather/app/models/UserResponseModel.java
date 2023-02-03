package com.weather.app.models;

import com.weather.app.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseModel {

    private String id;
    private String email;
    private String firstName;
    private String lastName;
    private UserRole userRole;
    private Boolean isActive;
    private LocalDateTime createdAt;
}
