package com.blog.app.exceptions;

import com.blog.app.payloads.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> handleResourceNotFoundException(ResourceNotFoundException ex){
        String expMessage = ex.getMessage();
        ApiResponse apiResponse = new ApiResponse(expMessage,false);

        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);

    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){

        Map<String, String> errorMap= new HashMap<>();

        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();

        for(ObjectError error: allErrors){
            String fieldName = ((FieldError) error).getField();
            String defaultMessage = error.getDefaultMessage();
            errorMap.put(fieldName,defaultMessage);
        }
        //OR  above method can be written using lambda expression
        ex.getBindingResult().getAllErrors().forEach((error)->{

            String fieldName = ((FieldError) error).getField();
            String defaultMessage = error.getDefaultMessage();
            errorMap.put(fieldName,defaultMessage);

        });

        return new ResponseEntity<Map<String ,String >>(errorMap,HttpStatus.BAD_REQUEST);
    }
}
