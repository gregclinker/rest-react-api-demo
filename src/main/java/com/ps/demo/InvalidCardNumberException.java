package com.ps.demo;

public class InvalidCardNumberException extends Exception {
    public InvalidCardNumberException() {
    }

    public InvalidCardNumberException(String message) {
        super(message);
    }
}
