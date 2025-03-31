package com.bookstore.exception;

public enum ErrorCode {
    USER_NOT_FOUND(1001, "User not found"),
    INVALID_CREDENTIALS(1002, "Invalid username or password"),
    EMAIL_ALREADY_EXISTS(1003, "Email is already in use"),
    ACCESS_DENIED(1004, "Access denied"),
    INTERNAL_SERVER_ERROR(1005, "Internal server error, please try again later"),
    DATABASE_ERROR(1006, "Database connection error"),
    UNCATEGORIZED_ERROR(1007, "Uncategorized error"),
    USER_ALREADY_EXISTS(1008, "User already exists"),
    ;

    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}

