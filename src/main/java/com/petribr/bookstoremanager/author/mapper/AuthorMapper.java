package com.petribr.bookstoremanager.author.mapper;

import com.petribr.bookstoremanager.author.dto.AuthorDTO;
import com.petribr.bookstoremanager.author.entity.Author;
 
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
 
@Mapper(componentModel = "spring")
public abstract class AuthorMapper {
 
    public static final AuthorMapper INSTANCE = Mappers.getMapper(AuthorMapper.class);
 
    public abstract Author toModel(AuthorDTO authorDTO);
 
    public abstract AuthorDTO toDTO(Author author);
 
}
/*@Mapper
public interface AuthorMapper {
	
	AuthorMapper INSTANCE = Mappers.getMapper(AuthorMapper.class);
	
	Author toModel(AuthorDTO authorDTO);
	
	AuthorDTO toDTO(Author author);
}*/
