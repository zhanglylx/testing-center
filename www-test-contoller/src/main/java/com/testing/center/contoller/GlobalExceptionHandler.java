package com.testing.center.contoller;

import com.testing.center.cmmon.utils.TestingCenterResult;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.xml.bind.ValidationException;

@ControllerAdvice
@Component
public class GlobalExceptionHandler {
    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public TestingCenterResult<Object> handle(ConstraintViolationException exception) {
        TestingCenterResult<Object> testingCenterResult = new TestingCenterResult<>();
        testingCenterResult.errorParameterDefaultNull(exception.getLocalizedMessage());
        return testingCenterResult;
    }
}
