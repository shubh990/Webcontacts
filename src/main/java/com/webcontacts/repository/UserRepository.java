package com.webcontacts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.webcontacts.entities.User;

@Component
public interface UserRepository extends JpaRepository<User,Integer > {
	
	public User findByEmail(String email);
	

}
