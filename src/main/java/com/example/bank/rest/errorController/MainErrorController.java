package com.example.bank.rest.errorController;

import com.example.bank.dto.ErrorDto;
import com.example.bank.rest.errorController.exception.BankIncorrectAmountException;
import com.example.bank.rest.errorController.exception.BankInsufficientFundsException;
import com.example.bank.rest.errorController.exception.BankProjectsDetailsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class MainErrorController {

    private static final Logger log = LoggerFactory.getLogger(MainErrorController.class);

    @ExceptionHandler({
            BankInsufficientFundsException.class,
            BankProjectsDetailsException.class,
            BankIncorrectAmountException.class
    })
    public ResponseEntity<ErrorDto> handleKnownException(Exception ex) {
        log.warn(ex.getMessage());
        return new ResponseEntity<>(new ErrorDto(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

}
