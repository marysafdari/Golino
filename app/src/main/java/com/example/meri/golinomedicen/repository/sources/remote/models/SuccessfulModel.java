package com.example.meri.golinomedicen.repository.sources.remote.models;

import com.google.gson.annotations.SerializedName;

public class SuccessfulModel {

    @SerializedName("message")
    private String message;

    @SerializedName("status")
    private int statusCode;

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

    @Override
    public String toString() {
        return "message : " + message + ", status code : " + statusCode;
    }

}
