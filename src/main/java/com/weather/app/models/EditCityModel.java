package com.weather.app.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EditCityModel {

    @NotNull(message = "name is required")
    private String name;
    @NotNull(message = "is Active is required")
    private Boolean isActive;
}
