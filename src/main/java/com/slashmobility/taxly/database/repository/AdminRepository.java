package com.slashmobility.taxly.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.slashmobility.taxly.database.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long>{

	Admin findByEmail(String email);

	boolean existsByEmail(String email);

}
