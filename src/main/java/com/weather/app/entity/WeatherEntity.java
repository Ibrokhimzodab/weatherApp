package com.weather.app.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "weather")
public class WeatherEntity {

    @Id
    private String id;
    private String cityId;
    private Integer temp;
    private WeatherType weatherType;
    private LocalDateTime updatedAt = LocalDateTime.now();

    public WeatherEntity(String cityId, Integer temp, WeatherType weatherType){
        this.cityId = cityId;
        this.temp = temp;
        this.weatherType = weatherType;
    }
}
