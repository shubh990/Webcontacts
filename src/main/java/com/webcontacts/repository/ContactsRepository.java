package com.webcontacts.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import com.webcontacts.entities.Contact;
import com.webcontacts.entities.User;

public interface ContactsRepository extends JpaRepository<Contact,Integer> {
	
	public Page<Contact> findByUser(User user, Pageable pageRequest);

}
