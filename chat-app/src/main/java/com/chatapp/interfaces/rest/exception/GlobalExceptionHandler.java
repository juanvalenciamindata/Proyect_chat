package com.chatapp.interfaces.rest.exception;

import com.chatapp.domain.exception.DomainException;
import com.chatapp.interfaces.rest.dto.ErrorResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DomainException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleDomainException(DomainException ex) {
        return new ErrorResponse(ex.getMessage(), 400);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleGenericException(Exception ex) {
        return new ErrorResponse("Internal server error", 500);
    }
}
