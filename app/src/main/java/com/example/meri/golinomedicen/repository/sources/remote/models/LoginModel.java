package com.example.meri.golinomedicen.repository.sources.remote.models;

import com.google.gson.annotations.SerializedName;

public class LoginModel {

    @SerializedName("message")
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "message : " + message;
    }
}
