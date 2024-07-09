package com.webcontacts.controller;

import java.security.Principal;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.webcontacts.entities.Contact;
import com.webcontacts.entities.User;
import com.webcontacts.repository.ContactsRepository;
import com.webcontacts.repository.UserRepository;

@Controller
@RequestMapping("/user/**")
public class UserController {

	@Autowired
	UserRepository repo;
	
	@Autowired
	ContactsRepository conrepo;
	
	Contact updatecontact;
	
	@ModelAttribute
	public void modelMethod(Model model, Principal principal ) {
		User user = repo.findByEmail(principal.getName());
		model.addAttribute("user", user);
	}

	@RequestMapping("/index")
	public String dashboard(Model model, Principal principal) {
		return "/user/index";
	}
	
	@RequestMapping("/addcontacts")
	public String addContacts(Model model) {
		model.addAttribute("contacts", new Contact());
		return "/user/addcontacts";
	}
	
	@RequestMapping("/addcontacts-saved")
	public String addContactsSaved(@ModelAttribute("contacts") Contact contacts, Principal principal) {
		
		User user = repo.findByEmail(principal.getName());
		
		contacts.setUser(user);
		
		user.getContacts().add(contacts);
		
		repo.save(user);
		
		System.out.println(contacts);
		
		return "/user/addcontacts";
	}
	
	
	@GetMapping("/showcontacts/{page}")
	public String showContacts(Principal principal, Model model, @PathVariable("page") int page) {
		
		User user = repo.findByEmail(principal.getName());
		
		Pageable pageRequest = PageRequest.of(page, 2);
		
		Page<Contact> listofContacts = conrepo.findByUser(user, pageRequest);
		
		model.addAttribute("contacts", listofContacts);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPage", listofContacts.getTotalPages());
		
		System.out.println(listofContacts);
		return "/user/showcontacts";
	}
	
	@PostMapping("/delete/{page}/{cid}")
	public String delete(@PathVariable("cid") int cid,@PathVariable("page") int page, Model model) {
		
		Optional<Contact> contactOpt = conrepo.findById(cid);
		Contact contact = contactOpt.get();
		
		conrepo.deleteById(contact.getCid());
		
		return "redirect:/user/showcontacts/{page}";
		
	}
	
	@PostMapping("/update/{cid}")
	public String update(@PathVariable("cid") int cid, Model model)
	{
		
		Optional<Contact> contactOpional = conrepo.findById(cid);
		updatecontact = contactOpional.get();
		model.addAttribute("contacts",updatecontact);
		return "user/update";
	}
	
	@PostMapping("/updated")
	public String updated(@ModelAttribute("contacts") Contact contacts) {
		
		contacts.setCid(updatecontact.getCid());
		contacts.setUser(updatecontact.getUser());
		System.out.println(contacts);
		conrepo.save(contacts);
		
		return "redirect:/user/showcontacts/0";
		
		
		
	}

	

}
