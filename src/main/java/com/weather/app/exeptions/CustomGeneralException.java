package com.weather.app.exeptions;

public class CustomGeneralException extends RuntimeException{

    private final String code;

    public CustomGeneralException(String message, String code) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public CustomGeneralException() {
        this("Unknown error happened, see the debug console for more info", "0000");
    }
}
