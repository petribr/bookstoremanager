package com.petribr.bookstoremanager.author.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.petribr.bookstoremanager.book.entity.Book;
import com.petribr.bookstoremanager.entity.Auditable;

import lombok.Data;

@Entity
@Data
@Table(name = "Authores")
public class Author extends Auditable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String name;
	
	@Column(columnDefinition = "integer default 0")
	private int age;
	
	@OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
	private List<Book> books;
}
