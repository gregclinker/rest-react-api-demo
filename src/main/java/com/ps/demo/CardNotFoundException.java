package com.ps.demo;

public class CardNotFoundException extends Exception {
    public CardNotFoundException() {
    }

    public CardNotFoundException(String message) {
        super(message);
    }
}
