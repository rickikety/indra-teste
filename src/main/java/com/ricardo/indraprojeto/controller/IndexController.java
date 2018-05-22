package com.ricardo.indraprojeto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
	
	@RequestMapping("/ricardo")
	public String index() {
		//return "index";
		return "redirect:/produtos";
	}
	
	@RequestMapping("/")
	public String Index() {
		return "redirect:/produtos";
	}
}
