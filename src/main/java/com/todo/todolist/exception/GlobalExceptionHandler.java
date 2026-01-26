package com.todo.todolist.exception;

import com.todo.todolist.dto.response.ApiErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(EntityNotFoundException.class)
    public ApiErrorResponse handleNotFound(
            EntityNotFoundException ex,
            HttpServletRequest request
    ) {
        return ApiErrorResponse.builder()
                               .status(HttpStatus.NOT_FOUND.value())
                               .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                               .message(ex.getMessage())
                               .timestamp(LocalDateTime.now())
                               .path(request.getRequestURI())
                               .build();
    }
    
    @ExceptionHandler(BadRequestException.class)
    public ApiErrorResponse handleBadRequest(
            BadRequestException ex,
            HttpServletRequest request
    ) {
        return ApiErrorResponse.builder()
                               .status(HttpStatus.BAD_REQUEST.value())
                               .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                               .message(ex.getMessage())
                               .timestamp(LocalDateTime.now())
                               .path(request.getRequestURI())
                               .build();
    }
    
    @ExceptionHandler(Exception.class)
    public ApiErrorResponse handleGeneric(
            Exception ex,
            HttpServletRequest request
    ) {
        return ApiErrorResponse.builder()
                               .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                               .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                               .message("Unexpected error occurred")
                               .timestamp(LocalDateTime.now())
                               .path(request.getRequestURI())
                               .build();
    }
}
