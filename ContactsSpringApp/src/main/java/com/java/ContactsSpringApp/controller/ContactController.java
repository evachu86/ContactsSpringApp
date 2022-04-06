/* 
 * Name: Yi-Wen Chu    991624614
 * Assignment: Assignment 3 
 * Program: Computer Systems Technology -
 * 	Software Development and Network Engineering
 * File: ContactController.java
 * Other Files in this Project: 
 * 	index.html
 * 	detail.html
 * 	del.html
 * 
 * Main class: ContactsSpringAppApplication.java
 * 
 * Date: Nov 28, 2021
 * 
 * Description: Use the Spring Framework 
 * to build a application for simple contact function.
 */
package com.java.ContactsSpringApp.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.java.ContactsSpringApp.dao.ContactDao;
import com.java.ContactsSpringApp.model.Contact;

/**
 * The Class ContactController.
 *
 * @author Yi-Wen Chu
 * Computer Systems Technology
 * Software Development and Network Engineering
 */
@Controller
public class ContactController {

	/** The dao. */
	@Autowired
	ContactDao dao;
	
	/**
	 * Index.
	 *
	 * @param model the model
	 * @return the string
	 */
	@RequestMapping("/")
	public String index(Model model) {
		
		model.addAttribute("contacts", dao.query(null));
		return "index";
	}
	
	/**
	 * Adds the contact.
	 *
	 * @param model the model
	 * @return the string
	 */
	@PostMapping("/addContact")
	public String add(Model model) {
		
		model.addAttribute("contact", new Contact());
		return "detail";
	}
	
	/**
	 * Edits the contact.
	 *
	 * @param req the req
	 * @param contact the contact
	 * @param model the model
	 * @return the string
	 */
	@PostMapping(value="/editContact")
	public String editContact(HttpServletRequest req, @ModelAttribute Contact contact, Model model) {
		
		String view = "redirect:/";
		String editBtn = req.getParameter("editBtn");
		
		List<Contact> contacts = dao.query(contact);
		model.addAttribute("contact", contacts.get(0));
		
		if(!contacts.isEmpty()) {
			switch(editBtn) {
				case "update": view = "detail";
					break;
				case "drop": view = "del";
					break;
			}
		}
		
		return view;
	}
	
	/**
	 * Cancel.
	 *
	 * @return the string
	 */
	@RequestMapping(value={"/deleteContact", "/saveConatct"}, params={"actBtn=Cancel"})
	public String cancel() {

		return "redirect:/";
	}
	
	/**
	 * Save.
	 *
	 * @param contact the contact
	 * @return the string
	 */
	@PostMapping("/saveConatct")
	public String save(@ModelAttribute Contact contact) {

		if(contact.getId() != null) {
			dao.updateContact(contact);
		} else {
			dao.addContact(contact);
		}
		
		return "redirect:/";
	}
	
	/**
	 * Delete.
	 *
	 * @param contact the contact
	 * @return the string
	 */
	@PostMapping("/deleteContact")
	public String delete(@ModelAttribute Contact contact) {

		dao.deleteContact(contact);
		return "redirect:/";
	}
}
