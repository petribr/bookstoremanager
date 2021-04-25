package com.petribr.bookstoremanager.author.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.petribr.bookstoremanager.author.dto.AuthorDTO;
import com.petribr.bookstoremanager.author.service.AuthorService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/authors")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AuthorController implements AuthorControllerDocs {
	private final AuthorService authorService;

	//Pode ter regras de interface e tratamento de dados dessa integração
	// toda a conversa com a parte externa da api
	// regras de negócio não (como aquele objeto se comporta no seu domínio)
	
	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public AuthorDTO create(@RequestBody @Valid AuthorDTO authorDTO) {
		return authorService.create(authorDTO);
	}
	
	

	@GetMapping()
	@ResponseStatus(HttpStatus.OK)
	public List<AuthorDTO> findAll() {

		return authorService.findAll();		
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public AuthorDTO findById(@PathVariable Long id) {
		return authorService.findById(id);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		authorService.delete(id);
	}
	
	
	
//	@GetMapping("{id}")
//	@ResponseStatus(HttpStatus.OK)
//	public ResponseEntity<AuthorDTO> getById(Long id) {
//
//		return ResponseEntity.ok(authorService.findById(id));		
//	}
}
