package com.bookstore.exception;

import org.springframework.validation.Errors;

public class AppException extends RuntimeException {
    public AppException(ErrorCode errors) {
        super(errors.getMessage());
        this.errorCode = errors;
    }
    private ErrorCode errorCode;

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
