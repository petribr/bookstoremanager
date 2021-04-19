package com.petribr.bookstoremanager.author.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.petribr.bookstoremanager.author.dto.AuthorDTO;
import com.petribr.bookstoremanager.author.entity.Author;
import com.petribr.bookstoremanager.author.mapper.AuthorMapper;
import com.petribr.bookstoremanager.author.repository.AuthorRepository;
import com.petribr.bookstoremanager.exception.AuthorAlreadyExistsException;

@Service
public class AuthorService {

	private final static AuthorMapper authorMapper = AuthorMapper.INSTANCE;

	private AuthorRepository authorRepository;

	// Spring injeta automaticamente uma instancia criada pelo próprio contaniner do
	// authorRepository para nós
	@Autowired
	public AuthorService(AuthorRepository authorRepository) {
		this.authorRepository = authorRepository;
	};

	public AuthorDTO create(AuthorDTO authorDTO) throws AuthorAlreadyExistsException {
		verifyIfExists(authorDTO.getName());
		Author authorToCreate = authorMapper.toModel(authorDTO);
		Author createdAuthor = authorRepository.save(authorToCreate);
		return authorMapper.toDTO(createdAuthor);
	}

	private void verifyIfExists(String authorName) throws AuthorAlreadyExistsException {
		Optional<Author> duplicatedAuthor = authorRepository.findByName(authorName);
		if (duplicatedAuthor.isPresent()) {
			throw new AuthorAlreadyExistsException(authorName);
		}
	}

	public List<AuthorDTO> getAuthors() {
		List<Author> authors = authorRepository.findAll();
		
		List<AuthorDTO> listAuthorsDTO = new ArrayList<>(); 
		for (Author author : authors) {
			listAuthorsDTO.add(authorMapper.toDTO(author));
		}
				
		return listAuthorsDTO;
	}
}
