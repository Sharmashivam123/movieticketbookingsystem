package com.shivamstack.authenticationservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class hellocontroller {

	@GetMapping("/hello")
	public String hello() {
		System.out.println("Insisde hello controller");
		return "hello";
	}
	
	@GetMapping("/home")
	public String home() {
		return "home";
	}
}
