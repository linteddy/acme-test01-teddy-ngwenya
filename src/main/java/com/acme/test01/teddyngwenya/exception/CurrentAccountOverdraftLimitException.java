package com.acme.test01.teddyngwenya.exception;

public class CurrentAccountOverdraftLimitException extends RuntimeException{
    public CurrentAccountOverdraftLimitException(String message) {
        super(message);
    }
}
