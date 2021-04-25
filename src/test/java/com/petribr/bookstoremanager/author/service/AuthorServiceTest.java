package com.petribr.bookstoremanager.author.service;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.petribr.bookstoremanager.author.builder.AuthorDTOBuilder;
import com.petribr.bookstoremanager.author.dto.AuthorDTO;
import com.petribr.bookstoremanager.author.entity.Author;
import com.petribr.bookstoremanager.author.exception.AuthorAlreadyExistsException;
import com.petribr.bookstoremanager.author.exception.AuthorNotFoundExcpetion;
import com.petribr.bookstoremanager.author.mapper.AuthorMapper;
import com.petribr.bookstoremanager.author.repository.AuthorRepository;

@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {

    private final AuthorMapper authorMapper = AuthorMapper.INSTANCE;

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorService authorService;

    private AuthorDTOBuilder authorDTOBuilder;

    @BeforeEach
    void setUp() {
        authorDTOBuilder = AuthorDTOBuilder.builder().build();
    }

    @Test
    @DisplayName("When new author is informed then it should be created")
    void whenNewAuthorIsInformedThenItShouldBeCreated() {
        // given
        AuthorDTO expectedAuthorToCreateDTO = authorDTOBuilder.buildAuthorDTO();
        Author expectedCreatedAuthor = authorMapper.toModel(expectedAuthorToCreateDTO);
        // when
        when(authorRepository.save(any(Author.class))).thenReturn(expectedCreatedAuthor); //cria elemento duble
        when(authorRepository.findByName(expectedAuthorToCreateDTO.getName())).thenReturn(Optional.empty());

        AuthorDTO createdAuthorDTO = authorService.create(expectedAuthorToCreateDTO);
        // then
        assertThat(createdAuthorDTO, is(equalTo(expectedAuthorToCreateDTO)));
    }
    
    @Test
    @DisplayName("When existing author is informed then an exception should be thrown")
    void whenExistingAuthorIsInformedThenAnExcpetionShouldBeThrown() {
    	// given
    	AuthorDTO expectedAuthorToCreateDTO = authorDTOBuilder.buildAuthorDTO();
    	Author expectedCreatedAuthor = authorMapper.toModel(expectedAuthorToCreateDTO);
    	// when
    	when(authorRepository.findByName(expectedAuthorToCreateDTO.getName()))
    		.thenReturn(Optional.of(expectedCreatedAuthor));
    	
    	Assertions.assertThrows(AuthorAlreadyExistsException.class, 
    			() -> authorService.create(expectedAuthorToCreateDTO));
    }
    
    @Test
    @DisplayName(" ")
    void whenValidIdIsGivenThenAnAuthorShouldBeReturnd() {
    	// given
    	AuthorDTO expectedFoundAuthorDTO = authorDTOBuilder.buildAuthorDTO();
    	Author expectedFoundAuthor = authorMapper.toModel(expectedFoundAuthorDTO);
    	
    	// when
    	when(authorRepository.findById(expectedFoundAuthorDTO.getId()))
    		.thenReturn(Optional.of(expectedFoundAuthor));    	
    	AuthorDTO foundAuthorDTO = authorService.findById(expectedFoundAuthorDTO.getId());
    	// Then
    	assertThat(foundAuthorDTO, is(equalTo(expectedFoundAuthorDTO)));
    }
    
    @Test
    @DisplayName(" ")
    void whenInvalidIdIsGivenThenAnExcpetionShouldBeThrown() {
    	// given
    	AuthorDTO expectedFoundAuthorDTO = authorDTOBuilder.buildAuthorDTO();
    	
    	// when
    	when(authorRepository.findById(expectedFoundAuthorDTO.getId()))
    		.thenReturn(Optional.empty());   
    	
    	// Then
    	assertThrows(AuthorNotFoundExcpetion.class, () -> authorService.findById(expectedFoundAuthorDTO.getId()));

    }
    
    @Test
    @DisplayName(" ")
    void whenListAuthorsIsCalledThenItShouldBeReturnd() {
    	// given
    	AuthorDTO expectedFoundAuthorDTO = authorDTOBuilder.buildAuthorDTO();
    	Author expectedFoundAuthor = authorMapper.toModel(expectedFoundAuthorDTO);
    	    	
    	// when
    	when(authorRepository.findAll())
    		.thenReturn(Collections.singletonList(expectedFoundAuthor));   
    	
    	List<AuthorDTO> foundAuthorsDTO = authorService.findAll();
    	
    	// Then
    	assertThat(foundAuthorsDTO.size(), is(1));
    	assertThat(foundAuthorsDTO.get(0), is(equalTo(expectedFoundAuthorDTO)));
    }
    
    @Test
    @DisplayName(" ")
    void whenListAuthorsIsCalledThenAnEmptyListShouldBeReturnd() {	
    	// when
    	when(authorRepository.findAll())
    		.thenReturn(Collections.emptyList());   
    	
    	List<AuthorDTO> foundAuthorsDTO = authorService.findAll();
    	
    	// Then
    	assertThat(foundAuthorsDTO.size(), is(0));
    }
    
    @Test
    @DisplayName(" ")
    void whenValidAuthorIdIsGivenThenItShouldBeDeleted() {	
    	// given
    	AuthorDTO expectedDeletedAuthorDTO = authorDTOBuilder.buildAuthorDTO();
    	Author expectedDeletedAuthor = authorMapper.toModel(expectedDeletedAuthorDTO);
    	    	
    	// when
    	Long expectedDeletedAuthorId = expectedDeletedAuthorDTO.getId();
    	doNothing().when(authorRepository).deleteById(expectedDeletedAuthorId);
    	when(authorRepository.findById(expectedDeletedAuthorId)).thenReturn(Optional.of(expectedDeletedAuthor));
    	
    	authorService.delete(expectedDeletedAuthorId);
    	// Then
    	verify(authorRepository, times(1)).deleteById(expectedDeletedAuthorId);
    	verify(authorRepository, times(1)).findById(expectedDeletedAuthorId);
    }
 
    @Test
    @DisplayName(" ")
    void whenInvalidAuthorIdIsGivenThenAnExceptionShouldBeThrown() {	
    	// given
    	var expectedInvalidAuthorId = 2L;
    	
    	// when    	
    	when(authorRepository.findById(expectedInvalidAuthorId)).thenReturn(Optional.empty());
    	
    	// Then
    	assertThrows(AuthorNotFoundExcpetion.class, () -> authorService.delete(expectedInvalidAuthorId));
    }
}