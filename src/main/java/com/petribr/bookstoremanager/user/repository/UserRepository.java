package com.petribr.bookstoremanager.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.petribr.bookstoremanager.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
