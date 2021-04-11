package com.linyuanlin.LibraryManagement.model;

public class CustomException extends Exception {
    private String code;
    private String message;
    private int status;

    public CustomException(String code, String message, int status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getJson() {
        return "{\"code\": \"" + code + "\", \"message\": \"" + message + "\"}";
    }
}
