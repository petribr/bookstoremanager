package com.petribr.bookstoremanager.publisher.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.petribr.bookstoremanager.publisher.entity.Publisher;

public interface PublisherRepository extends JpaRepository<Publisher, Long>{

	Optional<Publisher> findByNameOrCode(String name, String code);

}
