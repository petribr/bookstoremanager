package com.petribr.bookstoremanager.user.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.petribr.bookstoremanager.book.entity.Book;
import com.petribr.bookstoremanager.entity.Auditable;

import lombok.Data;

@Data
@Entity
public class User extends Auditable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private int age;	
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private Gender gender;	
	
	@Column(nullable = false, unique = true, length = 100)
	private String email;
	
	@Column(nullable = false, unique = true, length = 100)
	private String userName;
	
	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false, columnDefinition = "TIMESTAMP")
	private LocalDate birthDate;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<Book> books;
}
