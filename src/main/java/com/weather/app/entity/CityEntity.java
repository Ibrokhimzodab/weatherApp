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
@Document(collection = "cities")
public class CityEntity {

    @Id
    private String id;
    private String name;
    private Boolean isActive = true;
    private LocalDateTime createdAt = LocalDateTime.now();

    public CityEntity(String name){
        this.name = name;
    }
}
