package com.petribr.bookstoremanager.publisher.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.petribr.bookstoremanager.publisher.entity.Publisher;

public interface PublisherRepository extends JpaRepository<Publisher, Long>{

}
