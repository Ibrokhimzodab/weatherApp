package com.weather.app.models;

import com.weather.app.entity.WeatherType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SetWeatherModel {

    private Integer temp;
    private WeatherType weatherType;
}
