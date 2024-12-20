package com.app.booking_system.exception;

public class EmailNotFormattedException extends  RuntimeException{

    public EmailNotFormattedException(String message){
        super(message);
    }
}
