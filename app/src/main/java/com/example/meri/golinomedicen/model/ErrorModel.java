package com.example.meri.golinomedicen.model;

public class ErrorModel {


    public static final String UNKNOWN_MESSAGE = "unknown";
    public static final int UNKNOWN_STATUS_CODE = -1;


    private String message;
    private int statusCode;

    public ErrorModel(final String message, final int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }


    public ErrorModel(final String message) {
        this(message, UNKNOWN_STATUS_CODE);
    }

    public ErrorModel(final int statusCode) {
        this(UNKNOWN_MESSAGE, statusCode);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(final int statusCode) {
        this.statusCode = statusCode;
    }
}
