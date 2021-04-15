package com.slashmobility.taxly.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.slashmobility.taxly.database.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{

	User findByEmail(String email);

	boolean existsByEmail(String email);

}
