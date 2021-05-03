package com.petribr.bookstoremanager.user.exception;

import javax.persistence.EntityExistsException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserAlreadyExistsException extends EntityExistsException {

	public UserAlreadyExistsException(String username, String email) {
		super(String.format("User with username %s or email %s already exists!", username, email));
	}
}
