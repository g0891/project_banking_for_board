package com.example.bank.rest.errorController;

import com.example.bank.dto.ErrorDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class DefaultErrorController {

    private static final Logger log = LoggerFactory.getLogger(DefaultErrorController.class);

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorDto> handleUnknownException(Exception ex) {
        log.warn(ex.getMessage());
        log.debug("Details:", ex.getCause());
        ex.printStackTrace();
        return new ResponseEntity<>(new ErrorDto(String.format("Something unexpected happened: %s", ex.getMessage())), HttpStatus.BAD_REQUEST);
    }

}
