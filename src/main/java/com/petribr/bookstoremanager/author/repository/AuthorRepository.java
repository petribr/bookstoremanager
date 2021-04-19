package com.petribr.bookstoremanager.author.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.petribr.bookstoremanager.author.entity.Author;

public interface AuthorRepository extends JpaRepository<Author, Long>{
    Optional<Author> findByName(String name);

}
