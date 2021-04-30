package com.petribr.bookstoremanager.publisher.exception;

import javax.persistence.EntityExistsException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PublisherAlreadyExistsException extends EntityExistsException {

	public PublisherAlreadyExistsException(String name, String code) {
		super(String.format("Publisher with name %s or code %s already exists!", name, code));
	}
}
