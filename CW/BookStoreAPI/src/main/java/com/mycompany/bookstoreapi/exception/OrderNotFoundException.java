package com.mycompany.bookstoreapi.exception;



public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(String message) {
        super(message);
    }
}
