package com.packt.webstore.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.packt.webstore.domain.Product;
import com.packt.webstore.exception.NoProductsFoundUnderCategoryException;
import com.packt.webstore.exception.ProductNotFoundException;
import com.packt.webstore.service.ProductService;

@Controller
@RequestMapping("/products")
public class ProductController {
	
	@Autowired
	private ProductService productService;

	@RequestMapping
	public String list(Model model) {
		model.addAttribute("products", productService.getAllProducts());
		return "products";
	}
	
	@RequestMapping("/all")
	public ModelAndView allProducts() {
		ModelAndView modelAndView = new ModelAndView();
		
		modelAndView.addObject("products", productService.getAllProducts());
		modelAndView.setViewName("products");
		return modelAndView;
	}
	
	@RequestMapping("/{category}")
	public String getProductsByCategory(Model model, @PathVariable("category") String category) {
		List<Product> products = productService.getProductsByCategory(category);

		if (products == null || products.isEmpty()) {
			throw new NoProductsFoundUnderCategoryException();
		}

		model.addAttribute("products", products);
		return "products";
	}

	
	@RequestMapping("/filter/{ByCriteria}")
	public String getProductsByFilter(@MatrixVariable(pathVar="ByCriteria") Map<String,List<String>> filterParams, Model model) {
		model.addAttribute("products", productService.getProductsByFilter(filterParams));
		return "products";
	}
	
	@RequestMapping("/product")
	public String getProductById(Model model, @RequestParam("id") String productId) {
		Product product = productService.getProductById(productId);
		model.addAttribute("product", product);
		return "product";
	}

	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String getAddNewProductForm(@ModelAttribute("newProduct") Product newProduct) {
	   return "addProduct";
	}
	   
	
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String processAddNewProductForm(@ModelAttribute("newProduct") Product productToBeAdded, ModelMap map, BindingResult result, HttpServletRequest request) {
		String[] suppressedFields = result.getSuppressedFields();
		
		if (suppressedFields.length > 0) 
		{
			throw new RuntimeException("Attempting to bind disallowed fields: " + StringUtils.arrayToCommaDelimitedString(suppressedFields));
		}
		
		
		MultipartFile productImage = productToBeAdded.getProductImage();
		String rootDirectory = request.getSession().getServletContext().getRealPath("/");
				
			if (productImage!=null && !productImage.isEmpty()) 
			{
		       try 
		       {
		      	productImage.transferTo(new File(rootDirectory+"resources\\images\\"+productToBeAdded.getProductId() + ".png"));
		       } 
		       catch (Exception e) 
		       {
				throw new RuntimeException("Product Image saving failed", e);
		       }
		   }
			
			
			/////////////PRODUCT MANUAL THING/////////////////////////////////////////////
			MultipartFile productMan = productToBeAdded.getProductMan();
			String rootDirectory2 = request.getSession().getServletContext().getRealPath("/");
					
				
			
			if (productMan!=null && !productMan.isEmpty()) 
				{
			       try 
			       {
			    	   //MAC
			    	  // productMan.transferTo(new File(rootDirectory+"resources//pdf//"+productToBeAdded.getProductId() + ".pdf"));
			      	
			    	   //WINDOWS
			    	   productMan.transferTo(new File(rootDirectory+"resources\\pdf\\"+productToBeAdded.getProductId() + ".pdf"));
			       } 
			       catch (Exception e) 
			       {
					throw new RuntimeException("Product pdf saving failed", e);
			       }
			   }

		
	   	productService.addProduct(productToBeAdded);
		return "redirect:/products";
	}
	
	
	///////////PDF DOWNLOAD
	


@RequestMapping(value = "/download.do", method = RequestMethod.GET)
    public void downloadUserManual(HttpServletRequest request,HttpServletResponse response, Product product) throws IOException 
	{
        // get absolute path of the application
        ServletContext context = request.getServletContext();
        String appPath = context.getRealPath("");
        
		
        System.out.println("appPath = " + appPath);
       // System.out.println(response.getAttribute(product.getProductId()));
       // System.out.println("!!!" + request.getHeader(name)));


 
        // construct the complete absolute path of the file

        
        

        String fullPath = appPath + "\\resources\\userManuals\\P1234.pdf";              
        //MTESTING, WHY NO WORK!!!!
       // String fullPath = appPath + "//resources//pdf//"+ product.getProductId() + ".pdf";
        
        //SOMETHING TO DO WITH DATA BINDING!!!
        //Bean? REquest response, I mean the Product Id is in the url if only i could grab it form the request.....
        
        
        
        //TEST
        //String fullPath = appPath + "//resources//pdf//P1234.pdf";
        
        File downloadFile = new File(fullPath);
        FileInputStream inputStream = new FileInputStream(downloadFile);
         
        // get MIME type of the file
        String mimeType = context.getMimeType(fullPath);
        if (mimeType == null) {
            // set to binary type if MIME mapping not found
            mimeType = "application/octet-stream";
        }
        System.out.println("MIME type: " + mimeType);
 
        // set content attributes for the response
        response.setContentType(mimeType);
        response.setContentLength((int) downloadFile.length());
 
        // set headers for the response
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"",downloadFile.getName());
        response.setHeader(headerKey, headerValue);
 
        // get output stream of the response
        OutputStream outStream = response.getOutputStream();
 
        //byte[] buffer = new byte[BUFFER_SIZE];
        
        byte[] buffer = new byte[512];
        
        int bytesRead = -1;
 
        // write bytes read from the input stream into the output stream
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }
 
        inputStream.close();
        outStream.close();
    }
	
	
	
	
	
	
	
	@InitBinder
	public void initialiseBinder(WebDataBinder binder) {
		binder.setAllowedFields("productId","name","unitPrice","description","manufacturer","category","unitsInStock", "condition","productImage", "productMan");
	}
	
	@ExceptionHandler(ProductNotFoundException.class)
	public ModelAndView handleError(HttpServletRequest req, ProductNotFoundException exception) {
		 ModelAndView mav = new ModelAndView();
		 mav.addObject("invalidProductId", exception.getProductId());
		 mav.addObject("exception", exception);
		 mav.addObject("url", req.getRequestURL()+"?"+req.getQueryString());
		 mav.setViewName("productNotFound");
		 return mav;
	}


}
