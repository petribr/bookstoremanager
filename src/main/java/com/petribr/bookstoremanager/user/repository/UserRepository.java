package com.petribr.bookstoremanager.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.petribr.bookstoremanager.publisher.entity.Publisher;
import com.petribr.bookstoremanager.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByUserNameOrEmail(String username, String email);
}
