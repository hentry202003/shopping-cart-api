package com.shoppingcart.shopping_cart_api.exception;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(String message){
        super(message);
    }
}
