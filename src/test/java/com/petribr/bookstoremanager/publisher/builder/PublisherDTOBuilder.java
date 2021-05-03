package com.petribr.bookstoremanager.publisher.builder;

import java.time.LocalDate;

import com.petribr.bookstoremanager.publisher.dto.PublisherDTO;

import lombok.Builder;

@Builder
public class PublisherDTOBuilder {
    
    @Builder.Default
	private final Long id=2L;
    
    @Builder.Default
	private final String name="Oswaldo Cruz";
    
    @Builder.Default
	private final String code="OSWA2020";
    
    @Builder.Default
	private final LocalDate foundationDate=LocalDate.of(2020, 8, 11);
        
    public PublisherDTO buildPublisherDTO() {
        return new PublisherDTO(id, name, code, foundationDate);
    }
}
