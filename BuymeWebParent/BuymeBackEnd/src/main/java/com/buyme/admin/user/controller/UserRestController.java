package com.buyme.admin.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.buyme.admin.user.UserService;

@RestController
public class UserRestController {
	@Autowired
	private UserService service;
	
	//VERIFICAR SI EL EMAIL YA EXISTE
	@PostMapping("/users/check_email")
	public String checkDuplicateEmail(Integer id, String email) {
		return service.isEmailUnique(id, email) ? "OK" : "Duplicated";
	}
}
