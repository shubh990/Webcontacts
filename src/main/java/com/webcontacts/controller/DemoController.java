package com.webcontacts.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.webcontacts.entities.User;
import com.webcontacts.repository.UserRepository;

@Controller
public class DemoController {
	
	@Autowired
	UserRepository userrepo;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@GetMapping("/")
	public String home() {
		return "home";

	}
	
	@GetMapping("/signup")
	public String signup(Model mod) {
		mod.addAttribute("user", new User());
		return "signup";

	}
	
	@PostMapping("/home")
	public String homesignup(@Valid @ModelAttribute("user") User user,
			BindingResult result,Model model) {
		
		if(result.hasErrors()) {
			
			model.addAttribute("user", user);
			System.out.println(result);
			return "signup";
		}
			
		
		user.setRoll("ROLE_USER");
		user.setEnabled(true);
		user.setImageUrl("default.png");
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userrepo.save(user);
		
		
		return "home";

	}
	
	@RequestMapping("/login")
	public String login() {
		return "login";
	}
	
}
