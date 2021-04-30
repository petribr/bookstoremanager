package com.petribr.bookstoremanager.publisher.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.petribr.bookstoremanager.publisher.dto.PublisherDTO;
import com.petribr.bookstoremanager.publisher.entity.Publisher;
import com.petribr.bookstoremanager.publisher.exception.PublisherAlreadyExistsException;
import com.petribr.bookstoremanager.publisher.mapper.PublisherMapper;
import com.petribr.bookstoremanager.publisher.repository.PublisherRepository;

@Service
public class PublisherService {

	private static final PublisherMapper publisherMapper = PublisherMapper.INSTANCE;

	private PublisherRepository publisherRepository;

	@Autowired
	public PublisherService(PublisherRepository publisherRepository) {
		this.publisherRepository = publisherRepository;
	}

	public PublisherDTO create(PublisherDTO publisherDTO) throws PublisherAlreadyExistsException {
		verifyIfExists(publisherDTO.getName(), publisherDTO.getCode());
		
		Publisher publisherToCreate = publisherMapper.toModel(publisherDTO);
		var createdpublisher = publisherRepository.save(publisherToCreate);
		
		return publisherMapper.toDTO(createdpublisher);
	}

	public PublisherDTO findById(Long id) {
		Publisher foundPublisher = verifyAndGetpublisher(id);
		
		return publisherMapper.toDTO(foundPublisher);
	}

	private Publisher verifyAndGetpublisher(Long id) {
		return publisherRepository.findById(id)
			.orElseThrow(() -> new PublisherNotFoundException(id));
	}
	
	public List<PublisherDTO> findAll() {
		
		return publisherRepository.findAll()
				.stream().map(publisherMapper::toDTO)
				.collect(Collectors.toList());
	}
	
	private void verifyIfExists(String name, String code) {
		publisherRepository.findByNameOrCode(name, code)
        .ifPresent(publisher -> {throw new PublisherAlreadyExistsException(name, code);});
	}

	public void delete(Long id) {
		verifyAndGetpublisher(id);
		publisherRepository.deleteById(id);
	}
}
