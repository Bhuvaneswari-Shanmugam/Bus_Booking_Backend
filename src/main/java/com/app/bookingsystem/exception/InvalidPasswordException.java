package com.app.bookingsystem.exception;

public class InvalidPasswordException extends  RuntimeException{

    public InvalidPasswordException(String message){
        super(message);
    }
}
