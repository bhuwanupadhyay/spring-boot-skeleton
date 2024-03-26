package com.bhuwanupadhyay.demos.core.middleware;

import com.bhuwanupadhyay.demos.core.model.AppException;
import com.bhuwanupadhyay.demos.core.model.AppLogger;
import com.bhuwanupadhyay.demos.core.model.NotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.google.gson.Gson;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@RestController
public class GlobalExceptionMapper extends ResponseEntityExceptionHandler {

    private final AppLogger appLogger;

    public GlobalExceptionMapper(AppLogger appLogger) {
        this.appLogger = appLogger;
    }

    @ExceptionHandler(AppException.class)
    protected ResponseEntity<Object> handleAppException(AppException e) {
        return this.getErrorResponse(e);
    }

    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<Object> handleNotFoundException(NotFoundException e) {
        return this.getErrorResponse(
                new AppException(
                        HttpStatus.NOT_FOUND.value(), "Resource not found.", e.getMessage()));
    }

    @ExceptionHandler(JsonProcessingException.class)
    protected ResponseEntity<Object> handleValidationException(JsonProcessingException e) {
        return this.getErrorResponse(
                new AppException(
                        HttpStatus.BAD_REQUEST.value(), "Bad JSON format", e.getMessage()));
    }

    @ExceptionHandler(UnrecognizedPropertyException.class)
    protected ResponseEntity<Object> handleValidationException(UnrecognizedPropertyException e) {
        return this.getErrorResponse(
                new AppException(
                        HttpStatus.BAD_REQUEST.value(),
                        "Unrecognized fields found on request",
                        e.getMessage()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException e) {
        return this.getErrorResponse(
                new AppException(
                        HttpStatus.FORBIDDEN.value(), "Access is denied.", e.getMessage()));
    }

    @ExceptionHandler(ValidationException.class)
    protected ResponseEntity<Object> handleValidationException(ValidationException e) {
        return this.getErrorResponse(
                new AppException(
                        HttpStatus.BAD_REQUEST.value(), "Validation error.", e.getMessage()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintValidationException(
            ConstraintViolationException e) {
        appLogger.error("Validation exception", e);

        List<String> msgs = new ArrayList<>();
        for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
            msgs.add(violation.getMessage());
        }
        if (msgs.isEmpty()) {
            msgs.add("Invalid payload");
        }

        ObjectMapper mapper = new ObjectMapper();
        ArrayNode array = mapper.valueToTree(msgs);
        JsonNode result = mapper.createObjectNode().set("errors", array);

        return this.getErrorResponse(
                new AppException(
                        HttpStatus.BAD_REQUEST.value(), "Validation error.", result.toString()));
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleGeneralException(Exception e) {
        return this.getErrorResponse(
                new AppException(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Server error.",
                        "An unknown error has occurred.",
                        e));
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            @NonNull HttpRequestMethodNotSupportedException e,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest request) {
        return this.getErrorResponse(
                new AppException(
                        HttpStatus.METHOD_NOT_ALLOWED.value(),
                        "Method not found.",
                        "Method not found.",
                        e));
    }

    protected ResponseEntity<Object> getErrorResponse(AppException ex) {
        String exceptionMsg = ex.getError().getMessage();

        if (ex.getError().getCode() > 499) {
            this.appLogger.error(exceptionMsg, ex);
        } else {
            this.appLogger.warning(exceptionMsg, ex);
        }
        HttpStatus httpStatus = HttpStatus.valueOf(ex.getError().getCode());
        Gson gson = new Gson();
        return new ResponseEntity<>(gson.toJson(exceptionMsg), httpStatus);
    }
}
