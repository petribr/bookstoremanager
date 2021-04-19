package com.petribr.bookstoremanager.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.petribr.bookstoremanager.book.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long>{

}
