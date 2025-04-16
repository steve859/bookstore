package com.bookstore.exception;

import com.bookstore.dto.request.ApiResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<ApiResponse> handlingRuntimeException(RuntimeException e) {
        ApiResponse<String> apiResponse = ApiResponse.<String>builder()
            .message(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage())
            .code(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode())    
        .build();
        return ResponseEntity.badRequest().body(apiResponse);
    }
    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse> handlingAppException(AppException e){
        ErrorCode errorCode = e.getErrorCode();
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());
        return ResponseEntity
            .status(errorCode.getStatusCode())
            .body(apiResponse);

    }
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse> handlingValidation(MethodArgumentNotValidException exception){
        String enumKey = exception.getFieldError().getDefaultMessage();
        ErrorCode errorCode = ErrorCode.UNCATEGORIZED_EXCEPTION;

        try {
            errorCode = ErrorCode.valueOf(enumKey);
        } catch (IllegalArgumentException e){

        }
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());
        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ApiResponse> handlingAccessDeniedException( AccessDeniedException exception){
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;
        ApiResponse apiResponse = ApiResponse.builder()
            .code(errorCode.getCode())
            .message(errorCode.getMessage())
        .build();
        return ResponseEntity.status(errorCode.getStatusCode()).body(apiResponse);
    }
}
