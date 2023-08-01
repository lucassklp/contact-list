package com.contact.list.exceptions;

import org.springframework.http.HttpStatus;

public class ContactNotFoundException extends BusinessException {
    public ContactNotFoundException(){
        super("Contato não foi encontrado para esse usuário", HttpStatus.NOT_FOUND);
    }
}
