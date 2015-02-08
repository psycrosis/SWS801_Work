package com.packt.webstore.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.packt.webstore.domain.Product;
import com.packt.webstore.service.ProductService;

@Controller
@RequestMapping("/products")
public class ProductController {
	
	@Autowired
	private ProductService productService;

	/*@RequestMapping
	public String list(Model model) {
		model.addAttribute("products", productService.getAllProducts());
		return "products";
	}*/
	
	@RequestMapping("/all")
	public ModelAndView allProducts() {
		ModelAndView modelAndView = new ModelAndView();
		
		modelAndView.addObject("products", productService.getAllProducts());
		modelAndView.setViewName("products");
		return modelAndView;
	}
	
	@RequestMapping("/{category}")
	public String getProductsByCategory(@PathVariable("category") String productCategory, Model model) {
		model.addAttribute("products", productService.getProductsByCategory(productCategory));
		return "products";
	}
	
	@RequestMapping("/filter/{ByCriteria}")
	public String getProductsByFilter(@MatrixVariable(pathVar="ByCriteria") Map<String,List<String>> filterParams, Model model) {
		model.addAttribute("products", productService.getProductsByFilter(filterParams));
		return "products";
	}
	
	@RequestMapping("/product")
	public String getProductById(@RequestParam("id") String productId, Model model) {
		model.addAttribute("product", productService.getProductById(productId));
		return "product";
	}
	
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String getAddNewProductForm(Model model)
	{
		Product newProduct = new Product();
		model.addAttribute("newProduct", newProduct);
		return "addProduct";	
	}
	
	
	
	
	// PRE data binding
//	@RequestMapping(value = "/add", method = RequestMethod.POST )
//	public String processAddNewProductForm(@ModelAttribute("newProduct") Product newProduct)
//	{
//		productService.addProduct(newProduct);
//		return "redirect:/products";
//	}
	
	//post data binding
	@RequestMapping(value = "/add", method = RequestMethod.POST )
	public String processAddNewProductForm(@ModelAttribute("newProduct") Product productToBeAdded, BindingResult result)
	{
		String[] suppresedFields = result.getSuppressedFields();
		
		if(suppresedFields.length >0 )
		{
			throw new RuntimeException("attempting to bind disallowed fields: "+ StringUtils.arrayToCommaDelimitedString(suppresedFields));
		}
		
		productService.addProduct(productToBeAdded);
		return "redirect:/products";
	}
	
	
}
