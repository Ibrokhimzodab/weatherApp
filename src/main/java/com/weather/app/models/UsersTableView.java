package com.weather.app.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsersTableView {

    private String id;
    private String email;
    private String firstName;
    private String lastName;
    private Boolean isActive = true;
}
