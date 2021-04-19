package com.petribr.bookstoremanager.author.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.petribr.bookstoremanager.author.entity.Author;

public interface AuthorRepository extends JpaRepository<Author, Long>{

}
