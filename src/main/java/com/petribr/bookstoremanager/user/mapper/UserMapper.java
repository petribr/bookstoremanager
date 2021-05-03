package com.petribr.bookstoremanager.user.mapper;

import com.petribr.bookstoremanager.user.dto.UserDTO;
import com.petribr.bookstoremanager.user.entity.User;
 
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
 
@Mapper(componentModel = "spring")
public abstract class UserMapper {
 
    public static final UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
 
    public abstract User toModel(UserDTO userDTO);
 
    public abstract UserDTO toDTO(User user);
 
}