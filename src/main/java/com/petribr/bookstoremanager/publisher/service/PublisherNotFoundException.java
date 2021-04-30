package com.petribr.bookstoremanager.publisher.service;

import javax.persistence.EntityNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PublisherNotFoundException extends EntityNotFoundException{
	
	public PublisherNotFoundException(Long id) {
		super(String.format("Publisher with id %s not exists!", id));
	}
}
