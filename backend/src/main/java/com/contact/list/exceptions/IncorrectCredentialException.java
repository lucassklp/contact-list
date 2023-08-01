package com.contact.list.exceptions;

import org.springframework.http.HttpStatus;

public class IncorrectCredentialException extends BusinessException {
    public IncorrectCredentialException(){
        super("Email ou senha incorretas", HttpStatus.UNAUTHORIZED);
    }
}
