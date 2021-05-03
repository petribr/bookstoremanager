package com.petribr.bookstoremanager.user.builder;

import java.time.LocalDate;

import com.petribr.bookstoremanager.user.dto.UserDTO;
import com.petribr.bookstoremanager.user.entity.Gender;

import lombok.Builder;

@Builder
public class UserDTOBuilder {
    
    @Builder.Default
	private final Long id=2L;
    
    @Builder.Default
	private String name="Elias Pignaton";
	
    @Builder.Default
	private int age = 69;	
	
    @Builder.Default
	private Gender gender = Gender.MALE;	
	
    @Builder.Default
	private String email = "elias.tester@gmail.com";
	
    @Builder.Default
	private String userName = "eliaspig";
	
    @Builder.Default
	private String password = "123456";
	
    @Builder.Default
	private LocalDate birthDate = LocalDate.of(1955, 1, 1);
    
    public UserDTO buildUserDTO() {
        return new UserDTO(id, name, age, gender, email, userName, password, birthDate);
    }
}
