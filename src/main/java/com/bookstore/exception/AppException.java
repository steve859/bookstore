package com.bookstore.exception;

import org.springframework.validation.Errors;

public class AppException extends RuntimeException {
    public AppException(ErrorCode errors) {
        super(errors.getMessage());
        this.errorCode = errors;
    }
    private ErrorCode errorCode;

    public int getErrorCode() {
        return errorCode.getCode();
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
