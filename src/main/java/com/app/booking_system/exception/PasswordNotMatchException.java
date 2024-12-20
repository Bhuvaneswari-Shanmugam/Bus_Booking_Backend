package com.app.booking_system.exception;

public class PasswordNotMatchException extends RuntimeException{

    public PasswordNotMatchException(String message){
        super(message);
    }
}
