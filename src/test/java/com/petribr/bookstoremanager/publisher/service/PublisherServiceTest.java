package com.petribr.bookstoremanager.publisher.service;


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

import com.petribr.bookstoremanager.publisher.builder.PublisherDTOBuilder;
import com.petribr.bookstoremanager.publisher.dto.PublisherDTO;
import com.petribr.bookstoremanager.publisher.entity.Publisher;
import com.petribr.bookstoremanager.publisher.exception.PublisherAlreadyExistsException;
import com.petribr.bookstoremanager.publisher.mapper.PublisherMapper;
import com.petribr.bookstoremanager.publisher.repository.PublisherRepository;

@ExtendWith(MockitoExtension.class)
class PublisherServiceTest {

    private final PublisherMapper publisherMapper = PublisherMapper.INSTANCE;

    @Mock
    private PublisherRepository publisherRepository;

    @InjectMocks
    private PublisherService publisherService;

    private PublisherDTOBuilder publisherDTOBuilder;

    @BeforeEach
    void setUp() {
    	publisherDTOBuilder = PublisherDTOBuilder.builder().build();
    }

    @Test
    @DisplayName("When new publisher is informed then it should be created")
    void whenNewPublisherIsInformedThenItShouldBeCreated() {
        // given
        PublisherDTO expectedPublisherToCreateDTO = publisherDTOBuilder.buildPublisherDTO();
        Publisher expectedCreatedPublisher = publisherMapper.toModel(expectedPublisherToCreateDTO);
        // when
        when(publisherRepository.save(any(Publisher.class))).thenReturn(expectedCreatedPublisher);
        when(publisherRepository.findByNameOrCode(expectedPublisherToCreateDTO.getName(), expectedPublisherToCreateDTO.getCode())).thenReturn(Optional.empty());

        PublisherDTO createdPublisherDTO = publisherService.create(expectedPublisherToCreateDTO);
        // then
        assertThat(createdPublisherDTO, is(equalTo(expectedPublisherToCreateDTO)));
    }
    
    @Test
    @DisplayName("When existing publisher is informed then an exception should be thrown")
    void whenExistingPublisherIsInformedThenAnExcpetionShouldBeThrown() {
    	// given
    	PublisherDTO expectedPublisherToCreateDTO = publisherDTOBuilder.buildPublisherDTO();
    	Publisher expectedCreatedPublisher = publisherMapper.toModel(expectedPublisherToCreateDTO);
    	// when
    	when(publisherRepository.findByNameOrCode(expectedPublisherToCreateDTO.getName(), expectedPublisherToCreateDTO.getCode()))
    		.thenReturn(Optional.of(expectedCreatedPublisher));
    	
    	Assertions.assertThrows(PublisherAlreadyExistsException.class, 
    			() -> publisherService.create(expectedPublisherToCreateDTO));
    }
    
    @Test
    @DisplayName(" ")
    void whenValidIdIsGivenThenAnPublisherShouldBeReturnd() {
    	// given
    	PublisherDTO expectedFoundPublisherDTO = publisherDTOBuilder.buildPublisherDTO();
    	Publisher expectedFoundPublisher = publisherMapper.toModel(expectedFoundPublisherDTO);
    	
    	// when
    	Long id = expectedFoundPublisherDTO.getId();
		when(publisherRepository.findById(id))
    		.thenReturn(Optional.of(expectedFoundPublisher));    	
    	PublisherDTO foundPublisherDTO = publisherService.findById(id);
    	// Then
    	assertThat(foundPublisherDTO, is(equalTo(expectedFoundPublisherDTO)));
    }
    
    @Test
    @DisplayName(" ")
    void whenInvalidIdIsGivenThenAnExcpetionShouldBeThrown() {
    	// given
    	PublisherDTO expectedFoundPublisherDTO = publisherDTOBuilder.buildPublisherDTO();
    	
    	// when
    	Long id = expectedFoundPublisherDTO.getId();
		when(publisherRepository.findById(id))
    		.thenReturn(Optional.empty());   
    	
    	// Then
    	assertThrows(PublisherNotFoundException.class, () -> publisherService.findById(id));

    }
    
    @Test
    @DisplayName(" ")
    void whenListPublishersIsCalledThenItShouldBeReturnd() {
    	// given
    	PublisherDTO expectedFoundPublisherDTO = publisherDTOBuilder.buildPublisherDTO();
    	Publisher expectedFoundPublisher = publisherMapper.toModel(expectedFoundPublisherDTO);
    	    	
    	// when
    	when(publisherRepository.findAll())
    		.thenReturn(Collections.singletonList(expectedFoundPublisher));   
    	
    	List<PublisherDTO> foundPublishersDTO = publisherService.findAll();
    	
    	// Then
    	assertThat(foundPublishersDTO.size(), is(1));
    	assertThat(foundPublishersDTO.get(0), is(equalTo(expectedFoundPublisherDTO)));
    }
    
    @Test
    @DisplayName(" ")
    void whenListPublishersIsCalledThenAnEmptyListShouldBeReturnd() {	
    	// when
    	when(publisherRepository.findAll())
    		.thenReturn(Collections.emptyList());   
    	
    	List<PublisherDTO> foundPublishersDTO = publisherService.findAll();
    	
    	// Then
    	assertThat(foundPublishersDTO.size(), is(0));
    }
    
    @Test
    @DisplayName(" ")
    void whenValidPublisherIdIsGivenThenItShouldBeDeleted() {	
    	// given
    	PublisherDTO expectedDeletedPublisherDTO = publisherDTOBuilder.buildPublisherDTO();
    	Publisher expectedDeletedPublisher = publisherMapper.toModel(expectedDeletedPublisherDTO);
    	    	
    	// when
    	Long expectedDeletedPublisherId = expectedDeletedPublisherDTO.getId();
    	doNothing().when(publisherRepository).deleteById(expectedDeletedPublisherId);
    	when(publisherRepository.findById(expectedDeletedPublisherId)).thenReturn(Optional.of(expectedDeletedPublisher));
    	
    	publisherService.delete(expectedDeletedPublisherId);
    	// Then
    	verify(publisherRepository, times(1)).deleteById(expectedDeletedPublisherId);
    	verify(publisherRepository, times(1)).findById(expectedDeletedPublisherId);
    }
 
    @Test
    @DisplayName(" ")
    void whenInvalidPublisherIdIsGivenThenAnExceptionShouldBeThrown() {	
    	// given
    	var expectedInvalidPublisherId = 2L;
    	
    	// when    	
    	when(publisherRepository.findById(expectedInvalidPublisherId)).thenReturn(Optional.empty());
    	
    	// Then
    	assertThrows(PublisherNotFoundException.class, () -> publisherService.delete(expectedInvalidPublisherId));
    }
}