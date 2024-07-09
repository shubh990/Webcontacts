package com.webcontacts.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.webcontacts.entities.User;
import com.webcontacts.repository.UserRepository;

public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	UserRepository userrepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userrepo.findByEmail(username);
		if(user==null)
			throw new UsernameNotFoundException(username);
		CustomUserDetails customuserdetails = new CustomUserDetails(user);
		return customuserdetails;
	}

}
