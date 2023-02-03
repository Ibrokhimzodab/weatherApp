package com.weather.app.entity;

import com.fasterxml.jackson.annotation.JsonValue;

public enum WeatherType {

    SUNNY("SUNNY"),
    CLOUDY("CLOUDY"),
    WINDY("WINDY"),
    RAINY("RAINY"),
    STORMY("STORMY");

    final String slug;

    WeatherType(String slug) {
        this.slug = slug;
    }

    @JsonValue
    public String getSlug(){
        return slug;
    }
}
