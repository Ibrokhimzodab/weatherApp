package com.weather.app.entity;

import com.fasterxml.jackson.annotation.JsonValue;

public enum UserRole {

    ROOT("ROOT"),
    USER("USER");

    final String slug;

    UserRole(String slug) {
        this.slug = slug;
    }

    @JsonValue
    public String getSlug(){
        return slug;
    }
}
