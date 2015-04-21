package com.packt.bookstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController 
{
///////////////////////
	@RequestMapping("/")
	public String welcome(Model model)
	{
		model.addAttribute("greeting","The Book Depot");
		
		model.addAttribute("entryLink", "Click to Enter");
		
		return "welcome";
		//return "forward:/welcome/greeting";
	}
	
	@RequestMapping("/welcome/greeting")
	public String greeting() 
	{
		return "welcome";
	}
	//////////////////////////////
}
