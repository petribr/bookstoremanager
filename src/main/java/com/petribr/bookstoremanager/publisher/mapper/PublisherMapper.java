package com.petribr.bookstoremanager.publisher.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.petribr.bookstoremanager.publisher.dto.PublisherDTO;
import com.petribr.bookstoremanager.publisher.entity.Publisher;
 
@Mapper(componentModel = "spring")
public abstract class PublisherMapper {
 
    public static final PublisherMapper INSTANCE = Mappers.getMapper(PublisherMapper.class);
 
    public abstract Publisher toModel(PublisherDTO publisherDTO);
 
    public abstract PublisherDTO toDTO(Publisher publisher);
 
}
/*@Mapper
public interface AuthorMapper {
	
	AuthorMapper INSTANCE = Mappers.getMapper(AuthorMapper.class);
	
	Author toModel(AuthorDTO authorDTO);
	
	AuthorDTO toDTO(Author author);
}*/
