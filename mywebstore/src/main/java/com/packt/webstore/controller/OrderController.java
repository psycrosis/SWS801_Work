package com.packt.webstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.packt.webstore.service.OrderService;

@Controller
public class OrderController {
	
	@Autowired
	private OrderService orderService;

	@RequestMapping("/order/{productID}")
	public String receiveOrder(@PathVariable("productID") String productID, Model model) {
		model.addAttribute("theProduct", productID);
		
		return "order";
	}
	
	@RequestMapping("/order/{productID}/{quantity}") // P1234/2
	public String process(@PathVariable("productID") String productID,
			@PathVariable("quantity") String thisquantity) {
		orderService.processOrder(productID, Integer.parseInt(thisquantity));
		return "redirect:/products";
	}
}
