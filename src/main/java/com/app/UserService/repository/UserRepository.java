package com.app.UserService.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.UserService.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{
	
	Optional<User> findByUserEmail(String email);
}
