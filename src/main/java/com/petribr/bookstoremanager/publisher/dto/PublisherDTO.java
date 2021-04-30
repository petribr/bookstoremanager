package com.petribr.bookstoremanager.publisher.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.petribr.bookstoremanager.entity.Auditable;
import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublisherDTO {
	
	private Long id;
	
	@NotNull
	@NotEmpty
	@Size(max = 255)
	private String name;
	
	@NotNull
	@Size(max = 50)
	private String code;	
	
	@NotNull
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private LocalDate foundationDate;
}
