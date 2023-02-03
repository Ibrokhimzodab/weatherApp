package com.weather.app.models;

import com.weather.app.entity.WeatherType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CityDetailsModel {

    private String id;
    private String name;
    private Integer temp;
    private WeatherType weatherType;
    private LocalDateTime updatedAt;
}
