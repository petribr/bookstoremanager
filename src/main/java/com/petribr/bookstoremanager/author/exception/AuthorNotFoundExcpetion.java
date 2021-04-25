package com.petribr.bookstoremanager.author.exception;

import javax.persistence.EntityNotFoundException;

public class AuthorNotFoundExcpetion extends EntityNotFoundException {

	public AuthorNotFoundExcpetion(Long id) {
		super(String.format("Author with id %s not exists!", id));
	}
}
