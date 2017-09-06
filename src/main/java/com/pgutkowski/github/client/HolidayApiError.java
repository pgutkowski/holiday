package com.pgutkowski.github.client;

/**
 * Represents body of https://holidayapi.com/ error responses
 */
public class HolidayApiError {

    private int status;

    private String error;

    public HolidayApiError() { }

    public HolidayApiError(int status, String error) {
        this.status = status;
        this.error = error;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
