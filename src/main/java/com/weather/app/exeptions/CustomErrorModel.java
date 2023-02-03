package com.weather.app.exeptions;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class CustomErrorModel {

    private final String message;
    private final String code;
    private final int status;
    private final long timestamp;

    public CustomErrorModel(String message, String code, int status) {
        this.message = message;
        this.code = code;
        this.status = status;
        this.timestamp = Instant.now().toEpochMilli();
    }
}
