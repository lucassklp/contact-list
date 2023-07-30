package com.contact.list.exceptions;

import org.springframework.http.HttpStatus;

public class EmailAlreadyInUseException extends BusinessException {
    public EmailAlreadyInUseException(){
        super("Email already in use", HttpStatus.CONFLICT);
    }
}
