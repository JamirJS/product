package com.serviceproduct.infrastructure.adapter.output.exception;

import com.serviceproduct.domain.exception.InsufficientStockException;
import com.serviceproduct.domain.exception.InvalidPriceException;
import com.serviceproduct.domain.exception.ProductNotFoundException;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDate;
import java.util.*;


@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleProductEntityNotFound(ProductNotFoundException ex){
        logger.warn("Product Not found: {}", ex.getMessage(), ex);

        Map<String, String> errorResponse= Collections.singletonMap("error", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<Map<String, String>> handleInsufficientStock(InsufficientStockException ex){
        logger.warn("Insufficient Stock: {}", ex.getMessage(), ex);

        Map<String, String> errorResponse = Collections.singletonMap("error", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidPriceException.class)
    public ResponseEntity<Map<String, String>> handlerInvalidPrice(InvalidPriceException ex){
        logger.warn("Invalid Price: {}", ex.getMessage(), ex);

        Map<String, String> errorResponse = Collections.singletonMap("error", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handleContraintValidationException(ConstraintViolationException ex){
        Map<String, Object> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(violation -> {
            String propertyName = violation.getPropertyPath().toString();

            // Extraemos solo el nombre del parámetro (ej. "getByBrand.brand" -> "brand")
            String paramName = propertyName.substring(propertyName.lastIndexOf('.') + 1);
            errors.put(paramName, violation.getMessage());
        });
        return new ResponseEntity<>(this.formatResponse(errors), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        Map<String, Object> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
                errors.put(error.getField(), error.getDefaultMessage());
        });

        return new ResponseEntity<>(this.formatResponse(errors), HttpStatus.BAD_REQUEST);
    }

    private Map<String, Object> formatResponse(Map<String, Object> errors){
        Map<String, Object> response = new LinkedHashMap<>(); //linkedhashmap xq respeta el orden
        response.put("timestamp", LocalDate.now().toString());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("errors", errors);
        return response;
    }

}
