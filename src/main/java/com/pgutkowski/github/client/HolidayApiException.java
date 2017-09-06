package com.pgutkowski.github.client;

public class HolidayApiException extends RuntimeException {

    private final int status;

    public HolidayApiException(String message, int status) {
        super(message);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
