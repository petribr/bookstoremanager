package com.petribr.bookstoremanager.user.exception;

import javax.persistence.EntityNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends EntityNotFoundException{
	
	public UserNotFoundException(Long id) {
		super(String.format("User with id %s not exists!", id));
	}
}
