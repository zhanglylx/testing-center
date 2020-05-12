package com.testing.center.web.contoller;


import com.testing.center.web.common.utils.TestingCenterResult;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
@Component
public class GlobalExceptionHandler {
    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<TestingCenterResult<Object>> handle(ConstraintViolationException exception) {
        TestingCenterResult<Object> testingCenterResult = new TestingCenterResult<>();
        testingCenterResult.errorParameterDefaultNull(exception.getLocalizedMessage());
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json;charset=UTF-8");
        return new ResponseEntity<TestingCenterResult<Object>>(testingCenterResult, headers, HttpStatus.OK);
    }
}
