package com.weather.app.exeptions;

public class ForbiddenRequestException extends CustomGeneralException{

    public ForbiddenRequestException(String message, String code) {
        super(message, code);
    }

    public ForbiddenRequestException(String message) {
        super(message, "0000");
    }

    public ForbiddenRequestException() {
        super("Forbidden request", "0000");
    }
}
