package com.bookstore.exception;

import com.bookstore.dto.request.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<ApiResponse> handlerRuntimeException( RuntimeException e) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage(ErrorCode.UNCATEGORIZED_ERROR.getMessage());
        apiResponse.setCode(ErrorCode.UNCATEGORIZED_ERROR.getCode());
        return ResponseEntity.badRequest().body(apiResponse);
    }
}
