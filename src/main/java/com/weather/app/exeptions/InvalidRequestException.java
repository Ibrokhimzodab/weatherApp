package com.weather.app.exeptions;

public class InvalidRequestException extends CustomGeneralException {

    public InvalidRequestException(String message, String code) {
        super(message, code);
    }

    public InvalidRequestException(String message) {
        this(message, "0000");
    }

    public InvalidRequestException() {
        this("Invalid Request", "0000");
    }
}
