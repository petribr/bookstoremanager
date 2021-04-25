package com.petribr.bookstoremanager.author.exception;

import javax.persistence.EntityExistsException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AuthorAlreadyExistsException extends EntityExistsException {

	// EntityExistsException já tratada no "BookstoreExpectionHandler"
    public AuthorAlreadyExistsException(String name) {
        super(String.format("User with name %s already exists!", name));
    }
}
