package com.example.bank.rest.errorController.exception;

public class BankIncorrectAmountException extends RuntimeException{
    public BankIncorrectAmountException(String msg) {
        super(msg);
    }
}
