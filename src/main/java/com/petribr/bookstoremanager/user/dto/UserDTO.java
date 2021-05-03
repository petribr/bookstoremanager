package com.petribr.bookstoremanager.user.dto;

import java.time.LocalDate;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.petribr.bookstoremanager.user.entity.Gender;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Utilizado apenas para a entrada de dados
 * @author romulo.birschner
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
	private Long id;
	
	@NotNull
	@NotEmpty
	@Size(max = 255)
	private String name;
	
	@NotNull
	@Max(120)
	private int age;	
	
	@Enumerated(EnumType.STRING)
	@NotNull
	private Gender gender;	
	
	@NotNull
	@NotEmpty
	@Email
	private String email;
	
	@NotNull
	@NotEmpty
	@Size(max = 100)
	private String userName;
	
	@NotNull
	@NotEmpty
	private String password;
	
	@NotNull
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")	
	private LocalDate birthDate;
}
