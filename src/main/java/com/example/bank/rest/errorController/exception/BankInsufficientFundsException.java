package com.example.bank.rest.errorController.exception;

public class BankInsufficientFundsException extends RuntimeException{
    public BankInsufficientFundsException(String msg) {
        super(msg);
    }
}
