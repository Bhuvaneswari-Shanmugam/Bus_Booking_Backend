package com.app.bookingsystem.exception;

public class EmailNotFormattedException extends  RuntimeException{

    public EmailNotFormattedException(String message){
        super(message);
    }
}
