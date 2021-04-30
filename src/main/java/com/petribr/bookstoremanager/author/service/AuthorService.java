package com.petribr.bookstoremanager.author.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.petribr.bookstoremanager.author.dto.AuthorDTO;
import com.petribr.bookstoremanager.author.entity.Author;
import com.petribr.bookstoremanager.author.exception.AuthorAlreadyExistsException;
import com.petribr.bookstoremanager.author.exception.AuthorNotFoundExcpetion;
import com.petribr.bookstoremanager.author.mapper.AuthorMapper;
import com.petribr.bookstoremanager.author.repository.AuthorRepository;

@Service
public class AuthorService {

	private static final AuthorMapper authorMapper = AuthorMapper.INSTANCE;

	private AuthorRepository authorRepository;

	// Spring injeta automaticamente uma instancia criada pelo próprio contaniner do
	// authorRepository para nós
	@Autowired
	public AuthorService(AuthorRepository authorRepository) {
		this.authorRepository = authorRepository;
	}

	// A camada service vai sempre retornar um DTO para o nosso controlador
	// O controlador não conhece Entidades (@Entity)
	public AuthorDTO create(AuthorDTO authorDTO) throws AuthorAlreadyExistsException {
		verifyIfExists(authorDTO.getName());
		
		Author authorToCreate = authorMapper.toModel(authorDTO);
		var createdAuthor = authorRepository.save(authorToCreate);
		
		return authorMapper.toDTO(createdAuthor);
	}

	public AuthorDTO findById(Long id) {
		Author foundAuthor = verifyAndGetAuthor(id);
		
		return authorMapper.toDTO(foundAuthor);
	}

	private Author verifyAndGetAuthor(Long id) {
		return authorRepository.findById(id)
			.orElseThrow(() -> new AuthorNotFoundExcpetion(id));
	}
	
	public List<AuthorDTO> findAll() {
		
		return authorRepository.findAll()
				.stream().map(authorMapper::toDTO)
				.collect(Collectors.toList());
	}
	
	private void verifyIfExists(String authorName) {
		authorRepository.findByName(authorName)
        .ifPresent(author -> {throw new AuthorAlreadyExistsException(authorName);});
	}

	public void delete(Long id) {
		verifyAndGetAuthor(id);
		authorRepository.deleteById(id);
	}
}
