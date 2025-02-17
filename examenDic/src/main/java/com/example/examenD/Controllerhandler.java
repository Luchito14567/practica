package com.example.examenD;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;



@Controller

public class Controllerhandler {



	@GetMapping("/")

	public String ShowchatPage() {

		return "cuenta";

		

		

	}

	

	

}