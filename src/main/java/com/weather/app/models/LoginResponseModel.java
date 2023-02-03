package com.weather.app.models;

import com.weather.app.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseModel {

    private String id;
    private String email;
    private UserRole userRole;
    private String accessToken;
    private String refreshToken;
}
