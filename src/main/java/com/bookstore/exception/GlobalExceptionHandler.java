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
        ApiResponse<String> apiResponse = ApiResponse.<String>builder()
            .message(ErrorCode.UNCATEGORIZED_ERROR.getMessage())
            .code(ErrorCode.UNCATEGORIZED_ERROR.getCode())    
        .build();
        return ResponseEntity.badRequest().body(apiResponse);
    }
    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse> handlerAppException(AppException e){
        ApiResponse<String> apiResponse =  ApiResponse.<String>builder()
            .message(e.getMessage())
            .code(e.getErrorCode())
            .result(null)
        .build();
        return ResponseEntity.badRequest().body(apiResponse);
    }
}
