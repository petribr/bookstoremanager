package com.petribr.bookstoremanager.book.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.petribr.bookstoremanager.author.entity.Author;
import com.petribr.bookstoremanager.entity.Auditable;
import com.petribr.bookstoremanager.publisher.entity.Publisher;
import com.petribr.bookstoremanager.user.entity.User;

import lombok.Data;

@Entity
@Data
public class Book extends Auditable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 100)
	private String name;
	
	@Column(nullable = false, unique = true)
	private String isbn;	
	
	@Column(columnDefinition = "integer default 0")
	private int pages;	

	@Column(columnDefinition = "integer default 0")
	private int chapters;
	
	@ManyToOne(cascade = {CascadeType.MERGE})
	private Author author;
	
	@ManyToOne(cascade = {CascadeType.MERGE})
	private Publisher publisher;
	
	@ManyToOne(cascade = {CascadeType.MERGE})
	private User user;
}
