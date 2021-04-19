package com.petribr.bookstoremanager.author.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.petribr.bookstoremanager.author.dto.AuthorDTO;
import com.petribr.bookstoremanager.author.service.AuthorService;
import com.petribr.bookstoremanager.exception.AuthorAlreadyExistsException;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/authors")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AuthorController implements AuthorControllerDocs {
	private final AuthorService authorService;

	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public AuthorDTO create(@RequestBody @Valid AuthorDTO authorDTO) throws AuthorAlreadyExistsException {
		return authorService.create(authorDTO);
	}

	@GetMapping()
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<AuthorDTO>> get() {

		return ResponseEntity.ok(authorService.getAuthors());		
	}

//	@Autowired
//	public AuthorController(AuthorService authorService) {
//		this.authorService = authorService;
//	}	

}
