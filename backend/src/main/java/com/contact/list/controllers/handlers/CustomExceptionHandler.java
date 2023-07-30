package com.contact.list.controllers.handlers;

import com.contact.list.api.response.ValidationErrorResponse;
import com.contact.list.exceptions.BusinessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.validation.FieldError;

import java.util.*;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { BusinessException.class })
    protected ResponseEntity<Object> handleBusinessException(BusinessException ex, WebRequest request) {
        return handleExceptionInternal(ex, Map.of("error", ex.getMessage()),
          new HttpHeaders(), ex.getHttpStatus(), request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatusCode status,
                                                                  WebRequest request) {

        List<ValidationErrorResponse> validationErrors = Optional.ofNullable(ex.getBindingResult())
            .map(bidingResults -> {
                List<ValidationErrorResponse> errors = new ArrayList<>();
                for(var error : bidingResults.getAllErrors()){
                    if(error instanceof FieldError fieldError){
                        errors.add(new ValidationErrorResponse(
                                fieldError.getField(),
                                error.getObjectName(),
                                error.getDefaultMessage())
                        );
                    } else {
                        errors.add(new ValidationErrorResponse(
                                null,
                                error.getObjectName(),
                                error.getDefaultMessage())
                        );
                    }
                }
                return errors;
            })
            .orElse(List.of());

        return this.handleExceptionInternal(ex, validationErrors, headers, status, request);
    }
}
