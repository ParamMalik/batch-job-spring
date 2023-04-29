package com.trantor.bill.controller.advice;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.trantor.bill.exception.BadRequestException;
import com.trantor.bill.exception.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebInputException;

import javax.validation.ConstraintViolationException;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends DefaultErrorAttributes {

    private static final String API_ERROR = "API_ERROR";
    private static final String MISSING_REQUIRED_PARAMETERS = "MISSING_REQUIRED_PARAMETERS";
    private static final String INVALID_VALUE = "INVALID_VALUE";
    private static final String ERRORS = "errors";
    private static final String MESSAGE = "message";

    @ExceptionHandler(value = {ResponseStatusException.class})
    public ResponseEntity<Object> exception(ResponseStatusException exception) {
        HashMap<String, List<ErrorResponse>> errors = new HashMap<>();
        ErrorResponse errorResponse = ErrorResponse.builder().category(API_ERROR).code(INVALID_VALUE).detail(exception.getReason()).build();
        errors.put(ERRORS, List.of(errorResponse));
        log.error("", exception);
        return new ResponseEntity<>(errors, exception.getStatusCode());
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<Map<String, List<ErrorResponse>>> handleException(WebExchangeBindException e) {

        Map<String, List<ErrorResponse>> body = new HashMap<>();
        List<ErrorResponse> errors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> ErrorResponse.builder()
                        .code(API_ERROR)
                        .category(MISSING_REQUIRED_PARAMETERS)
                        .detail(error.getField() + " " + error.getDefaultMessage())
                        .build())
                .collect(Collectors.toList());
        log.error("Web Bind exception " + errors.get(0).getDetail());
        body.put(ERRORS, Collections.unmodifiableList(errors));
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(ServerWebInputException.class)
    public ResponseEntity<Map<String, List<ErrorResponse>>> handleServerWebInputException(ServerWebInputException e) {
        ErrorResponse errorResponse = null;
        Map<String, List<ErrorResponse>> body = new HashMap<>();
        if (Objects.equals(e.getReason(), "Type mismatch.")) {
            errorResponse = ErrorResponse.builder().category(API_ERROR).code(MISSING_REQUIRED_PARAMETERS).detail("ID Format is not recognized").build();
        } else if (Objects.requireNonNull(e.getMessage()).contains("Enum class")) {
            errorResponse = ErrorResponse.builder().category(API_ERROR).code(MISSING_REQUIRED_PARAMETERS).detail("Cannot deserialize the provided JSON").build();
        } else {
            errorResponse = ErrorResponse.builder().category(API_ERROR).code(MISSING_REQUIRED_PARAMETERS).detail(((BadRequestException) Objects.requireNonNull(e.getRootCause())).getReason()).build();
        }
        body.put(ERRORS, List.of(errorResponse));
        return ResponseEntity.badRequest().body(body);
    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, List<ErrorResponse>>> dataIntegrityViolationException(DataIntegrityViolationException exception) {
        Map<String, List<ErrorResponse>> body = new HashMap<>();
        ErrorResponse errorResponse = ErrorResponse.builder().category(API_ERROR).code(MISSING_REQUIRED_PARAMETERS).detail(exception.getCause().getMessage()).build();
        body.put(ERRORS, List.of(errorResponse));
        return new ResponseEntity<>(body, BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, List<ErrorResponse>>> constraintViolationException(ConstraintViolationException exception) {
        Map<String, List<ErrorResponse>> body = new HashMap<>();
        List<ErrorResponse> errorResponse = exception.getConstraintViolations()
                .stream()
                .map(error -> ErrorResponse.builder()
                        .code(API_ERROR)
                        .category(MISSING_REQUIRED_PARAMETERS)
                        .detail(error.getMessage())
                        .build())
                .collect(Collectors.toList());
        body.put(ERRORS, errorResponse);
        return new ResponseEntity<>(body, BAD_REQUEST);
    }

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request,
                                                  ErrorAttributeOptions options) {
        String errorMessage = Objects.nonNull(super.getErrorAttributes(request, options).get(MESSAGE)) ? super.getErrorAttributes(request, options).get(MESSAGE).toString() : super.getError(request).getMessage();
        log.error("Global handler exception {}", super.getError(request).getMessage());
        Map<String, Object> map = super.getErrorAttributes(
                request, options);
        map.put("status", HttpStatus.BAD_REQUEST.value());
        map.put(MESSAGE, errorMessage);
        map.put("error", API_ERROR);
        return map;
    }

    @ExceptionHandler(value = {InvalidFormatException.class})
    public ResponseEntity<Map<String, List<ErrorResponse>>> handleInvalidFormatException(InvalidFormatException e) {
        Map<String, List<ErrorResponse>> body = new HashMap<>();
        ErrorResponse errorResponse = ErrorResponse.builder().category(API_ERROR).code(MISSING_REQUIRED_PARAMETERS).detail(e.getCause().getMessage()).build();
        body.put(ERRORS, List.of(errorResponse));
        return ResponseEntity.badRequest().body(body);
    }
}
