package com.weather.app.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import reactor.core.publisher.Flux;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsModel {

    private String id;
    private String email;
    private String firstName;
    private String lastName;
    private Boolean isActive = true;
    private List<UserSubscriptionsModel> userSubscriptions;
}
