package com.petribr.bookstoremanager.author.exception;

import javax.persistence.EntityNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AuthorNotFoundExcpetion extends EntityNotFoundException {

	public AuthorNotFoundExcpetion(Long id) {
		super(String.format("Author with id %s not exists!", id));
	}
}
